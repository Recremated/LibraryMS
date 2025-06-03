package com.librarysystem.handler;

import com.librarysystem.model.Library;
import com.librarysystem.model.Librarian;
import com.librarysystem.service.BookService;
import com.librarysystem.service.MemberService;

import java.util.Scanner;

public class MenuHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static void runMainMenu(Library library, BookService bookService,
                                   MemberService memberService, Librarian librarian) {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = getInt("Choose an option: ");

            switch (choice) {
                case 1 -> HandlerUtils.addBook(bookService);
                case 2 -> bookService.listAllBooks();
                case 3 -> HandlerUtils.searchBookByID(bookService);
                case 4 -> HandlerUtils.borrowBook(library, bookService, memberService, librarian);
                case 5 -> HandlerUtils.returnBook(library, bookService, memberService, librarian);
                case 6 -> HandlerUtils.registerMember(memberService);
                case 7 -> HandlerUtils.listBooksByAuthor(bookService);
                case 8 -> HandlerUtils.listBooksByCategory(bookService);
                case 9 -> HandlerUtils.updateBook(bookService);
                case 10 -> HandlerUtils.deleteBook(bookService);
                case 11 -> showMemberDetailsMenu(memberService);
                case 12 -> HandlerUtils.listBorrowedBooks(bookService);
                case 13 -> HandlerUtils.showMemberBorrowedBooks(memberService);
                case 14 -> HandlerUtils.searchWhoHasBook(bookService);
                case 15 -> HandlerUtils.searchBookWithAvailability(bookService);

                case 0 -> {
                    System.out.println("💾 Saving data and exiting... Bye!");
                    running = false;
                }
                default -> System.out.println("❌ Invalid option.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        LIBRARY MANAGEMENT SYSTEM");
        System.out.println("=".repeat(50));
        System.out.println("📚 BOOK OPERATIONS:");
        System.out.println("  1. Add New Book");
        System.out.println("  2. List All Books");
        System.out.println("  3. Search Book by ID");
        System.out.println("  7. List Books by Author");
        System.out.println("  8. List Books by Category");
        System.out.println("  9. Update Book Price");
        System.out.println("  10. Delete Book");
        System.out.println("  15. Check Book Availability"); // NEW OPTION

        System.out.println("\n👥 MEMBER OPERATIONS:");
        System.out.println("  6. Register New Member");
        System.out.println("  11. Show Member Details");

        System.out.println("\n📖 LIBRARY OPERATIONS:");
        System.out.println("  4. Borrow Book");
        System.out.println("  5. Return Book");

        System.out.println("\n📊 BORROWING REPORTS:");
        System.out.println("  12. List All Borrowed Books");
        System.out.println("  13. Show Member's Borrowed Books");
        System.out.println("  14. Check Who Has a Specific Book");


        System.out.println("\n  0. Exit");
        System.out.println("=".repeat(50));
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
            default -> System.out.println("❌ Invalid option.");
        }
    }

    private static int getInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty.");
                    continue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}