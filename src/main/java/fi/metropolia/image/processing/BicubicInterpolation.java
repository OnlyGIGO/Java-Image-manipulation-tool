package fi.metropolia.image.processing;

import fi.metropolia.image.Image;
import fi.metropolia.logger.Logger;

public class BicubicInterpolation implements ImageProcessingCommand{
    private Logger logger = Logger.getInstance();
    private final int KERNEL_SIZE=4;
    @Override
    public void execute(Image image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] matrix = image.getMatrix();
        int[][] newMatrix = new int[height][width];
        int kernelCenter = (int)(KERNEL_SIZE / 2.0f);

        double[][] weights = new double[KERNEL_SIZE+1][KERNEL_SIZE+1];
        for (int i = 0; i < KERNEL_SIZE; i++) {
            for (int j = 0; j < KERNEL_SIZE; j++) {
                double distance = Math.sqrt(Math.pow((double)i - kernelCenter, 2) + Math.pow((double)j - kernelCenter, 2));
                weights[i][j] = cubicBSplineWeight(distance);
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] sum = new int[3];
                double[] totalWeight = new double[3];
                if (matrix[y][x] != 0) {
                    newMatrix[y][x] = matrix[y][x];
                    continue;
                }
                for (int i = -kernelCenter; i <= kernelCenter; i++) {
                    for (int j = -kernelCenter; j <= kernelCenter; j++) {
                        int newY = y + i;
                        int newX = x + j;
                        if (newY < 0 || newY >= height || newX < 0 || newX >= width||matrix[newY][newX] == 0) {
                            continue;
                        }
                        int rgb = matrix[newY][newX];
                        sum[0] += (int) (((rgb >> 16) & 0xFF) * weights[i+kernelCenter][j+kernelCenter]);
                        sum[1] += (int) (((rgb >> 8) & 0xFF) * weights[i+kernelCenter][j+kernelCenter]);
                        sum[2] += (int) ((rgb & 0xFF) * weights[i+kernelCenter][j+kernelCenter]);
                        totalWeight[0] += weights[i+kernelCenter][j+kernelCenter];
                        totalWeight[1] += weights[i+kernelCenter][j+kernelCenter];
                        totalWeight[2] += weights[i+kernelCenter][j+kernelCenter];
                    }
                }
                for (int c = 0; c < 3; c++) {
                    if (totalWeight[c] == 0.0) {
                        continue;
                    }
                    sum[c] = (int) Math.round(sum[c] / totalWeight[c]);
                }
                newMatrix[y][x] = (sum[0] << 16) | (sum[1] << 8) | sum[2];
            }
        }
        image.setMatrix(newMatrix);
        logger.info("Interpolation done");

    }

    @Override
    public void setParameters(Object... parameters) {
        // No parameters needed
    }


    private double cubicBSplineWeight(double distance) {
        double result = 0;
        double absDistance = Math.abs(distance);
        if (absDistance <= 1) {
            result = (2.0 / 3.0) - absDistance * absDistance * (0.5 - absDistance);
        } else if (absDistance> 1 && absDistance <= 2) {
            result = Math.pow(2 - absDistance, 3) / 6.0;
        }
        return result;
    }
}
