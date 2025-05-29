package com.librarysystem.model;

public class Librarian {
    private String name;
    private String password;

    public Librarian(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public boolean verifyMember(MemberRecord member) {
        return member.canIssue();
    }

    public void issueBook(Book book, Reader reader, MemberRecord record) {
        if (!verifyMember(record)) {
            System.out.println("Member has reached the book limit.");
            return;
        }
        if (book.getStatus().equals("Borrowed")) {
            System.out.println("Book is already borrowed.");
            return;
        }
        reader.borrowBook(book);
        record.issueBook();
        System.out.println("Book issued to " + reader.getName());
    }

    public void returnBook(Book book, Reader reader, MemberRecord record) {
        reader.returnBook(book);
        record.returnBook();
        System.out.println("Book returned by " + reader.getName());
    }

    public double calculateFine(int overdueDays) {
        return overdueDays * 2.0; // örnek: her gün için 2 TL ceza
    }

    public void createBill(Book book, Reader reader) {
        System.out.println("💳 Bill for " + reader.getName() + ":");
        System.out.println("Book: " + book.getName());
        System.out.printf("Amount Charged: $%.2f%n", book.getPrice());
    }

    public void refund(Book book, Reader reader) {
        System.out.println("💸 Refund to " + reader.getName() + ":");
        System.out.printf("Refunded Amount: $%.2f%n", book.getPrice());
    }

    public String getName() {
        return name;
    }

    public boolean checkPassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}