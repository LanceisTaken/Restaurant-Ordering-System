package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class OrderMenuController {
    @FXML
    private VBox ReportBox;
    @FXML
    private Label totalPrice;
    @FXML
    private ListView<Items> orderSummaryListView;
    @FXML
    private HBox ButtonsAboveScrollPane;
    @FXML
    private VBox FoodSelection;
    private OrderMenuLogic orderMenuLogic;
    private LoginMenuController loginMenuController;
    private UserDatabase userDatabase;
    private ItemDatabase itemDatabase;
    private TransactionDatabase transactionDatabase;
    private Users currentUser;
    private Stage orderMenuStage;
    private List<Items> cart;
    private int numberOfPages;
    private List<String> categories;
    public OrderMenuController() {
    }
    public void setLoginMenuController(LoginMenuController controller) {
        this.loginMenuController = controller;
        this.itemDatabase = loginMenuController.getItemDatabase();
        this.orderMenuLogic = new OrderMenuLogic(itemDatabase);
    }
    public void initializeController() {
        this.userDatabase = loginMenuController.getUserDatabase();
        this.itemDatabase = loginMenuController.getItemDatabase();
        this.transactionDatabase = loginMenuController.getTransactionDatabase();
        this.currentUser = loginMenuController.getLoggedInUser();
        this.cart = new ArrayList<>();
        this.categories = orderMenuLogic.getCategorySet();
        this.numberOfPages = orderMenuLogic.getNumberofPages(categories);
        orderMenuLogic = new OrderMenuLogic(itemDatabase);
        System.out.println(categories);
        createPageButtons();
        orderSummaryListView.setCellFactory(createCellFactory());
    }
    private void createPageButtons() {
        for (int i = 1; i <= numberOfPages; i++) { // Start from 1 instead of 0
            HBox buttonsBox = new HBox();
            buttonsBox.setSpacing(10); // Set spacing between buttons

            int startIdx = (i - 1) * 3; // Adjust start index calculation
            int endIdx = Math.min(startIdx + 3, categories.size());
            List<String> categoriesPerPage = categories.subList(startIdx, endIdx);

            Button button = new Button(String.valueOf(i));
            button.setOnAction(event -> handlePageButtonAction(categoriesPerPage));
            buttonsBox.getChildren().add(button);

            ButtonsAboveScrollPane.getChildren().add(buttonsBox);

            // Trigger the button press for the first button during initialization
            if (i == 1) { // Adjust condition to trigger for the first button
                button.fire();
            }
        }
    }
    private void handlePageButtonAction(List<String> categoriesPerPage) {
        VBox selectedCategoryVBox = new VBox(); // VBox to hold ScrollPanes for selected categories
        selectedCategoryVBox.setAlignment(Pos.CENTER);
        for (String category : categoriesPerPage) {
            ScrollPane scrollPane = new ScrollPane();
            Label label = new Label(category);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);

            HBox categoryBox = new HBox(); // HBox to hold items for each category
            populateItems(category, categoryBox);

            scrollPane.setContent(categoryBox);

            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            selectedCategoryVBox.getChildren().addAll(label,scrollPane);
        }
        // Clear previous ScrollPanes
        FoodSelection.getChildren().clear();
        // Set the vertical grow priority to ALWAYS for the selectedCategoryVBox
        VBox.setVgrow(selectedCategoryVBox, Priority.ALWAYS);
        //Adds the ScrollPanes into the Box
        FoodSelection.getChildren().add(selectedCategoryVBox);
    }
    //This method fills the Menu with items from database
    private void populateItems(String category, HBox container) {
        //System.out.println("Populating items for category: " + category);
        List<Items> items = orderMenuLogic.getItemsByCategory(category);
        //System.out.println("Items list size: " + items.size());
        container.getChildren().clear();
        for (Items item : items) {
            Image image = new Image(new ByteArrayInputStream(item.getImage()));
            ImageView imageView = new ImageView(image);
            resizeImageView(imageView, 300, 135);
            Button button = new Button("", imageView);
            Label nameLabel = new Label(item.getName());
            Label priceLabel = new Label("Price: " + item.getPrice());
            button.setOnAction(event -> handleOrderAction(item,cart));
            VBox itemBox = new VBox(button, nameLabel, priceLabel);
            itemBox.setAlignment(Pos.CENTER);
            itemBox.setSpacing(10);
            container.getChildren().add(itemBox);
            System.out.println("Item name: " + item.getName() + ", Price: " + item.getPrice());
        }
    }
    @FXML
    private void handleOrderAction(Items item,List<Items>cart) {
        orderMenuLogic.addToCart(item,cart);
        updateOrderSummaryListView();
        updateTotalPrice();
    }
    @FXML
    private void handleRemoveButtonAction() {
        Items selectedItem = orderSummaryListView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            orderMenuLogic.removeFromCart(selectedItem,cart);
            updateOrderSummaryListView();
            updateTotalPrice();
        }
    }
    private void updateOrderSummaryListView() {
        List<Items> orderSummaryList = orderMenuLogic.getCart(cart);
        ObservableList<Items> observableList = FXCollections.observableArrayList(orderSummaryList);
        orderSummaryListView.setItems(observableList);
    }
    private void updateTotalPrice() {
        double total = orderMenuLogic.calculateTotalCost(cart);
        totalPrice.setText("Total: RM" + total);
    }
    private Callback<ListView<Items>, ListCell<Items>> createCellFactory() {
        return new Callback<ListView<Items>, ListCell<Items>>() {
            @Override
            public ListCell<Items> call(ListView<Items> listView) {
                return new ListCell<Items>() {
                    @Override
                    protected void updateItem(Items item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getName() + " - Price: RM" + item.getPrice());
                        }
                    }
                };
            }
        };
    }
    public void handleCheckout(ActionEvent event) {
        double totalCost = orderMenuLogic.calculateTotalCost(cart);
        if (userDatabase.getBalance(currentUser) < totalCost) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Insufficient Balance");
            alert.setContentText("You don't have enough balance to complete this transaction.");
            alert.showAndWait();
            return;
        }
        String report = orderMenuLogic.generateReceipt(cart);
        Text newText = new Text(report);
        Transaction transaction = new Transaction(currentUser.getUserID(), report);
        transactionDatabase.addObject(transaction);
        userDatabase.deductBalance(totalCost, currentUser);

        ReportBox.getChildren().clear();
        ReportBox.getChildren().add(newText);

        orderSummaryListView.getItems().clear();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Checkout Successful!");
        alert.setHeaderText(null);
        alert.setContentText("Checkout Successful!");
        alert.showAndWait();
    }

    public void resizeImageView(ImageView imageView, double targetWidth, double targetHeight) {
        imageView.setFitWidth(targetWidth);
        imageView.setFitHeight(targetHeight);
        imageView.setPreserveRatio(true); // Maintain aspect ratio
    }

    public void handleGoBack(ActionEvent event) {
        if (orderMenuStage != null) {
            orderMenuStage.close();
        }
        MainMenu mainMenu = new MainMenu(loginMenuController);
        try {
            mainMenu.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOrderMenuStage(Stage primaryStage) {
        this.orderMenuStage = primaryStage;
    }
}
