package fi.metropolia.image.processing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpolationStrategyFactoryTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void createInterpolationStrategy() {
        ImageProcessingCommand bicubic = InterpolationStrategyFactory.createInterpolationStrategy("bicubic");
        assertNotNull(bicubic);
    }
    @Test
    void createInterpolationStrategyWithInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> InterpolationStrategyFactory.createInterpolationStrategy("invalid"));
    }
}