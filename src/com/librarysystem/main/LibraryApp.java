package com.librarysystem.main;

import com.librarysystem.data.DataManager;
import com.librarysystem.handler.LoginHandler;
import com.librarysystem.handler.MenuHandler;
import com.librarysystem.model.*;
import com.librarysystem.service.BookService;
import com.librarysystem.service.MemberService;

public class LibraryApp {
    public static void main(String[] args) {
        // Get Singleton instances
        Library library = Library.getInstance();
        DataManager dataManager = DataManager.getInstance();

        BookService bookService = new BookService(library);
        MemberService memberService = new MemberService();
        Librarian librarian = new Librarian("admin", "1234");

        // Load data from files using Singleton DataManager
        library.setBooks(dataManager.loadBooks("books.dat"));
        memberService.setMemberMap(dataManager.loadMembers("members.dat"));

        // Login
        if (!LoginHandler.login(librarian)) {
            System.out.println("Too many failed attempts. Exiting...");
            return;
        }

        // Menu
        MenuHandler.runMainMenu(library, bookService, memberService, librarian);

        // Save data when exiting using Singleton DataManager
        dataManager.saveBooks(library.getBooks(), "books.dat");
        dataManager.saveMembers(memberService.getMemberMap(), "members.dat");
        System.out.println("✅ Data saved successfully.");
    }
}