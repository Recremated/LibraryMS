package com.librarysystem.service;

import com.librarysystem.model.MemberRecord;
import java.util.HashMap;
import java.util.Map;

public class MemberService {
    private Map<Integer, MemberRecord> memberMap;

    public MemberService() {
        this.memberMap = new HashMap<>();
    }

    public void registerMember(MemberRecord member) {
        memberMap.put(member.getMemberId(), member);
        System.out.println("Member registered: " + member.getName());
    }

    public MemberRecord getMember(int memberId) {
        return memberMap.get(memberId);
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
            System.out.println("ID: " + member.getMemberId());
            System.out.println("Name: " + member.getName());
            System.out.println("Books Issued: " + member.getBooksIssued());
        } else {
            System.out.println("Member not found.");
        }
    }

    public void setMemberMap(Map<Integer, MemberRecord> memberMap) {
        this.memberMap = memberMap;
    }

    public Map<Integer, MemberRecord> getMemberMap() {
        return memberMap;
    }
}