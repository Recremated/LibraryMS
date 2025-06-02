package com.librarysystem.model;

import java.util.ArrayList;
import java.util.List;

public class Reader extends Person {
    private List<Book> books;
    private int memberId;

    public Reader(String name, int memberId) {
        super(name);
        this.memberId = memberId;
        this.books = new ArrayList<>();
    }

    public void borrowBook(Book book) {
        books.add(book);
        // Yeni metodu kullan - kitabı kimin aldığını kaydet
        book.setBorrowedBy(this.memberId, this.name);
        System.out.println("✅ " + name + " borrowed the book: " + book.getName());
    }

    public void returnBook(Book book) {
        if (books.remove(book)) {
            // Yeni metodu kullan - kitap iade bilgilerini temizle
            book.returnBook();
            System.out.println("✅ " + name + " returned the book: " + book.getName());
        } else {
            System.out.println("❌ " + name + " doesn't have this book: " + book.getName());
        }
    }

    // Üyenin aldığı tüm kitapları göster
    public void displayBorrowedBooks() {
        if (books.isEmpty()) {
            System.out.println("📚 " + name + " has no borrowed books.");
        } else {
            System.out.println("📚 Books borrowed by " + name + ":");
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                System.out.println((i + 1) + ". " + book.getName() + " (ID: " + book.getBookID() + ")");
            }
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
        System.out.println("Currently borrowed books: " + books.size());
    }
}