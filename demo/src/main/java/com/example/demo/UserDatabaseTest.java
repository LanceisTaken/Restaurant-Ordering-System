package com.example.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDatabaseTest {
    private UserDatabase userDatabase;

    @Before
    public void setUp() {
        userDatabase = new UserDatabase("users.dat");
    }
    @After
    public void tearDown(){
        userDatabase.clearDatabase();
    }

    @Test
    public void testGetUser() {
        Users user = new Users("test", "test",  false);
        userDatabase.addObject(user);
        Users foundUser = userDatabase.getUser("test", "test", userDatabase.getObjectArray());
        assertEquals(user, foundUser);
    }

    @Test
    public void testSetName() {
        Users user = new Users("test", "test",  false);
        userDatabase.addObject(user);
        userDatabase.setName("newName", user);
        assertEquals("newName", userDatabase.getName(user));
    }

}
