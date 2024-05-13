package fi.metropolia.logger;

import java.io.*;

public enum Logger {
    INSTANCE;

    private static final String RED=Colors.RED.getColor();
    private static final String GREEN=Colors.GREEN.getColor();
    private static final String YELLOW=Colors.YELLOW.getColor();
    private static final String RESET=Colors.RESET.getColor();
    private static final String WHITE=Colors.WHITE.getColor();

    public static Logger getInstance() {
        return INSTANCE;
    }



    public enum Type {
        CONSOLE, FILE;

        public static Type fromString(String string) {
            switch (string.toUpperCase()) {
                case "CONSOLE":
                    return CONSOLE;
                case "FILE":
                    return FILE;
                default:
                    throw new IllegalArgumentException("Invalid string value for Type: " + string);
            }
        }
    }

    public enum DisplayLevel {
        ALL(3),
        MEDIUM(2),
        LOW(1),
        NONE(0);

        private final int value;

        DisplayLevel(int value) {
            this.value = value;
        }


        public static DisplayLevel fromInt(int value) {
            switch (value) {
                case 0:
                    return NONE;
                case 1:
                    return LOW;
                case 2:
                    return MEDIUM;
                case 3:
                    return ALL;
                default:
                    throw new IllegalArgumentException("Invalid integer value for DisplayLevel: " + value);
            }
        }
    }

    private enum Level {
        INFO, WARNING, ERROR
    }
    private DisplayLevel displayLevel = DisplayLevel.ALL;
    private Type type = Type.CONSOLE;
    private File file = new File(".JImage_log.txt");

    public void setDisplayLevel(DisplayLevel displayLevel) {
        this.displayLevel = displayLevel;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void info(String message) {
       log(message, Level.INFO);
    }

    public void warning(String message) {
        log(message, Level.WARNING);
    }

    public void error(String message) {
        log(message, Level.ERROR);
    }


    private void writeToFile(String message){
        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            writer.println(message);
        } catch (IOException e) {
            System.out.printf("%s%s: %s%s%n", RED, Level.ERROR, e.getMessage(), RESET);
        }
    }

    public DisplayLevel getDisplayLevel() {
        return displayLevel;
    }

    public Type getType() {
        return type;
    }

    public File getFile() {
        return file;
    }
    private void log(String message, Level level) {
        switch (displayLevel){
            case ALL:
                if(level==Level.INFO){
                    if(type == Type.CONSOLE){
                        System.out.printf("%s%s: %s%s%n", GREEN, level, message, RESET);
                    } else if (type == Type.FILE){
                        writeToFile(String.format("%s: %s", level, message));
                    }
                }
            case MEDIUM:
                if(level==Level.WARNING){
                    if(type == Type.CONSOLE){
                        System.out.printf("%s%s: %s%s%n", YELLOW, level, message, RESET);
                    } else if (type == Type.FILE){
                        writeToFile(String.format("%s: %s", level, message));
                    }
                }
                case LOW:
                    if(level==Level.ERROR){
                        if(type == Type.CONSOLE){
                            System.out.printf("%s%s: %s%s%n", RED, level, message, RESET);
                        } else if (type == Type.FILE){
                            writeToFile(String.format("%s: %s", level, message));
                        }
                    }
            case NONE:
                break;
        }
    }

}
