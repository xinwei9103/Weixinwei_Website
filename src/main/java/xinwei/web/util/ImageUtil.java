package xinwei.web.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by xinwei on 2/1/2017.
 */
public class ImageUtil {


    public static File rotateImage(MultipartFile file, String outFilePath) throws IOException, ImageProcessingException, MetadataException {

        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        Metadata metadata = ImageMetadataReader.readMetadata(file.getInputStream());
        ExifIFD0Directory exifIFD0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        JpegDirectory jpegDirectory =metadata.getFirstDirectoryOfType(JpegDirectory.class);

        int orientation = 1;
        try {
            orientation = exifIFD0Directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int width = jpegDirectory.getImageWidth();
        int height = jpegDirectory.getImageHeight();

        AffineTransform affineTransform = new AffineTransform();

        switch (orientation) {
            case 1:
                break;
            case 2: // Flip X
                affineTransform.scale(-1.0, 1.0);
                affineTransform.translate(-width, 0);
                break;
            case 3: // PI rotation
                affineTransform.translate(width, height);
                affineTransform.rotate(Math.PI);
                break;
            case 4: // Flip Y
                affineTransform.scale(1.0, -1.0);
                affineTransform.translate(0, -height);
                break;
            case 5: // - PI/2 and Flip X
                affineTransform.rotate(-Math.PI / 2);
                affineTransform.scale(-1.0, 1.0);
                break;
            case 6: // -PI/2 and -width
                affineTransform.translate(height, 0);
                affineTransform.rotate(Math.PI / 2);
                break;
            case 7: // PI/2 and Flip
                affineTransform.scale(-1.0, 1.0);
                affineTransform.translate(-height, 0);
                affineTransform.translate(0, width);
                affineTransform.rotate(3 * Math.PI / 2);
                break;
            case 8: // PI / 2
                affineTransform.translate(0, width);
                affineTransform.rotate(3 * Math.PI / 2);
                break;
            default:
                break;
        }

        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);
        BufferedImage destinationImage = new BufferedImage(originalImage.getHeight(), originalImage.getWidth(), originalImage.getType());
        destinationImage = affineTransformOp.filter(originalImage, destinationImage);
        File output = new File(outFilePath);
        if(!output.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
           // System.out.println("目标文件所在目录不存在，准备创建它！");
            if(!output.getParentFile().mkdirs()) {
                //System.out.println("创建目标文件所在目录失败！");
            }
        }
        //System.out.println(output.getAbsolutePath());
        //output.createNewFile();
        ImageIO.write(destinationImage, "jpg", output);
        return output;
    }

}
