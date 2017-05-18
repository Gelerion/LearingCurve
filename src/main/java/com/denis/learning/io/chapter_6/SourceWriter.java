package com.denis.learning.io.chapter_6;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * There are no subclasses of FilterWriter in the core API. Example 20-8, SourceWriter, is an example of
 * a FilterWriter that converts Unicode text to \\u-escaped ASCII. The big question is what to do if
 * the input text contains an unescaped backslash. The simplest and most robust solution is to replace
 * it with \u005C, the Unicode escape for the backslash itself.
 */
public class SourceWriter extends FilterWriter {

    protected SourceWriter(Writer out) {
        super(out);
    }

    public void write(char[] text, int offset, int length) throws IOException {
        for (int i = offset; i < offset + length; i++) {
            this.write(text[i]);
        }
    }

    public void write(String s, int offset, int length) throws IOException {
        for (int i = offset; i < offset + length; i++) {
            this.write(s.charAt(i));
        }
    }

    @Override
    public void write(int c) throws IOException {
        if (c == '\\') out.write("\\u005C");
        else if (c < 128) out.write(c);
        else {
            String hexString = Integer.toHexString(c);
            // Pad with leading zeroes if necessary.
            if (c < 256) hexString = "00" + hexString;
            else if (c < 4096) hexString = "0" + hexString;

            out.write("\\u");
            out.write(hexString);
        }
    }
}
