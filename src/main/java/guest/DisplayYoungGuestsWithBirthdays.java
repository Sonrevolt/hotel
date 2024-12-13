package guest;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DisplayYoungGuestsWithBirthdays {
    public static void displayYoungGuestsWithBirthdays(Scanner scanner) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        System.out.print("Enter maximum age: ");
        int maxAge = scanner.nextInt();
        System.out.print("Enter birth month (1-12): ");
        int birthMonth = scanner.nextInt();

        String sql = "SELECT id AS guest_id, " +
                "       name AS guest_name, " +
                "       age AS guest_age,  "+
                "       gender, " +
                "       dob, " +
                "       phone, " +
                "       email, " +
                "       booking_count " +
                "FROM guests " +
                "WHERE EXTRACT(MONTH FROM dob) = ? " +
                "  AND EXTRACT(YEAR FROM AGE(dob)) < ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, birthMonth); // Gán tháng sinh
            stmt.setInt(2, maxAge);     // Gán độ tuổi tối đa

            var resultSet = stmt.executeQuery();
            System.out.println("Young Guests with Birthdays This Month:");
            boolean hasResults = false;

            while (resultSet.next()) {
                hasResults = true;
                int guestId = resultSet.getInt("guest_id");
                String guestName = resultSet.getString("guest_name");
                int guestAge = resultSet.getInt("guest_age");
                String gender = resultSet.getString("gender");
                String dob = resultSet.getDate("dob").toString();
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                int bookingCount = resultSet.getInt("booking_count");

                System.out.printf("Guest ID: %d, Name: %s, Age: %s, Gender: %s, DOB: %s, Phone: %s, Email: %s, Bookings: %d\n",
                        guestId, guestName, guestAge, gender, dob, phone, email, bookingCount);
            }

            if (!hasResults) {
                System.out.println("No guests found matching the criteria.");
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving data: " + e.getMessage());
        }
    }

}
