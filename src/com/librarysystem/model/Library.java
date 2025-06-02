package com.librarysystem.model;

import java.util.*;

public class Library {
    // Singleton instance
    private static Library instance;
    private static final Object lock = new Object();

    private List<Book> books;
    private List<Reader> readers;

    // Private constructor to prevent instantiation
    private Library() {
        books = new ArrayList<>();
        readers = new ArrayList<>();
    }

    // Thread-safe Singleton getInstance method
    public static Library getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new Library();
                }
            }
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getName());
    }

    public void removeBook(String bookID) {
        books.removeIf(book -> book.getBookID().equals(bookID));
    }

    public Book searchBookByID(String bookID) {
        for (Book book : books) {
            if (book.getBookID().equals(bookID)) return book;
        }
        return null;
    }

    public List<Book> searchBooksByAuthor(String authorName) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().getName().equalsIgnoreCase(authorName)) { // Author nesnesini kullan
                result.add(book);
            }
        }
        return result;
    }
    
    public List<Book> getBooks() {
        return books;
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public void displayAllBooks() {
        for (Book book : books) {
            book.display();
            System.out.println("----------------------");
        }
    }


    public void setBooks(List<Book> books) {
        this.books = books;
    }
}