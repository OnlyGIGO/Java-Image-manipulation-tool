package fi.metropolia.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PngImage extends Image{
    public PngImage(String path) {
        super(path);
    }
    public PngImage(int[][] matrix) {
        super(matrix);
        width = matrix[0].length;
        height = matrix.length;
    }

    public void write(String path){
        super.write(path,"png");
    }
    @Override
    protected int[][] load() {
        try {
            File file = new File(path);
            logger.info("Loading image: " + file.getName());
            BufferedImage image = ImageIO.read(file);

            int width = image.getWidth();
            int height = image.getHeight();

            int[][] matrix = new int[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);
                    matrix[y][x] = rgb;
                }
            }
            logger.info("Image loaded successfully");
            this.height = height;
            this.width = width;
            return matrix;

        } catch (IOException e) {
            logger.error("Error loading image: " + e.getMessage());
            return new int[][]{};
        }
    }
}
