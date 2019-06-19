import phonebook.PhoneBook;

import java.util.Scanner;

public class PhoneBookApp {

    private static PhoneBook phoneBook;
    private static String fileName;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Loading...");
        loadPhoneBook();

        while (true) {
            showMenu();
        }
    }

    private static void loadPhoneBook() {
        System.out.println("Enter the full path to the file from which you want to load your phone book. ");
        if (scanner.hasNextLine()) {
            fileName = scanner.nextLine();
        }
        phoneBook = PhoneBook.loadContactsFromTextFile(fileName);
        if (phoneBook != null) {
            phoneBook.displayContacts();
        } else {
            phoneBook = new PhoneBook(fileName);
            System.out.println("You created a new phonebook at " + fileName);
        }
    }

    private static void showMenu() {
        System.out.println("Menu:\n " +
                "Press 1 to view contacts\n " +
                "Press 2 to add a contact\n " +
                "Press 3 to delete a contact\n " +
                "Press 4 to save your contacts\n " +
                "Press 5 to view top 5 contacts with most outgoing calls\n " +
                "Press 6 to view a contact's phone number\n " +
                "Press 7 to make a call\n " +
                "Press 8 to quit\n " +
                "If you quit without saving your newly added contacts the changes won't be saved to the file. ");

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
                    if (phoneBook.saveContactsToTextFile()) {
                        System.out.println("Saved changes.");
                    } else {
                        System.out.println("Failed to save changes.");
                    }
                    break;
                case "5":
                    phoneBook.displayContactsWithMostOutgoingCalls();
                    break;
                case "6":
                    System.out.println("Enter the name of the contact. ");
                    if (scanner.hasNextLine()) {
                        name = scanner.nextLine();
                        phoneBook.accessAPhoneNumber(name);
                    }
                    break;
                case "7":
                    System.out.println("Who do you want to call? ");
                    if (scanner.hasNextLine()) {
                        name = scanner.nextLine();
                        phoneBook.makeACall(name);
                    } else {
                        System.out.println("There is no such contact. ");
                    }
                    break;
                case "8":
                    System.out.println("Goodbye!");
                    System.exit(100);
                default:
                    System.out.println(userSelection + "is not a valid selection. ");
            }
        }
    }
}
