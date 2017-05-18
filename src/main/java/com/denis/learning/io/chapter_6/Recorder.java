package com.denis.learning.io.chapter_6;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.*;

/**
 * What if the input data in the buffer does not contain a complete multibyte character? That is,
 * what if it reads in only the first byte of a 2-byte or longer character? In this case, that character
 * is replaced by the replacement character (usually a question mark). However, suppose you have a long
 * stream that requires multiple reads from the channel into the buffer—that is, say the entire stream
 * can’t fit into the buffer at once. Or suppose the channel is nonblocking and the first couple of
 * bytes of a 3- or 4-byte character have arrived, but the last bytes haven’t. In other words, suppose
 * the data in the buffer is malformed, even though the stream itself isn’t. The encode( ) method does
 * not leave anything in the buffer. It will drain the buffer completely and use replacement characters
 * at the end if necessary. This has the potential to corrupt good data, and it can be a very hard bug
 * to diagnose because 99% of the time you’re not going to hit the fencepost condition that triggers the
 * bug. (One way to make it a little more likely to show up is to reduce the size of the buffer to something
 * quite small, even three or four bytes.)
 *
 * @see CharsetDecoder
 */
public class Recorder {
    public static void main(String[] args) throws IOException {
        System.out.println(Charset.availableCharsets());
        Charset utf8 = Charset.forName("UTF-8");
        Charset big5 = Charset.forName("x-MacArabic");
        convert(utf8, big5, System.in, System.out);
    }

    static void convert(Charset inputEncoding, Charset outputEncoding,
                               InputStream in, OutputStream out) throws IOException {
        ReadableByteChannel readableChannel = Channels.newChannel(in);
        WritableByteChannel writableChannel = Channels.newChannel(out);

        for (ByteBuffer buffer = ByteBuffer.allocate(5 * 1024); readableChannel.read(buffer) != -1; buffer.clear()) {
            buffer.flip();
            CharBuffer decoded = inputEncoding.decode(buffer);
            ByteBuffer encodedBuffer = outputEncoding.encode(decoded);
            while (encodedBuffer.hasRemaining()) writableChannel.write(encodedBuffer);
        }
    }

    //better
    static ByteBuffer convertToUTF8(String s) throws IOException {
        CharBuffer input = CharBuffer.wrap(s);
        CharsetEncoder encoder = StandardCharsets.UTF_8.newEncoder();
        //encoder.maxBytesPerChar()

        //In UTF-8, each char in the string is encoded into at !!most!! three bytes in the output array,
        //so there’s no possibility of underflow or overflow.
        ByteBuffer output = ByteBuffer.allocate(input.length() * 3);

        while (input.hasRemaining()) {
            //there is a small chance of the data being malformed if surrogate characters
            //are used incorrectly in the input string. Java doesn’t check for this.
            CoderResult result = encoder.encode(input, output, false);
            if(result.isError()) throw new IOException("Could not encode " + s);
        }

        encoder.encode(input, output, true);
        encoder.flush(output);
        output.flip();

        return output;
    }
}
