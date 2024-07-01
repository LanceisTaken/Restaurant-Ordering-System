package com.example.demo;


import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class TransactionTest {

    @Test
    public void testTransactionIDGeneration() {
        Transaction transaction1 = new Transaction(1, "Test Report 1");
        Transaction transaction2 = new Transaction(2, "Test Report 2");

        assertEquals(transaction1.getTransactionID(), transaction1.getTransactionID());
        assertEquals(transaction2.getTransactionID(), transaction2.getTransactionID());
        assertEquals(transaction1.getTransactionID() != transaction2.getTransactionID(), true);
    }

    @Test
    public void testSetAndGetUserID() {
        Transaction transaction = new Transaction(1, "Test Report");
        transaction.setUserID(2);

        assertEquals(transaction.getUserID(), 2);
    }

    @Test
    public void testSetAndGetTransactionDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        Transaction transaction = new Transaction(1, "Test Report");
        String dateTime = transaction.getTransactionDateTime();
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime, formatter);

        assertEquals(parsedDateTime.getYear(), now.getYear());
        assertEquals(parsedDateTime.getMonthValue(), now.getMonthValue());
        assertEquals(parsedDateTime.getDayOfMonth(), now.getDayOfMonth());
        assertEquals(parsedDateTime.getHour(), now.getHour());
        assertEquals(parsedDateTime.getMinute(), now.getMinute());
        assertEquals(parsedDateTime.getSecond(), now.getSecond());
    }

    @Test
    public void testSetReport() {
        Transaction transaction = new Transaction(1, "Test Report");
        transaction.setReport("Updated Test Report");

        assertEquals(transaction.getReport(), "Updated Test Report");
    }
}