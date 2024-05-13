package fi.metropolia.arguments;

public interface Argument {
    String getName();
    String getDescription();
    Object getDefaultValue();
    Object getValue();
    void setValue(Object value);
    boolean isRequired();
    boolean isFlag();
    boolean validator(Object value);
}
