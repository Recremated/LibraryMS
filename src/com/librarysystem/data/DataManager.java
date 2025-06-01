package com.librarysystem.data;

import com.librarysystem.model.Book;
import com.librarysystem.model.MemberRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataManager {
    // Singleton instance
    private static DataManager instance;
    private static final Object lock = new Object();

    // Private constructor to prevent instantiation
    private DataManager() {
    }

    // Thread-safe Singleton getInstance method
    public static DataManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new DataManager();
                }
            }
        }
        return instance;
    }

    public void saveBooks(List<Book> books, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(books);
        } catch (IOException e) {
            System.out.println("❌ Error saving books: " + e.getMessage());
        }
    }

    public List<Book> loadBooks(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<Book>) ois.readObject();
        } catch (Exception e) {
            System.out.println("📁 No saved books found, starting empty.");
            return new ArrayList<>();
        }
    }

    public void saveMembers(Map<Integer, MemberRecord> members, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(members);
        } catch (IOException e) {
            System.out.println("❌ Error saving members: " + e.getMessage());
        }
    }

    public Map<Integer, MemberRecord> loadMembers(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Map<Integer, MemberRecord>) ois.readObject();
        } catch (Exception e) {
            System.out.println("📁 No saved members found, starting empty.");
            return new java.util.HashMap<>();
        }
    }
}