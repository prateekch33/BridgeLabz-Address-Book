package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class AddressBook {
    private final List<Contact> contacts;

    public AddressBook() {
        this.contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        if (this.contacts.stream().anyMatch(c -> c.equals(contact))) {
            System.out.println("Contact already exists.");
        } else {
            this.contacts.add(contact);
        }
    }

    public void editContact(String firstName, String lastName, String newAddress, String newCity, String newState,
            String newZip, String newPhoneNumber, String newEmail) {
        for (Contact contact : this.contacts) {
            if (contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName)) {
                contact.setAddress(newAddress);
                contact.setCity(newCity);
                contact.setState(newState);
                contact.setZip(newZip);
                contact.setPhoneNumber(newPhoneNumber);
                contact.setEmail(newEmail);
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    public void deleteContact(String firstName, String lastName) {
        for (Iterator<Contact> iterator = this.contacts.iterator(); iterator.hasNext();) {
            Contact contact = iterator.next();
            if (contact.getFirstName().equals(firstName) && contact.getLastName().equals(lastName)) {
                iterator.remove();
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    public List<Contact> getContacts() {
        return this.contacts;
    }

    public Contact getContact(int index) {
        return this.contacts.get(index);
    }

    public void removeContact(int index) {
        this.contacts.remove(index);
    }

    public int getNumberOfContacts() {
        return this.contacts.size();
    }

    public List<Contact> sortContactsByName() {
        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getFirstName).thenComparing(Contact::getLastName))
                .collect(Collectors.toList());
    }

    public List<Contact> sortContactsByCity() {
        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getCity))
                .collect(Collectors.toList());
    }

    public List<Contact> sortContactsByState() {
        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getState))
                .collect(Collectors.toList());
    }

    public List<Contact> sortContactsByZip() {
        return contacts.stream()
                .sorted(Comparator.comparing(Contact::getZip))
                .collect(Collectors.toList());
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Contact contact : contacts) {
                writer.write(contact.getFirstName() + "," + contact.getLastName() + "," +
                        contact.getCity() + "," + contact.getState() + "," +
                        contact.getZip() + "," + contact.getPhoneNumber() + "," +
                        contact.getEmail());
                writer.newLine();
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Contact contact = new Contact(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
                contacts.add(contact);
            }
        }
    }

}