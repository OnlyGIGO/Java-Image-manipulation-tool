package fi.metropolia.image.processing;

import fi.metropolia.image.Image;
import fi.metropolia.image.PngImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RotateTest {

    private ImageProcessingCommand rotate;
    @BeforeEach
    void setUp() {
        rotate = new Rotate();
    }

    @Test
    void execute() {
        //test the execute method with dummy image object, then iterate through it to check the pixels are rotated
        int[][] matrix = new int[][]{
                {1, 0, 0},
                {0, 0xffffff, 0},
                {0, 0, 1}
        };
        Image image = new PngImage(matrix);
        rotate.setParameters(90);
        rotate.execute(image);
        int[][] newMatrix = image.getMatrix();
        System.out.println(Arrays.deepToString(newMatrix));
        assertEquals(0, newMatrix[0][0]);
        assertEquals(0, newMatrix[1][0]);
        assertEquals(1, newMatrix[2][0]);
        assertEquals(0, newMatrix[0][1]);
        assertEquals(0xffffff, newMatrix[1][1]);
        assertEquals(0, newMatrix[2][1]);
        assertEquals(1, newMatrix[0][2]);
        assertEquals(0, newMatrix[1][2]);
        assertEquals(0, newMatrix[2][2]);
    }
}