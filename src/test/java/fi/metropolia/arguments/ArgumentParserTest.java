package fi.metropolia.arguments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserTest {

    @Test
    void parse() {
       Argument test = new ArgumentBuilder()
               .setName("test")
               .setDescription("Test argument")
               .setDefaultValue("default")
               .setRequired(true)
               .setValidator(value -> true)
               .build();
        ArgumentParser parser=new ArgumentParser(test);
        assertThrows(IllegalArgumentException.class, () -> parser.parse(new String[]{}));
        assertDoesNotThrow(() -> parser.parse(new String[]{"--test", "value"}));
        assertEquals("value", test.getValue());

        Argument test2 = new ArgumentBuilder()
                .setName("test2")
                .setDescription("Test argument")
                .setDefaultValue("default")
                .setRequired(false)
                .setValidator(value -> false)
                .build();
        ArgumentParser parser2=new ArgumentParser(test2);
        assertThrows(IllegalArgumentException.class, () -> parser2.parse(new String[]{"--test2", "value"}));
        assertDoesNotThrow(() -> parser2.parse(new String[]{}));
    }
}