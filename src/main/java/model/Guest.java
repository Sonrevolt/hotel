package model;

import java.time.LocalDate;

public class Guest {
    private String name;
    private int age;
    private String gender;
    private String phone;
    private String email;
    private LocalDate dob; // Thêm ngày sinh

    public Guest(String name, int age, String gender, String phone, String email, LocalDate dob) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDob() {
        return dob;
    }

    @Override
    public String toString() {
        return String.format("Name: %s, Age: %d, Gender: %s, Phone: %s, Email: %s, DOB: %s",
                name, age, gender, phone, email, dob);
    }
}
