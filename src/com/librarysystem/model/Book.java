package com.librarysystem.model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bookID;
    private String author;
    private String name;
    private double price;
    private String status; // Available, Borrowed
    private int edition;
    private LocalDate dateOfPurchase;

    public Book(String bookID, String author, String name, double price, int edition, LocalDate dateOfPurchase) {
        this.bookID = bookID;
        this.author = author;
        this.name = name;
        this.price = price;
        this.status = "Available";
        this.edition = edition;
        this.dateOfPurchase = dateOfPurchase;
    }

    public String getBookID() { return bookID; }
    public String getAuthor() { return author; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
    public int getEdition() { return edition; }
    public LocalDate getDateOfPurchase() { return dateOfPurchase; }

    public void setStatus(String status) { this.status = status; }
    public void setPrice(double price) { this.price = price; }

    public void display() {
        System.out.println("Book ID: " + bookID);
        System.out.println("Name: " + name);
        System.out.println("Author: " + author);
        System.out.println("Edition: " + edition);
        System.out.println("Price: " + price);
        System.out.println("Status: " + status);
        System.out.println("Date of Purchase: " + dateOfPurchase);
    }
}