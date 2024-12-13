package rental;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class SearchRentalsInDatabase {
    public static void searchRentalsInDatabase(Scanner scanner) {
        System.out.print("Enter keyword to search in rentals: ");
        String keyword = scanner.next();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        String sql = "SELECT g.id AS guest_id, g.name AS guest_name, r.room_number, r.check_in_date, r.check_out_date " +
                "FROM rents r " +
                "JOIN guests g ON r.guest_id = g.id " +
                "WHERE CAST(g.id AS TEXT) LIKE ? OR g.name ILIKE ? OR CAST(r.room_number AS TEXT) LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);

            var resultSet = stmt.executeQuery();
            System.out.println("Search results for rentals:");
            boolean hasResults = false;

            while (resultSet.next()) {
                hasResults = true;
                int guestId = resultSet.getInt("guest_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String checkInDate = resultSet.getDate("check_in_date").toString();
                String checkOutDate = resultSet.getDate("check_out_date").toString();

                System.out.printf("Guest ID: %d, Name: %s, Room Number: %d, Check-In: %s, Check-Out: %s\n",
                        guestId, guestName, roomNumber, checkInDate, checkOutDate);
            }

            if (!hasResults) {
                System.out.println("No rentals match the keyword.");
            }
        } catch (SQLException e) {
            System.out.println("Error while searching rentals: " + e.getMessage());
        }
    }

}
