import network.Guest;
import network.Host;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = scanner.nextLine();
        System.out.println("1. Host");
        System.out.println("2. Connect to host");
        int choice = scanner.nextInt();
        if (choice == 1) {
            Host host = new Host(name, 23456);
            host.runHost();
        } else {
            System.out.print("Connect to what IP?");
            String ip = scanner.nextLine();
            Guest guest = new Guest(name, ip, 23456);
            guest.runGuest();
        }
    }
}