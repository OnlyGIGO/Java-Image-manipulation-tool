package fi.metropolia.image.processing;

public class InterpolationStrategyFactory {
    private InterpolationStrategyFactory() {
    }
    public static ImageProcessingCommand createInterpolationStrategy(String userInput) {
        if ("bicubic".equalsIgnoreCase(userInput)) {
            return new BicubicInterpolation();
        } else {
            throw new IllegalArgumentException("Unsupported interpolation method: " + userInput);
        }
    }
}