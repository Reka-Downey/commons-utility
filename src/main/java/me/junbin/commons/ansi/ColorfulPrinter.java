package me.junbin.commons.ansi;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.Color.*;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/24 10:02
 * @description :
 */
public final class ColorfulPrinter {

    private ColorfulPrinter() {
        throw new AssertionError("No instances of ColorfulPrinter for you!");
    }

    public static String fgString(final Ansi.Color color, final Object object) {
        return Ansi.ansi().eraseScreen().fgBright(color).a(object).reset().toString();
    }

    public static String bgString(final Ansi.Color color, final Object object) {
        return Ansi.ansi().eraseScreen().bgBright(color).a(object).reset().toString();
    }

    public static String fgDefaultString(final Object object) {
        return fgString(DEFAULT, object);
    }

    public static String bgDefaultString(final Object object) {
        return bgString(DEFAULT, object);
    }

    public static String fgWhiteString(final Object object) {
        return fgString(WHITE, object);
    }

    public static String bgWhiteString(final Object object) {
        return bgString(WHITE, object);
    }

    public static String fgBlackString(final Object object) {
        return fgString(BLACK, object);
    }

    public static String bgBlackString(final Object object) {
        return bgString(BLACK, object);
    }

    public static String fgRedString(final Object object) {
        return fgString(RED, object);
    }

    public static String bgRedString(final Object object) {
        return bgString(RED, object);
    }

    public static String fgGreenString(final Object object) {
        return fgString(GREEN, object);
    }

    public static String bgGreenString(final Object object) {
        return bgString(GREEN, object);
    }

    public static String fgBlueString(final Object object) {
        return fgString(BLUE, object);
    }

    public static String bgBlueString(final Object object) {
        return bgString(BLUE, object);
    }

    public static String fgMagentaString(final Object object) {
        return fgString(MAGENTA, object);
    }

    public static String bgMagentaString(final Object object) {
        return bgString(MAGENTA, object);
    }

    public static String fgCyanString(final Object object) {
        return fgString(CYAN, object);
    }

    public static String bgCyanString(final Object object) {
        return bgString(CYAN, object);
    }

    public static String fgYellowString(final Object object) {
        return fgString(YELLOW, object);
    }

    public static String bgYellowString(final Object object) {
        return bgString(YELLOW, object);
    }

    public static String boldString(final Object object) {
        return Ansi.ansi().eraseScreen().a(Ansi.Attribute.INTENSITY_BOLD).a(object).reset().toString();
    }

    public static String underlineString(final Object object) {
        return Ansi.ansi().eraseScreen().a(Ansi.Attribute.UNDERLINE).a(object).reset().toString();
    }

    public static String negativeString(final Object object) {
        return Ansi.ansi().eraseScreen().a(Ansi.Attribute.NEGATIVE_ON).a(object).reset().toString();
    }

    public static void fgDefault(final Object object) {
        System.out.println(fgDefaultString(object));
    }

    public static void bgDefault(final Object object) {
        System.out.println(bgDefaultString(object));
    }


    public static void white(final Object object) {
        System.out.println(fgWhiteString(object));
    }

    public static void fgWhite(final Object object) {
        white(object);
    }

    public static void bgWhite(final Object object) {
        System.out.println(bgWhiteString(object));
    }


    public static void black(final Object object) {
        System.out.println(fgBlackString(object));
    }

    public static void fgBlack(final Object object) {
        black(object);
    }

    public static void bgBlack(final Object object) {
        System.out.println(bgBlackString(object));
    }


    public static void red(final Object object) {
        System.out.println(fgRedString(object));
    }

    public static void fgRed(final Object object) {
        red(object);
    }

    public static void bgRed(final Object object) {
        System.out.println(bgRedString(object));
    }


    public static void green(final Object object) {
        System.out.println(fgGreenString(object));
    }

    public static void fgGreen(final Object object) {
        green(object);
    }

    public static void bgGreen(final Object object) {
        System.out.println(bgGreenString(object));
    }


    public static void blue(final Object object) {
        System.out.println(fgBlueString(object));
    }

    public static void fgBlue(final Object object) {
        blue(object);
    }

    public static void bgBlue(final Object object) {
        System.out.println(bgBlueString(object));
    }

    public static void cyan(final Object object) {
        System.out.println(fgCyanString(object));
    }

    public static void fgCyan(final Object object) {
        cyan(object);
    }

    public static void bgCyan(final Object object) {
        System.out.println(bgCyanString(object));
    }


    public static void magenta(final Object object) {
        System.out.println(fgMagentaString(object));
    }

    public static void fgMagenta(final Object object) {
        magenta(object);
    }

    public static void bgMagenta(final Object object) {
        System.out.println(bgMagentaString(object));
    }


    public static void yellow(final Object object) {
        System.out.println(fgYellowString(object));
    }

    public static void fgYellow(final Object object) {
        yellow(object);
    }

    public static void bgYellow(final Object object) {
        System.out.println(bgYellowString(object));
    }


    public static void bold(final Object object) {
        System.out.println(boldString(object));
    }

    public static void underline(final Object object) {
        System.out.println(underlineString(object));
    }

    public static void negative(final Object object) {
        System.out.println(negativeString(object));
    }

}
