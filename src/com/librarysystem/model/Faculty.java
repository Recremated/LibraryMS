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

    public double getFineExemptionRate() {
        return switch (position.toLowerCase()) {
            case "professor" -> 1.0; // 100% exemption
            case "associate professor" -> 0.8; // 80% exemption
            case "assistant professor" -> 0.5; // 50% exemption
            default -> 0.0; // No exemption for others
        };
    }
}
