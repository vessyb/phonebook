package phonebook;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class PhoneBookFileManager {

    static boolean save(PhoneBook phoneBook) {
        if (phoneBook != null) {
            String fileName = phoneBook.getFileName();
            try (PrintWriter printWriter = new PrintWriter(new FileWriter(fileName))) {
                phoneBook.getContacts()
                        .stream()
                        .map(contact -> contact.getName()
                                + ", "
                                + contact.getNumber())
                        .forEach(printWriter::println   );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    static PhoneBook load(String fileName) {
        if (fileName != null && !fileName.trim().isEmpty()) {
            PhoneBook phoneBook = new PhoneBook(fileName);
            List<Contact> contacts = new ArrayList<>();
            try {
                List<String> lines = Files.readAllLines(Paths.get(fileName));
                contacts = lines.stream()
                        .map(line -> line.trim().replaceAll("\n+", "").split(",\\s+"))
                        .map(split -> new Contact(split[0], split[1]))
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
            phoneBook.addFromFile(contacts);
            return phoneBook;
        }
        return null;
    }
}

