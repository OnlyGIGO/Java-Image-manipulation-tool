package fi.metropolia.image.processing;

import fi.metropolia.image.Image;
import fi.metropolia.logger.Logger;

public class Rotate implements ImageProcessingCommand {

    private int degrees;
    private Logger logger = Logger.getInstance();

    public void setParameters(Object... parameters) throws IllegalArgumentException {
        if (parameters.length != 1) {
            throw new IllegalArgumentException("Rotate command requires a parameter: angle");
        }
        degrees = (Integer) parameters[0];
    }
    public void execute(Image image) {
        boolean clockwise = true;
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] matrix = image.getMatrix();
        degrees = degrees % 360;
        if (degrees < 0) {
            logger.info("Negative degrees, turning other way around");
            degrees = -degrees;
            clockwise = false;
        } else if(degrees==0){
            logger.info("Degrees is 0, no rotation done");
            return;
        }
        double[][] transformation = new double[2][2];
        if(clockwise){
            transformation[0][0] = Math.cos(Math.toRadians(degrees));
            transformation[0][1] = -Math.sin(Math.toRadians(degrees));
            transformation[1][0] = Math.sin(Math.toRadians(degrees));
            transformation[1][1] = Math.cos(Math.toRadians(degrees));
        }else{
            transformation[0][0] = Math.cos(Math.toRadians(degrees));
            transformation[0][1] = Math.sin(Math.toRadians(degrees));
            transformation[1][0] = -Math.sin(Math.toRadians(degrees));
            transformation[1][1] = Math.cos(Math.toRadians(degrees));
        }


        int newHeight;
        int newWidth;
        if(degrees==90){
            newHeight=width;
            newWidth=height;
        }else if(degrees<90){
            newHeight = (int) Math.ceil(width*Math.sin(Math.toRadians(degrees)) + height*Math.cos(Math.toRadians(degrees)));
            newWidth = (int) Math.ceil(width*Math.cos(Math.toRadians(degrees)) + height*Math.sin(Math.toRadians(degrees)));
        }else if(degrees>180&&degrees<270){
            int temp = degrees-180;
            newHeight = (int) Math.ceil(width*Math.sin(Math.toRadians(temp)) + height*Math.cos(Math.toRadians(temp)));
            newWidth = (int) Math.ceil(width*Math.cos(Math.toRadians(temp)) + height*Math.sin(Math.toRadians(temp)));

        }else if(degrees>270){
            int temp = degrees-270;
            newHeight = (int) Math.ceil(height*Math.sin(Math.toRadians(temp)) + width*Math.cos(Math.toRadians(temp)));
            newWidth = (int) Math.ceil(height*Math.cos(Math.toRadians(temp)) + width*Math.sin(Math.toRadians(temp)));
        }
        else {
            int temp = degrees-90;
            newHeight = (int) Math.ceil(height*Math.sin(Math.toRadians(temp)) + width*Math.cos(Math.toRadians(temp)));
            newWidth = (int) Math.ceil(height*Math.cos(Math.toRadians(temp)) + width*Math.sin(Math.toRadians(temp)));
        }
        int[][] newMatrix = new int[newHeight][newWidth];

        int centerX = width / 2;
        int centerY = height / 2;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int newX = x - centerX;
                int newY = y - centerY;
                int transformedX = (int) Math.round(transformation[0][0] * (newX) + transformation[0][1] *(newY))+newWidth/2;
                int transformedY = (int) Math.round(transformation[1][0] * (newX) + transformation[1][1] *(newY))+newHeight/2;
                if (transformedX < 0 || transformedX >= newWidth || transformedY < 0 || transformedY >= newHeight) {
                    logger.warning("Rotation caused pixel to be outside of image");
                    continue;
                }
                newMatrix[transformedY][transformedX] = matrix[y][x];
            }
        }

        image.setWidth(newWidth);
        image.setHeight(newHeight);
        image.setMatrix(newMatrix);
        logger.info("Rotated image "+degrees+" degrees");

    }
}
