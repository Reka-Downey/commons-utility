package me.junbin.commons.magnitude;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/30 10:59
 * @description :
 */
public abstract class MagnitudeUtils {

/*
    public static final long DECA_PER_HECTA = 10;
    public static final long DECA_PER_KILO = 100;
    public static final long DECA_PER_MEGA = 1000;
    public static final long DECA_PER_GIGA = 100;
    public static final long DECA_PER_TERA = 100;
    public static final long DECA_PER_PETA = 100;


    public static long decaToHecta(long deca) {
        return deca / 10;
    }

    public static long decaToKilo(long deca) {
        return deca / 100;
    }

    public static long decaToMega(long deca) {
        return deca / 1000;
    }
*/

    public static long byteToKibi(long _byte) {
        return _byte >>> 10;
    }

    public static long byteToMebi(long _byte) {
        return _byte >>> 20;
    }

    public static long byteToGibi(long _byte) {
        return _byte >>> 30;
    }

    public static long byteToTebi(long _byte) {
        return _byte >>> 40;
    }

    public static long byteToPebi(long _byte) {
        return _byte >>> 50;
    }

    public static long kibiToMebi(long kibi) {
        return kibi >>> 10;
    }

    public static long kibiToGibi(long kibi) {
        return kibi >>> 20;
    }

    public static long kibiToTebi(long kibi) {
        return kibi >>> 30;
    }

    public static long kibiToPebi(long kibi) {
        return kibi >>> 40;
    }

    public static long mebiToGibi(long mebi) {
        return mebi >>> 10;
    }

    public static long mebiToTebi(long mebi) {
        return mebi >>> 20;
    }

    public static long mebiToPebi(long mebi) {
        return mebi >>> 30;
    }

    public static long gibiToTebi(long gibi) {
        return gibi >>> 10;
    }

    public static long gibiToPebi(long gibi) {
        return gibi >>> 20;
    }

    public static long tebiToPebi(long tebi) {
        return tebi >>> 10;
    }

    public static long pebiToByte(long pebi) {
        return pebi << 50;
    }

    public static long pebiToKibi(long pebi) {
        return pebi << 40;
    }

    public static long pebiToMebi(long pebi) {
        return pebi << 30;
    }

    public static long pebiToGibi(long pebi) {
        return pebi << 20;
    }

    public static long pebiToTebi(long pebi) {
        return pebi << 10;
    }

    public static long tebiToGibi(long tebi) {
        return tebi << 10;
    }

    public static long tebiToMebi(long tebi) {
        return tebi << 20;
    }

    public static long tebiToKibi(long tebi) {
        return tebi << 30;
    }

    public static long tebiToByte(long tebi) {
        return tebi << 40;
    }

    public static long gibiToMebi(long gibi) {
        return gibi << 10;
    }

    public static long gibiToKibi(long gibi) {
        return gibi << 20;
    }

    public static long gibiToByte(long gibi) {
        return gibi << 30;
    }

    public static long mebiToKibi(long mebi) {
        return mebi << 10;
    }

    public static long mebiToByte(long mebi) {
        return mebi << 20;
    }

    public static long kibiToByte(long kibi) {
        return kibi << 10;
    }

}
