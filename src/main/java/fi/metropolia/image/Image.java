package fi.metropolia.image;

import fi.metropolia.logger.Logger;

import java.awt.image.BufferedImage;

public abstract class Image {
    protected int[][] matrix;
    protected Logger logger = Logger.getInstance();
    protected int width;
    protected int height;
    protected String path;

    protected Image(String path) {
        this.path = path;
        this.matrix = load();
    }
    protected Image(int[][] matrix) {
        this.path = "";
        this.matrix = matrix;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getPath() {
        return path;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setPath(String path) {
        this.path = path;
    }

    protected abstract int[][] load();

    public abstract void write(String path);

    void write(String path, String type) {
        logger.info("Writing image to: " + path);
        if (this.getMatrix().length == 0) {
            logger.error("Image is empty");
            return;
        }
        BufferedImage bimage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                bimage.setRGB(x, y, this.getMatrix()[y][x]);
            }
        }
        try {
            if (!new java.io.File(path).exists()) {
                java.io.File file = new java.io.File(path);
                boolean created=file.createNewFile();
                if(created){
                    logger.info("File created: " + file.getName());
                }else{
                    logger.warning("File already exists and will be overwritten: " + file.getName());
                }
            }
            javax.imageio.ImageIO.write(bimage, type, new java.io.File(path));
        } catch (java.io.IOException e) {
            logger.error("Error writing image: " + e.getMessage());
        }
    }
}
