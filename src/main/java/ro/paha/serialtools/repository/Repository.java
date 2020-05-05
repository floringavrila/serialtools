package ro.paha.serialtools.repository;

public interface Repository {

    public boolean open();

    public void write(String message);

    public boolean close();
}
