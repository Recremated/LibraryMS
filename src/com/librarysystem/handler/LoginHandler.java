package com.librarysystem.handler;

import com.librarysystem.model.Librarian;

import java.util.Scanner;

public class LoginHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static boolean login(Librarian librarian) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter librarian username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (librarian.getName().equals(username) && librarian.checkPassword(password)) {
                System.out.println("✅ Login successful. Welcome, " + username + "!");
                return true;
            } else {
                System.out.println("❌ Incorrect credentials.");
                attempts++;
            }
        }
        return false;
    }
}