package fi.metropolia.arguments;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentBuilderTest {
    ArgumentBuilder test;

    @BeforeEach
    void setUp() {
        test = new ArgumentBuilder();
    }

    @Test
    void setName() {
        Argument argument=test.setName("test").build();
        assertEquals("test", argument.getName());
    }

    @Test
    void setDescription() {
        Argument argument=test.setDescription("Test argument").build();
        assertEquals("Test argument", argument.getDescription());
    }

    @Test
    void setDefaultValue() {
        Argument argument=test.setDefaultValue("default").build();
        assertEquals("default", argument.getDefaultValue());
    }

    @Test
    void setRequired() {
        Argument argument=test.setRequired(true).build();
        assertTrue(argument.isRequired());
    }

    @Test
    void setFlag() {
        Argument argument=test.setFlag(true).build();
        assertTrue(argument.isFlag());
    }

    @Test
    void setValidator() {
        Argument argument=test.setValidator(value -> true).build();
        assertTrue(argument.validator("test"));
    }

    @Test
    void build() {
        Argument argument=test.setName("test")
                .setDescription("Test argument")
                .setDefaultValue("default")
                .setRequired(true)
                .setValidator(value -> true)
                .build();
        assertEquals("test", argument.getName());
        assertEquals("Test argument", argument.getDescription());
        assertEquals("default", argument.getDefaultValue());
        assertTrue(argument.isRequired());
        assertFalse(argument.isFlag());
        assertTrue(argument.validator("test"));
    }
}