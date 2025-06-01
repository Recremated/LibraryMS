package com.librarysystem.service;

import com.librarysystem.model.*;
import java.util.HashMap;
import java.util.Map;

public class MemberService {
    private Map<Integer, MemberRecord> memberMap;

    public MemberService() {
        this.memberMap = new HashMap<>();
    }

    public void registerMember(MemberRecord member) {
        memberMap.put(member.getMemberId(), member);
        System.out.println("Member registered: " + member.getName() + " (" + member.getType() + ")");
    }

    // Specific registration methods
    public void registerStudent(Student student) {
        memberMap.put(student.getMemberId(), student);
        System.out.println("✅ Student registered: " + student.getName() +
                " (ID: " + student.getStudentId() + ")");
    }

    public void registerFaculty(Faculty faculty) {
        memberMap.put(faculty.getMemberId(), faculty);
        System.out.println("✅ Faculty registered: " + faculty.getName() +
                " (Position: " + faculty.getPosition() + ")");
    }

    public MemberRecord getMember(int memberId) {
        return memberMap.get(memberId);
    }

    public Student getStudent(int memberId) {
        MemberRecord member = memberMap.get(memberId);
        return (member instanceof Student) ? (Student) member : null;
    }

    public Faculty getFaculty(int memberId) {
        MemberRecord member = memberMap.get(memberId);
        return (member instanceof Faculty) ? (Faculty) member : null;
    }

    public void payBill(int memberId, double amount) {
        MemberRecord member = memberMap.get(memberId);
        if (member != null) {
            System.out.println("Bill paid by " + member.getName() + ": $" + amount);
        } else {
            System.out.println("Member not found.");
        }
    }

    public void showMemberDetails(int memberId) {
        MemberRecord member = memberMap.get(memberId);
        if (member != null) {
            member.displayMemberInfo();
        } else {
            System.out.println("Member not found.");
        }
    }

    // List all students
    public void listAllStudents() {
        System.out.println("\n=== ALL STUDENTS ===");
        boolean found = false;
        for (MemberRecord member : memberMap.values()) {
            if (member instanceof Student) {
                member.displayMemberInfo();
                System.out.println("-------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No students registered.");
        }
    }

    // List all faculty
    public void listAllFaculty() {
        System.out.println("\n=== ALL FACULTY ===");
        boolean found = false;
        for (MemberRecord member : memberMap.values()) {
            if (member instanceof Faculty) {
                member.displayMemberInfo();
                System.out.println("-------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No faculty registered.");
        }
    }

    public void setMemberMap(Map<Integer, MemberRecord> memberMap) {
        this.memberMap = memberMap;
    }

    public Map<Integer, MemberRecord> getMemberMap() {
        return memberMap;
    }
}