package rental;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DisplayFrequentGuests {
    public static void displayFrequentGuests() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        String sql = "SELECT id AS guest_id, " +
                "       name AS guest_name, " +
                "       gender, " +
                "       dob, " +
                "       phone, " +
                "       email, " +
                "       booking_count " +
                "FROM guests " +
                "WHERE booking_count > 3";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            System.out.println("Guests with More Than 3 Bookings:");
            boolean hasResults = false;

            while (resultSet.next()) {
                hasResults = true;
                int guestId = resultSet.getInt("guest_id");
                String guestName = resultSet.getString("guest_name");
                String gender = resultSet.getString("gender");
                String dob = resultSet.getDate("dob").toString();
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                int bookingCount = resultSet.getInt("booking_count");

                System.out.printf("Guest ID: %d, Name: %s, Gender: %s, DOB: %s, Phone: %s, Email: %s, Bookings: %d\n",
                        guestId, guestName, gender, dob, phone, email, bookingCount);
            }

            if (!hasResults) {
                System.out.println("No guests found with more than 3 bookings.");
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving data: " + e.getMessage());
        }
    }

}
