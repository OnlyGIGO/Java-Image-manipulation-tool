package fi.metropolia;

import fi.metropolia.arguments.Argument;
import fi.metropolia.arguments.ArgumentBuilder;
import fi.metropolia.arguments.ArgumentParser;
import fi.metropolia.image.Image;
import fi.metropolia.image.ImageFactory;
import fi.metropolia.image.processing.*;
import fi.metropolia.logger.Logger;

public class Main
{
    public static void main( String[] args )
    {
        Argument verbose = new ArgumentBuilder().setName("verbose")
                .setDescription("Verbosity level(0-3), example --verbose 2")
                .setDefaultValue(0)
                .setRequired(false)
                .setFlag(false)
                .setValidator(value -> {
                    try {
                        int val=Integer.parseInt(value.toString());
                        return val >= 0 && val <= 3;
                    } catch (NumberFormatException e) {
                        return false;
                    }

                })
                .build();
        Argument interpolation = new ArgumentBuilder().setName("interpolate")
                .setDescription("Interpolation method, example --interpolate bicubic")
                .setRequired(false)
                .setFlag(false)
                .setValidator(value ->true)
                .setDefaultValue("bicubic")
                .build();
        Argument angle = new ArgumentBuilder().setName("angle")
                .setDescription("Rotation angle(-360-360, negative value mean counterclockwise), example --angle 90")
                .setDefaultValue("0")
                .setRequired(false)
                .setFlag(false)
                .setValidator(value -> {
                    try {
                        Integer.parseInt(value.toString());
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }

                })
                .build();
        Argument inputFile = new ArgumentBuilder().setName("inputFile")
                .setDescription("Input file, example --inputFile input.png")
                .setDefaultValue("")
                .setRequired(true)
                .setFlag(false)
                .setValidator(value -> true)
                .build();
        Argument outputFile = new ArgumentBuilder().setName("outputFile")
                .setDescription("Output file, example --outputFile output.png")
                .setDefaultValue("output.png")
                .setRequired(true)
                .setFlag(false)
                .setValidator(value -> true)
                .build();
        Argument logType = new ArgumentBuilder().setName("logType")
                .setDescription("Log type(console, file), example --logType console")
                .setDefaultValue("console")
                .setRequired(false)
                .setFlag(false)
                .setValidator(value ->{
                    String val = value.toString();
                    return val.equalsIgnoreCase("console")||val.equalsIgnoreCase("file");

                })
                .build();

        Argument[] arguments = {angle,interpolation,inputFile,outputFile,verbose,logType};

        ArgumentParser parser = new ArgumentParser(arguments);
        try{
        parser.parse(args);
        }catch (IllegalArgumentException e){
            Logger.getInstance().error(e.getMessage());
            System.exit(1);
        }

        String inputFileArgValue = inputFile.getValue().toString();
        String outputFileArgValue = outputFile.getValue().toString();
        int verboseArgValue = Integer.parseInt(verbose.getValue().toString());
        String interpolationArgValue = (String) interpolation.getValue();
        int angleArgValue = Integer.parseInt((String) angle.getValue());
        String logTypeValue = logType.getValue().toString();

        Logger.Type type = Logger.Type.fromString(logTypeValue);
        Logger.DisplayLevel displayLevel=Logger.DisplayLevel.fromInt(verboseArgValue);

        Logger.getInstance().setDisplayLevel(displayLevel);
        Logger.getInstance().setType(type);

        ImageProcessingCommand rotate = new Rotate();
        ImageProcessingCommand interpolate = InterpolationStrategyFactory.createInterpolationStrategy(interpolationArgValue);

        Image image= ImageFactory.createImage(inputFileArgValue);
        if (image==null){
            Logger.getInstance().error("Unsupported file type");
            System.exit(1);
        }
        rotate.setParameters(angleArgValue);
        rotate.execute(image);
        interpolate.execute(image);
        image.write(outputFileArgValue);
    }
}
