package main;

import model.Guest;
import model.Room;
import model.Rent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static guest.ManageGuests.manageGuests;
import static rental.ManageRentals.manageRentals;
import static room.ManageRooms.manageRooms;

public class MainGuest {
    private static List<Guest> guestList = new ArrayList<>();
    private static List<Room> roomList = new ArrayList<>();
    private static List<Rent> rentList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n --- HOTEL MANAGEMENT --- ");
            System.out.println("1. Manage Guests");
            System.out.println("2. Manage Rooms");
            System.out.println("3. Manage Rentals");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    manageGuests(scanner);
                    break;
                case 2:
                    manageRooms(scanner);
                    break;
                case 3:
                    manageRentals(scanner);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}



