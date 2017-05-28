package me.junbin.commons.jts;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import me.junbin.commons.charset.Charsets;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/29 12:58
 * @description :
 */
public abstract class JtsUtils {

    public static final GeometryFactory FACTORY = new GeometryFactory();
    public static final WKTReader WKT_READER = new WKTReader(FACTORY);
    private static final Charset DEFAULT_CHARSET = Charsets.UTF8;

    public static Coordinate newCoordinate(double x, double y) {
        return new Coordinate(x, y);
    }

    public static Coordinate newCoordinate(String x, String y) {
        return newCoordinate(Double.parseDouble(x), Double.parseDouble(y));
    }

    public static Point newPoint(double x, double y) {
        return FACTORY.createPoint(new Coordinate(x, y));
    }

    public static Point newPoint(String x, String y) {
        return newPoint(Double.parseDouble(x), Double.parseDouble(y));
    }

    public static LineString newLineString(Coordinate[] coordinates) {
        return FACTORY.createLineString(coordinates);
    }

    public static LineString newLineString(List<Coordinate> coordinateList) {
        Coordinate[] coordinates = new Coordinate[coordinateList.size()];
        coordinates = coordinateList.toArray(coordinates);
        return newLineString(coordinates);
    }

    /**
     * @param lineString 点集，格式为：x1 y1, x2 y2,...
     * @return 点集成线，按照点的顺序首尾相接形成一条线，该线可能是直线，也可能是曲线
     */
/*
    public static LineString newLineString(String lineString) {
        Pattern pattern = Pattern.compile("\\s*(?<x>[0-9]+?\\.?[0-9]+?)\\s+(?<y>[0-9]+?\\.?[0-9]+?)\\s*");
        String[] coordinateStrings = lineString.split(",");
        List<Coordinate> coordinateList = new ArrayList<>();
        Matcher matcher;
        for (String coordinate : coordinateStrings) {
            matcher = pattern.matcher(coordinate);
            if (matcher.matches()) {
                coordinateList.add(newCoordinate(matcher.group("x"), matcher.group("y")));
            }
        }
        return newLineString(coordinateList);
    }
*/

    /**
     * 点
     *
     * @param point 格式为：POINT (${x} ${y})
     */
    public static Point readPointByWkt(String point) throws ParseException {
        return (Point) WKT_READER.read(point);
    }

    /**
     * 点集
     *
     * @param multiPoint 格式为：MULTIPOINT (${x1} ${y1}, ${x2} ${y2}, ...)
     */
    public static MultiPoint readMultiPointByWkt(String multiPoint) throws ParseException {
        return (MultiPoint) WKT_READER.read(multiPoint);
    }

    /**
     * 线
     *
     * @param lineString 格式为：LINESTRING (${x1} ${y1}, ${x2} ${y2}, ...)
     */
    public static LineString readLineStringByWkt(String lineString) throws ParseException {
        return (LineString) WKT_READER.read(lineString);
    }

    /**
     * 线集
     *
     * @param multiLineString 格式为：MULTILINESTRING ((${x11} ${y11}, ${x12} ${y12}, ...), (${x21} ${y21}, ${x22} ${y22}, ...))
     */
    public static MultiLineString readMultiLineStringByWkt(String multiLineString) throws ParseException {
        return (MultiLineString) WKT_READER.read(multiLineString);
    }

    /**
     * 环形线
     *
     * @param linearRing 格式为：LINEARRING (${x1} ${y1}, ${x2} ${y2}, ${x3} ${y3} ..., ${x1} ${y1})
     */
    public static LinearRing readLinearRingByWkt(String linearRing) throws ParseException {
        return (LinearRing) WKT_READER.read(linearRing);
    }

    /**
     * 多边形
     * <pre>
     *     1、首尾相连，故第一个点必须与最后一个点相同
     *     2、顶点个数必须大于等于 3，即多边形必须是至少拥有三条边
     * </pre>
     *
     * @param polygon 格式为：POLYGON((${x1} ${y1}, ${x2} ${y2}, ${x3} ${y3} ..., ${x1} ${y1}))
     */
    public static Polygon readPolygonByWkt(String polygon) throws ParseException {
        return (Polygon) WKT_READER.read(polygon);
    }

    /**
     * 多边形集合
     *
     * @param multiPolygon 格式为：MULTIPOLYGON(((${x11} ${y11}, ${x12} ${y12}, ${x13} ${y13} ..., ${x11} ${y11}), (${x21} ${y21}, ${x22} ${y22}, ${x23} ${y23} ..., ${x21} ${y21})))
     */
    public static MultiPolygon readMultiPolygonByWkt(String multiPolygon) throws ParseException {
        return (MultiPolygon) WKT_READER.read(multiPolygon);
    }

/*
    public static Point readPointByWkt(Path pointFile) throws IOException, ParseException {
        return readPointByWkt(pointFile, DEFAULT_CHARSET);
    }

    public static Point readPointByWkt(Path pointFile, Charset charset) throws IOException, ParseException {
        return (Point) WKT_READER.read(Files.newBufferedReader(pointFile, charset));
    }
*/

}
