package ro.paha.serialtools.repository;

abstract class EventEmitter implements Repository {

    private OnLineReceivedListener listener;

    public void setOnLineReceivedListener(OnLineReceivedListener listener) {
        this.listener = listener;
    }

    public abstract boolean open();

    public void write(String line) {
        if (listener != null) {
            listener.onLineReceived(line);
        }
    }

    public abstract boolean close();
}
