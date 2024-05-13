package fi.metropolia.image;

import fi.metropolia.utils.MagicBytes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
public class ImageFactory {

    private ImageFactory() {
    }

    public static Image createImage(String path) {
        try {
            byte[] head = MagicBytes.extract(Files.newInputStream(Paths.get(path)), 2);
            if (MagicBytes.PNG.is(head)) {
                return new PngImage(path);
            }else if (MagicBytes.JPG.is(head)) {
                return new JpgImage(path);
            } else {
                return null;
            }

        } catch (IOException e) {
            return null;
        }
    }
}
