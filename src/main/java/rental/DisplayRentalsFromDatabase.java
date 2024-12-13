package rental;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class DisplayRentalsFromDatabase {
    public static void displayRentalsFromDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        String sql = "SELECT r.id, g.name AS guest_name, g.booking_count, r.room_number, r.check_in_date, r.check_out_date, r.total_cost " +
                "FROM rents r " +
                "JOIN guests g ON r.guest_id = g.id";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            var resultSet = pstmt.executeQuery();
            System.out.println("Rental List from Database:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String guestName = resultSet.getString("guest_name");
                int bookingCount = resultSet.getInt("booking_count");
                int roomNumber = resultSet.getInt("room_number");
                LocalDate checkInDate = resultSet.getDate("check_in_date").toLocalDate();
                LocalDate checkOutDate = resultSet.getDate("check_out_date").toLocalDate();
                double totalCost = resultSet.getDouble("total_cost");

                System.out.printf("ID: %d, Guest Name: %s (Bookings: %d), Room Number: %d, Check-In: %s, Check-Out: %s, Total Cost: %.2f\n",
                        id, guestName, bookingCount, roomNumber, checkInDate, checkOutDate, totalCost);
            }
        } catch (SQLException e) {
            System.out.println("Error displaying rentals: " + e.getMessage());
        }
    }

}
