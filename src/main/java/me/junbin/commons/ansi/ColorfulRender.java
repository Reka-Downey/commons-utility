package me.junbin.commons.ansi;

import org.fusesource.jansi.Ansi;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/27 13:06
 * @description : 色彩斑斓定制器
 */
public final class ColorfulRender {

    private final Ansi ansi;
    /**
     * 是否开启亮色调，默认开启
     */
    private boolean bright = true;
    /**
     * 是否开启前景色渲染，默认开启
     */
    private boolean fg = true;
    /**
     * 是否开启背景色渲染，默认关闭
     */
    private boolean bg = false;
    /**
     * 前景色配置
     */
    private Ansi.Color fgColor = Ansi.Color.DEFAULT;
    /**
     * 背景色配置
     */
    private Ansi.Color bgColor = Ansi.Color.DEFAULT;
    /**
     * 是否开启反显，默认关闭
     */
    private boolean negative = false;
    /**
     * 是否开启下划线，默认关闭
     */
    private boolean underline = false;
    /**
     * 是否开启黑体，默认关闭
     */
    private boolean bold = false;

    /**
     * 是否已终止
     */
    private boolean terminated = false;

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@@@@@@@@@ 初始化操作 @@@@@@@@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    public ColorfulRender() {
        this.ansi = Ansi.ansi().eraseScreen();
    }

    public static ColorfulRender custom() {
        return new ColorfulRender();
    }

