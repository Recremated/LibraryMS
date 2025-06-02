package com.librarysystem.model;

public class Faculty extends MemberRecord {
    private String employeeId;
    private String department;
    private String position; // Professor, Associate Professor, Assistant Professor, Lecturer
    private String specialization;
    private int yearsOfExperience;

    public Faculty(int memberId, String name, String dateOfMembership,
                   String address, String phoneNo, String employeeId,
                   String department, String position, String specialization,
                   int yearsOfExperience) {
        super(memberId, name, "Faculty", dateOfMembership, address, phoneNo);
        this.employeeId = employeeId;
        this.department = department;
        this.position = position;
        this.specialization = specialization;
        this.yearsOfExperience = yearsOfExperience;
        // Faculty members have higher book limit
        setMaxBookLimit(10);
    }

    public String getPosition() { return position; }

    @Override
    public void displayMemberInfo() {
        super.displayMemberInfo();
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Department: " + department);
        System.out.println("Position: " + position);
        System.out.println("Specialization: " + specialization);
        System.out.println("Years of Experience: " + yearsOfExperience);
    }

    // Faculty-specific methods
    public boolean canReserveBooks() {
        // Faculty can reserve books in advance
        return true;
    }

    public boolean isEligibleForInterlibraryLoan() {
        // Senior faculty can request books from other libraries
        return yearsOfExperience >= 5 ||
                "Professor".equalsIgnoreCase(position) ||
                "Associate Professor".equalsIgnoreCase(position);
    }

    public int getMaxReservationDays() {
        // Faculty can keep reserved books longer
        return 30;
    }

    public double getFineExemptionRate() {
        // Senior faculty get fine exemptions
        if ("Professor".equalsIgnoreCase(position)) return 1.0; // 100% exemption
        if ("Associate Professor".equalsIgnoreCase(position)) return 0.8; // 80% exemption
        return 0.0; // No exemption for others
    }
}
