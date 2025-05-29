package com.librarysystem.handler;

import com.librarysystem.model.*;
import com.librarysystem.service.BookService;
import com.librarysystem.service.MemberService;

import java.time.LocalDate;
import java.util.Scanner;

public class HandlerUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static void addBook(BookService bookService) {
        System.out.println("\n-- Add Book --");
        String id = getString("Enter book ID: ");
        String title = getString("Enter title: ");
        String author = getString("Enter author: ");
        double price = getDouble("Enter price: ");
        int edition = getInt("Enter edition: ");
        LocalDate date = LocalDate.now();

        Book book = new StudyBooks(id, author, title, price, edition, date);
        bookService.addBook(book);
    }

    public static void searchBookByID(BookService bookService) {
        System.out.println("\n-- Search Book by ID --");
        String id = getString("Enter book ID: ");
        Book book = bookService.findBookById(id);
        if (book != null) book.display();
        else System.out.println("Book not found.");
    }

    public static void registerMember(MemberService memberService) {
        System.out.println("\n-- Register New Member --");
        int id = getInt("Enter member ID: ");
        String name = getString("Enter name: ");
        String type = getString("Enter type (Student/Faculty): ");
        String date = getString("Enter membership date (YYYY-MM-DD): ");
        String address = getString("Enter address: ");
        String phone = getString("Enter phone: ");

        MemberRecord member = new MemberRecord(id, name, type, date, address, phone);
        memberService.registerMember(member);
    }

    public static void borrowBook(Library library, BookService bookService, MemberService memberService, Librarian librarian) {
        System.out.println("\n-- Borrow Book --");
        int memberId = getInt("Enter member ID: ");
        MemberRecord member = memberService.getMember(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        String bookId = getString("Enter book ID: ");
        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        Reader reader = new Reader(member.getName(), memberId);
        librarian.issueBook(book, reader, member);
        librarian.createBill(book, reader);
    }

    public static void returnBook(Library library, BookService bookService, MemberService memberService, Librarian librarian) {
        System.out.println("\n-- Return Book --");
        int memberId = getInt("Enter member ID: ");
        MemberRecord member = memberService.getMember(memberId);
        if (member == null) {
            System.out.println("Member not found.");
            return;
        }

        String bookId = getString("Enter book ID: ");
        Book book = bookService.findBookById(bookId);
        if (book == null || book.getStatus().equals("Available")) {
            System.out.println("Book is not borrowed.");
            return;
        }

        Reader reader = new Reader(member.getName(), memberId);
        librarian.returnBook(book, reader, member);
        librarian.refund(book, reader);
    }

    public static void listBooksByAuthor(BookService bookService) {
        String author = getString("Enter author name: ");
        bookService.listBooksByAuthor(author);
    }

    public static void listBooksByCategory(BookService bookService) {
        System.out.println("1. Journals\n2. StudyBooks\n3. Magazines");
        int choice = getInt("Choose category: ");
        switch (choice) {
            case 1 -> bookService.listBooksByCategory(Journals.class);
            case 2 -> bookService.listBooksByCategory(StudyBooks.class);
            case 3 -> bookService.listBooksByCategory(Magazines.class);
            default -> System.out.println("Invalid category.");
        }
    }

    public static void updateBook(BookService bookService) {
        String id = getString("Enter book ID: ");
        double newPrice = getDouble("Enter new price: ");
        bookService.updateBookPrice(id, newPrice);
    }

    public static void deleteBook(BookService bookService) {
        String id = getString("Enter book ID: ");
        bookService.removeBookById(id);
    }

    // ---------- yardımcılar ----------
    private static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static double getDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
}