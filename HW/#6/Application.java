import java.io.IOException;

public interface Application {
    boolean login(String userID, String password);
    void register();
    User register(Object o, String password, String name) throws IOException, NullPointerException, UnsupportedOperationException;
}
