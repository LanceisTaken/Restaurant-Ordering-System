package com.example.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderMenuLogicTest {

    private OrderMenuLogic orderMenuLogic;
    private ItemDatabase itemDatabase;

    @Before
    public void setUp() {
        itemDatabase = new ItemDatabase("itemsTest.dat");
        orderMenuLogic = new OrderMenuLogic(itemDatabase);
    }
    @After
    public void tearDown(){
        itemDatabase.clearDatabase();
    }

    @Test
    public void testGetNumberOfPages() {
        int numberOfCategories = 15;
        List<String> categorySet = Arrays.asList(new String[numberOfCategories]);
        int expectedNumberOfPages = (numberOfCategories / 3) + (numberOfCategories % 3 > 0 ? 1 : 0);

        assertEquals(orderMenuLogic.getNumberofPages(categorySet), expectedNumberOfPages);
    }


    @Test
    public void testAddToCart() {
        List<Items> cart = new ArrayList<>();
        Items item = new Items("item1",10.0,"Main Courses","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");
        orderMenuLogic.addToCart(item, cart);
        assertEquals(cart.size(), 1);
        assertEquals(cart.get(0), item);
    }

    @Test
    public void testRemoveFromCart() {
        List<Items> cart = new ArrayList<>();
        Items item = new Items("item1",10.0,"Main Courses","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");

        orderMenuLogic.addToCart(item, cart);

        orderMenuLogic.removeFromCart(item, cart);

        assertEquals(cart.size(), 0);
    }

    @Test
    public void testGetCartItems() {
        List<Items> cart = new ArrayList<>();
        Items item = new Items("item1",10.0,"Main Courses","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");

        orderMenuLogic.addToCart(item, cart);

        List<Items> retrievedCartItems = orderMenuLogic.getCart(cart);

        assertEquals(retrievedCartItems.size(), 1);
        assertEquals(retrievedCartItems.get(0), item);
    }

    @Test
    public void testCalculateTotalCost() {
        List<Items> cart = new ArrayList<>();
        Items item1 = new Items("item1",10.0,"Main Courses","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");
        Items item2 = new Items("item2",10.0,"Main Courses","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");

        orderMenuLogic.addToCart(item1, cart);
        orderMenuLogic.addToCart(item2, cart);

        double totalCost = orderMenuLogic.calculateTotalCost(cart);

        DecimalFormat df = new DecimalFormat("#.##");
        double expectedTotalCost = Double.parseDouble(df.format(item1.getPrice() + item2.getPrice()));

        assertEquals(totalCost, expectedTotalCost, 0.01);
    }

    @Test
    public void testGenerateReceipt() {
        List<Items> cart = new ArrayList<>();
        Items item1 = new Items("item1",10.0,"Main Courses","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");
        Items item2 = new Items("item2",15.0,"Main Courses","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");
        orderMenuLogic.addToCart(item1,cart);
        orderMenuLogic.addToCart(item2,cart);
        orderMenuLogic.addToCart(item1,cart);
        String receipt = orderMenuLogic.generateReceipt(cart);
        System.out.println(receipt);
        assertEquals(receipt.contains(item1.getName()), true);
        assertEquals(receipt.contains(String.valueOf(item1.getPrice())), true);
        assertEquals(receipt.contains(String.valueOf(item1.getName())), true);
        assertEquals(receipt.contains(String.valueOf(item2.getPrice())), true);
        assertEquals(receipt.contains(String.valueOf(item2.getName())), true);
        assertEquals(receipt.contains("20"), true);//checks if it total of item 1 is correct, 10 + 10
        assertEquals(receipt.contains("35"), true);//checks if total of cart is correct, 20 + 15
        assertEquals(receipt.contains("Total: RM"), true);
    }
}