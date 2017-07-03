package me.junbin.commons.protostuff;

import com.dyuproject.protostuff.*;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.google.gson.reflect.TypeToken;
import org.objenesis.ObjenesisStd;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:junbinzhong@linkcm.com">发送邮件</a>
 * @createDate : 2017/6/29 22:08
 * @description :
 */
@Deprecated
public final class ProtostuffCollection {

    public static <T> byte[] serialize(Collection<T> collection, TypeToken<T> typeToken) {
        Schema<T> schema = RuntimeSchema.getSchema((Class<T>) typeToken.getRawType());
        CollectionSchema<T> collectionSchema = new MessageCollectionSchema<>(schema);

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] data;
        try {
            data = ProtostuffIOUtil.toByteArray(collection, collectionSchema, buffer);
        } finally {
            buffer.clear();
        }
        return data;
    }

    public static <T> List<T> deserializeAsList(byte[] data, TypeToken<T> typeToken, Class<? extends List> instance) {
        Schema<T> schema = RuntimeSchema.getSchema((Class<T>) typeToken.getRawType());
        CollectionSchema<T> collectionSchema = new MessageCollectionSchema<>(schema);
        List<T> collection = new ObjenesisStd(true).newInstance(instance);
        ProtostuffIOUtil.mergeFrom(data, collection, collectionSchema);
        return collection;
    }

    public static <T> Set<T> deserializeAsSet(byte[] data, TypeToken<T> typeToken, Class<? extends Set> instance) {
        Schema<T> schema = RuntimeSchema.getSchema((Class<T>) typeToken.getRawType());
        CollectionSchema<T> collectionSchema = new MessageCollectionSchema<>(schema);
        Set<T> collection = new ObjenesisStd(true).newInstance(instance);
        ProtostuffIOUtil.mergeFrom(data, collection, collectionSchema);
        return collection;
    }

    public static <T> Collection<T> deserialize(byte[] data, TypeToken<T> typeToken, Class<? extends Collection> instance) {
        Schema<T> schema = RuntimeSchema.getSchema((Class<T>) typeToken.getRawType());
        CollectionSchema<T> collectionSchema = new MessageCollectionSchema<>(schema);
        Collection<T> collection = new ObjenesisStd(true).newInstance(instance);
        ProtostuffIOUtil.mergeFrom(data, collection, collectionSchema);
        return collection;
    }

}
