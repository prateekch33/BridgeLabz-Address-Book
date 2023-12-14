package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    AddressBook addressBook = new AddressBook();

    public void addContactFromConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter address:");
        String address = scanner.nextLine();

        System.out.println("Enter city:");
        String city = scanner.nextLine();

        System.out.println("Enter state:");
        String state = scanner.nextLine();

        System.out.println("Enter zip:");
        String zip = scanner.nextLine();

        System.out.println("Enter phone number:");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter email:");
        String email = scanner.nextLine();

        Contact contact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
        addressBook.addContact(contact);
    }

    public void editContactFromConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter first name of the contact to edit:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name of the contact to edit:");
        String lastName = scanner.nextLine();

        System.out.println("Enter new address:");
        String address = scanner.nextLine();

        System.out.println("Enter new city:");
        String city = scanner.nextLine();

        System.out.println("Enter new state:");
        String state = scanner.nextLine();

        System.out.println("Enter new zip:");
        String zip = scanner.nextLine();

        System.out.println("Enter new phone number:");
        String phoneNumber = scanner.nextLine();

        System.out.println("Enter new email:");
        String email = scanner.nextLine();

        addressBook.editContact(firstName, lastName, address, city, state, zip, phoneNumber, email);
    }

    public void deleteContactFromConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter first name of the contact to delete:");
        String firstName = scanner.nextLine();

        System.out.println("Enter last name of the contact to delete:");
        String lastName = scanner.nextLine();

        addressBook.deleteContact(firstName, lastName);
    }

    private static Map<String, AddressBook> addressBooks = new HashMap<>();

    public void addAddressBookFromConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the new address book:");
        String name = scanner.nextLine();

        AddressBook addressBook = new AddressBook();
        addressBooks.put(name, addressBook);

        System.out.println("Address book added.");
    }

    public void addMultipleContactsFromConsole() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the address book to add contacts to:");
        String name = scanner.nextLine();

        AddressBook addressBook = addressBooks.get(name);
        if (addressBook == null) {
            System.out.println("Address book not found.");
            return;
        }

        String addMore = "yes";
        while (addMore.equalsIgnoreCase("yes")) {
            System.out.println("Enter first name:");
            String firstName = scanner.nextLine();

            System.out.println("Enter last name:");
            String lastName = scanner.nextLine();

            System.out.println("Enter address:");
            String address = scanner.nextLine();

            System.out.println("Enter city:");
            String city = scanner.nextLine();

            System.out.println("Enter state:");
            String state = scanner.nextLine();

            System.out.println("Enter zip:");
            String zip = scanner.nextLine();

            System.out.println("Enter phone number:");
            String phoneNumber = scanner.nextLine();

            System.out.println("Enter email:");
            String email = scanner.nextLine();

            Contact contact = new Contact(firstName, lastName, address, city, state, zip, phoneNumber, email);
            addressBook.addContact(contact);

            System.out.println("Do you want to add another contact? (yes/no)");
            addMore = scanner.nextLine();
        }
    }

    public List<Contact> searchContactsByCityOrState(String cityOrState) {
        List<Contact> matchingContacts = new ArrayList<>();

        for (AddressBook addressBook : addressBooks.values()) {
            List<Contact> contacts = addressBook.getContacts().stream()
                    .filter(c -> c.getCity().equals(cityOrState) || c.getState().equals(cityOrState))
                    .collect(Collectors.toList());

            matchingContacts.addAll(contacts);
        }

        return matchingContacts;
    }

    private Map<String, List<Contact>> contactsByCity = new HashMap<>();
    private Map<String, List<Contact>> contactsByState = new HashMap<>();

    public void addContact(Contact contact, AddressBook addressBook) {
        addressBook.addContact(contact);

        String city = contact.getCity();
        String state = contact.getState();

        contactsByCity.computeIfAbsent(city, k -> new ArrayList<>()).add(contact);
        contactsByState.computeIfAbsent(state, k -> new ArrayList<>()).add(contact);
    }

    public List<Contact> getContactsByCity(String city) {
        return contactsByCity.getOrDefault(city, new ArrayList<>());
    }

    public List<Contact> getContactsByState(String state) {
        return contactsByState.getOrDefault(state, new ArrayList<>());
    }

    public int getContactCountByCity(String city) {
        return contactsByCity.getOrDefault(city, new ArrayList<>()).size();
    }

    public int getContactCountByState(String state) {
        return contactsByState.getOrDefault(state, new ArrayList<>()).size();
    }

    //

    public static void main(String[] args) {

        // System.out.println("Hello world!");
        AddressBook addressBook = addressBooks.get("Some Address Book");
        List<Contact> sortedContacts = addressBook.sortContactsByName();
        sortedContacts.forEach(System.out::println);

        Scanner scanner = new Scanner(System.in);
        int choice;
        // AddressBook addressBook = new AddressBook();

        do {
            System.out.println("\nAddress Book Menu:");
            System.out.println("1. Add contact");
            System.out.println("2. View contacts by city");
            System.out.println("3. View contacts by state");
            System.out.println("4. Get contact count by city");
            System.out.println("5. Get contact count by state");
            System.out.println("6. Sort contacts by name");
            System.out.println("7. Sort contacts by city");
            System.out.println("8. Sort contacts by state");
            System.out.println("9. Sort contacts by zip");
            System.out.println("10. Save to file");
            System.out.println("11. Load from file");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            Main ob = new Main();

            switch (choice) {
                case 1:
                    // Assuming you have a method to create a new contact
                    ob.addContactFromConsole();
                    break;
                case 2:
                    System.out.println("Enter city:");
                    String city = scanner.nextLine();
                    ob.getContactsByCity(city);
                    break;
                case 3:
                    System.out.println("Enter state:");
                    String state = scanner.nextLine();
                    ob.getContactCountByState(state);
                    break;
                case 4:
                    System.out.println("Enter city:");
                    city = scanner.nextLine();
                    ob.getContactCountByCity(city);
                    break;
                case 5:
                    System.out.println("Enter state:");
                    state = scanner.nextLine();
                    ob.getContactCountByState(state);
                    break;
                case 6:
                    System.out.println("Sorted contacts: " + addressBook.sortContactsByName());
                    break;
                case 7:
                    System.out.println("Sorted contacts: " + addressBook.sortContactsByCity());
                    break;
                case 8:
                    System.out.println("Sorted contacts: " + addressBook.sortContactsByState());
                    break;
                case 9:
                    System.out.println("Sorted contacts: " + addressBook.sortContactsByZip());
                    break;
                case 10:
                    System.out.println("Enter filename:");
                    String filename = scanner.nextLine();
                    try {
                        addressBook.saveToFile(filename);
                    } catch (IOException e) {
                        System.out.println("Error saving to file: " + e.getMessage());
                    }
                    break;
                case 11:
                    System.out.println("Enter filename:");
                    filename = scanner.nextLine();
                    try {
                        addressBook.loadFromFile(filename);
                    } catch (IOException e) {
                        System.out.println("Error loading from file: " + e.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);

        scanner.close();
    }
}