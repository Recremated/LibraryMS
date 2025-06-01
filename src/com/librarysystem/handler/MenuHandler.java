// Updated MenuHandler.java
package com.librarysystem.handler;

import com.librarysystem.model.Library;
import com.librarysystem.model.Librarian;
import com.librarysystem.service.BookService;
import com.librarysystem.service.MemberService;

import java.util.Scanner;

public class MenuHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static void runMainMenu(Library library, BookService bookService, MemberService memberService, Librarian librarian) {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = getInt("Choose an option: ");

            switch (choice) {
                case 1 -> HandlerUtils.addBook(bookService);
                case 2 -> bookService.listAllBooks();
                case 3 -> HandlerUtils.searchBookByID(bookService);
                case 4 -> HandlerUtils.borrowBookEnhanced(library, bookService, memberService, librarian);
                case 5 -> HandlerUtils.returnBook(library, bookService, memberService, librarian);
                case 6 -> showMemberMenu(memberService);
                case 7 -> HandlerUtils.listBooksByAuthor(bookService);
                case 8 -> HandlerUtils.listBooksByCategory(bookService);
                case 9 -> HandlerUtils.updateBook(bookService);
                case 10 -> HandlerUtils.deleteBook(bookService);
                case 11 -> showMemberDetailsMenu(memberService);
                case 0 -> {
                    System.out.println("Exiting... Bye!");
                    running = false;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
        System.out.println("📚 BOOK OPERATIONS:");
        System.out.println("1. Add New Book");
        System.out.println("2. List All Books");
        System.out.println("3. Search Book by ID");
        System.out.println("7. List Books by Author");
        System.out.println("8. List Books by Category");
        System.out.println("9. Update Book Price");
        System.out.println("10. Delete Book");

        System.out.println("\n👥 MEMBER OPERATIONS:");
        System.out.println("6. Register New Member");
        System.out.println("11. Show Member Details");

        System.out.println("\n📖 LIBRARY OPERATIONS:");
        System.out.println("4. Borrow Book");
        System.out.println("5. Return Book");

        System.out.println("\n0. Exit");
        System.out.println("=====================================");
    }

    private static void showMemberMenu(MemberService memberService) {
        System.out.println("\n=== MEMBER REGISTRATION ===");
        System.out.println("1. Register Student");
        System.out.println("2. Register Faculty");
        System.out.println("3. Register General Member");
        System.out.println("4. Back to Main Menu");

        int choice = getInt("Choose option: ");

        switch (choice) {
            case 1 -> HandlerUtils.registerStudent(memberService);
            case 2 -> HandlerUtils.registerFaculty(memberService);
            case 3 -> HandlerUtils.registerMember(memberService);
            case 4 -> System.out.println("Returning to main menu...");
            default -> System.out.println("Invalid option.");
        }
    }

    private static void showMemberDetailsMenu(MemberService memberService) {
        System.out.println("\n=== MEMBER DETAILS ===");
        System.out.println("1. Show Specific Member Details");
        System.out.println("2. List All Students");
        System.out.println("3. List All Faculty");
        System.out.println("4. Back to Main Menu");

        int choice = getInt("Choose option: ");

        switch (choice) {
            case 1 -> {
                int memberId = getInt("Enter member ID: ");
                memberService.showMemberDetails(memberId);
            }
            case 2 -> memberService.listAllStudents();
            case 3 -> memberService.listAllFaculty();
            case 4 -> System.out.println("Returning to main menu...");
            default -> System.out.println("Invalid option.");
        }
    }

    private static int getInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}