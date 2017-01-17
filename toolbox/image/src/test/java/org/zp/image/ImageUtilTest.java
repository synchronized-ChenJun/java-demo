/**
 * The Apache License 2.0
 * Copyright (c) 2016 Victor Zhang
 */
package org.zp.image;

import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Assert;
import org.junit.Test;
import org.zp.image.dto.ImageParamDTO;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author Victor Zhang
 * @date 2017/1/16.
 */
public class ImageUtilTest {
    @Test
    public void testToFile() throws IOException {
        final String oldFile = System.getProperty("user.dir") + "/src/main/resources/images/lion2.jpg";
        final String newFile = System.getProperty("user.dir") + "/src/main/resources/images/lion2_watermark.png";
        final String warterFile = System.getProperty("user.dir") + "/src/main/resources/images/wartermark.png";

        ImageParamDTO params = new ImageParamDTO();
        params.setScale(0.5f);
        params.setWartermarkPath(warterFile);
        params.setWartermarkPosition(Positions.BOTTOM_LEFT);
        params.setWartermarkOpacity(0.8f);

        ImageUtil.toFile(oldFile, newFile, params);
    }

    @Test
    public void testToBufferedImage() throws IOException {
        final String oldFile = System.getProperty("user.dir") + "/src/main/resources/images/lion2.jpg";

        ImageParamDTO params = new ImageParamDTO();
        params.setWidth(64);
        params.setHeight(64);

        BufferedImage bufferedImage = ImageUtil.toBufferedImage(oldFile, params);
        Assert.assertNotNull(bufferedImage);
    }
}
