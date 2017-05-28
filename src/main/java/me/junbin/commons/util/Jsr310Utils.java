package me.junbin.commons.util;

import org.springframework.util.Assert;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/24 13:13
 * @description :
 */
public abstract class Jsr310Utils {

    public static final DateTimeFormatter yyyyMMddHHmmssSSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    public static final DateTimeFormatter yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter yyyyMMddHH = DateTimeFormatter.ofPattern("yyyyMMddHH");
    public static final DateTimeFormatter yyyyMMdd = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter HHmmss = DateTimeFormatter.ofPattern("HHmmss");
    public static final DateTimeFormatter hyphen_yyyyMMddHHmmssSSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    public static final DateTimeFormatter hyphen_yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter slash_yyyyMMddHHmmssSSS = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
    public static final DateTimeFormatter slash_yyyyMMddHHmmss = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static final DateTimeFormatter hyphen_yyyyMMdd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter colon_HHmmss = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final ConcurrentMap<String, DateTimeFormatter> formatterCache = new ConcurrentHashMap<>();

    static {
        formatterCache.put("yyyyMMddHHmmssSSS", yyyyMMddHHmmssSSS);
        formatterCache.put("yyyyMMddHHmmss", yyyyMMddHHmmss);
        formatterCache.put("yyyyMMddHH", yyyyMMddHH);
        formatterCache.put("yyyyMMdd", yyyyMMdd);
        formatterCache.put("HHmmss", HHmmss);
        formatterCache.put("yyyy-MM-dd HH:mm:ss.SSS", hyphen_yyyyMMddHHmmssSSS);
        formatterCache.put("yyyy-MM-dd HH:mm:ss", hyphen_yyyyMMddHHmmss);
        formatterCache.put("yyyy/MM/dd HH:mm:ss.SSS", slash_yyyyMMddHHmmssSSS);
        formatterCache.put("yyyy/MM/dd HH:mm:ss", slash_yyyyMMddHHmmss);
        formatterCache.put("yyyy-MM-dd", hyphen_yyyyMMdd);
        formatterCache.put("HH:mm:ss", colon_HHmmss);
    }

    private static final ZoneId DEFAULT_ZONE_ID;
    private static final ZoneId UTC_ZONE_ID;
    private static final ZoneId SHANGHAI_ZONE_ID;
    private static final ZoneOffset DEFAULT_ZONE_OFFSET;
    private static final ZoneOffset UTC_ZONE_OFFSET;
    private static final ZoneOffset SHANGHAI_ZONE_OFFSET;
    private static final Clock DEFAULT_CLOCK;
    private static final Clock UTC_CLOCK;
    private static final Clock SHANGHAI_CLOCK;

    static {
        DEFAULT_ZONE_ID = ZoneId.systemDefault();
        UTC_ZONE_ID = ZoneId.of("UTC");
        SHANGHAI_ZONE_ID = ZoneId.of("Asia/Shanghai");

        String zoneRules = DEFAULT_ZONE_ID.getRules().toString();
        String offset = zoneRules.substring(zoneRules.lastIndexOf("=") + 1, zoneRules.length() - 1);
        DEFAULT_ZONE_OFFSET = ZoneOffset.of(offset);
        UTC_ZONE_OFFSET = ZoneOffset.UTC;
        SHANGHAI_ZONE_OFFSET = ZoneOffset.ofHours(8);

        DEFAULT_CLOCK = Clock.systemDefaultZone();
        UTC_CLOCK = Clock.systemUTC();
        SHANGHAI_CLOCK = Clock.system(SHANGHAI_ZONE_ID);
    }

    public static DateTimeFormatter getFormatter(final String pattern) {
        Assert.notNull(pattern, "The date time format must not be null");
        DateTimeFormatter formatter = formatterCache.get(pattern);
        if (formatter == null) {
            formatter = DateTimeFormatter.ofPattern(pattern);
            formatterCache.put(pattern, formatter);
        }
        return formatter;
    }

    /**
     * 获取系统默认时区
     *
     * @return 系统默认时区
     */
    public static ZoneId defaultZone() {
        return DEFAULT_ZONE_ID;
    }

    /**
     * 获取 UTC 时区
     *
     * @return UTC 时区
     */
    public static ZoneId utcZone() {
        return UTC_ZONE_ID;
    }

    /**
     * 获取上海时区
     *
     * @return 上海时区
     */
    public static ZoneId shanghaiZone() {
        return SHANGHAI_ZONE_ID;
    }

    /**
     * 获取中国时区，中国五大时区都采用 东八区
     *
     * @return 中国时区
     */
    public static ZoneId chinaZone() {
        return shanghaiZone();
    }

