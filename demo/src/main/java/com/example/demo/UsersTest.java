package com.example.demo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UsersTest {

    private Users users;

    @Before
    public void setUp() {
        users = new Users("John Doe", "1234");
    }

    @Test
    public void testUserIDGenerator() {
        int id1 = users.userIDGenerator();
        int id2 = users.userIDGenerator();
        // Ensure IDs are unique
        assertTrue(id1 != id2);
    }

    @Test
    public void testSetDefaultBalance() {
        assertEquals(users.balance, 100);
    }

    @Test
    public void testSetDefaultAdmin() {
        assertFalse(users.admin);
    }

    @Test
    public void testSetID() {
        users.setID();
        assertTrue(users.userID > 0);
    }
}