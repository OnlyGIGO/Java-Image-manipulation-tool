package fi.metropolia.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MagicBytesTest {

    @Test
    void isPng() {
        byte[] bytes = new byte[]{(byte) 0x89, (byte) 0x50};
        assertTrue(MagicBytes.PNG.is(bytes));
    }


    @Test
    void isJpg() {
        byte[] bytes = new byte[]{(byte) 0xFF, (byte) 0xD8};
        assertTrue(MagicBytes.JPG.is(bytes));
    }

    @Test
    void isNotJpg() {
        byte[] bytes = new byte[]{(byte) 0x89, (byte) 0x51};
        assertFalse(MagicBytes.JPG.is(bytes));
    }
    @Test
    void isNotPng() {
        byte[] bytes = new byte[]{(byte) 0x89, (byte) 0x51};
        assertFalse(MagicBytes.PNG.is(bytes));
    }

}