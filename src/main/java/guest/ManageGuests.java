package guest;

import model.Guest;

import java.util.Scanner;

import static guest.AddGuestToDatabase.addGuestToDatabase;
import static guest.DisplayGuestsFromDatabase.displayGuestsFromDatabase;
import static guest.DisplayYoungGuestsWithBirthdays.displayYoungGuestsWithBirthdays;
import static guest.SearchGuestsInDatabase.searchGuestsInDatabase;
import static main.MainGuest.*;

public class ManageGuests {
    public static void manageGuests(Scanner scanner) {
        while (true) {
            System.out.println("Manage Guests");
            System.out.println("1. Add Guest");
            System.out.println("2. Display Guests");
            System.out.println("3. Search Guests");
            System.out.println("4. Display Guests with Birthdays This Month");
            System.out.println("5. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    addGuestToDatabase(scanner);
                    break;
                case 2:
                    displayGuestsFromDatabase();
                    break;
                case 3:
                    System.out.print("Enter search keyword: ");
                    String keyword = scanner.nextLine();
                    searchGuestsInDatabase(keyword);
                    break;
                case 4:
                    displayYoungGuestsWithBirthdays(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

}
