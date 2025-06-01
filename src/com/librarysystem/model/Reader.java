package com.librarysystem.model;

import java.util.ArrayList;
import java.util.List;

public class Reader extends Person {
    private List<Book> books;
    private int memberId;
    private MemberRecord memberRecord; // Reference to member for limit checking

    public Reader(String name, int memberId) {
        super(name);
        this.memberId = memberId;
        this.books = new ArrayList<>();
    }

    // Set member record for limit checking
    public void setMemberRecord(MemberRecord memberRecord) {
        this.memberRecord = memberRecord;
    }

    public void borrowBook(Book book) {
        // Use member's actual limit instead of hardcoded 5
        int limit = (memberRecord != null) ? memberRecord.getMaxBookLimit() : 5;

        if (books.size() >= limit) {
            System.out.println("Borrowing limit reached! Maximum: " + limit);
            return;
        }
        books.add(book);
        book.setStatus("Borrowed");
        System.out.println(name + " borrowed the book: " + book.getName());
    }

    public void returnBook(Book book) {
        if (books.remove(book)) {
            book.setStatus("Available");
            System.out.println(name + " returned the book: " + book.getName());
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public int getMemberId() {
        return memberId;
    }

    @Override
    public void whoYouAre() {
        System.out.println("I am a Reader. My name is " + name + " (ID: " + memberId + ")");
    }
}