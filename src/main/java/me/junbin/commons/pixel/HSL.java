package me.junbin.commons.pixel;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/29 16:17
 * @description :
 * H 代表 色调（Hue） -- 色调指图像的整体明暗程度，若图像中亮色像素较多，则图像整体比较明快，反之则比较昏暗；色调通常由
 * 　　色相、明度、冷暖、纯度 四个方面确定；通常也将 H 理解为 色相；
 * S 代表 饱和度（Saturation） -- 饱和度指色相中灰色分量所占的比例，从 0%(灰色) ~ 100%(完全饱和) 变化；
 * L 代表 亮度（Luminance） -- 亮度指颜色的相对明暗程度，从 0%(黑色) ~ 100%(白色) 变化；
 * alpha 代表 透明度，从 0.0(透明) ~ 1.0(不透明) 变化，也可以从 0(透明) ~ 255(不透明) 变化，存在一个 255 倍率。
 */
public final class HSL implements Serializable {

    private final float hue;
    private final float saturation;
    private final float luminance;
    private final float alpha;

    public HSL(float hue, float saturation, float luminance, float alpha) {
        this.hue = hue;
        this.saturation = saturation;
        this.luminance = luminance;
        this.alpha = alpha;
    }

    @Override
    public String toString() {
        return "HSL{" +
                "hue=" + hue +
                ", saturation=" + saturation +
                ", luminance=" + luminance +
                ", alpha=" + alpha +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        HSL hsl = (HSL) object;
        return Float.compare(hsl.hue, hue) == 0 &&
                Float.compare(hsl.saturation, saturation) == 0 &&
                Float.compare(hsl.luminance, luminance) == 0 &&
                Float.compare(hsl.alpha, alpha) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hue, saturation, luminance, alpha);
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

}
