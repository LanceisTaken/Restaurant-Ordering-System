package com.example.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class LoginMenuLogicTest {

    private UserDatabase userDatabase;
    private LoginMenuLogic loginMenuLogic;

    @Before
    public void setUp() {
        // Create a mock UserDatabase for testing
        userDatabase = new UserDatabase("test.dat");
        userDatabase.addObject(new Users("testUser", "password"));
        loginMenuLogic = new LoginMenuLogic(userDatabase);
    }

    @After
    public void tearDown() {
        // Clean up the test database after each test
        userDatabase.clearDatabase();
    }

    @Test
    public void testLoginValidUser() {
        assertTrue(loginMenuLogic.login("testUser", "password", userDatabase.getObjectArray()));
    }

    @Test
    public void testLoginInvalidUser() {
        assertFalse(loginMenuLogic.login("invalidUser", "password", userDatabase.getObjectArray()));
    }

    @Test
    public void testRegisterNewUser() {
        assertTrue(loginMenuLogic.register("newUser", "password", userDatabase.getObjectArray()));
    }

    @Test
    public void testRegisterExistingUser() {
        assertFalse(loginMenuLogic.register("testUser", "password", userDatabase.getObjectArray()));
    }
}
