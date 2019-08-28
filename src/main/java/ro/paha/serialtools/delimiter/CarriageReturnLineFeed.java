package ro.paha.serialtools.delimiter;

public class CarriageReturnLineFeed implements Delimiter {

    public byte[] getDelimiter() {
        return new byte[]{(byte) 0x0d, (byte) 0x0a};
    }
}
