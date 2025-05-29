package com.librarysystem.model;

import java.io.Serializable;

public class MemberRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private int memberId;
    private String type; // Student or Faculty
    private String dateOfMembership;
    private int maxBookLimit = 5;
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

    public int getBooksIssued() { return booksIssued; }

    public Integer getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }
}