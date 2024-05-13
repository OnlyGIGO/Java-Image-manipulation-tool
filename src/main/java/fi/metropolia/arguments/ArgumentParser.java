package fi.metropolia.arguments;

import fi.metropolia.logger.Logger;

import java.util.ArrayList;

import java.util.List;

public class ArgumentParser {
    private final Argument[] arguments;
    private final Logger logger = Logger.getInstance();

    public ArgumentParser(Argument... arguments){
        this.arguments = arguments;
    }

    public void parse(String[] argv) throws IllegalArgumentException {
        List<Argument> parsedArgs= new ArrayList<>();
        for (int i = 0; i < argv.length; i++) {
            for (Argument argument : arguments) {
                if("--help".equalsIgnoreCase(argv[i])){
                    logger.info("Usage:");
                    for (Argument arg : arguments) {
                        logger.info(arg.getDescription());
                    }
                    System.exit(0);
                    return;
                }
                if (String.format("--%s",argument.getName()).equalsIgnoreCase(argv[i])) {
                    if (argument.isFlag()) {
                        argument.setValue(true);
                        parsedArgs.add(argument);
                    } else {
                        if (i + 1 < argv.length) {
                            if (argument.validator(argv[i + 1])) {
                                argument.setValue(argv[i + 1]);
                                parsedArgs.add(argument);
                            } else {
                                throw new IllegalArgumentException(String.format("Invalid value for argument %s%nUsage:%s", argument.getName(),argument.getDescription()));
                            }
                        } else {
                            throw new IllegalArgumentException(String.format("Missing value for argument %s%nUsage:%s", argument.getName(),argument.getDescription()));
                        }
                    }
                }
            }
        }
        for (Argument argument : arguments) {
            if (argument.isRequired() && !parsedArgs.contains(argument)) {
                throw new IllegalArgumentException("Required argument " + argument.getName() + " missing");
            } else if (!parsedArgs.contains(argument)) {
                argument.setValue(argument.getDefaultValue());
                parsedArgs.add(argument);
            }
        }

    }


}
