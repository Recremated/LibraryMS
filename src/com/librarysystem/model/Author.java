package com.librarysystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Author extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Book> books;

    public Author(String name) {
        super(name);
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public void whoYouAre() {
        System.out.println("I am an Author. My name is " + name);
    }
}