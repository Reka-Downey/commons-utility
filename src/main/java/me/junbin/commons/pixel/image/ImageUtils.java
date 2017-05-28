package me.junbin.commons.pixel.image;

import me.junbin.commons.pixel.PixelUtils;
import me.junbin.commons.util.Args;
import me.junbin.commons.util.PathUtils;

import javax.imageio.ImageIO;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:rekadowney@163.com">发送邮件</a>
 * @createDate : 2017/1/29 12:26
 * @description :
 */
public abstract class ImageUtils {

    public static BufferedImage ashing(final Path imgPath) throws IOException {
        Path src = Args.notNull(imgPath).normalize();
        if (Files.notExists(src)) {
            throw new FileNotFoundException(String.format("Path{%s} is not exists", src.toString()));
        }
        BufferedImage in = ImageIO.read(Files.newInputStream(src));
        return ashing(in);
    }

    public static BufferedImage ashing(final BufferedImage image) {
        Args.notNull(image);
        int width = image.getWidth();
        int height = image.getHeight();
        int[] rowRgb = new int[width];

        BufferedImage output = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            // 读取一行的像素点
            rowRgb = image.getRGB(0, y, width, 1, rowRgb, 0, width);
            // 灰化
            rowRgb = PixelUtils.ashing(rowRgb);
            // 将像素渲染到另一张图片中
            output.setRGB(0, y, width, 1, rowRgb, 0, width);
        }

        return output;
    }

    public static void ashing(final BufferedImage srcImg, final Path storePath) throws IOException {
        BufferedImage output = ashing(srcImg);
        output(output, storePath);
    }

    public static void ashing(final Path srcPath, final Path storePath) throws IOException {
        BufferedImage image = ashing(srcPath);
        output(image, storePath);
    }

    public static BufferedImage ashingByColorConvertOp(final Path imgPath) throws IOException {
        Path src = Args.notNull(imgPath).normalize();
        if (Files.notExists(src)) {
            throw new FileNotFoundException(String.format("Path{%s} is not exists", src.toString()));
        }
        BufferedImage in = ImageIO.read(Files.newInputStream(src));
        return ashingByColorConvertOp(in);
    }

    public static BufferedImage ashingByColorConvertOp(final BufferedImage image) {
        Args.notNull(image);
        ColorSpace grayCS = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp convertOp = new ColorConvertOp(grayCS, null);
        BufferedImage output = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        return convertOp.filter(image, output);
    }

    public static void ashingByColorConvertOp(final BufferedImage srcImg, final Path storePath) throws IOException {
        BufferedImage image = ashingByColorConvertOp(srcImg);
        output(image, storePath);
    }

    public static void ashingByColorConvertOp(final Path srcPath, final Path storePath) throws IOException {
        BufferedImage image = ashingByColorConvertOp(srcPath);
        output(image, storePath);
    }

    private static void output(final BufferedImage inImage, final Path outputPath) throws IOException {
        Path destPath = Args.notNull(outputPath).normalize();
        String ext = PathUtils.extensionName(destPath);
        ImageIO.write(inImage, ext, Files.newOutputStream(destPath));
    }

}
