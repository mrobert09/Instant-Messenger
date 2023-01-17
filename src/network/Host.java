package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Host {
    private final String name;
    private final int port;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;

    public Host(String name, int port) {
        this.name = name;
        this.port = port;
    }

    public void runHost() {
        try {
            this.server = new ServerSocket(this.port, 2);
            try {
                waitForConnection();
                setupStreams();
                setupThreads();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitForConnection() throws IOException {
        System.out.println("(Host) Waiting for guest to connect...");
        connection = server.accept();
        System.out.println("(Host) Connected to " + connection.getInetAddress().getHostAddress());
    }

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("(Host) Stream connection established.");
    }

    private void setupThreads() {
        Listener listener = new Listener(input, 1);
        Thread listenThread = new Thread(listener);
        listenThread.start();

        Writer writer = new Writer(name, output);
        Thread writeThread = new Thread(writer);
        writeThread.start();
    }
}
