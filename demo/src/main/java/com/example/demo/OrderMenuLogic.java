package com.example.demo;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMenuLogic {
    private ItemDatabase itemDatabase;
    private List<String> categorySet;

    public OrderMenuLogic(ItemDatabase itemDatabase) {
        this.itemDatabase = itemDatabase;
        this.categorySet = itemDatabase.getCategorySet();
    }

    public int getNumberofPages(List<String> categorySet) {
        int length = categorySet.size();
        int quotient = length / 3;
        if (length % 3 != 0) {
            quotient++; // Increment quotient if there is a remainder
        }
        return quotient;
    }

    public List<String> getCategorySet() {
        return categorySet;
    }

    public List<Items> getItemsByCategory(String category) {
        return itemDatabase.getItemsByCategory(category);
    }

    public void addToCart(Items item, List<Items> cart) {
        cart.add(item);
    }

    public void removeFromCart(Items item, List<Items> cart) {
        cart.remove(item);
    }

    public List<Items> getCart(List<Items> cart) {
        return cart;
    }

    public double calculateTotalCost(List<Items> cart) {
        double totalCost = 0;
        for (Items item : cart) {
            double price = item.getPrice();
            totalCost += price;
        }
        // Formatting totalCost to two decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        totalCost = Double.parseDouble(df.format(totalCost));
        return totalCost;
    }

    public String generateReceipt(List<Items> cart) {
        HashMap<Integer, Integer> itemQuantity = new HashMap<>();
        HashMap<Integer, Double> itemPrice = new HashMap<>();
        HashMap<Integer, String> itemName = new HashMap<>();

        for (Items item : cart) {
            int itemId = item.getItemsID();
            int quantity = itemQuantity.getOrDefault(itemId, 0);
            itemQuantity.put(itemId, quantity + 1);
            itemPrice.put(itemId, item.getPrice());
            itemName.put(itemId, item.getName());
        }

        StringBuilder receipt = new StringBuilder();
        double totalCost = calculateTotalCost(cart);
        for (Map.Entry<Integer, Integer> entry : itemQuantity.entrySet()) {
            int itemId = entry.getKey();
            int quantity = entry.getValue();
            double price = itemPrice.get(itemId);
            double itemTotal = price * quantity;
            String itemNameStr = itemName.get(itemId);
            receipt.append(itemNameStr).append(" x").append(quantity)
                    .append(" (RM").append(price).append(" each) : RM").append(itemTotal).append("\n");
        }
        receipt.append("\t\t\tTotal: RM").append(totalCost);

        cart.clear();

        return receipt.toString();
    }
}

