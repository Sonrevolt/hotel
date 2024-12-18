package rental;

import java.util.Scanner;

import static rental.AddRental.addRental;
import static rental.CancelRental.cancelRental;
import static rental.DisplayFrequentGuests.displayFrequentGuests;
import static rental.DisplayMaleGuestsWithLongStays.displayMaleGuestsWithLongStays;
import static rental.DisplayRentalsFromDatabase.displayRentalsFromDatabase;
import static rental.DisplayRentalsWithinDateRange.displayRentalsWithinDateRange;
import static rental.SearchRentalsInDatabase.searchRentalsInDatabase;

public class ManageRentals {
    public static void manageRentals(Scanner scanner) {
        while (true) {
            System.out.println("\n--- RENTAL MANAGEMENT ---");
            System.out.println("1. Add Rental");
            System.out.println("2. Display Rentals");
            System.out.println("3. Search Rentals");
            System.out.println("4. Cancel Rental");
            System.out.println("5. Display Rentals Within Date Range");
            System.out.println("6. Display Male Guests with Long Stays");
            System.out.println("7. Display Frequent Guests");
            System.out.println("8. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addRental(scanner);
                    break;
                case 2:
                    displayRentalsFromDatabase();
                    break;
                case 3:
                    searchRentalsInDatabase(scanner);
                    break;
                case 4:
                    cancelRental(scanner);
                    break;
                case 5:
                    displayRentalsWithinDateRange(scanner);
                    break;
                case 6:
                    displayMaleGuestsWithLongStays();
                    break;
                case 7:
                    displayFrequentGuests();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
