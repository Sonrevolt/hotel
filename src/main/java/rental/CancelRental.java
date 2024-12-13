package rental;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CancelRental {
    public static void cancelRental(Scanner scanner) {
        System.out.print("Enter Rent ID to cancel: ");
        int rentId = scanner.nextInt();

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            // Begin transaction
            connection.setAutoCommit(false);

            // Get details of the rent
            String getRentSql = "SELECT room_number FROM rents WHERE id = ?";
            int roomNumber = -1;

            try (PreparedStatement getRentStmt = connection.prepareStatement(getRentSql)) {
                getRentStmt.setInt(1, rentId);
                var resultSet = getRentStmt.executeQuery();
                if (resultSet.next()) {
                    roomNumber = resultSet.getInt("room_number");
                } else {
                    System.out.println("Rent not found!");
                    connection.rollback();
                    return;
                }
            }

            // Delete rent record
            String deleteRentSql = "DELETE FROM rents WHERE id = ?";
            try (PreparedStatement deleteRentStmt = connection.prepareStatement(deleteRentSql)) {
                deleteRentStmt.setInt(1, rentId);
                deleteRentStmt.executeUpdate();
            }

            // Update room availability
            String updateRoomSql = "UPDATE rooms SET available = true WHERE room_number = ?";
            try (PreparedStatement updateRoomStmt = connection.prepareStatement(updateRoomSql)) {
                updateRoomStmt.setInt(1, roomNumber);
                updateRoomStmt.executeUpdate();
            }

            // Commit transaction
            connection.commit();
            System.out.println("Rental canceled successfully.");
        } catch (SQLException e) {
            System.out.println("Error while canceling rental: " + e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.out.println("Error during rollback: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                System.out.println("Error resetting auto-commit: " + autoCommitEx.getMessage());
            }
        }
    }


}