    /**
     * 获取系统默认时区偏移量
     *
     * @return 系统默认时区偏移量
     */
    public static ZoneOffset defaultZoneOffset() {
        return DEFAULT_ZONE_OFFSET;
    }

    /**
     * 获取 UTC 时区偏移量
     *
     * @return UTC 时区偏移量
     */
    public static ZoneOffset utcZoneOffset() {
        return UTC_ZONE_OFFSET;
    }

    /**
     * 获取上海时区偏移量
     *
     * @return 上海时区偏移量
     */
    public static ZoneOffset shanghaiZoneOffset() {
        return SHANGHAI_ZONE_OFFSET;
    }

    /**
     * 获取中国时区偏移量
     *
     * @return 中国时区偏移量
     */
    public static ZoneOffset chinaZoneOffset() {
        return shanghaiZoneOffset();
    }

    /**
     * 获取系统默认时钟
     *
     * @return 系统默认时钟
     */
    public static Clock defaultClock() {
        return DEFAULT_CLOCK;
    }

    /**
     * 获取 UTC 时钟
     *
     * @return UTC 时钟
     */
    public static Clock utcClock() {
        return UTC_CLOCK;
    }

    /**
     * 获取上海时钟
     *
     * @return 上海时钟
     */
    public static Clock shanghaiClock() {
        return SHANGHAI_CLOCK;
    }

    /**
     * 获取中国时钟
     *
     * @return 中国时钟
     */
    public static Clock chinaClock() {
        return shanghaiClock();
    }

    /**
     * 格式化时间
     *
     * @param temporal 时间对象
     * @param pattern  时间格式
     * @return 格式化字符串
     */
    public static String format(final TemporalAccessor temporal, final String pattern) {
        return getFormatter(pattern).format(temporal);
    }

    public static String format(final Date temporal, final String pattern) {
        return getFormatter(pattern).format(toZonedDateTime(temporal));
    }

    /**
     * 解析时间字符串
     *
     * @param temporal 时间字符串
     * @param pattern  时间格式
     * @param query    时间对象类型
     * @param <T>      泛型
     * @return 时间对象
     */
    public static <T> T parse(final String temporal, final String pattern, final TemporalQuery<T> query) {
        return getFormatter(pattern).parse(temporal, query);
    }

    /**
     * 解析时间字符串成 {@link LocalDateTime} 对象
     *
     * @param temporal 时间字符串
     * @param pattern  时间格式
     * @return {@link LocalDateTime} 对象
     */
    public static LocalDateTime parseDateTime(final String temporal, final String pattern) {
        return parse(temporal, pattern, LocalDateTime::from);
    }

    /**
     * 解析时间字符串成 {@link LocalDate} 对象
     *
     * @param temporal 时间字符串
     * @param pattern  时间格式
     * @return {@link LocalDate} 对象
     */
    public static LocalDate parseDate(final String temporal, final String pattern) {
        return parse(temporal, pattern, LocalDate::from);
    }

    /**
     * 解析时间字符串成 {@link LocalTime} 对象
     *
     * @param temporal 时间字符串
     * @param pattern  时间格式
     * @return {@link LocalTime} 对象
     */
    public static LocalTime parseTime(final String temporal, final String pattern) {
        return parse(temporal, pattern, LocalTime::from);
    }

    /**
     * 解析时间字符串
     *
     * @param temporal 时间字符串
     * @param pattern  时间格式
     * @return 时间对象，未确定类型
     */
    public static TemporalAccessor parse(final String temporal, final String pattern) {
        return getFormatter(pattern).parse(temporal);
    }

    /**
     * 将瞬时对象转成带时区的时间对象，其中时区为系统默认时区
     *
     * @param instant 瞬时对象
     * @return 带时区的时间对象
     */
    public static ZonedDateTime toZonedDateTime(final Instant instant) {
        return toZonedDateTime(instant, defaultZone());
    }

    /**
     * 将瞬时对象转成带时区的时间对象
     *
     * @param instant 瞬时对象
     * @param zoneId  时区限定
     * @return 带时区的时间对象
     */
    public static ZonedDateTime toZonedDateTime(final Instant instant, final ZoneId zoneId) {
        return instant.atZone(zoneId);
    }

    public static ZonedDateTime toZonedDateTime(final LocalDateTime localDateTime) {
        return toZonedDateTime(localDateTime, defaultZone());
    }

    public static ZonedDateTime toZonedDateTime(final LocalDateTime localDateTime, final ZoneId zoneId) {
        return localDateTime.atZone(zoneId);
    }

    /**
     * 将标准阳历对象转成带时区的时间对象
     *
     * @param gregorianCalendar 标准阳历
     * @return 带时区的时间对象
     */
    public static ZonedDateTime toZonedDateTime(final GregorianCalendar gregorianCalendar) {
        return gregorianCalendar.toZonedDateTime();
    }

