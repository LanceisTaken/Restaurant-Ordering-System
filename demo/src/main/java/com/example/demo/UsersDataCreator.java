package com.example.demo;

import javafx.scene.image.Image;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class UsersDataCreator {



    public static void main(String[] args) {
        createUsersDataFile();
        createItemsDataFile();
    }

    public static void createUsersDataFile() {
        ArrayList<Users> users = new ArrayList<>();
        users.add(new Users("John", "1234"));
        users.add(new Users("Jane", "5678"));
        users.add(new Users("admin", "admin",true));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
            System.out.println("users.dat created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void createItemsDataFile() {
        ArrayList<Items> Items = new ArrayList<>();
        Items.add(new Items("Satay Ayam (1 Piece)", 1.5,"Appetizers","/satayayam.png"));
        Items.add(new Items("Roti Canai (1 Piece)", 2.3,"Appetizers","/RotiCanai-1.jpg"));
        Items.add(new Items("Popiah", 7.5,"Appetizers","/Popiah.JPG"));
        Items.add(new Items("Otak-Otak (1 Piece)", 1.5,"Appetizers","/otak.jpg"));
        Items.add(new Items("Keropok Lekor (1 Piece)", 1.3,"Appetizers","/Keropok.jpg"));
        Items.add(new Items("Nasi Lemak Ayam Goreng", 11.2,"Main Courses","/nasilemak.PNG"));
        Items.add(new Items("Nasi Lemak Daging Rendang", 12.2,"Main Courses","/nasilemakdaginrendang.PNG"));
        Items.add(new Items("Mee Goreng Mamak", 12,"Main Courses","/meegorengmamak.PNG"));
        Items.add(new Items("Hainanese Curry Chicken Rice", 15,"Main Courses","/haicurryrice.PNG"));
        Items.add(new Items("Asam Pedas Fish", 15,"Main Courses","/Asam Pedas.PNG"));
        Items.add(new Items("Pisang Goreng (6 Pieces)", 10.9,"Desserts","/pisanggoreng.PNG"));
        Items.add(new Items("Cendol", 9.9,"Desserts","/cendol.PNG"));
        Items.add(new Items("Ais Kacang", 9.9,"Desserts","/aiskacang.PNG"));
        Items.add(new Items("ABC Special", 11.9,"Desserts","/ABC Special.PNG"));
        Items.add(new Items("Orea Milkshake", 15.9,"Desserts","/oreamilkshake.PNG"));

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("items.dat"))) {
            oos.writeObject(Items);
            System.out.println("items.dat created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}