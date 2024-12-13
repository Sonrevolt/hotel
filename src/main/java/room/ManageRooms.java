package room;

import model.Room;

import java.util.Scanner;

import static room.AddRoomToDatabase.addRoomToDatabase;
import static room.DisplayRoomsFromDatabase.displayRoomsFromDatabase;
import static room.SearchRoomsInDatabase.searchRoomsInDatabase;

public class ManageRooms {
    public static void manageRooms(Scanner scanner) {
        while (true) {
            System.out.println("1. Add Room\n2. Display Rooms\n3. Search Room\n4. Back");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter room type: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Is the room available? (true/false): ");
                    boolean available = scanner.nextBoolean();
                    addRoomToDatabase(new Room(roomNumber, type, price, available));
                    break;
                case 2:
                    displayRoomsFromDatabase();
                    break;
                case 3:
                    System.out.print("Enter keyword to search: ");
                    String keyword = scanner.nextLine();
                    searchRoomsInDatabase(keyword);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
