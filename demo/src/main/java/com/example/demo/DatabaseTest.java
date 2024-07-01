package com.example.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {
    private Database<String> database;

    @Before
    public void setUp() {
        database = new Database<>("test.dat");
    }
    @After
    public void tearDown(){
        database.clearDatabase();
    }
    @Test
    public void testAddObject() {
        database.addObject("test");
        assertTrue(database.getObjectArray().contains("test"));
    }
    @Test
    public void testRemoveObject() {
        database.addObject("test");
        database.removeObject(0);
        assertFalse(database.getObjectArray().contains("test"));
    }
    @Test
    public void testSaveAndLoad() {
        database.addObject("test");
        database.save();
        database = new Database<>("test.dat");
        assertTrue(database.getObjectArray().contains("test"));
    }
    @Test
    public void testClearDatabase() {
        database.addObject("test");
        database.clearDatabase();
        assertTrue(database.getObjectArray().isEmpty());
    }
}