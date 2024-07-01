package com.example.demo;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Users implements Serializable {
    String name,password;
    int userID;
    double balance;
    boolean admin;
    private final AtomicLong counter = new AtomicLong(0);
    private final Random random = new Random();

    public Users(String name, String password) {
        this.name = name;
        this.password = password;
        this.setDefaultBalance();
        this.setDefaultAdmin();
        this.setID();
    }
    public Users(String name, String password,Boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.admin = isAdmin;
        this.setDefaultBalance();
        this.setID();
    }

    public void setDefaultBalance() {
        this.balance = 100.00;
    }

    public void setDefaultAdmin() {
        this.admin = false;
    }
    public int getUserID() {
        return userID;
    }
    public void setID(){
        userID = userIDGenerator();
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void deductBalance(double balance) {this.balance-=balance;}
    public void addBalance(double balance) {this.balance+=balance;}

    public int userIDGenerator() {
        long currentTimeMillis = System.currentTimeMillis();
        long randomNumber = random.nextLong();
        if (randomNumber < 0) {
            randomNumber *= -1;
        }
        long combined = currentTimeMillis + randomNumber;
        long id = counter.getAndIncrement();
        // Convert the combined value to an int, truncating any leading digits
        int intUserId = (int) (combined % Integer.MAX_VALUE);
        // If the truncated value is negative, add Integer.MAX_VALUE to make it positive
        if (intUserId < 0) {
            intUserId += Integer.MAX_VALUE;
        }
        // Add the unique sequence number to the truncated combined value
        intUserId += id;
        return intUserId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return password.equals(users.password)&&
                Objects.equals(name, users.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }

    // Override toString() to return only user ID and name
    @Override
    public String toString() {
        return name + " (ID: " + userID + ")";
    }


}
