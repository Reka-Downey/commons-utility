package me.junbin.commons.yaml;

import me.junbin.commons.util.Args;
import me.junbin.commons.util.StringUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.parser.ParserException;
import org.yaml.snakeyaml.reader.UnicodeReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/28 22:17
 * @description : 从 Spring 抽取而来
 */
public abstract class YamlProcessor<T> {

    private ResolutionMethod resolutionMethod = ResolutionMethod.OVERRIDE;
    private Path[] resources = new Path[0];
    private List<DocumentMatcher> documentMatchers = Collections.emptyList();
    private boolean matchDefault = true;

    public void setDocumentMatchers(DocumentMatcher... matchers) {
        this.documentMatchers = Arrays.asList(matchers);
    }

    public void setMatchDefault(boolean matchDefault) {
        this.matchDefault = matchDefault;
    }

    public void setResolutionMethod(ResolutionMethod resolutionMethod) {
        Args.notNull(resolutionMethod, "ResolutionMethod must not be null");
        this.resolutionMethod = resolutionMethod;
    }

    public void setResources(Path... resources) {
        this.resources = resources;
    }

    protected void process(MatchCallback callback) {
        Yaml yaml = createYaml();
        for (Path resource : this.resources) {
            boolean found = process(callback, yaml, resource);
            if (this.resolutionMethod == ResolutionMethod.FIRST_FOUND && found) {
                return;
            }
        }
    }

    protected Yaml createYaml() {
        return new Yaml(new StrictMapAppenderConstructor());
    }

    private boolean process(MatchCallback callback, Yaml yaml, Path resource) {
        int count = 0;
        try {
            Reader reader = new UnicodeReader(Files.newInputStream(resource));
            try {
                for (Object object : yaml.loadAll(reader)) {
                    if (object != null && process(asMap(object), callback)) {
                        count++;
                        if (this.resolutionMethod == ResolutionMethod.FIRST_FOUND) {
                            break;
                        }
                    }
                }
            } finally {
                reader.close();
            }
        } catch (IOException ex) {
            handleProcessError(resource, ex);
        }
        return (count > 0);
    }

    private void handleProcessError(Path resource, IOException ex) {
        if (this.resolutionMethod != ResolutionMethod.FIRST_FOUND &&
                this.resolutionMethod != ResolutionMethod.OVERRIDE_AND_IGNORE) {
            throw new IllegalStateException(ex);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> asMap(Object object) {
        // YAML can have numbers as keys
        Map<String, Object> result = new LinkedHashMap<>();
        if (!(object instanceof Map)) {
            // A document can be a text literal
            result.put("document", object);
            return result;
        }

        Map<Object, Object> map = (Map<Object, Object>) object;
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                value = asMap(value);
            }
            Object key = entry.getKey();
            if (key instanceof CharSequence) {
                result.put(key.toString(), value);
            } else {
                // It has to be a map key in this case
                result.put("[" + key.toString() + "]", value);
            }
        }
        return result;
    }

    private boolean process(Map<String, Object> map, MatchCallback callback) {
        Properties properties = new Properties();
        properties.putAll(getFlattenedMap(map));

        if (this.documentMatchers.isEmpty()) {
            callback.process(properties, map);
            return true;
        }

        MatchStatus result = MatchStatus.ABSTAIN;
        for (DocumentMatcher matcher : this.documentMatchers) {
            MatchStatus match = matcher.matches(properties);
            result = MatchStatus.getMostSpecific(match, result);
            if (match == MatchStatus.FOUND) {
                callback.process(properties, map);
                return true;
            }
        }

        if (result == MatchStatus.ABSTAIN && this.matchDefault) {
            callback.process(properties, map);
            return true;
        }

        return false;
    }

    protected final Map<String, Object> getFlattenedMap(Map<String, Object> source) {
        Map<String, Object> result = new LinkedHashMap<>();
        buildFlattenedMap(result, source, null);
        return result;
    }

    private void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, String path) {
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey();
            if (StringUtils.notEmpty(path)) {
                if (key.startsWith("[")) {
                    key = path + key;
                } else {
                    key = path + "." + key;
                }
            }
            Object value = entry.getValue();
            if (value instanceof String) {
                result.put(key, value);
            } else if (value instanceof Map) {
                // Need a compound key
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) value;
                buildFlattenedMap(result, map, key);
            } else if (value instanceof Collection) {
                // Need a compound key
                @SuppressWarnings("unchecked")
                Collection<Object> collection = (Collection<Object>) value;
                int count = 0;
                for (Object object : collection) {
                    buildFlattenedMap(result,
                            Collections.singletonMap("[" + (count++) + "]", object), key);
                }
            } else {
                result.put(key, value != null ? value : "");
            }
        }
    }


    /**
     * Callback interface used to process properties in a resulting map.
     */
    public interface MatchCallback {

        /**
         * Process the properties.
         *
         * @param properties the properties to process
         * @param map        a mutable result map
         */
        void process(Properties properties, Map<String, Object> map);
    }


    /**
     * Strategy interface used to test if properties match.
     */
    public interface DocumentMatcher {

        /**
         * Test if the given properties match.
         *
         * @param properties the properties to test
         * @return the status of the match
         */
        MatchStatus matches(Properties properties);
    }


    /**
     * Status returned from {@link DocumentMatcher#matches(java.util.Properties)}
     */
    public enum MatchStatus {

        /**
         * A match was found.
         */
        FOUND,

        /**
         * No match was found.
         */
        NOT_FOUND,

        /**
         * The matcher should not be considered.
         */
        ABSTAIN;

        /**
         * Compare two {@link MatchStatus} items, returning the most specific status.
         */
        public static MatchStatus getMostSpecific(MatchStatus a, MatchStatus b) {
            return (a.ordinal() < b.ordinal() ? a : b);
        }
    }


    /**
     * Method to use for resolving resources.
     */
    public enum ResolutionMethod {

        /**
         * Replace values from earlier in the list.
         */
        OVERRIDE,

        /**
         * Replace values from earlier in the list, ignoring any failures.
         */
        OVERRIDE_AND_IGNORE,

        /**
         * Take the first resource in the list that exists and use just that.
         */
        FIRST_FOUND
    }


    /**
     * A specialized {@link Constructor} that checks for duplicate keys.
     */
    protected static class StrictMapAppenderConstructor extends Constructor {

        // Declared as public for use in subclasses
        public StrictMapAppenderConstructor() {
            super();
        }

        @Override
        protected Map<Object, Object> constructMapping(MappingNode node) {
            try {
                return super.constructMapping(node);
            } catch (IllegalStateException ex) {
                throw new ParserException("while parsing MappingNode",
                        node.getStartMark(), ex.getMessage(), node.getEndMark());
            }
        }

        @Override
        protected Map<Object, Object> createDefaultMap() {
            final Map<Object, Object> delegate = super.createDefaultMap();
            return new AbstractMap<Object, Object>() {
                @Override
                public Object put(Object key, Object value) {
                    if (delegate.containsKey(key)) {
                        throw new IllegalStateException("Duplicate key: " + key);
                    }
                    return delegate.put(key, value);
                }

                @Override
                public Set<Entry<Object, Object>> entrySet() {
                    return delegate.entrySet();
                }
            };
        }
    }

    public abstract T getObject();

}
