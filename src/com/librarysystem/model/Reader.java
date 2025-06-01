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

    // HATA DÜZELTİLDİ: Reader sadece kitap tutma işini yapmalı
    // Limit kontrolü Librarian tarafından yapılmalı
    public void borrowBook(Book book) {
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