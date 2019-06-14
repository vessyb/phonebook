package phonebook;

import java.io.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

class PhoneBookFileManager {

    private static String getFileNameWithExtension(String fileName){
        if(fileName != null && !fileName.trim().isEmpty()){
            fileName = fileName.replaceAll("[^A-Za-z0-9]", "");
            return fileName.contains(".txt") ? fileName : fileName + ".txt";
        }else{
            return fileName;
        }
    }

    static boolean save(PhoneBook phoneBook) {
        if(phoneBook != null){
            String fileName = phoneBook.getFileName();
            String fileNameAndExtension = getFileNameWithExtension(fileName);

            try(final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileNameAndExtension))){
                List<Contact> serializableList = new LinkedList<>(phoneBook.getContacts());
                objectOutputStream.writeObject(serializableList);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    static PhoneBook load(String fileName){
        if(fileName != null && !fileName.trim().isEmpty()){
            String filneNameWithExtension = getFileNameWithExtension(fileName);

            try(final ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(filneNameWithExtension))) {
                Collection<Contact> deserializedContacts = (Collection<Contact>) objectInputStream.readObject();
                PhoneBook phoneBook = new PhoneBook(fileName);
                phoneBook.addFromFile(deserializedContacts);
                return phoneBook;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
