package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Items implements Serializable {
    private final AtomicLong counter = new AtomicLong(0);
    private final Random random = new Random();
    public int itemsID;
    String Name,Category;
    double Price;
    private byte[] image;

    public Items(String name, double price, String category, String filePath) {
        this.Name = name;
        this.Price = price;
        this.Category = category;
        this.image = loadImageDataFromFile(filePath);
        this.setID();
    }
    // Getters and setters
    public int getItemsID() {
        return itemsID;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        this.Price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private static byte[] loadImageDataFromFile(String filePath) {
        // Check if the file path is a resource path
        if (filePath.startsWith("/")) {
            return loadImageDataFromResource(filePath);
        } else {
            try {
                Path path = Path.of(filePath);
                return Files.readAllBytes(path);
            } catch (IOException e) {
                // Handle file not found or other IO errors
                e.printStackTrace();
                return null; // Or throw an exception to propagate the error
            }
        }
    }

    private static byte[] loadImageDataFromResource(String resourcePath) {
        try (InputStream inputStream = Items.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IOException("Resource not found: " + resourcePath);
            }
            return inputStream.readAllBytes();
        } catch (IOException e) {
            // Handle resource not found or other IO errors
            e.printStackTrace();
            return null; // Or throw an exception to propagate the error
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, Price,Category,itemsID);
    }

    @Override
    public String toString() {
        return "Items{" +
                "name='" + Name + '\'' +
                ", price=" + Price +
                ", category=" + Category +
                ", id=" + itemsID +
                '}';
    }
    public int itemIDGenerator() {
        long currentTimeMillis = System.currentTimeMillis();
        long randomNumber = random.nextLong();
        if (randomNumber < 0) {
            randomNumber *= -1;
        }
        long combined = currentTimeMillis + randomNumber;
        long id = counter.getAndIncrement();
        // Convert the combined value to an int, truncating any leading digits
        int intItemId = (int) (combined % Integer.MAX_VALUE);
        // If the truncated value is negative, add Integer.MAX_VALUE to make it positive
        if (intItemId < 0) {
            intItemId += Integer.MAX_VALUE;
        }
        // Add the unique sequence number to the truncated combined value
        intItemId += id;
        return intItemId;
    }
    public void setID(){
        itemsID = itemIDGenerator();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Items other = (Items) obj;
        return itemsID == other.getItemsID();
    }
}



