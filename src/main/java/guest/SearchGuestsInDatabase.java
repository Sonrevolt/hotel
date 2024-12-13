package guest;

import main.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class SearchGuestsInDatabase {
    public static void searchGuestsInDatabase(String keyword) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return;

        String sql = "SELECT * FROM guests WHERE name ILIKE ? OR phone ILIKE ? OR email ILIKE ? OR CAST(dob AS TEXT) ILIKE ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);

            var resultSet = pstmt.executeQuery();
            System.out.println("Search results for keyword: " + keyword);
            boolean hasResults = false;
            while (resultSet.next()) {
                hasResults = true;
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

            if (!hasResults) {
                System.out.println("No results found for keyword: " + keyword);
            }
        } catch (SQLException e) {
            System.out.println("Error while searching guests: " + e.getMessage());
        }
    }

}
