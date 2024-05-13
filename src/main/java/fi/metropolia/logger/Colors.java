package fi.metropolia.logger;

public enum Colors{
    RESET("\u001B[0m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    private final String color;

    private Colors(String s) {
        this.color = s;
    }
    public String getColor() {
        return color;
    }
}
