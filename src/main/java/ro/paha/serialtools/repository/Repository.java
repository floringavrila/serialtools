package ro.paha.serialtools.repository;

public interface Repository {

    public interface OnLineReceivedListener {
        public void onLineReceived(String line);
    }

    public void setOnLineReceivedListener(OnLineReceivedListener listener);

    public boolean open();

    public void write(String message);

    public boolean close();
}
