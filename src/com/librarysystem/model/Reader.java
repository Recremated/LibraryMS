package com.librarysystem.model;

import java.util.ArrayList;
import java.util.List;

public class Reader extends Person {
    private List<Book> books;

    public Reader(String name, int memberId) {
        super(name);
        this.books = new ArrayList<>();
    }

    public void borrowBook(Book book) {
        if (books.size() >= 5) {
            System.out.println("Borrowing limit reached!");
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

    @Override
    public void whoYouAre() {
        System.out.println("I am a Reader. My name is " + name);
    }
}