/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.image;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.name.Rename;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Victor Zhang
 * @date 2017/1/17.
 */
public class ThumbnailatorTest {
    /**
     * 测试输入单个对象
     * 执行后会在D:\下生成几张不同尺寸的图片
     */
    @Test
    public void testInput01() throws IOException {
        final String oldFile = System.getProperty("user.dir") + "/src/main/resources/images/lion.jpg";

        // 输入形式：文件名
        Thumbnails.of(oldFile)
                .size(16, 16)
                .toFile("d:\\test_input_16_16.png");
        Assert.assertTrue(new File("d:\\test_input_16_16.png").exists());

        // 输入形式：File 对象
        Thumbnails.of(new File(oldFile))
                .size(32, 32)
                .toFile(new File("d:\\test_input_32_32.png"));
        Assert.assertTrue(new File("d:\\test_input_32_32.png").exists());

        // 输入形式：URL 对象
        URL url = new URL("https://raw.githubusercontent.com/atlantis1024/JavaParty/master/toolbox/image/src/main/resources/images/lion.jpg");
        Thumbnails.of(url)
                .size(64, 64)
                .toFile(new File("d:\\test_input_64_64.png"));
        Assert.assertTrue(new File("d:\\test_input_64_64.png").exists());

        // 输入形式：BufferedImage 对象
        BufferedImage originalImage = ImageIO.read(new File(oldFile));
        Thumbnails.of(originalImage)
                .size(80, 80)
                .toFile(new File("d:\\test_input_80_80.png"));
        Assert.assertTrue(new File("d:\\test_input_80_80.png").exists());

        // 输入形式：InputStream 对象
        InputStream fis = new FileInputStream(oldFile);
        Thumbnails.of(fis)
                .size(96, 96)
                .toFile(new File("d:\\test_input_96_96.png"));
        Assert.assertTrue(new File("d:\\test_input_96_96.png").exists());
    }

    /**
     * 测试输入多个对象
     * 执行后会在D:\下生成几个含有图片的文件夹
     * 注：如果输入是多个对象，则输出也必须选用输出多个对象的方式
     *
     * @throws IOException
     */
    @Test
    public void testInput02() throws IOException {
        final String oldFile = System.getProperty("user.dir") + "/src/main/resources/images/lion.jpg";
        final String oldFile2 = System.getProperty("user.dir") + "/src/main/resources/images/lion2.jpg";

        createFolderIfNotExist("D:\\fromFilenames");
        Set<String> filenames = new HashSet<String>();
        filenames.add(oldFile);
        filenames.add(oldFile2);
        Thumbnails.fromFilenames(filenames)
                .size(50, 50)
                .toFiles(new File("D:\\fromFilenames"), Rename.PREFIX_DOT_THUMBNAIL);

        createFolderIfNotExist("D:\\fromFiles");
        List<File> files = new ArrayList<File>();
        files.add(new File(oldFile));
        files.add(new File(oldFile2));
        Thumbnails.fromFiles(files)
                .size(50, 50)
                .toFiles(new File("D:\\fromFiles"), Rename.PREFIX_HYPHEN_THUMBNAIL);
    }

    @Test
    public void testResize() throws IOException {
        final String oldFile = System.getProperty("user.dir") + "/src/main/resources/images/lion2.jpg";
        Thumbnails.of(oldFile)
                .size(16, 16)
                .toFile("d:\\lion_16_16.png");

        Thumbnails.of(oldFile)
                .scale(2.0)
                .toFile("d:\\lion_scale_2.0.png");

        Thumbnails.of(oldFile)
                .scale(1.0, 0.5)
                .toFile("d:\\lion_scale_1.0_0.5.png");
    }

    @Test
    public void testRotate() throws IOException {
        final String oldFile = System.getProperty("user.dir") + "/src/main/resources/images/lion2.jpg";
        Thumbnails.of(oldFile)
                .scale(0.8)
                .rotate(90)
                .toFile("d:\\lion2_rotate_90.png");

        Thumbnails.of(oldFile)
                .scale(0.8)
                .rotate(180)
                .toFile("d:\\lion2_rotate_180.png");
    }

    @Test
    public void testWatermark() throws IOException {
        final String oldFile = System.getProperty("user.dir") + "/src/main/resources/images/lion2.jpg";
        final String wartermarkFile = System.getProperty("user.dir") + "/src/main/resources/images/wartermark.png";
        BufferedImage watermarkImage = ImageIO.read(new File(wartermarkFile));
        Thumbnails.of(oldFile)
                .scale(0.8)
                .watermark(Positions.BOTTOM_LEFT, watermarkImage, 0.5f)
                .toFile("d:\\lion2_watermark.png");
    }

    @Test
    public void testBatchChange() throws IOException {
        final String oldFile = System.getProperty("user.dir") + "/src/main/resources/images/lion.jpg";
        final String oldFile2 = System.getProperty("user.dir") + "/src/main/resources/images/lion2.jpg";
        final String wartermarkFile = System.getProperty("user.dir") + "/src/main/resources/images/wartermark.png";
        BufferedImage watermarkImage = ImageIO.read(new File(wartermarkFile));
        createFolderIfNotExist("D:\\watermark");

        Thumbnails.of(oldFile, oldFile2)
                .scale(0.8)
                .watermark(Positions.BOTTOM_LEFT, watermarkImage, 0.5f)
                .toFiles(new File("D:\\watermark"), Rename.PREFIX_DOT_THUMBNAIL);
    }

    private void createFolderIfNotExist(String folderPath) throws IOException {
        File f = new File(folderPath);
        if (!(f.exists() && f.isDirectory())) {
            f.mkdirs();
        }
    }
}