    public static ColorfulRender start() {
        return new ColorfulRender();
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@@@@@@@@@ 初始化操作 @@@@@@@@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@@@@@@@@@ 中间操作 @@@@@@@@@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    /**
     * 色调渲染切换
     *
     * @param bright {@code true} 表示切换为亮色调，{@code false} 表示切换为暗色调
     */
    public ColorfulRender bright(boolean bright) {
        this.bright = bright;
        return this;
    }

    /**
     * 切换为亮色调渲染
     */
    public ColorfulRender bright() {
        this.bright = true;
        return this;
    }

    /**
     * 切换为亮色调渲染
     */
    public ColorfulRender brightOn() {
        this.bright = true;
        return this;
    }

    /**
     * 切换为暗色调渲染
     */
    public ColorfulRender brightOff() {
        this.bright = false;
        return this;
    }

    /**
     * 前景色渲染设置
     *
     * @param fg {@code true} 表示打开前景色渲染，{@code false} 表示关闭前景色渲染
     */
    public ColorfulRender fg(boolean fg) {
        this.fg = fg;
        return this;
    }

    /**
     * 打开前景色渲染
     */
    public ColorfulRender fg() {
        this.fg = true;
        return this;
    }

    /**
     * 打开前景色渲染
     */
    public ColorfulRender fgOn() {
        this.fg = true;
        return this;
    }

    /**
     * 关闭前景色渲染
     */
    public ColorfulRender fgOff() {
        this.fg = false;
        return this;
    }

    /**
     * 背景色渲染设置
     *
     * @param bg {@code true} 表示打开背景色渲染，{@code false} 表示关闭背景色渲染
     */
    public ColorfulRender bg(boolean bg) {
        this.bg = bg;
        return this;
    }

    /**
     * 打开背景色渲染
     */
    public ColorfulRender bg() {
        this.bg = true;
        return this;
    }

    /**
     * 打开背景色渲染
     */
    public ColorfulRender bgOn() {
        this.bg = true;
        return this;
    }

    /**
     * 关闭背景色渲染
     */
    public ColorfulRender bgOff() {
        this.bg = false;
        return this;
    }

    /**
     * 下划线渲染设置
     *
     * @param underline {@code true} 表示打开下划线渲染，{@code false} 表示关闭下划线渲染
     */
    public ColorfulRender underline(boolean underline) {
        this.underline = underline;
        return this;
    }

    /**
     * 打开下划线渲染
     */
    public ColorfulRender underline() {
        this.underline = true;
        return this;
    }

    /**
     * 打开下划线渲染
     */
    public ColorfulRender underlineOn() {
        this.underline = true;
        return this;
    }

    /**
     * 关闭下划线渲染
     */
    public ColorfulRender underlineOff() {
        this.underline = false;
        return this;
    }

    /**
     * 反显渲染设置
     *
     * @param negative {@code true} 表示打开反显渲染，{@code false} 表示关闭反显渲染
     */
    public ColorfulRender negative(boolean negative) {
        this.negative = negative;
        return this;
    }

    /**
     * 打开反显渲染
     */
    public ColorfulRender negative() {
        this.negative = true;
        return this;
    }

    /**
     * 打开反显渲染
     */
    public ColorfulRender negativeOn() {
        this.negative = true;
        return this;
    }

    /**
     * 关闭反显渲染
     */
    public ColorfulRender negativeOff() {
        this.negative = false;
        return this;
    }

    /**
     * 黑体渲染设置
     *
     * @param bold {@code true} 表示打开黑体渲染，{@code false} 表示关闭黑体渲染
     */
    public ColorfulRender bold(boolean bold) {
        this.bold = bold;
        return this;
    }

    /**
     * 打开黑体渲染
     */
    public ColorfulRender bold() {
        this.bold = true;
        return this;
    }

    /**
     * 打开黑体渲染
     */
    public ColorfulRender boldOn() {
        this.bold = true;
        return this;
    }

    /**
     * 关闭黑体渲染
     */
    public ColorfulRender boldOff() {
        this.bold = false;
        return this;
    }

    /**
     * 配置前景色
     *
     * @param fgColor 前景色
     */
    public ColorfulRender fgColor(Ansi.Color fgColor) {
        this.fgColor = fgColor;
        return this;
    }

    /**
     * 使用默认的前景色
     */
    public ColorfulRender fgDefault() {
        return fgColor(Ansi.Color.DEFAULT);
    }

    /**
     * 使用白色的前景色
     */
    public ColorfulRender fgWhite() {
        return fgColor(Ansi.Color.WHITE);
    }

    /**
     * 使用黑色的前景色
     */
    public ColorfulRender fgBlack() {
        return fgColor(Ansi.Color.BLACK);
    }

    /**
     * 使用红色的前景色
     */
    public ColorfulRender fgRed() {
        return fgColor(Ansi.Color.RED);
    }

    /**
     * 使用绿色的前景色
     */
    public ColorfulRender fgGreen() {
        return fgColor(Ansi.Color.GREEN);
    }

    /**
     * 使用蓝色的前景色
     */
    public ColorfulRender fgBlue() {
        return fgColor(Ansi.Color.BLUE);
    }

    /**
     * 使用洋红色的前景色
     */
    public ColorfulRender fgMagenta() {
        return fgColor(Ansi.Color.MAGENTA);
    }

    /**
     * 使用青色的前景色
     */
    public ColorfulRender fgCyan() {
        return fgColor(Ansi.Color.CYAN);
    }

    /**
     * 使用黄色的前景色
     */
    public ColorfulRender fgYellow() {
        return fgColor(Ansi.Color.YELLOW);
    }

    /**
     * 配置背景色
     *
     * @param bgColor 背景色
     */
    public ColorfulRender bgColor(Ansi.Color bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    /**
     * 使用默认的背景色
     */
    public ColorfulRender bgDefault() {
        return bgColor(Ansi.Color.DEFAULT);
    }

    /**
     * 使用白色的背景色
     */
    public ColorfulRender bgWhite() {
        return bgColor(Ansi.Color.WHITE);
    }

    /**
     * 使用黑色的背景色
     */
    public ColorfulRender bgBlack() {
        return bgColor(Ansi.Color.BLACK);
    }

    /**
     * 使用红色的背景色
     */
    public ColorfulRender bgRed() {
        return bgColor(Ansi.Color.RED);
    }

    /**
     * 使用绿色的背景色
     */
    public ColorfulRender bgGreen() {
        return bgColor(Ansi.Color.GREEN);
    }

    /**
     * 使用蓝色的背景色
     */
    public ColorfulRender bgBlue() {
        return bgColor(Ansi.Color.BLUE);
    }

    /**
     * 使用洋红色的背景色
     */
    public ColorfulRender bgMagenta() {
        return bgColor(Ansi.Color.MAGENTA);
    }

    /**
     * 使用青色的背景色
     */
    public ColorfulRender bgCyan() {
        return bgColor(Ansi.Color.CYAN);
    }

    /**
     * 使用黄色的背景色
     */
    public ColorfulRender bgYellow() {
        return bgColor(Ansi.Color.YELLOW);
    }

    private void offStyle() {
        this.ansi.a(Ansi.Attribute.UNDERLINE_OFF);
        this.ansi.a(Ansi.Attribute.INTENSITY_BOLD_OFF);
        this.ansi.a(Ansi.Attribute.NEGATIVE_OFF);
    }

    /**
     * 样式渲染
     */
    private void renderStyle() {
        offStyle();
        if (this.underline) {
            this.ansi.a(Ansi.Attribute.UNDERLINE);
        }
        if (this.bold) {
            this.ansi.a(Ansi.Attribute.INTENSITY_BOLD);
        }
        if (this.negative) {
            this.ansi.a(Ansi.Attribute.NEGATIVE_ON);
        }
    }

    /**
     * 色彩渲染
     */
    private void renderColor() {
        if (bright) {
            if (this.fg) {
                this.ansi.fgBright(fgColor);
            } else {
                this.ansi.fgBrightDefault();
            }
            if (this.bg) {
                this.ansi.bgBright(bgColor);
            } else {
                this.ansi.bgBrightDefault();
            }
        } else {
            if (this.fg) {
                this.ansi.fg(fgColor);
            } else {
                this.ansi.fgDefault();
            }
            if (this.bg) {
                this.ansi.bg(bgColor);
            } else {
                this.ansi.bgDefault();
            }
        }
    }

    /**
     * 内容渲染
     *
     * @param object 要渲染的内容
     */
    public ColorfulRender render(Object object) {
        if (terminated) {
            throw new IllegalStateException("Render operation has already been terminated!");
        }
        this.renderStyle();
        this.renderColor();
        this.ansi.a(object);
        return this;
    }

    /**
     * 内容渲染
     *
     * @param object 要渲染的内容
     */
    public ColorfulRender andThen(Object object) {
        return render(object);
    }

    /**
     * 插入新行
     */
    public ColorfulRender newLine() {
        this.ansi.newline();
        return this;
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@@@@@@@@@ 中间操作 @@@@@@@@@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@@@@@@@@@ 终止操作 @@@@@@@@@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    /**
     * 结束当前的渲染
     */
    public Ansi end() {
        if (this.terminated) {
            throw new IllegalStateException("Render operation has already been terminated!");
        }
        this.terminated = true;
        return this.ansi.reset();
    }

    /**
     * 将当前的渲染内容以 ASCII 字符串形式返回
     */
    public String ansiString() {
        return this.end().toString();
    }

    /**
     * 输出当前的渲染内容
     */
    public void output() {
        System.out.println(this.ansiString());
    }

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // @@@@@@@@@@@@@ 终止操作 @@@@@@@@@@@@@
    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

}
