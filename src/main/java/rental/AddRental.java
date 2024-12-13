package rental;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddRental {
    public static void addRental(Scanner scanner) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        try {
            // Fetch Guests and Rooms from Database
            String getGuestsSql = "SELECT id, name FROM guests";
            String getRoomsSql = "SELECT room_number, type, price FROM rooms WHERE available = true";

            // Display guests
            System.out.println("Available Guests:");
            try (PreparedStatement guestStmt = connection.prepareStatement(getGuestsSql)) {
                var guestResultSet = guestStmt.executeQuery();
                while (guestResultSet.next()) {
                    int id = guestResultSet.getInt("id");
                    String name = guestResultSet.getString("name");
                    System.out.printf("ID: %d, Name: %s\n", id, name);
                }
            }

            System.out.print("Enter Guest ID: ");
            int guestId = scanner.nextInt();

            // Display available rooms
            System.out.println("Available Rooms:");
            try (PreparedStatement roomStmt = connection.prepareStatement(getRoomsSql)) {
                var roomResultSet = roomStmt.executeQuery();
                while (roomResultSet.next()) {
                    int roomNumber = roomResultSet.getInt("room_number");
                    String type = roomResultSet.getString("type");
                    double price = roomResultSet.getDouble("price");
                    System.out.printf("Room Number: %d, Type: %s, Price: %.2f\n", roomNumber, type, price);
                }
            }

            System.out.print("Enter Room Number: ");
            int roomNumber = scanner.nextInt();

            System.out.print("Enter Check-In Date (YYYY-MM-DD): ");
            String checkIn = scanner.next();
            System.out.print("Enter Check-Out Date (YYYY-MM-DD): ");
            String checkOut = scanner.next();

            // Insert rental record into `rents`
            String insertRentSql = "INSERT INTO rents (guest_id, room_number, check_in_date, check_out_date, total_cost) " +
                    "VALUES (?, ?, ?, ?, ?)";
            String updateRoomSql = "UPDATE rooms SET available = false WHERE room_number = ?";
            String updateGuestSql = "UPDATE guests SET booking_count = booking_count + 1 WHERE id = ?";

            // Calculate total cost (for simplicity, price * number of nights)
            String getRoomPriceSql = "SELECT price FROM rooms WHERE room_number = ?";
            double pricePerNight = 0.0;

            try (PreparedStatement priceStmt = connection.prepareStatement(getRoomPriceSql)) {
                priceStmt.setInt(1, roomNumber);
                var priceResultSet = priceStmt.executeQuery();
                if (priceResultSet.next()) {
                    pricePerNight = priceResultSet.getDouble("price");
                }
            }

            LocalDate checkInDate = LocalDate.parse(checkIn);
            LocalDate checkOutDate = LocalDate.parse(checkOut);
            long nights = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
            double totalCost = pricePerNight * nights;

            // Execute database operations
            connection.setAutoCommit(false);

            try (PreparedStatement rentStmt = connection.prepareStatement(insertRentSql);
                 PreparedStatement roomStmt = connection.prepareStatement(updateRoomSql);
                 PreparedStatement guestStmt = connection.prepareStatement(updateGuestSql)) {

                // Insert into rents
                rentStmt.setInt(1, guestId);
                rentStmt.setInt(2, roomNumber);
                rentStmt.setDate(3, java.sql.Date.valueOf(checkInDate));
                rentStmt.setDate(4, java.sql.Date.valueOf(checkOutDate));
                rentStmt.setDouble(5, totalCost);
                rentStmt.executeUpdate();

                // Update room availability
                roomStmt.setInt(1, roomNumber);
                roomStmt.executeUpdate();

                // Update guest booking count
                guestStmt.setInt(1, guestId);
                guestStmt.executeUpdate();

                connection.commit();
                System.out.println("Rental added successfully.");
            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Error adding rental: " + e.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error while adding rental: " + e.getMessage());
        }
    }

}
