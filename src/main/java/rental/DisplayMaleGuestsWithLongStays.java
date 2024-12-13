package rental;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DisplayMaleGuestsWithLongStays {
    public static void displayMaleGuestsWithLongStays() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        String sql = "SELECT g.id AS guest_id, " +
                "       g.name AS guest_name, " +
                "       g.gender, " +
                "       r.room_number, " +
                "       r.check_in_date, " +
                "       r.check_out_date, " +
                "       AGE(r.check_out_date, r.check_in_date) AS duration " +
                "FROM rents r " +
                "JOIN guests g ON r.guest_id = g.id " +
                "WHERE g.gender = 'M' " +
                "  AND r.check_out_date > r.check_in_date + INTERVAL '3 days'";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            var resultSet = stmt.executeQuery();
            System.out.println("Male Guests with Stays Longer than 3 Days:");
            boolean hasResults = false;

            while (resultSet.next()) {
                hasResults = true;
                int guestId = resultSet.getInt("guest_id");
                String guestName = resultSet.getString("guest_name");
                String gender = resultSet.getString("gender");
                int roomNumber = resultSet.getInt("room_number");
                String checkInDate = resultSet.getDate("check_in_date").toString();
                String checkOutDate = resultSet.getDate("check_out_date").toString();
                String duration = resultSet.getString("duration");

                System.out.printf("Guest ID: %d, Name: %s, Gender: %s, Room: %d, Check-In: %s, Check-Out: %s, Duration: %s\n",
                        guestId, guestName, gender, roomNumber, checkInDate, checkOutDate, duration);
            }

            if (!hasResults) {
                System.out.println("No male guests with stays longer than 3 days found.");
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving data: " + e.getMessage());
        }
    }

}
