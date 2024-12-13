package room;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SearchRoomsInDatabase {
    public static void searchRoomsInDatabase(String keyword) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        String sql = "SELECT * FROM rooms WHERE CAST(room_number AS TEXT) ILIKE ? OR type ILIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            var resultSet = pstmt.executeQuery();
            System.out.println("Search results for keyword: " + keyword);
            while (resultSet.next()) {
                int roomNumber = resultSet.getInt("room_number");
                String type = resultSet.getString("type");
                double price = resultSet.getDouble("price");
                boolean available = resultSet.getBoolean("available");

                System.out.printf("Room Number: %d, Type: %s, Price: %.2f, Available: %s\n",
                        roomNumber, type, price, available ? "Yes" : "No");
            }
        } catch (SQLException e) {
            System.out.println("Error while searching rooms: " + e.getMessage());
        }
    }
}
