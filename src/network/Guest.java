package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Guest {

    private final String hostIP;
    private final int port;
    private Socket connection;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private final String name;

    public Guest(String name, String ip, int port) {
        this.name = name;
        this.hostIP = ip;
        this.port = port;
    }

    public void runGuest() {
        try {
            connectToHost();
            setupStreams();
            setupThreads();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void connectToHost() throws IOException {
        System.out.println("(Guest) Attempting connection...");
        connection = new Socket(InetAddress.getByName(this.hostIP), this.port);
        System.out.println("(Guest) Connected to " + connection.getInetAddress().getHostName());
    }

    private void setupStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        System.out.println("(Guest) Streams connected.");
    }

    private void setupThreads() {
        Listener listener = new Listener(input, 2);
        Thread listenThread = new Thread(listener);
        listenThread.start();

        Writer writer = new Writer(name, output);
        Thread writeThread = new Thread(writer);
        writeThread.start();
    }
}
