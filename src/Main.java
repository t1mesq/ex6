import service.ServerHandler;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new ServerHandler("localhost", 9889).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}