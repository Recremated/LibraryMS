package com.librarysystem.main;

import com.librarysystem.data.DataManager;
import com.librarysystem.handler.LoginHandler;
import com.librarysystem.handler.MenuHandler;
import com.librarysystem.model.*;
import com.librarysystem.service.BookService;
import com.librarysystem.service.MemberService;

public class LibraryApp {
    public static void main(String[] args) {
        Library library = new Library();
        BookService bookService = new BookService(library);
        MemberService memberService = new MemberService();
        Librarian librarian = new Librarian("admin", "1234");

        // Verileri dosyadan yükle
        library.setBooks(DataManager.loadBooks("books.dat"));
        memberService.setMemberMap(DataManager.loadMembers("members.dat"));

        // Giriş
        if (!LoginHandler.login(librarian)) {
            System.out.println("Too many failed attempts. Exiting...");
            return;
        }

        // Menü
        MenuHandler.runMainMenu(library, bookService, memberService, librarian);

        // Çıkarken verileri kaydet
        DataManager.saveBooks(library.getBooks(), "books.dat");
        DataManager.saveMembers(memberService.getMemberMap(), "members.dat");
        System.out.println("✅ Data saved successfully.");
    }
}