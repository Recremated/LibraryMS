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

        // ID duplicate kontrolü
        if (bookService.findBookById(id) != null) {
            System.out.println("❌ Book ID already exists!");
            return;
        }

        String title = getString("Enter title: ");
        String author = getString("Enter author: ");
        double price = getValidPrice("Enter price: "); // Validation eklendi
        int edition = getInt("Enter edition: ");
        LocalDate date = LocalDate.now();

        // Kategori seçimi eklendi
        System.out.println("Select book category:");
        System.out.println("1. Study Books");
        System.out.println("2. Journals");
        System.out.println("3. Magazines");

        int categoryChoice = getInt("Choose category (1-3): ");

        Book book = switch (categoryChoice) {
            case 1 -> new StudyBooks(id, author, title, price, edition, date);
            case 2 -> new Journals(id, author, title, price, edition, date);
            case 3 -> new Magazines(id, author, title, price, edition, date);
            default -> {
                System.out.println("Invalid choice, defaulting to Study Books");
                yield new StudyBooks(id, author, title, price, edition, date);
            }
        };

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


    // New method for registering students
    public static void registerStudent(MemberService memberService) {
        System.out.println("\n-- Register New Student --");
        int memberId = getInt("Enter member ID: ");
        String name = getString("Enter name: ");
        String date = getString("Enter membership date (YYYY-MM-DD): ");
        String address = getString("Enter address: ");
        String phone = getString("Enter phone: ");
        String studentId = getString("Enter student ID: ");
        String department = getString("Enter department: ");
        int semester = getInt("Enter semester: ");
        String degree = getString("Enter degree (Bachelor/Master/PhD): ");

        Student student = new Student(memberId, name, date, address, phone,
                studentId, department, semester, degree);
        memberService.registerStudent(student);
    }

    // New method for registering faculty
    public static void registerFaculty(MemberService memberService) {
        System.out.println("\n-- Register New Faculty --");
        int memberId = getInt("Enter member ID: ");
        String name = getString("Enter name: ");
        String date = getString("Enter membership date (YYYY-MM-DD): ");
        String address = getString("Enter address: ");
        String phone = getString("Enter phone: ");
        String employeeId = getString("Enter employee ID: ");
        String department = getString("Enter department: ");
        String position = getString("Enter position (Professor/Associate Professor/Assistant Professor/Lecturer): ");
        String specialization = getString("Enter specialization: ");
        int experience = getInt("Enter years of experience: ");

        Faculty faculty = new Faculty(memberId, name, date, address, phone,
                employeeId, department, position, specialization, experience);
        memberService.registerFaculty(faculty);
    }
    // Enhanced member registration with type selection
    public static void registerMemberWithType(MemberService memberService) {
        System.out.println("\n-- Register New Member --");
        System.out.println("1. Student");
        System.out.println("2. Faculty");
        System.out.println("3. General Member");

        int choice = getInt("Choose member type: ");

        switch (choice) {
            case 1 -> registerStudent(memberService);
            case 2 -> registerFaculty(memberService);
            case 3 -> registerGeneralMember(memberService);
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void registerGeneralMember(MemberService memberService) {
        System.out.println("\n-- Register General Member --");
        int id = getInt("Enter member ID: ");
        String name = getString("Enter name: ");
        String type = getString("Enter type: ");
        String date = getString("Enter membership date (YYYY-MM-DD): ");
        String address = getString("Enter address: ");
        String phone = getString("Enter phone: ");

        MemberRecord member = new MemberRecord(id, name, type, date, address, phone);
        memberService.registerMember(member);
    }

    // Enhanced borrow book with member type specific features
    public static void borrowBookEnhanced(Library library, BookService bookService,
                                          MemberService memberService, Librarian librarian) {
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

        // Check member type specific rules
        if (member instanceof Faculty) {
            Faculty faculty = (Faculty) member;
            System.out.println("Faculty member detected. Enhanced privileges apply.");
            if (faculty.canReserveBooks()) {
                System.out.println("✅ Book reservation available if needed.");
            }
        } else if (member instanceof Student) {
            Student student = (Student) member;
            if (student.isEligibleForExtendedLoan()) {
                System.out.println("✅ Extended loan period available for PhD student.");
            }
        }

        librarian.issueBook(book, reader, member);
        librarian.createBill(book, reader);
    }

    // List members by type
    public static void listMembersByType(MemberService memberService) {
        System.out.println("\n-- List Members by Type --");
        System.out.println("1. Students");
        System.out.println("2. Faculty");

        int choice = getInt("Choose type to list: ");

        switch (choice) {
            case 1 -> memberService.listAllStudents();
            case 2 -> memberService.listAllFaculty();
            default -> System.out.println("Invalid choice.");
        }
    }

    // Existing utility methods...
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

    private static double getValidPrice(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double price = Double.parseDouble(scanner.nextLine());
                if (price < 0) {
                    System.out.println("Price cannot be negative. Please enter a valid price.");
                    continue;
                }
                return price;
            } catch (Exception e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
}