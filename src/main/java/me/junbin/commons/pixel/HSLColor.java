package me.junbin.commons.pixel;

import me.junbin.commons.util.Args;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/29 16:09
 * @description :
 */
public final class HSLColor implements Serializable {

    private final Color color;
    private final float hue;
    private final float saturation;
    private final float luminance;
    private final float alpha;

    public HSLColor(HSL hsl) {
        Args.notNull(hsl, "hsl must not be null");
        this.color = HSLUtils.toColor(hsl);
        this.hue = hsl.getHue();
        this.saturation = hsl.getSaturation();
        this.luminance = hsl.getLuminance();
        this.alpha = hsl.getAlpha();
    }

    public HSLColor(int rgb) {
        this(new Color(rgb));
/*
        this.color = new Color(rgb);
        HSL hsl = HSLUtils.toHSL(rgb);
        this.hue = hsl.getHue();
        this.saturation = hsl.getSaturation();
        this.luminance = hsl.getLuminance();
        this.alpha = hsl.getAlpha();
*/
    }

    public HSLColor(Color color) {
        this.color = Args.notNull(color, "color must not be null");
        HSL hsl = HSLUtils.toHSL(color);
        this.hue = hsl.getHue();
        this.saturation = hsl.getSaturation();
        this.luminance = hsl.getLuminance();
        this.alpha = hsl.getAlpha();
    }

    public static HSLColor of(int rgb) {
        return new HSLColor(rgb);
    }

    public static HSLColor of(final Color color) {
        return new HSLColor(color);
    }

    public static HSLColor of(final HSL hsl) {
        return new HSLColor(hsl);
    }

    /**
     * 调整当前 {@link HSLColor} 的色调为指定值，色调一旦确定，颜色就确定；因此色调调整与当前
     * {@link HSLColor} 的值没有任何关系。
     * 基本存在如下规律：
     * <pre>
     *      当 {@code hue} 为 0.0   或者 360.0 的时候固定为红色；
     *      当 {@code hue} 为 30.0  的时候固定为橙色；
     *      当 {@code hue} 为 60.0  的时候固定为黄色；
     *      当 {@code hue} 为 90.0  的时候固定为浅绿色；
     *      当 {@code hue} 为 120.0 的时候固定为绿色；
     *      当 {@code hue} 为 150.0 的时候固定为青绿色；
     *      当 {@code hue} 为 180.0 的时候固定为青色；
     *      当 {@code hue} 为 210.0 的时候固定为蓝色；
     *      当 {@code hue} 为 240.0 的时候固定为深蓝色；
     *      当 {@code hue} 为 270.0 的时候固定为紫色；
     *      当 {@code hue} 为 300.0 的时候固定为花生红色；
     *      当 {@code hue} 为 330.0 的时候固定为玫红色
     * </pre>
     *
     * @param hue 0.0 ~ 360.0
     * @return 色调调整之后的 {@code HSLColor}
     */
    public HSLColor withHue(float hue) {
        if (hue < 0 || hue > 360) {
            throw new IllegalArgumentException(String.format("hue{%.2f} must range from 0.0 to 360.0", hue));
        }
        HSL hsl = new HSL(hue, this.saturation, this.luminance, this.alpha);
        return new HSLColor(hsl);
    }

    /**
     * 调整当前 {@link HSLColor} 的饱和度为指定值，当 {@code saturation} 为 0.0 的时候变成灰色；
     * 当 {@code saturation} 为 100.0 的时候颜色不变。
     *
     * @param saturation 0.0 ~ 100.0
     * @return 饱和度调整之后的 {@code HSLColor}
     */
    public HSLColor withSaturation(float saturation) {
        if (saturation < 0 || saturation > 100) {
            throw new IllegalArgumentException(String.format("saturation{%.2f} must range from 0.0 to 100.0", saturation));
        }
        HSL hsl = new HSL(this.hue, saturation, this.luminance, this.alpha);
        return new HSLColor(hsl);
    }

    /**
     * 调整当前 {@link HSLColor} 的亮度为指定值，当 {@code luminance} 为 0.0 的时候变成黑色；
     * 当 {@code luminance} 为 100.0 的时候变成白色。
     *
     * @param luminance 0.0 ~ 100.0
     * @return 亮度调整之后的 {@code HSLColor}
     */
    public HSLColor withLuminance(float luminance) {
        if (luminance < 0 || luminance > 100) {
            throw new IllegalArgumentException(String.format("luminance{%.2f} must range from 0.0 to 100.0", luminance));
        }
        HSL hsl = new HSL(this.hue, saturation, luminance, this.alpha);
        return new HSLColor(hsl);
    }

    /**
     * 相对当前 {@link HSLColor} 进行深亮度渲染，如果 {@code percentage} 为 0.0，
     * 那么实际上并没有做任何的渲染处理；如果 {@code percentage} 为 100.0，那么实际
     * 上渲染的结果为变成了黑色。{@code percentage} 越大亮度越深
     *
     * @param percentage 0.0 ~ 100.0
     * @return 亮度调深之后生成的 {@code HSLColor}
     */
    public HSLColor darker(float percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException(String.format("percentage{%.2f} must range from 0.0 to 100.0", percentage));
        }
        float multiplier = (100F - percentage) / 100F;
        float luminance = Math.max(0F, this.luminance * multiplier);
        return withLuminance(luminance);
    }

    /**
     * 相对当前 {@link HSLColor} 进行浅亮度渲染，如果 {@code percentage} 为 0.0，
     * 那么实际上并没有做任何的渲染处理；如果 {@code percentage} 为 100.0，那么实际
     * 上渲染的结果为变成了白色。{@code percentage} 越大亮度越浅
     *
     * @param percentage 0.0 ~ 100.0
     * @return 亮度调浅之后生成的 {@code HSLColor}
     */
    public HSLColor brighter(float percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException(String.format("percentage{%f} must range from 0.0 to 100.0", percentage));
        }
        float multiplier = (100F + percentage) / 100F;
        float luminance = Math.min(100F, this.luminance * multiplier);
        return withLuminance(luminance);
    }

    /**
     * 获取当前 {@link HSLColor} 的补色，比如 当前的实际颜色为 CYAN，那么
     * 得到的补色就是 RED
     *
     * @return 当前 {@code HSLColor} 的补色
     */
    public HSLColor complementary() {
        float hue = (this.hue + 180F) % 360F;
        return withHue(hue);
    }

    public Color getColor() {
        return color;
    }

    public float getHue() {
        return hue;
    }

    public float getSaturation() {
        return saturation;
    }

    public float getLuminance() {
        return luminance;
    }

    public float getAlpha() {
        return alpha;
    }

    @Override
    public String toString() {
        return "HSLColor{" +
                "color=" + color +
                ", hue=" + hue +
                ", saturation=" + saturation +
                ", luminance=" + luminance +
                ", alpha=" + alpha +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        HSLColor hslColor = (HSLColor) object;
        return Float.compare(hslColor.hue, hue) == 0 &&
                Float.compare(hslColor.saturation, saturation) == 0 &&
                Float.compare(hslColor.luminance, luminance) == 0 &&
                Float.compare(hslColor.alpha, alpha) == 0 &&
                Objects.equals(color, hslColor.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, hue, saturation, luminance, alpha);
    }

}
