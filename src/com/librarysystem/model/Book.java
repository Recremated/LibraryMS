package com.librarysystem.model;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bookID;
    private Author author; // Author sınıfını kullan
    private String name;
    private double price;
    private String status; // Available, Borrowed
    private int edition;
    private LocalDate dateOfPurchase;

    // Ödünç alma ile ilgili alanlar
    private Integer borrowedByMemberId;
    private String borrowedByMemberName;
    private LocalDate borrowDate;

    public Book(String bookID, String authorName, String name, double price, int edition, LocalDate dateOfPurchase) {
        this.bookID = bookID;
        this.author = new Author(authorName); // Author nesnesi oluştur
        this.name = name;
        this.price = price;
        this.status = "Available";
        this.edition = edition;
        this.dateOfPurchase = dateOfPurchase;
        this.borrowedByMemberId = null;
        this.borrowedByMemberName = null;
        this.borrowDate = null;

        // Yazarın kitap listesine bu kitabı ekle
        this.author.addBook(this);
    }

    // Getter'lar
    public String getBookID() { return bookID; }
    public Author getAuthor() { return author; }
    public String getAuthorName() { return author.getName(); } // Geriye uyumluluk için
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }
    public int getEdition() { return edition; }
    public LocalDate getDateOfPurchase() { return dateOfPurchase; }
    public Integer getBorrowedByMemberId() { return borrowedByMemberId; }
    public String getBorrowedByMemberName() { return borrowedByMemberName; }
    public LocalDate getBorrowDate() { return borrowDate; }

    // Setter'lar
    public void setStatus(String status) { this.status = status; }
    public void setPrice(double price) { this.price = price; }
    public void setBorrowedByMemberId(Integer borrowedByMemberId) {
        this.borrowedByMemberId = borrowedByMemberId;
    }
    public void setBorrowedByMemberName(String borrowedByMemberName) {
        this.borrowedByMemberName = borrowedByMemberName;
    }
    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    // Kitap ödünç verme ve iade metodları
    public void setBorrowedBy(Integer memberId, String memberName) {
        this.borrowedByMemberId = memberId;
        this.borrowedByMemberName = memberName;
        this.borrowDate = LocalDate.now();
        this.status = "Borrowed";
    }

    public void returnBook() {
        this.borrowedByMemberId = null;
        this.borrowedByMemberName = null;
        this.borrowDate = null;
        this.status = "Available";
    }

    public void display() {
        System.out.println("📖 Book Information:");
        System.out.println("Book ID: " + bookID);
        System.out.println("Name: " + name);
        System.out.println("Author: " + author.getName());
        System.out.println("Edition: " + edition);
        System.out.println("Price: $" + price);
        System.out.println("Status: " + status);

        if ("Borrowed".equals(status) && borrowedByMemberId != null) {
            System.out.println("👤 Borrowed by: " + borrowedByMemberName + " (ID: " + borrowedByMemberId + ")");
            System.out.println("📅 Borrow Date: " + borrowDate);
        }

        System.out.println("📅 Date of Purchase: " + dateOfPurchase);
        System.out.println("Type: " + this.getClass().getSimpleName());
    }
}