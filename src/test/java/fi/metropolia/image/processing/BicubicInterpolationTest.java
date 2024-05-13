package fi.metropolia.image.processing;

import fi.metropolia.image.Image;
import fi.metropolia.image.PngImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BicubicInterpolationTest {

    private ImageProcessingCommand bicubicInterpolation;
    @BeforeEach
    void setUp() {
        bicubicInterpolation = new BicubicInterpolation();
    }

    @Test
    void execute() {
        Image image = new PngImage(new int[][]{
                {0, 0, 0},
                {0, 0xffff, 0},
                {0, 0, 0}
        });
        bicubicInterpolation.execute(image);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                assertNotEquals(0, image.getMatrix()[y][x]);
            }
        }

    }
}