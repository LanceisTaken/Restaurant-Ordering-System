package com.example.demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ItemDatabaseTest {
    private ItemDatabase itemDatabase;

    @Before
    public void setUp() {
        itemDatabase = new ItemDatabase("items.dat");
    }

    @After
    public void tearDown() {
        itemDatabase.clearDatabase();
    }

    @Test
    public void testGetItemsByCategory() {
        Items item1 = new Items("item1", 10.0, "Main Courses","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");
        Items item2 = new Items("item2", 5.0, "Appetizers","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");
        Items item3 = new Items("item3", 15.0, "Main Courses","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");
        itemDatabase.addObject(item1);
        itemDatabase.addObject(item2);
        itemDatabase.addObject(item3);
        List<Items> mainCourses = itemDatabase.getItemsByCategory("Main Courses");
        assertEquals(2, mainCourses.size());
        assertTrue(mainCourses.contains(item1));
        assertTrue(mainCourses.contains(item3));
    }

    @Test
    public void testSaveObjectArray() {
        Items item1 = new Items("item1", 10.0, "Main Courses","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");
        Items item2 = new Items("item2", 5.0, "Appetizers","C:\\Users\\Asus\\IdeaProjects\\demo\\src\\main\\resources\\Happy.png");
        ArrayList<Items> itemsArray = new ArrayList<>();
        itemsArray.add(item1);
        itemsArray.add(item2);
        itemDatabase.saveItemsArray(itemsArray);
        assertEquals(2, itemDatabase.getObjectArray().size());
        assertTrue(itemDatabase.getObjectArray().contains(item1));
        assertTrue(itemDatabase.getObjectArray().contains(item2));
    }

    @Test
    public void testAddCategory() {
        itemDatabase.addCategory("New Category");
        assertTrue(itemDatabase.getCategorySet().contains("New Category"));
    }

    @Test
    public void testRemoveCategory() {
        itemDatabase.addCategory("Test Category");
        assertTrue(itemDatabase.getCategorySet().contains("Test Category"));
        itemDatabase.removeCategory("Test Category");
        assertFalse(itemDatabase.getCategorySet().contains("Test Category"));
    }

    @Test
    public void testLoadCategorySet() {
        itemDatabase = new ItemDatabase("items.dat");
        ArrayList<String> categorySet = itemDatabase.getCategorySet();
        assertTrue(categorySet.contains("Main Courses"));
        assertTrue(categorySet.contains("Desserts"));
        assertTrue(categorySet.contains("Appetizers"));
    }
}
