package com.example.demo;

import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class MainMenuLogicTest {

    private MainMenuLogic mainMenuLogic;
    private LoginMenuController loginMenuControllerMock;
    private MainMenuController mainMenuControllerMock;
    private UserDatabase userDatabaseMock;
    private Users userMock;
    private Stage stageMock;

    @Before
    public void setUp() {
        loginMenuControllerMock = mock(LoginMenuController.class);
        mainMenuControllerMock = mock(MainMenuController.class);
        userDatabaseMock = mock(UserDatabase.class);
        userMock = mock(Users.class);
        stageMock = mock(Stage.class);

        when(loginMenuControllerMock.getLoggedInUser()).thenReturn(userMock);
        when(loginMenuControllerMock.getUserDatabase()).thenReturn(userDatabaseMock);
        when(mainMenuControllerMock.getMainMenuStage()).thenReturn(stageMock);

        mainMenuLogic = new MainMenuLogic(loginMenuControllerMock);
    }

    @Test
    public void testInitializeController() {
        double balance = 100.0;
        when(userDatabaseMock.getBalance(any(Users.class))).thenReturn(balance);

        mainMenuLogic.initializeController(mainMenuControllerMock);

        verify(mainMenuControllerMock).setBalanceLabelText("100.00");
    }

    @Test
    public void testHandleOrdering() {
        assertTrue(mainMenuLogic.handleOrdering(true));
    }

    @Test
    public void testHandleUserTransactions() {
        assertTrue(mainMenuLogic.handleUserTransactions(true));
    }

    @Test
    public void testHandleTopUp() {
        assertTrue(mainMenuLogic.handleTopUp(true));
    }

    @Test
    public void testHandleLogout() {
        mainMenuLogic.handleLogout(loginMenuControllerMock, mainMenuControllerMock);
        verify(mainMenuControllerMock.getMainMenuStage(), times(1)).close();
        verify(loginMenuControllerMock, times(1)).handleLogout();
    }
}
