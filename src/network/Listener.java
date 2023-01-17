package network;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Listener implements Runnable {

    private final ObjectInputStream input;
    private final String name;

    public Listener(ObjectInputStream input, int entity) {
        this.input = input;
        if (entity == 1) {
            name = "(Guest) ";
        } else {
            name = "(Host) ";
        }
    }

    @Override
    public void run() {
        String[] payload;
        do {
            try {
                payload = (String[]) input.readObject();
                System.out.println(payload[0] + ": " + payload[1]);
            } catch(ClassNotFoundException | IOException cnf) {
                System.out.println(name + "Unknown command sent.");
                break;
            }
        } while(!payload[1].equals("exit"));
    }
}
