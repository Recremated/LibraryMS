package com.librarysystem.model;

import java.time.LocalDate;

public class Journals extends Book {
    public Journals(String bookID, String author, String name, double price, int edition, LocalDate dateOfPurchase) {
        super(bookID, author, name, price, edition, dateOfPurchase);
    }
}