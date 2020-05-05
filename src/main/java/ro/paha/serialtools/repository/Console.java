package ro.paha.serialtools.repository;

public class Console implements Repository {
    @Override
    public boolean open() {
        return true;
    }

    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public boolean close() {
        return true;
    }
}
