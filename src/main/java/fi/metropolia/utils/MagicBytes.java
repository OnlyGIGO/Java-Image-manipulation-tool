package fi.metropolia.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public enum MagicBytes {
    PNG(0x89, 0x50),  // PNG magic bytes
    JPG(0xFF, 0xD8);  // JPEG magic bytes

    private final int[] magicBytes;

    private MagicBytes(int...bytes) {
        magicBytes = bytes;
    }

    public boolean is(byte[] bytes) {
        if (bytes.length != magicBytes.length)
            throw new RuntimeException("I need the first "+magicBytes.length
                    + " bytes of an input stream.");
        for (int i=0; i<bytes.length; i++)
            if (Byte.toUnsignedInt(bytes[i]) != magicBytes[i])
                return false;
        return true;
    }

    public static byte[] extract(InputStream is, int length) throws IOException {
        try (is) {
            byte[] buffer = new byte[length];
            is.read(buffer, 0, length);
            return buffer;
        }
    }

    public boolean is(File file) throws IOException {
        return is(Files.newInputStream(file.toPath()));
    }

    public boolean is(InputStream is) throws IOException {
        return is(extract(is, magicBytes.length));
    }
}
