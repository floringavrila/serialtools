package ro.paha.serialtools.repository;

public class Console extends EventEmitter {
    @Override
    public boolean open() {
        return true;
    }

    public void write(String message) {
        super.write(message);
        System.out.println(message);
    }

    @Override
    public boolean close() {
        return true;
    }
}
