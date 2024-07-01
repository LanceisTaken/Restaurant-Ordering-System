package com.example.demo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Transaction implements Serializable {
    public int transactionID, userID;
    private String report;
    private LocalDateTime transactionDateTime;
    public String dateAndTime;
    private final AtomicLong counter = new AtomicLong(0);
    private final Random random = new Random();

    public Transaction(int userID, String report) {
        this.userID = userID;
        this.report = report;
        this.transactionDateTime = LocalDateTime.now();
        setDateAndTime();
        setTransactionID();
    }

    public int getTransactionID() {
        return transactionID;
    }


    public void setReport(String report) {
        this.report = report;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setTransactionDateTime(LocalDateTime transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }
    public String getTransactionDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
        return transactionDateTime.format(formatter);
    }
    private void setDateAndTime(){
        this.dateAndTime = this.getTransactionDateTime();
    }
    private void setTransactionID(){
        this.transactionID = IDGenerator();
    }
    public String getDateAndTime(){
        return this.dateAndTime;
    }


    public int IDGenerator() {
        long currentTimeMillis = System.currentTimeMillis();
        long randomNumber = random.nextLong();
        if (randomNumber < 0) {
            randomNumber *= -1;
        }
        long combined = currentTimeMillis + randomNumber;
        long id = counter.getAndIncrement();
        // Convert the combined value to an int, truncating any leading digits
        int intTransactionId = (int) (combined % Integer.MAX_VALUE);
        // If the truncated value is negative, add Integer.MAX_VALUE to make it positive
        if (intTransactionId < 0) {
            intTransactionId += Integer.MAX_VALUE;
        }
        // Add the unique sequence number to the truncated combined value
        intTransactionId += id;
        return intTransactionId;
    }

    public String getReport() {
        return report;
    }
}
