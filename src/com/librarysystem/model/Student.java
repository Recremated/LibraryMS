package com.librarysystem.model;

public class Student extends MemberRecord {
    private String studentId;
    private String department;
    private int semester;
    private String degree; // Bachelor, Master, PhD

    public Student(int memberId, String name, String dateOfMembership,
                   String address, String phoneNo, String studentId,
                   String department, int semester, String degree) {
        super(memberId, name, "Student", dateOfMembership, address, phoneNo);
        this.studentId = studentId;
        this.department = department;
        this.semester = semester;
        this.degree = degree;
        // Students have standard book limit (5)
        setMaxBookLimit(5);
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    @Override
    public void displayMemberInfo() {
        super.displayMemberInfo();
        System.out.println("Student ID: " + studentId);
        System.out.println("Department: " + department);
        System.out.println("Semester: " + semester);
        System.out.println("Degree: " + degree);
    }

    // Student-specific methods
    public boolean isEligibleForExtendedLoan() {
        // PhD students can borrow books for longer periods
        return "PhD".equalsIgnoreCase(degree);
    }

    public double getDiscountRate() {
        // Students get discount on fines
        return 0.5; // 50% discount
    }
}