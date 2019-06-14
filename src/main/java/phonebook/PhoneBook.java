package phonebook;

import java.util.*;
import java.util.stream.Collectors;

public class PhoneBook {

    private final String fileName;
    private final Map<String, Contact> contactMap = new HashMap<>();

    public PhoneBook(String fileName){
        this.fileName = fileName;
    }

    String getFileName(){
        return fileName;
    }

    Collection<Contact> getContacts(){
        return contactMap.values();
    }

    public enum Result{
        ADDED,
        UPDATED,
        INVALID_NUMBER,
        INVALID_NAME
    }

    private boolean isValidName(String name){
        return name != null && !name.trim().isEmpty();
    }

    private boolean isValidPhoneNumber(String number){
        return number != null && number.replace(" ", "")
                .matches("(\\+359|0|00)8[789][2-9]\\d{6}")
                && number.length() > 9
                && number.length() < 15;
    }

    void addFromFile(Collection<Contact> contacts){
        if(contacts != null){
            contacts.stream()
                    .filter(Objects::nonNull)
                    .forEach(contact -> contactMap.put(contact.getName(), contact));
        }
    }

    public Result addContact(String name, String number){
        if (isValidName(name)) {
            if (isValidPhoneNumber(number)) {
                name = name.toLowerCase();
                Result result;
                Contact existingContact = contactMap.get(name);

                if(existingContact != null) {
                    existingContact.setNumber(number);
                    result = Result.UPDATED;
                } else {
                    contactMap.put(name, new Contact(name, number));
                    result = Result.ADDED;
                }
                return result;
            } else {
                return Result.INVALID_NUMBER;
            }
        } else {
            return Result.INVALID_NAME;
        }
    }

    public boolean deleteContact(String name){
        if(name != null){
            name = name.toLowerCase();
            return contactMap.remove(name) != null;
        }
        return false;
    }

    public void displayContacts(){
        if(!contactMap.isEmpty()){
            String contacts = contactMap.values()
                    .stream()
                    .map(contact -> contact + " | ")
                    .sorted()
                    .collect(Collectors.joining());
            System.out.println("Contacts: \n\t" + contacts);
        } else {
            System.out.println("No contacts.");
        }
    }

    public void displayContactsWithMostOutgoingCalls(){
        if(!contactMap.isEmpty()){
            List<String> contacts = contactMap.values()
                    .stream()
                    .sorted((Comparator.comparing(Contact::getNumberOfOutgoingCalls).reversed()))
                    .limit(5)
                    .map(contact -> contact + " | ")
                    .collect(Collectors.toList());
            System.out.println("Contacts: \n\t " + contacts);
        } else {
            System.out.println("No contacts");
        }
    }

    public boolean saveContactsToDisk(){
        return PhoneBookFileManager.save(this);
    }

    public static PhoneBook loadContactsFromDisk(String fileName){
        return PhoneBookFileManager.load((fileName));
    }
}
