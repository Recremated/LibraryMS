package com.librarysystem.service;

import com.librarysystem.model.Book;
import com.librarysystem.model.Library;

import java.util.List;

public class BookService {
    private Library library;

    public BookService(Library library) {
        this.library = library;
    }

    // Alternative constructor that uses Singleton Library
    public BookService() {
        this.library = Library.getInstance();
    }

    public void addBook(Book book) {
        library.addBook(book);
    }

    public void removeBookById(String bookID) {
        Book book = library.searchBookByID(bookID);
        if (book != null) {
            library.removeBook(bookID);
            System.out.println("Book removed: " + book.getName());
        } else {
            System.out.println("Book not found with ID: " + bookID);
        }
    }

    public void updateBookPrice(String bookID, double newPrice) {
        Book book = library.searchBookByID(bookID);
        if (book != null) {
            book.setPrice(newPrice);
            System.out.println("Price updated for book: " + book.getName());
        } else {
            System.out.println("Book not found with ID: " + bookID);
        }
    }

    public void listBooksByAuthor(String authorName) {
        List<Book> books = library.searchBooksByAuthor(authorName);
        if (books.isEmpty()) {
            System.out.println("No books found for author: " + authorName);
        } else {
            System.out.println("\n📚 Books by " + authorName + ":");
            books.forEach(book -> {
                book.display();
                System.out.println("─".repeat(40));
            });
        }
    }

    public void listBooksByCategory(Class<? extends Book> categoryClass) {
        System.out.println("\n📚 Books in category: " + categoryClass.getSimpleName());
        boolean found = false;
        for (Book book : library.getBooks()) {
            if (categoryClass.isInstance(book)) {
                book.display();
                System.out.println("─".repeat(40));
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found in this category.");
        }
    }

    public void listAllBooks() {
        if (library.getBooks().isEmpty()) {
            System.out.println("📚 No books in the library.");
        } else {
            System.out.println("\n📚 ALL BOOKS IN LIBRARY:");
            library.displayAllBooks();
        }
    }

    public Book findBookById(String bookID) {
        return library.searchBookByID(bookID);
    }

    public List<Book> searchBooksByTitle(String title) {
        return library.searchBooksByTitle(title);
    }
}