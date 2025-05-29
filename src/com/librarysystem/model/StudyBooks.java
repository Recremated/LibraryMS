package com.librarysystem.model;

import java.time.LocalDate;

public class StudyBooks extends Book {
    public StudyBooks(String bookID, String author, String name, double price, int edition, LocalDate dateOfPurchase) {
        super(bookID, author, name, price, edition, dateOfPurchase);
    }
    public StudyBooks(String bookID, String author, String name, double price, int edition) {
        super(bookID, author, name, price, edition, LocalDate.now());
    }
}