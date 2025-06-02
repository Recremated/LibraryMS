package com.librarysystem.handler;

import com.librarysystem.model.*;
import com.librarysystem.service.BookService;
import com.librarysystem.service.MemberService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class HandlerUtils {
    private static final Scanner scanner = new Scanner(System.in);

    public static void addBook(BookService bookService) {
        System.out.println("\n-- Add Book --");
        String id = getString("Enter book ID: ");

        if (bookService.findBookById(id) != null) {
            System.out.println("❌ Book ID already exists!");
            return;
        }

        String title = getString("Enter title: ");
        String author = getString("Enter author: ");
        double price = getValidPrice("Enter price: ");
        int edition = getInt("Enter edition: ");
        LocalDate date = LocalDate.now();

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

    // HATA DÜZELTİLDİ: Çift registration methodları kaldırıldı
    public static void registerMember(MemberService memberService) {
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

    public static void registerStudent(MemberService memberService) {
        System.out.println("\n-- Register New Student --");
        int memberId = getInt("Enter member ID: ");

        // HATA DÜZELTİLDİ: ID kontrolü eklendi
        if (memberService.getMember(memberId) != null) {
            System.out.println("❌ Member ID already exists!");
            return;
        }

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

    public static void registerFaculty(MemberService memberService) {
        System.out.println("\n-- Register New Faculty --");
        int memberId = getInt("Enter member ID: ");

        // HATA DÜZELTİLDİ: ID kontrolü eklendi
        if (memberService.getMember(memberId) != null) {
            System.out.println("❌ Member ID already exists!");
            return;
        }

        String name = getString("Enter name: ");
        String date = getString("Enter membership date (YYYY-MM-DD): ");
        String address = getString("Enter address: ");
        String phone = getString("Enter phone: ");
        String employeeId = getString("Enter employee ID: ");
        String department = getString("Enter department: ");
        String position = getString("Enter position: ");
        String specialization = getString("Enter specialization: ");
        int experience = getInt("Enter years of experience: ");

        Faculty faculty = new Faculty(memberId, name, date, address, phone,
                employeeId, department, position, specialization, experience);
        memberService.registerFaculty(faculty);
    }

    private static void registerGeneralMember(MemberService memberService) {
        System.out.println("\n-- Register General Member --");
        int id = getInt("Enter member ID: ");

        // HATA DÜZELTİLDİ: ID kontrolü eklendi
        if (memberService.getMember(id) != null) {
            System.out.println("❌ Member ID already exists!");
            return;
        }

        String name = getString("Enter name: ");
        String type = getString("Enter type: ");
        String date = getString("Enter membership date (YYYY-MM-DD): ");
        String address = getString("Enter address: ");
        String phone = getString("Enter phone: ");

        MemberRecord member = new MemberRecord(id, name, type, date, address, phone);
        memberService.registerMember(member);
    }

    // Updated HandlerUtils.java - borrowBook method with enhanced validation
    public static void borrowBook(Library library, BookService bookService,
                                  MemberService memberService, Librarian librarian) {
        System.out.println("\n-- Borrow Book --");
        int memberId = getInt("Enter member ID: ");
        MemberRecord member = memberService.getMember(memberId);
        if (member == null) {
            System.out.println("❌ Member not found.");
            return;
        }

        String bookId = getString("Enter book ID: ");
        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("❌ Book not found.");
            return;
        }

        // ENHANCED: Check if book is already borrowed with detailed message
        if ("Borrowed".equals(book.getStatus())) {
            System.out.println("❌ This book is already borrowed!");
            System.out.println("📖 Book: " + book.getName());
            if (book.getBorrowedByMemberName() != null) {
                System.out.println("👤 Currently with: " + book.getBorrowedByMemberName() +
                        " (ID: " + book.getBorrowedByMemberId() + ")");
                System.out.println("📅 Borrowed on: " + book.getBorrowDate());
            }
            System.out.println("⏳ Please wait for the book to be returned before borrowing.");
            return;
        }

        // Check if member can borrow more books
        if (!member.canIssue()) {
            System.out.println("❌ Member has reached the maximum book limit (" +
                    member.getMaxBookLimit() + " books).");
            System.out.println("📊 Currently borrowed: " + member.getBooksIssued() +
                    "/" + member.getMaxBookLimit());
            return;
        }

        Reader reader = new Reader(member.getName(), memberId);

        // Display member advantages
        displayMemberAdvantages(member);

        // Issue the book
        librarian.issueBook(book, reader, member);
        librarian.createBill(book, reader);
    }

    private static void displayMemberAdvantages(MemberRecord member) {
        if (member instanceof Faculty faculty) {
            System.out.println("🎓 Faculty privileges:");
            System.out.println("- Book limit: " + faculty.getMaxBookLimit());
            if (faculty.canReserveBooks()) {
                System.out.println("- Can reserve books");
            }
            if (faculty.getFineExemptionRate() > 0) {
                System.out.printf("- Fine exemption: %.0f%%\n", faculty.getFineExemptionRate() * 100);
            }
        } else if (member instanceof Student student) {
            System.out.println("📚 Student privileges:");
            if (student.isEligibleForExtendedLoan()) {
                System.out.println("- Extended loan period (PhD student)");
            }
            System.out.printf("- Fine discount: %.0f%%\n", student.getDiscountRate() * 100);
        }
    }

    // Diğer method'lar aynı kalacak...
    public static void returnBook(Library library, BookService bookService,
                                  MemberService memberService, Librarian librarian) {
        System.out.println("\n-- Return Book --");
        int memberId = getInt("Enter member ID: ");
        MemberRecord member = memberService.getMember(memberId);
        if (member == null) {
            System.out.println("❌ Member not found.");
            return;
        }

        String bookId = getString("Enter book ID: ");
        Book book = bookService.findBookById(bookId);
        if (book == null || book.getStatus().equals("Available")) {
            System.out.println("❌ Book is not borrowed.");
            return;
        }

        Reader reader = new Reader(member.getName(), memberId);
        librarian.returnBook(book, reader, member);
        librarian.refund(book, reader);
    }

    // Utility methods - aynı kalacak
    private static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim(); // trim eklendi
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
                System.out.println("Enter a valid number.");
            }
        }
    }

    private static double getValidPrice(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("Input cannot be empty.");
                    continue;
                }
                double price = Double.parseDouble(input);
                if (price < 0) {
                    System.out.println("Price cannot be negative.");
                    continue;
                }
                return price;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }

    // Diğer gereksiz methodlar kaldırıldı
    public static void searchBookByID(BookService bookService) {
        System.out.println("\n-- Search Book by ID --");
        String id = getString("Enter book ID: ");
        Book book = bookService.findBookById(id);
        if (book != null) book.display();
        else System.out.println("❌ Book not found.");
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
        Book book = bookService.findBookById(id);
        if (book == null) {
            System.out.println("❌ Book not found.");
            return;
        }
        double newPrice = getValidPrice("Enter new price: ");
        bookService.updateBookPrice(id, newPrice);
    }

    public static void deleteBook(BookService bookService) {
        String id = getString("Enter book ID: ");
        Book book = bookService.findBookById(id);
        if (book == null) {
            System.out.println("❌ Book not found.");
            return;
        }
        if (book.getStatus().equals("Borrowed")) {
            System.out.println("❌ Cannot delete borrowed book.");
            return;
        }
        bookService.removeBookById(id);
    }

    // HandlerUtils.java'ya eklenecek yeni metodlar:

    // Bu metodu HandlerUtils sınıfına ekleyin
    public static void listBorrowedBooks(BookService bookService) {
        System.out.println("\n=== BORROWED BOOKS ===");
        List<Book> allBooks = Library.getInstance().getBooks();
        boolean foundBorrowedBooks = false;

        for (Book book : allBooks) {
            if ("Borrowed".equals(book.getStatus())) {
                book.display();
                System.out.println("─".repeat(40));
                foundBorrowedBooks = true;
            }
        }

        if (!foundBorrowedBooks) {
            System.out.println("📚 No books are currently borrowed.");
        }
    }

    // Belirli bir üyenin aldığı kitapları göstermek için
    public static void showMemberBorrowedBooks(MemberService memberService) {
        System.out.println("\n=== MEMBER'S BORROWED BOOKS ===");
        int memberId = getInt("Enter member ID: ");

        MemberRecord member = memberService.getMember(memberId);
        if (member == null) {
            System.out.println("❌ Member not found.");
            return;
        }

        System.out.println("📚 Books borrowed by " + member.getName() + ":");
        List<Book> allBooks = Library.getInstance().getBooks();
        boolean foundBooks = false;

        for (Book book : allBooks) {
            if ("Borrowed".equals(book.getStatus()) &&
                    member.getMemberId().equals(book.getBorrowedByMemberId())) {
                book.display();
                System.out.println("─".repeat(40));
                foundBooks = true;
            }
        }

        if (!foundBooks) {
            System.out.println("📚 " + member.getName() + " has no borrowed books.");
        }
    }

    // Hangi kitabın hangi üye tarafından alındığını arama
    public static void searchWhoHasBook(BookService bookService) {
        System.out.println("\n=== WHO HAS THIS BOOK? ===");
        String bookId = getString("Enter book ID: ");

        Book book = bookService.findBookById(bookId);
        if (book == null) {
            System.out.println("❌ Book not found.");
            return;
        }

        System.out.println("\n📖 Book: " + book.getName());
        System.out.println("📊 Status: " + book.getStatus());

        if ("Borrowed".equals(book.getStatus()) && book.getBorrowedByMemberId() != null) {
            System.out.println("👤 Currently with: " + book.getBorrowedByMemberName() +
                    " (ID: " + book.getBorrowedByMemberId() + ")");
            System.out.println("📅 Borrowed on: " + book.getBorrowDate());
        } else {
            System.out.println("✅ This book is available in the library.");
        }
    }
    // Additional utility method to check book availability
    public static boolean isBookAvailable(Book book) {
        return book != null && "Available".equals(book.getStatus());
    }

    // Method to get detailed book status
    public static void showBookStatus(Book book) {
        if (book == null) {
            System.out.println("❌ Book not found.");
            return;
        }

        System.out.println("\n📚 Book Status Information:");
        System.out.println("📖 Title: " + book.getName());
        System.out.println("🆔 Book ID: " + book.getBookID());
        System.out.println("👨‍💼 Author: " + book.getAuthor());
        System.out.println("📊 Status: " + book.getStatus());

        if ("Borrowed".equals(book.getStatus())) {
            System.out.println("🚫 This book is currently unavailable");
            if (book.getBorrowedByMemberName() != null) {
                System.out.println("👤 Borrowed by: " + book.getBorrowedByMemberName());
                System.out.println("🆔 Member ID: " + book.getBorrowedByMemberId());
                System.out.println("📅 Borrow Date: " + book.getBorrowDate());
            }
        } else {
            System.out.println("✅ This book is available for borrowing");
        }
    }

    // Enhanced search method with availability check
    public static void searchBookWithAvailability(BookService bookService) {
        System.out.println("\n-- Search Book with Availability --");
        String id = getString("Enter book ID: ");
        Book book = bookService.findBookById(id);

        if (book != null) {
            showBookStatus(book);
        } else {
            System.out.println("❌ Book not found with ID: " + id);
        }
    }
}