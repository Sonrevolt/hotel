package room;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DisplayRoomsFromDatabase {
    public static void displayRoomsFromDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;
        String sql = "SELECT * FROM rooms";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            var resultSet = ps.executeQuery();
            System.out.println("Rooms from Database:");
            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                String type = resultSet.getString("type");
                double price = resultSet.getDouble("price");
                boolean available = resultSet.getBoolean("available");
                System.out.printf("Room Number: %d, Type: %s, Price: %.2f, Available: %s%n",
                        roomNumber, type, price, available ? "Yes" : "No");
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rooms from database: " + e.getMessage());
        }
    }
}
