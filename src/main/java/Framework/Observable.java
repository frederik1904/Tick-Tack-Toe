package Framework;

public interface Observable {
    void attatch(Observer o);
    void detatch(Observer o);
    void notifyUpdate();
}
