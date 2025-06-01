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

    // Getters and Setters
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public int getYearsOfExperience() { return yearsOfExperience; }
    public void setYearsOfExperience(int yearsOfExperience) { this.yearsOfExperience = yearsOfExperience; }

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
