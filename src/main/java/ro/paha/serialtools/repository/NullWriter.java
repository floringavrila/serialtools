package ro.paha.serialtools.repository;

public class NullWriter extends EventEmitter {
    @Override
    public boolean open() {
        return true;
    }

    public void write(String message) {
        super.write(message);
    }

    @Override
    public boolean close() {
        return true;
    }
}
