package fi.metropolia.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageFactoryTest {
    @BeforeEach
    void setUp() {
    }

    @Test
    void createImage() {
        Image image = ImageFactory.createImage("src/test/resources/test.png");
        assertNotNull(image);
    }
    @Test
    void createImageWithInvalidPath() {
        Image image = ImageFactory.createImage("src/test/resources/test.jpg");
        assertNull(image);
    }
}