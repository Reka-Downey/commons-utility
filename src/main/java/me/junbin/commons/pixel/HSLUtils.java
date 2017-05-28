package me.junbin.commons.pixel;

import java.awt.*;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/29 16:42
 * @description :
 */
public abstract class HSLUtils {

    public static HSL toHSL(final int rgb) {
        return toHSL(new Color(rgb));
    }

    public static HSL toHSL(final Color color) {
        //float[] rgb = color.getRGBColorComponents(new float[3]);

        // 第一步：获取 RGB 颜色值的浮点表示 0.0 ~ 1.0，即 0.0 代表 0， 1.0 代表 255
        float[] rgb = color.getRGBColorComponents(null);
        float r = rgb[0];
        float g = rgb[1];
        float b = rgb[2];
        float a = color.getAlpha() / 255F;

        // 第二步：获取浮点 RGB 的最大值和最小值，用于 HSL 计算
        float min = Math.min(r, Math.min(g, b));
        float max = Math.max(r, Math.max(g, b));

        // 第三步：计算 色调/色相 Hue
        float h = 0;
        if (max == min) {
            h = 0;
        } else if (max == r) {
            h = ((60 * (g - b) / (max - min)) + 360) % 360;
        } else if (max == g) {
            h = (60 * (b - r) / (max - min)) + 120;
        } else if (max == b) {
            h = (60 * (r - g) / (max - min)) + 240;
        }

        // 第四步：计算 亮度 Luminance，因为 饱和度 的计算依赖于 亮度
        float l = (min + max) / 2;

        // 第五步：计算 饱和度 Saturation
        float s;
        if (max == min) {
            s = 0;
        } else if (l <= .5F) {
            s = (max - min) / (max + min);
        } else {
            s = (max - min) / (2 - max - min);
        }

        return new HSL(h, s * 100, l * 100, a);
    }

    public static Color toColor(HSL hsl) {
        return toColor(hsl.getHue(), hsl.getSaturation(), hsl.getLuminance(), hsl.getAlpha());
    }

    public static Color toColor(float h, float s, float l) {
        return toColor(h, s, l, 1.0F);
    }

    public static Color toColor(float h, float s, float l, float alpha) {
        if (h < .0F || h > 360F) {
            throw new IllegalArgumentException(
                    String.format("Color parameter h{%.2f} outside of expected range - Hue", h));
        }

        if (s < 0.0f || s > 100.0f) {
            throw new IllegalArgumentException(
                    String.format("Color parameter s{%.2f} outside of expected range - Saturation", s));
        }

        if (l < 0.0f || l > 100.0f) {
            throw new IllegalArgumentException(
                    String.format("Color parameter l{%.2f} outside of expected range - Luminance", l));
        }

        if (alpha < 0.0f || alpha > 1.0f) {
            throw new IllegalArgumentException(
                    String.format("Color parameter alpha {%.2f} outside of expected range - Alpha", alpha));
        }

        // 将 HSL 规范化
        h = h % 360.0f;
        h /= 360f;
        s /= 100f;
        l /= 100f;

        float q;
        if (l < .5F) {
            q = l * (1 + s);
        } else {
            q = (l + s) - (s * l);
        }

        float p = 2 * l - q;

        float r = Math.max(0, hue2RGB(p, q, h + (1.0F / 3.0F)));
        float g = Math.max(0, hue2RGB(p, q, h));
        float b = Math.max(0, hue2RGB(p, q, h - (1.0F / 3.0F)));


        r = Math.min(r, 1.0F);
        g = Math.min(g, 1.0F);
        b = Math.min(b, 1.0F);

        return new Color(r, g, b, alpha);
    }

    private static float hue2RGB(float p, float q, float h) {
        while (h < 0) {
            h += 1;
        }

        if (h > 1) {
            h -= 1;
        }

        if (6 * h < 1) {
            return p + ((q - p) * 6 * h);
        }

        if (2 * h < 1) {
            return q;
        }

        if (3 * h < 2) {
            return p + ((q - p) * 6 * ((2.0f / 3.0f) - h));
        }

        return p;
    }

}
