package rental;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DisplayRentalsWithinDateRange {
    public static void displayRentalsWithinDateRange(Scanner scanner) {
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.next();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.next();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        String sql = "SELECT g.id AS guest_id, g.name AS guest_name, r.room_number, r.check_in_date, r.check_out_date " +
                "FROM rents r " +
                "JOIN guests g ON r.guest_id = g.id " +
                "WHERE r.check_in_date BETWEEN ? AND ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));

            var resultSet = stmt.executeQuery();
            System.out.println("Rentals within the specified date range:");
            while (resultSet.next()) {
                int guestId = resultSet.getInt("guest_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String checkInDate = resultSet.getDate("check_in_date").toString();
                String checkOutDate = resultSet.getDate("check_out_date").toString();

                System.out.printf("Guest ID: %d, Name: %s, Room Number: %d, Check-In: %s, Check-Out: %s\n",
                        guestId, guestName, roomNumber, checkInDate, checkOutDate);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving rentals: " + e.getMessage());
        }
    }

}
