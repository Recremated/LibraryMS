package com.librarysystem.data;

import com.librarysystem.model.Book;
import com.librarysystem.model.MemberRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class DataManager {
    private static volatile DataManager instance;
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
        System.out.println("💾 Saving " + books.size() + " books to " + filename + "...");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(books);
            System.out.println("✅ Books saved successfully to " + filename);
        } catch (IOException e) {
            System.err.println("❌ Error saving books: " + e.getMessage());
            e.printStackTrace(); // Debug için
        }
    }

    @SuppressWarnings("unchecked")
    public List<Book> loadBooks(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("📁 No saved books found (" + filename + "), starting with empty library.");
            return new ArrayList<>();
        }

        System.out.println("📖 Loading books from " + filename + "...");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                List<Book> books = (List<Book>) obj;
                System.out.println("✅ " + books.size() + " books loaded successfully from " + filename);
                return books;
            } else {
                System.err.println("❌ Invalid data format in " + filename);
            }
        } catch (IOException e) {
            System.err.println("❌ IO Error loading books from " + filename + ": " + e.getMessage());
            e.printStackTrace(); // Debug için
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Class not found error loading books: " + e.getMessage());
            e.printStackTrace(); // Debug için
        }
        return new ArrayList<>();
    }

    public void saveMembers(Map<Integer, MemberRecord> members, String filename) {
        System.out.println("💾 Saving " + members.size() + " members to " + filename + "...");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(members);
            System.out.println("✅ Members saved successfully to " + filename);
        } catch (IOException e) {
            System.err.println("❌ Error saving members: " + e.getMessage());
            e.printStackTrace(); // Debug için
        }
    }

    @SuppressWarnings("unchecked")
    public Map<Integer, MemberRecord> loadMembers(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("📁 No saved members found (" + filename + "), starting with empty records.");
            return new HashMap<>();
        }

        System.out.println("👥 Loading members from " + filename + "...");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = ois.readObject();
            if (obj instanceof Map<?, ?>) {
                Map<Integer, MemberRecord> members = (Map<Integer, MemberRecord>) obj;
                System.out.println("✅ " + members.size() + " members loaded successfully from " + filename);
                return members;
            } else {
                System.err.println("❌ Invalid data format in " + filename);
            }
        } catch (IOException e) {
            System.err.println("❌ IO Error loading members from " + filename + ": " + e.getMessage());
            e.printStackTrace(); // Debug için
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Class not found error loading members: " + e.getMessage());
            e.printStackTrace(); // Debug için
        }
        return new HashMap<>();
    }

    // Debug metodu - dosya durumunu kontrol et
    public void checkFileStatus(String filename) {
        File file = new File(filename);
        System.out.println("📁 File: " + filename);
        System.out.println("   Exists: " + file.exists());
        if (file.exists()) {
            System.out.println("   Size: " + file.length() + " bytes");
            System.out.println("   Readable: " + file.canRead());
            System.out.println("   Writable: " + file.canWrite());
        }
    }
}