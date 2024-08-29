package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginRegistration authService = new LoginRegistration();
        int choice;
        boolean exit= false ;
        do {
            System.out.println("Register/Login \n  1.Register \n 2.Login  \n 3.Exit \n Enter your choice:");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.next();
                    System.out.print("Enter username: ");
                    String username = scanner.next();
                    System.out.print("Enter password: ");
                    String password = scanner.next();
                    authService.registerUser(name, username, password);
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    username = scanner.next();
                    System.out.print("Enter password: ");
                    password = scanner.next();
                    authService.loginUser(username, password);
                    break;

                case 3:
                    System.out.println("Exiting the application.");

                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        while (!exit);
    }
}
