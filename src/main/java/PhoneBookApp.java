import phonebook.PhoneBook;

import java.util.Scanner;

public class PhoneBookApp {

    private static String phoneBookFileName = "default";
    private static PhoneBook phoneBook;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Loading...");
        loadPhoneBook();

        while (true) {
            showMenu();
        }
    }

    private static void loadPhoneBook() {
        System.out.println("Choose a file to load your phonebook from: ");
        if (scanner.hasNextLine()) {
            phoneBookFileName = scanner.nextLine();
        }
        phoneBook = PhoneBook.loadContactsFromDisk(phoneBookFileName);
        if (phoneBook != null) {
            System.out.println("You loaded your contacts from " + phoneBookFileName);
            phoneBook.displayContacts();
        } else {
            phoneBook = new PhoneBook(phoneBookFileName);
            System.out.println("Created a new phonebook " + phoneBookFileName);
        }

    }

    private static void showMenu() {
        System.out.println("Menu: " +
                "Press 1 to view contacts " +
                "Press 2 to add a contact " +
                "Press 3 to delete a contact " +
                "Press 4 to save your contacts " +
                "Press 5 to view top 5 contacts with most outgoing calls" +
                "Press 6 to quit ");

        handleUserMenuSelection();
    }

    private static void handleUserMenuSelection() {
        if (scanner.hasNextLine()) {
            String userSelection = scanner.nextLine();
            String name, phone;

            switch (userSelection) {
                case "1":
                    phoneBook.displayContacts();
                    break;
                case "2":
                    System.out.println("Name: ");
                    if (scanner.hasNextLine()) {
                        name = scanner.nextLine();
                        System.out.println("Phone number: ");
                        if (scanner.hasNextLine()) {
                            phone = scanner.nextLine();
                            PhoneBook.Result result = phoneBook.addContact(name, phone);
                            switch (result) {
                                case ADDED:
                                    System.out.println("Added: " + name);
                                    break;
                                case UPDATED:
                                    System.out.println("Updated: " + name);
                                    break;
                                case INVALID_NAME:
                                    System.out.println(name + " is not a valid name");
                                    break;
                                case INVALID_NUMBER:
                                    System.out.println(phone + " is not a valid phone number");
                                    break;
                            }
                        }
                    }
                    break;
                case "3":
                    System.out.println("Name: ");
                    if (scanner.hasNextLine()) {
                        name = scanner.nextLine();
                        if (phoneBook.deleteContact(name)) {
                            System.out.println("Deleted: " + name);
                        } else {
                            System.out.println("Failed to delete " + name);
                        }
                    }
                    break;
                case "4":
                    if (phoneBook.saveContactsToDisk()) {
                        System.out.println("Saved changes.");
                    } else {
                        System.out.println("Failed to save changes.");
                    }
                    break;
                case "5":
                    phoneBook.displayContactsWithMostOutgoingCalls();
                    break;
                case "6":
                    System.out.println("Goodbye!");
                    System.exit(100);
                default:
                    System.out.println(userSelection + "is not a valid selection. ");
            }
        }
    }
}
