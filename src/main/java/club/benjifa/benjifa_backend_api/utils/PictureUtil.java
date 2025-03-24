package club.benjifa.benjifa_backend_api.utils;


import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PictureUtil {

    private PictureUtil() {
    }


    public static byte[] createThumbnail(MultipartFile file) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(file.getBytes()));

        double thumbWidth = 200.0;
        double thumbHeight = (200.0 / originalImage.getWidth()) * originalImage.getHeight();

        BufferedImage thumbnail = new BufferedImage((int) thumbWidth, (int) thumbHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = thumbnail.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Draw the scaled image onto the thumbnail directly
        graphics2D.drawImage(originalImage, 0, 0, (int) thumbWidth, (int) thumbHeight, null);

        graphics2D.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(thumbnail, "jpg", baos);
        byte[] thumbnailBytes = baos.toByteArray();
        baos.close();

        return thumbnailBytes;
    }


}
