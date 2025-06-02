package com.librarysystem.model;

import java.io.Serializable;

public class MemberRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private int memberId;
    private String type; // Student or Faculty
    private String dateOfMembership;
    protected int maxBookLimit = 5; // Made protected for inheritance
    private int booksIssued = 0;
    private String name;
    private String address;
    private String phoneNo;

    public MemberRecord(int memberId, String name, String type, String dateOfMembership, String address, String phoneNo) {
        this.memberId = memberId;
        this.name = name;
        this.type = type;
        this.dateOfMembership = dateOfMembership;
        this.address = address;
        this.phoneNo = phoneNo;
    }

    public boolean canIssue() {
        return booksIssued < maxBookLimit;
    }

    public void issueBook() {
        if (canIssue()) booksIssued++;
    }

    public void returnBook() {
        if (booksIssued > 0) booksIssued--;
    }

    // Getters
    public int getBooksIssued() { return booksIssued; }
    public Integer getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getMaxBookLimit() { return maxBookLimit; }

    // Setters
    public void setMaxBookLimit(int maxBookLimit) { this.maxBookLimit = maxBookLimit; }
    public void setName(String name) { this.name = name; }
    // Display method that can be overridden
    public void displayMemberInfo() {
        System.out.println("=== Member Information ===");
        System.out.println("Member ID: " + memberId);
        System.out.println("Name: " + name);
        System.out.println("Type: " + type);
        System.out.println("Membership Date: " + dateOfMembership);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phoneNo);
        System.out.println("Books Issued: " + booksIssued + "/" + maxBookLimit);
    }
}