package fi.metropolia.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoggerTest {
    private Logger logger;

    @BeforeEach
    void setUp() {
        logger = Logger.getInstance();
        logger.setType(Logger.Type.CONSOLE);
        logger.setDisplayLevel(Logger.DisplayLevel.ALL);
    }

    @Test
    void getInstance() {
        Logger logger2 = Logger.getInstance();
        assertEquals(logger, logger2);
    }

    @Test
    void setDisplayLevel() {
        logger.setDisplayLevel(Logger.DisplayLevel.NONE);
        assertEquals(Logger.DisplayLevel.NONE, logger.getDisplayLevel());
    }

    @Test
    void setType() {
        logger.setType(Logger.Type.FILE);
        assertEquals(Logger.Type.FILE, logger.getType());
    }

    @Test
    void setFile() {
        logger.setFile(new File("test.txt"));
        assertEquals("test.txt", logger.getFile().getName());
    }

    @Test
    void info() {
        logger.info("Test");
    }

    @Test
    void warning() {
        logger.warning("Test");
    }

    @Test
    void error() {
        logger.error("Test");
    }
}