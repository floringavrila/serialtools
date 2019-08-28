package ro.paha.serialtools.delimiter;

public class CarriageReturn implements Delimiter {

    public byte[] getDelimiter() {
        return new byte[]{(byte) 0x0d};
    }
}
