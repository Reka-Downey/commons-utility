package me.junbin.commons.jts;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/29 12:58
 * @description :
 */
public abstract class JtsUtils {

    public static final GeometryFactory FACTORY = new GeometryFactory();

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


}
