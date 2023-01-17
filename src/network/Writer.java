package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Writer implements Runnable {

    private final ObjectOutputStream output;
    private String command = "";
    private final Scanner scanner = new Scanner(System.in);
    private final String name;

    public Writer (String name, ObjectOutputStream output) {
        this.name = name;
        this.output = output;
    }

    @Override
    public void run() {
        do {
            command = scanner.nextLine();
            sendCommand();
        } while (!command.equals("exit"));
    }

    public void sendCommand() {
        String[] payload = {name, command};
        try {
            output.writeObject(payload);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
