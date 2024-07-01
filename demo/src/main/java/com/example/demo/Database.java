package com.example.demo;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Database<T> {
    public int id;
    public ArrayList<T> data;
    public String filename;

    public Database(String name) {
        filename = name;
        load();
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            data = (ArrayList<T>) ois.readObject();
            //System.out.println("Data loaded successfully: " + data); // Debug print
        } catch (IOException | ClassNotFoundException e) {
            // If the file doesn't exist, create a new one
            data = new ArrayList<>();
            save(); // This will create the file
        }
    }

    public void addObject(T obj) {
        data.add(obj);
        save();
    }

    public void removeObject(int index) {
        System.out.println(data);
        data.remove(index);
        save();
    }

    public ArrayList<T> getObjectArray() {
        load();
        System.out.println(data);
        return data;
    }



    public void clearDatabase() {
        // Create a File object
        File file = new File(filename);

        // Check if the file exists
        if (file.exists()) {
            // Attempt to delete the file
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }
    }


class UserDatabase extends Database<Users>{
    public UserDatabase(String name) {
        super(name);
    }

    public Users getUser(String username, String password, ArrayList<Users> users) {
        load();
        for (Users user : users) {
            if (user.getName().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void setName(String name, Users user) {
        for (Users u : data) {
            if (u.equals(user)) {
                u.setName(name);
                save();
                return;
            }
        }
    }

    public void setPassword(String password, Users user) {
        for (Users u : data) {
            if (u.equals(user)) {
                u.setPassword(password);
                save();
                return;
            }
        }
    }

    public void setBalance(Double balance, Users user) {
        for (Users u : data) {
            if (u.equals(user)) {
                u.setBalance(balance);
                save();
                return;
            }
        }
    }

    public void setAdmin(boolean isAdmin, Users user) {
        for (Users u : data) {
            if (u.equals(user)) {
                u.setAdmin(isAdmin);
                save();
                return;
            }
        }
    }

    public void deductBalance(Double balance, Users user) {
        for (Users u : data) {
            if (u.equals(user)) {
                u.deductBalance(balance);
                save();
                return;
            }
        }
    }
    public void addBalance(Double balance, Users user) {
        for (Users u : data) {
            if (u.equals(user)) {
                u.addBalance(balance);
                save();
                return;
            }
        }
    }

    public double getBalance(Users user) {
        try {
            for (Users u : data) {
                if (u.equals(user)) {
                    return u.getBalance();
                }
            }
            // If the loop completes without finding the user, throw an exception
            throw new IllegalArgumentException("User not found");
        } catch (IllegalArgumentException e) {
            // Handle the exception here, you can log it or do any necessary actions
            System.err.println(e.getMessage());
            // Return a default value or rethrow the exception if needed
            throw e;
        }
    }
    public String getName(Users user){
        try {
            for (Users u : data) {
                if (u.equals(user)) {
                    return u.getName();
                }
            }
            // If the loop completes without finding the user, throw an exception
            throw new IllegalArgumentException("User not found");
        } catch (IllegalArgumentException e) {
            // Handle the exception here, you can log it or do any necessary actions
            System.err.println(e.getMessage());
            // Return a default value or rethrow the exception if needed
            throw e;
        }
    }
}

class ItemDatabase extends Database<Items>{
    private ArrayList<String> categorySet;
    public ItemDatabase(String name) {
        super(name);
        loadCategorySet();
    }

    @Override
    public void clearDatabase() {
        super.clearDatabase();
        // Create a File object
        File file = new File("category.dat");

        // Check if the file exists
        if (file.exists()) {
            // Attempt to delete the file
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

    public List<Items> getItemsByCategory(String category) {
        List<Items> itemsByCategory = new ArrayList<>();
        for (Items item : getObjectArray()) {
            if (item.getCategory().equals(category)) {
                itemsByCategory.add(item);
            }
        }
        return itemsByCategory;
    }
    public void saveItemsArray(ArrayList<Items> arrayList) {
        this.data = arrayList;
        save();
    }
    public void setName(String name, Items item){
        for (Items i : data) {
            if (i.equals(item)) {
                i.setName(name);
                save();
                return;
            }
        }
    }
    public void setPrice(Double price, Items item){
        for (Items i : data) {
            if (i.equals(item)) {
                i.setPrice(price);
                save();
                return;
            }
        }
    }
    public void setCategory(String category, Items item){
        for (Items i : data) {
            if (i.equals(item)) {
                i.setCategory(category);
                save();
                return;
            }
        }
    }
    public void setImage(byte[] image, Items item){
        for (Items i : data) {
            if (i.equals(item)) {
                i.setImage(image);
                save();
                return;
            }
        }
    }

    public ArrayList<String> getCategorySet() {
        return categorySet;
    }

    @Override
    public void addObject(Items obj) {
        super.addObject(obj);
        addCategory(obj.getCategory());
    }
    public void addCategory(String category) {
        if (!categorySet.contains(category)) {
            categorySet.add(category);
            saveCategorySet();
        }
    }
    public void removeCategory(String category){
        categorySet.remove(category);
        saveCategorySet();
    }

    public void saveCategorySet() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("category.dat"))) {
            oos.writeObject(categorySet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadCategorySet() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("category.dat"))) {
            categorySet = (ArrayList<String>) ois.readObject();
            System.out.println(categorySet);
        } catch (IOException | ClassNotFoundException e) {
            // If the file doesn't exist, create a new one
            categorySet = new ArrayList<>(Arrays.asList("Main Courses", "Desserts", "Appetizers"));
            saveCategorySet(); // This will create the file
        }
    }




}
class TransactionDatabase extends  Database<Transaction>{
    public TransactionDatabase(String name) {
        super(name);
    }

    public void setReport(String report, Transaction transaction) {
        for (Transaction t : data) {
            if (t.equals(transaction)) {
                t.setReport(report);
                save();
                return;
            }
        }
    }

    public void setTransactionDateTime(LocalDateTime dateTime, Transaction transaction) {
        for (Transaction t : data) {
            if (t.equals(transaction)) {
                t.setTransactionDateTime(dateTime);
                save();
                return;
            }
        }
    }

    public void setUserID(int userID, Transaction transaction) {
        for (Transaction t : data) {
            if (t.equals(transaction)) {
                t.setUserID(userID);
                save();
                return;
            }
        }
    }


}





