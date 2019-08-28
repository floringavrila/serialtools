package ro.paha.serialtools.delimiter;

public class LineFeed implements Delimiter {

    public byte[] getDelimiter() {
        return new byte[]{(byte) 0x0a};
    }
}
