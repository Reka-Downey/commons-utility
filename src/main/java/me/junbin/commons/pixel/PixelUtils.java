package me.junbin.commons.pixel;

import me.junbin.commons.util.Args;

import java.awt.*;
import java.awt.image.ColorModel;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/29 11:13
 * @description :
 */
public abstract class PixelUtils {

    private static final int WHITE_RGB = -1;
    /**
     * 可以通过如下方法获取三原色
     * {@link ColorModel#getAlpha(int)}
     * {@link ColorModel#getRed(int)}
     * {@link ColorModel#getGreen(int)}
     * {@link ColorModel#getBlue(int)}
     */
    private static final ColorModel ARGB_MODEL = ColorModel.getRGBdefault();

    public static int getAlpha(final int rgb) {
        return rgb >>> 24 & 0XFF;
    }

    public static int getRed(final int rgb) {
        return rgb >>> 16 & 0XFF;
    }

    public static int getGreen(final int rgb) {
        return rgb >>> 8 & 0XFF;
    }

    public static int getBlue(final int rgb) {
        return rgb & 0XFF;
    }

    public static int getAlpha(final Color color) {
        Color notNullColor = Args.notNull(color);
        return notNullColor.getAlpha();
    }

    public static int getRed(final Color color) {
        Color notNullColor = Args.notNull(color);
        return notNullColor.getRed();
    }

    public static int getGreen(final Color color) {
        Color notNullColor = Args.notNull(color);
        return notNullColor.getGreen();
    }

    public static int getBlue(final Color color) {
        Color notNullColor = Args.notNull(color);
        return notNullColor.getBlue();
    }

    /**
     * 三原色灰化，alpha 通道不影响灰化效果，因此也可以不加上 255 << 24
     *
     * @param rgb rgb 三原色
     * @return 该三原色对应的灰色调三原色
     */
    public static int ashing(final int rgb) {
        //int gray = (int) (getRed(rgb) * 0.299F + getGreen(rgb) * 0.587F + getBlue(rgb) * 0.114F);
        // 使用整数进行计算效率会快一点
        int gray = (getRed(rgb) * 299 + getGreen(rgb) * 587 + getBlue(rgb) * 114 + 500) / 1000;
        //noinspection NumericOverflow
        return 255 << 24 | gray << 16 | gray << 8 | gray;
    }

    /**
     * 三原色灰化
     *
     * @param color 颜色值
     * @return 该颜色对应的灰色调颜色
     */
    public static Color ashing(final Color color) {
        Color notNullColor = Args.notNull(color);
        int gray = (notNullColor.getRed() * 299 +
                notNullColor.getGreen() * 587 +
                notNullColor.getBlue() * 114
                + 500) / 1000;
        return new Color(color.getAlpha(), gray, gray, gray);
    }

    /**
     * 三原色数组灰化
     *
     * @param rgbArr 三原色数组
     * @return 灰化之后的三原色数组
     */
    public static int[] ashing(final int[] rgbArr) {
        int[] res = new int[rgbArr.length];
        for (int i = 0, len = rgbArr.length; i < len; i++) {
            res[i] = ashing(rgbArr[i]);
        }
        return res;
    }

    /**
     * 确定三原色数组是否都是白色
     *
     * @param rgbArr 三原色数组
     * @return 如果所有三原色都是白色则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isAllWhite(final int[] rgbArr) {
        for (int rgb : rgbArr) {
            if (!isWhite(rgb)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 确定该三原色是否是白色
     *
     * @param rgb 像素三原色
     * @return 如果该像素是白色则返回 {@code true}，否则返回 {@code false}
     */
    public static boolean isWhite(final int rgb) {
        return rgb == WHITE_RGB;
    }

}
