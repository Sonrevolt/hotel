package guest;

import main.DatabaseConnection;
import model.Guest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class AddGuestToDatabase {
    public static void addGuestToDatabase(Scanner scanner) {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter date of birth (yyyy-mm-dd): ");
        String dobInput = scanner.nextLine();

        LocalDate dob = LocalDate.parse(dobInput);

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        String sql = "INSERT INTO guests (name, age, gender, phone, email, dob) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, gender);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.setDate(6, java.sql.Date.valueOf(dob));
            pstmt.executeUpdate();
            System.out.println("Guest added successfully to database.");
        } catch (SQLException e) {
            System.out.println("Error adding guest to database: " + e.getMessage());
        }
    }

}
