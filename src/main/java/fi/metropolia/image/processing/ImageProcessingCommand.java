package fi.metropolia.image.processing;

import fi.metropolia.image.Image;

public interface ImageProcessingCommand {
    void execute(Image image);
    void setParameters(Object... parameters);
}
