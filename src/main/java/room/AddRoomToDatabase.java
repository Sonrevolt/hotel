package room;

import main.DatabaseConnection;
import model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddRoomToDatabase {
    public static void addRoomToDatabase(Room room) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;
        String checkSql = "SELECT COUNT(*) FROM rooms WHERE room_number = ?";
        String insertSql = "INSERT INTO rooms (room_number, type, price, available) VALUES (?, ?, ?, ?)";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkSql);
             PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
            checkStmt.setInt(1, room.getRoomNumber());
            var resultSet = checkStmt.executeQuery();
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                System.out.println("Room with this number already exists. Cannot add.");
                return;
            }
            insertStmt.setInt(1, room.getRoomNumber());
            insertStmt.setString(2, room.getType());
            insertStmt.setDouble(3, room.getPrice());
            insertStmt.setBoolean(4, room.isAvailable());
            insertStmt.executeUpdate();
            System.out.println("Room added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding room to database: " + e.getMessage());
        }
    }
}
