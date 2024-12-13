package guest;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class DisplayGuestsFromDatabase {
    public static void displayGuestsFromDatabase() {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        String sql = "SELECT * FROM guests";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            var resultSet = pstmt.executeQuery();
            System.out.println("Guest List from Database:");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                LocalDate dob = resultSet.getDate("dob").toLocalDate();
                System.out.printf("ID: %d, Name: %s, Age: %d, Gender: %s, Phone: %s, Email: %s, DOB: %s\n",
                        id, name, age, gender, phone, email, dob);
            }
        } catch (SQLException e) {
            System.out.println("Error displaying guests: " + e.getMessage());
        }
    }

}
