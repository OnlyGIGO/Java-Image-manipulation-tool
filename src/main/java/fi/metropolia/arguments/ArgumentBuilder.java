package fi.metropolia.arguments;

public class ArgumentBuilder {
    private String name;
    private String description;
    private Object defaultValue=null;
    private Object value=null;
    private boolean required;
    private boolean flag;
    private Validator validator= val -> true;

    public ArgumentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ArgumentBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ArgumentBuilder setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }


    public ArgumentBuilder setRequired(boolean required) {
        this.required = required;
        return this;
    }

    public ArgumentBuilder setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public ArgumentBuilder setValidator(Validator validator) {
        this.validator = validator;
        return this;
    }

    public Argument build() {
        return new Argument() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getDescription() {
                return description;
            }

            @Override
            public Object getDefaultValue() {
                return defaultValue;
            }

            @Override
            public Object getValue() {
                if (value == null) {
                    value=defaultValue;
                }
                return value;
            }

            @Override
            public void setValue(Object val) {
                value = val;
            }


            @Override
            public boolean isRequired() {
                return required;
            }

            @Override
            public boolean isFlag() {
                return flag;
            }

            @Override
            public boolean validator(Object value) {
                try{
                    return validator.validate(value);
                } catch (Exception e) {
                    return false;
                }
            }
            @Override
            public String toString() {
                return String.format("Argument{name='%s', description='%s', defaultValue='%s', value='%s', required=%s, flag=%s}", name, description, defaultValue, value, required, flag);
            }
        };
    }
}
