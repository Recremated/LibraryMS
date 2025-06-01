package com.librarysystem.data;

import com.librarysystem.model.Book;
import com.librarysystem.model.MemberRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class DataManager {
    private static DataManager instance;
    private static final Object lock = new Object();

    private DataManager() {}

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
            System.out.println("✅ Books saved successfully.");
        } catch (IOException e) {
            System.err.println("❌ Error saving books: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked") // Suppressed for type casting
    public List<Book> loadBooks(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("📁 No saved books found, starting with empty library.");
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                System.out.println("✅ Books loaded successfully.");
                return (List<Book>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error loading books: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void saveMembers(Map<Integer, MemberRecord> members, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(members);
            System.out.println("✅ Members saved successfully.");
        } catch (IOException e) {
            System.err.println("❌ Error saving members: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked") // Suppressed for type casting
    public Map<Integer, MemberRecord> loadMembers(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("📁 No saved members found, starting with empty records.");
            return new HashMap<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            if (obj instanceof Map<?, ?>) {
                System.out.println("✅ Members loaded successfully.");
                return (Map<Integer, MemberRecord>) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Error loading members: " + e.getMessage());
        }
        return new HashMap<>();
    }
}