    /**
     * 将本地日期时间对象转成标准阳历，其中时区为系统默认时区。
     * 用于执行 {@code java.sql.PreparedStatement#setTimestamp(int, Timestamp, Calendar)}
     *
     * @param localDateTime 本地日期时间
     * @return 标准阳历
     */
    public static GregorianCalendar toGregorianCalendar(final LocalDateTime localDateTime) {
        return toGregorianCalendar(localDateTime, defaultZone());
    }

    /**
     * 将本地日期时间对象转成标准阳历
     * 用于执行 {@code java.sql.PreparedStatement#setTimestamp(int, Timestamp, Calendar)}
     *
     * @param localDateTime 本地日期时间
     * @param zoneId        时区对象
     * @return 标准阳历
     */
    public static GregorianCalendar toGregorianCalendar(final LocalDateTime localDateTime, final ZoneId zoneId) {
        return toGregorianCalendar(ZonedDateTime.of(localDateTime, zoneId));
    }

    /**
     * 将带时区的日期时间对象转成标准阳历
     *
     * @param zonedDateTime 带时区的日期时间对象
     * @return 标准阳历
     */
    public static GregorianCalendar toGregorianCalendar(final ZonedDateTime zonedDateTime) {
        return GregorianCalendar.from(zonedDateTime);
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@   JSR310 <==> Date   @@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@
    public static Instant toInstant(final Date date) {
        return date.toInstant();
    }

    public static ZonedDateTime toZonedDateTime(final Date date) {
        return toZonedDateTime(date, defaultZone());
    }

    public static ZonedDateTime toZonedDateTime(final Date date, final ZoneId zoneId) {
        return toInstant(date).atZone(zoneId);
    }

    public static <T> T toLocal(final Date date, final TemporalQuery<T> query) {
        return toLocal(date, defaultZone(), query);
    }

    public static <T> T toLocal(final Date date, final ZoneId zoneId, final TemporalQuery<T> query) {
        return query.queryFrom(toZonedDateTime(date, zoneId));
    }

    public static LocalDateTime toLocalDateTime(final Date date) {
        return toLocalDateTime(date, defaultZone());
    }

    public static LocalDateTime toLocalDateTime(final Date date, final ZoneId zoneId) {
        return toLocal(date, zoneId, LocalDateTime::from);
    }

    public static LocalDate toLocalDate(final Date date) {
        return toLocalDate(date, defaultZone());
    }

    public static LocalDate toLocalDate(final Date date, final ZoneId zoneId) {
        return toLocal(date, zoneId, LocalDate::from);
    }

    public static LocalTime toLocalTime(final Date date) {
        return toLocalTime(date, defaultZone());
    }

    public static LocalTime toLocalTime(final Date date, final ZoneId zoneId) {
        return toLocal(date, zoneId, LocalTime::from);
    }

    public static Date toDate(final LocalDateTime localDateTime) {
        return toDate(toZonedDateTime(localDateTime));
    }

    public static Date toDate(final LocalDateTime localDateTime, final ZoneId zoneId) {
        return toDate(toZonedDateTime(localDateTime, zoneId));
    }

    public static Date toDate(final ZonedDateTime zonedDateTime) {
        return toDate(zonedDateTime.toInstant());
    }

    public static Date toDate(final Instant instant) {
        return Date.from(instant);
    }
    // @@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@   JSR310 <==> Date   @@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@

    // --------------------------------------------------------

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@   JSR310 <==> SqlTime   @@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // java.sql.Timestamp ==> java.time.LocalDateTime
    public static LocalDateTime toLocalDateTime(final Timestamp sqlTimestamp) {
        return toLocalDateTime(sqlTimestamp, defaultZone());
    }

    // java.sql.Timestamp ==> java.time.LocalDateTime
    public static LocalDateTime toLocalDateTime(final Timestamp sqlTimestamp, final ZoneId zoneId) {
        //return sqlTimestamp.toLocalDateTime();
        return LocalDateTime.ofInstant(sqlTimestamp.toInstant(), zoneId);
    }

    // java.time.LocalDateTime ==> java.sql.Timestamp
    public static Timestamp toSqlTimestamp(final LocalDateTime localDateTime) {
        return Timestamp.valueOf(localDateTime);
    }

    // java.sql.Date ==> java.time.LocalDate
    public static LocalDate toLocalDate(final java.sql.Date sqlDate) {
        return sqlDate.toLocalDate();
    }

    // java.time.LocalDate ==> java.sql.Date
    public static java.sql.Date toSqlDate(final LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    // java.sql.Time ==> java.time.LocalTime
    public static LocalTime toLocalTime(final Time time) {
        return time.toLocalTime();
    }

    // java.time.LocalTime ==> java.sql.Time
    public static Time toSqlTime(final LocalTime localTime) {
        return Time.valueOf(localTime);
    }
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@   JSR310 <==> SqlTime   @@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    // {@link LocalTime} 不支持时间粒度大于等于 {@link ChronoUnit#DAYS} 的计算；
    // {@link LocalDate} 不支持时间粒度小于等于 {@link ChronoUnit#HOURS} 的计算；
    // {@link LocalDateTime} 支持所有时间粒度的计算。
    // 推荐将 {@link LocalDate} 或者 {@link LocalTime} 先转成 {@link LocalDateTime} 再调用相应的 between 方法

    /**
     * 获取两个时间的差值
     *
     * @param start 起始时间
     * @param end   结束时间
     * @return 时间差
     */
    public static LocalTime durationBetween(LocalTime start, LocalTime end) {
        return LocalTime.ofNanoOfDay(Duration.between(start, end).toNanos());
    }

    /**
     * 获取两个日期之间有多少个纪元（1000000000年，暂且以中文“纪元”为10亿年的缩写）
     * 实际上：科学上有一个宇宙年为2.25亿年；印度教中有个卡巴尔为43亿2千万年
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 纪元个数
     */
    public static long eraBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.ERAS.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少个千禧年（1000年）
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 千禧年个数
     */
    public static long millenniumBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.MILLENNIA.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少个世纪（100年）
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 世纪数
     */
    public static long centuriesBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.CENTURIES.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少个十年期
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 十年期个数
     */
    public static long decadesBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.DECADES.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少年
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 年数
     */
    public static long yearsBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.YEARS.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少月
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 月数
     */
    public static long monthsBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.MONTHS.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少周
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 周数
     */
    public static long weeksBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.WEEKS.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少天
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 天数
     */
    public static long daysBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.DAYS.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少个半天（12小时）
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 半天个数
     */
    public static long halfDaysBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.HALF_DAYS.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少小时
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 小时数
     */
    public static long hoursBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.HOURS.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }


    /**
     * 获取两个日期之间有多少分钟
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 分钟数
     */
    public static long minutesBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.MINUTES.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少秒
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 秒数
     */
    public static long secondsBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.SECONDS.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少毫秒
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 毫秒数
     */
    public static long millsBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.MILLIS.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少微秒
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 微秒数
     */
    public static long microsBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.MICROS.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    /**
     * 获取两个日期之间有多少纳秒
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 纳秒数
     */
    public static long nanosBetween(Temporal start, Temporal end) {
        try {
            return ChronoUnit.NANOS.between(start, end);
        } catch (UnsupportedTemporalTypeException e) {
            return 0;
        }
    }

    public static String agoInfo(LocalDateTime ago) {
        LocalDateTime now = LocalDateTime.now();
        return agoInfo(ago, now);
    }

    public static String agoInfo(LocalDateTime start, LocalDateTime end) {
        long nanos = Duration.between(start, end).toNanos();
        long seconds = nanos / 1000_000_000L;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        long months = days / 30;
        long years = months / 12;
        long decades = years / 10;
        if (decades > 0) {
            return decades + "0年前";
        }
        if (years > 0) {
            return years + "年前";
        }
        if (months > 0) {
            return months + "月前";
        }
        if (weeks > 0) {
            return weeks + "周前";
        }
        if (days > 0) {
            return days + "天前";
        }
        if (hours > 0) {
            return hours + "小时前";
        }
        if (minutes > 0) {
            return minutes + "分钟前";
        }
        if (seconds > 0) {
            return seconds + "秒前";
        }
        return "刚刚";
    }

    public static <T> T parse(final String temporal, final TemporalQuery<T> query, final String firstPattern, String... moreCandidatePatterns) {
        try {
            return getFormatter(firstPattern).parse(temporal, query);
        } catch (DateTimeException e) {
            for (String pattern : moreCandidatePatterns) {
                try {
                    return getFormatter(pattern).parse(temporal, query);
                } catch (DateTimeException ignored) {
                }
            }
            throw new IllegalArgumentException(String.format("No pattern matches date string [%s]", temporal));
        }
    }

    public static LocalDateTime parseDateTime(final String temporal, final String pattern, String... moreCandidatePatterns) {
        return parse(temporal, LocalDateTime::from, pattern, moreCandidatePatterns);
    }

    public static LocalDate parseDate(final String temporal, final String pattern, String... moreCandidatePatterns) {
        return parse(temporal, LocalDate::from, pattern, moreCandidatePatterns);
    }

    public static LocalTime parseTime(final String temporal, final String pattern, String... moreCandidatePatterns) {
        return parse(temporal, LocalTime::from, pattern, moreCandidatePatterns);
    }

}
