package com.example.demo;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminMenuController {
    @FXML
    private HBox belowTableView;
    @FXML
    private HBox tableViewHolder;
    @FXML
    private Button switchToItemsButton;
    @FXML
    private Button switchToUsersButton;
    @FXML
    private Button switchToTransactionsButton;
    @FXML
    private Label WelcomeLabel;
    @FXML
    private HBox SetCategoryBox;
    @FXML
    private HBox RemoveCategoryBox;
    Users currentUser;
    ImageView imageView;
    private TableView<Items> itemsTableView;
    private TableView<Users> usersTableView;
    private TableView<Transaction> transactionTableView;
    public ObservableList<Items> itemList;
    private ObservableList<Users> userList;
    private ObservableList<Transaction> transactionList;
    public ItemDatabase itemDatabase;
    private UserDatabase userDatabase;
    private TransactionDatabase transactionDatabase;
    private Button addButton;
    private Button removeButton;
    private TextField textField1;
    private TextField textField2;
    private TextField filePathField;
    private PasswordField passwordField;
    private ComboBox<String> categoryComboBox;
    private ComboBox<Users> userComboBox;
    private Stage stage; // Reference to the stage for file chooser
    private LoginMenuController loginMenuController;

    public void setLoginMenuController(LoginMenuController controller) {
        this.loginMenuController = controller;
    }
    // Method to handle logout
    public void handleLogout() {
        stage.close();
        if (loginMenuController != null) {
            loginMenuController.handleLogout();
        }
    }
    //Initialises The Menu
    public void initializeController() {
        this.userDatabase = loginMenuController.getUserDatabase();
        this.itemDatabase = loginMenuController.getItemDatabase();
        this.transactionDatabase = loginMenuController.getTransactionDatabase();

        itemList = FXCollections.observableArrayList(itemDatabase.getObjectArray());
        userList = FXCollections.observableArrayList(userDatabase.getObjectArray());
        transactionList = FXCollections.observableArrayList(transactionDatabase.getObjectArray());

        //Welcoming Admin
        this.currentUser = loginMenuController.getLoggedInUser();
        String name = userDatabase.getName(currentUser);
        WelcomeLabel.setText("Welcome, "+name);
        switchToItemsButton.setMaxWidth(Double.MAX_VALUE);
        switchToUsersButton.setMaxWidth(Double.MAX_VALUE);
        switchToTransactionsButton.setMaxWidth(Double.MAX_VALUE);

        // Initialize items table view
        itemsTableView = new TableView<>();
        itemsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Set column resize policy
        itemsTableView.setEditable(true); // Enable editing

        // Define columns for items table view
        //Items ID
        TableColumn<Items, Integer> itemsIdColumn = new TableColumn<>("ID");
        itemsIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemsID"));
        //Items Name
        TableColumn<Items, String> itemsNameColumn = new TableColumn<>("Name");
        itemsNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemsNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        itemsNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Items, String> event) -> {
            // On editing commit, update item name
            Items item = event.getRowValue();
            String newValue = event.getNewValue();
            if (newValue == null || newValue.isEmpty()) {
                showAlert(AlertType.ERROR, "Error", "Missing Information", "Please fill in the Name field.");
                return;
            }
            itemDatabase.setName(newValue,item);
        });
        //Items Category
        TableColumn<Items, String> itemsCategoryColumn = new TableColumn<>("Category");
        itemsCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        itemsCategoryColumn.setCellFactory(ComboBoxTableCell.forTableColumn("Main Course", "Dessert", "Appetizers"));
        itemsCategoryColumn.setOnEditCommit((TableColumn.CellEditEvent<Items, String> event) -> {
            // On editing commit, update item category
            Items item = event.getRowValue();
            String newValue = event.getNewValue();
            String oldValue = event.getOldValue();
            if (!isValidCategory(newValue)) {
                showAlert(AlertType.ERROR, "Error", "Invalid Category", "Category must be either Main Course, Dessert, or Appetizers.");
                itemDatabase.setCategory(oldValue,item);
                return;
            }
            itemDatabase.setCategory(newValue,item);
        });
        //Items Price
        TableColumn<Items, Double> itemsPriceColumn = new TableColumn<>("Price (RM)");
        itemsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        // Set the custom DoubleStringConverter for the itemsPriceColumn
        itemsPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
        itemsPriceColumn.setOnEditCommit((TableColumn.CellEditEvent<Items, Double> event) -> {
            // On editing commit, update item price
            Items item = event.getRowValue();
            Double newValue = event.getNewValue();
            Double oldValue = event.getOldValue();
            if (newValue == null || newValue <= 0) {
                showAlert(AlertType.ERROR, "Error", "Invalid Information", "Please enter a valid Price value.");
                itemDatabase.setPrice(oldValue,item);
                return;
            }
            // Check the number of decimal places
            if ((newValue.toString().indexOf('.') != -1 ? newValue.toString().substring(newValue.toString().indexOf('.') + 1).length() : 0) > 2) {
                showAlert(AlertType.ERROR, "Error", "Invalid Information", "Price should have at most two decimal places.");
                itemDatabase.setPrice(oldValue,item);
                return;
            }
            itemDatabase.setPrice(newValue,item);
        });
        //Items Image
        TableColumn<Items, byte[]> itemsImageColumn = new TableColumn<>("Image");
        itemsImageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        itemsImageColumn.setCellFactory(param -> new TableCell<Items, byte[]>() {
            private final ImageView imageView = new ImageView();
            {
                setGraphic(imageView);
                setEditable(true); // Enable editing for the cell
                // Handle mouse click event
                setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) { // Detect double click
                        Items item = getTableView().getItems().get(getIndex());
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Select Image File");
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
                        );
                        File selectedFile = fileChooser.showOpenDialog(stage);
                        if (selectedFile != null) {
                            try {
                                byte[] newImageBytes = Files.readAllBytes(selectedFile.toPath());
                                itemDatabase.setImage(newImageBytes, item); // Update image in the database
                                itemsTableView.refresh(); // Refresh table view
                            } catch (IOException e) {
                                e.printStackTrace(); // Handle file read error
                            }
                        }
                    }
                });
            }
            @Override
            protected void updateItem(byte[] imageBytes, boolean empty) {
                super.updateItem(imageBytes, empty);
                if (empty || imageBytes == null) {
                    setGraphic(null);
                } else {
                    Image image = new Image(new ByteArrayInputStream(imageBytes));
                    imageView.setImage(image);
                    imageView.setFitWidth(100); // Adjust the width as needed
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
            }
        });
        itemsImageColumn.setEditable(true); // Enable editing for the image column
        // Add columns to items table view
        itemsTableView.getColumns().addAll(itemsIdColumn, itemsNameColumn, itemsCategoryColumn, itemsPriceColumn,itemsImageColumn);
        itemsTableView.getItems().addListener((InvalidationListener) observable -> {
            // Save items to database when list changes
            Items[] array = itemsTableView.getItems().toArray(new Items[0]);
            ArrayList<Items> arrayList = new ArrayList<>(Arrays.asList(array));
            itemDatabase.saveItemsArray(arrayList);
        });

        // Initialize users table view
        usersTableView = new TableView<>();
        usersTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Set column resize policy
        usersTableView.setEditable(true); // Enable editing
        // Define columns for users table view
        // User ID
        TableColumn<Users, Integer> usersIdColumn = new TableColumn<>("ID");
        usersIdColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        //User Name
        TableColumn<Users, String> usersNameColumn = new TableColumn<>("Name");
        usersNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usersNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        usersNameColumn.setOnEditCommit((TableColumn.CellEditEvent<Users, String> event) -> {
            // On editing commit, update user name
            Users user = event.getRowValue();
            String newValue = event.getNewValue();
            String oldValue = event.getOldValue();
            if (newValue == null || newValue.isEmpty()) {
                showAlert(AlertType.ERROR, "Error", "Missing Information", "Please fill in the Name field.");
                userDatabase.setName(oldValue,user);
                return;
            }
            userDatabase.setName(newValue,user);
        });
        //User Password
        TableColumn<Users, String> usersPasswordColumn = new TableColumn<>("Password");
        usersPasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        usersPasswordColumn.setOnEditCommit((TableColumn.CellEditEvent<Users, String> event) -> {
            // On editing commit, update user password
            Users user = event.getRowValue();
            String newValue = event.getNewValue();
            String oldValue = event.getOldValue();
            if (newValue == null || newValue.isEmpty()) {
                showAlert(AlertType.ERROR, "Error", "Missing Information", "Please fill in the Password field.");
                userDatabase.setPassword(oldValue,user);
                return;
            }
            userDatabase.setPassword(newValue,user);
        });
        //User Admin Status
        TableColumn<Users, Boolean> usersIsAdminColumn = new TableColumn<>("Is Admin");
        usersIsAdminColumn.setCellValueFactory(data -> new SimpleBooleanProperty(data.getValue().isAdmin()));
        usersIsAdminColumn.setCellFactory(CheckBoxTableCell.forTableColumn(usersIsAdminColumn));
        usersIsAdminColumn.setOnEditCommit((TableColumn.CellEditEvent<Users, Boolean> event) -> {
            // On editing commit, update user admin status
            Users user = event.getRowValue();
            Boolean newValue = event.getNewValue();
            userDatabase.setAdmin(newValue,user);
        });

        TableColumn<Users, Double> usersBalanceColumn = new TableColumn<>("Balance");
        usersBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));
        usersBalanceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new CustomDoubleStringConverter()));
        usersBalanceColumn.setOnEditCommit((TableColumn.CellEditEvent<Users, Double> event) -> {
            // On editing commit, update user balance
            Users user = event.getRowValue();
            Double newValue = event.getNewValue();
            Double oldValue = event.getOldValue();
            if (newValue == null || newValue <= 0) {
                showAlert(AlertType.ERROR, "Error", "Invalid Information", "Please enter a valid Balance value.");
                userDatabase.setBalance(oldValue,user);
                return;
            }
            // Check the number of decimal places
            String newValueStr = String.format("%.2f", newValue); // Format to two decimal places
            if (!newValue.toString().equals(newValueStr)) {
                showAlert(AlertType.ERROR, "Error", "Invalid Information", "Balance should have at most two decimal places.");
                userDatabase.setBalance(oldValue,user);
                return;
            }
            userDatabase.setBalance(newValue,user);
        });
        // Add columns to users table view
        usersTableView.getColumns().addAll(usersIdColumn, usersNameColumn, usersPasswordColumn, usersBalanceColumn, usersIsAdminColumn);

        // Initialize transaction table view
        transactionTableView = new TableView<>();
        transactionTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Set column resize policy
        transactionTableView.setEditable(true);

        // Define columns for transaction table view
        //Transaction ID
        TableColumn<Transaction, Integer> transactionIdColumn = new TableColumn<>("TransactionID");
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        //User ID
        TableColumn<Transaction, Integer> userIdColumn = new TableColumn<>("UserID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        // Create a map of user IDs to user names
        Map<Integer, String> userIdToNameMap = userList.stream()
                .collect(Collectors.toMap(Users::getUserID, Users::getName));

        // Create a list of strings containing both ID and name
        ObservableList<String> userIdWithNameList = userList.stream()
                .map(user -> user.getUserID() + " - " + user.getName())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        // Create a ComboBoxTableCell for UserID column
        userIdColumn.setCellFactory(column -> {
            return new TableCell<>() {
                private final ComboBox<String> comboBox = new ComboBox<>(userIdWithNameList);

                {
                    comboBox.setEditable(false);
                    comboBox.setMaxWidth(Double.MAX_VALUE);
                    comboBox.setConverter(new StringConverter<>() {
                        @Override
                        public String toString(String item) {
                            return item;
                        }

                        @Override
                        public String fromString(String string) {
                            return string;
                        }
                    });
                    comboBox.setOnAction(event -> {
                        String selection = comboBox.getSelectionModel().getSelectedItem();
                        if (selection != null) {
                            int newUserId = Integer.parseInt(selection.split(" - ")[0]);
                            getTableView().edit(getIndex(), getTableColumn());
                            getTableView().getItems().get(getIndex()).setUserID(newUserId);
                            commitEdit(newUserId);
                        }
                    });
                    // Show ComboBox only when cell is clicked
                    setGraphic(null);
                    setOnMouseClicked(event -> {
                        if (!isEmpty()) {
                            comboBox.show();
                        }
                    });
                }

                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        comboBox.getSelectionModel().select(item + " - " + userIdToNameMap.get(item));
                        setGraphic(comboBox);
                    }
                }
            };
        });

        // Add an event listener to the UserID column
        userIdColumn.setOnEditCommit(event -> {
            Transaction transaction = event.getRowValue();
            int newUserId = event.getNewValue();
            transactionDatabase.setUserID(newUserId, transaction);
        });

        TableColumn<Transaction, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateAndTime"));

        // Add an event listener to the Date column
        dateColumn.setOnEditCommit(event -> {
            Transaction transaction = event.getRowValue();
            String newDateString = event.getNewValue();
            String previousDateString = event.getOldValue();

            if (newDateString != null && !newDateString.isEmpty() && !newDateString.matches(".*\\d+.*")) {
                // Check if the format matches "yy-MM-dd HH-mm-ss"
                if (newDateString.matches("\\d{2}-\\d{2}-\\d{2} \\d{2}-\\d{2}-\\d{2}")) {
                    // Date format is correct, update transaction date time
                    try {
                        LocalDateTime newDateTime = LocalDateTime.parse(newDateString, DateTimeFormatter.ofPattern("yy-MM-dd HH-mm-ss"));
                        transactionDatabase.setTransactionDateTime(newDateTime, transaction);
                    } catch (DateTimeParseException e) {
                        // Revert to the previous value
                        transactionDatabase.setTransactionDateTime(LocalDateTime.parse(previousDateString, DateTimeFormatter.ofPattern("yy-MM-dd HH-mm-ss")), transaction);
                        // Display error message
                        System.out.println("Error: Unable to parse the new date.");
                    }
                } else {
                    // Revert to the previous value
                    transactionDatabase.setTransactionDateTime(LocalDateTime.parse(previousDateString, DateTimeFormatter.ofPattern("yy-MM-dd HH-mm-ss")), transaction);
                    // Display error message
                    System.out.println("Error: Date format must be yy-MM-dd HH-mm-ss.");
                }
            } else {
                // Revert to the previous value
                transactionDatabase.setTransactionDateTime(LocalDateTime.parse(previousDateString, DateTimeFormatter.ofPattern("yy-MM-dd HH-mm-ss")), transaction);
                // Display error message
                System.out.println("Error: Date must not be empty and cannot contain numbers.");
            }
        });

        TableColumn<Transaction, String> reportColumn = new TableColumn<>("Report");
        reportColumn.setCellValueFactory(new PropertyValueFactory<>("report"));

        // Add editing support for Report column
        reportColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        // Add an event listener to the Report column
        reportColumn.setOnEditCommit(event -> {
            Transaction transaction = event.getRowValue();
            String newReport = event.getNewValue();
            transactionDatabase.setReport(newReport,transaction);
        });

        // Add columns to transaction table view
        transactionTableView.getColumns().addAll(transactionIdColumn,userIdColumn, dateColumn, reportColumn);


        // Set HBox.hgrow property for the TableView
        HBox.setHgrow(usersTableView, Priority.ALWAYS);
        HBox.setHgrow(itemsTableView, Priority.ALWAYS);
        HBox.setHgrow(transactionTableView, Priority.ALWAYS);


        itemsTableView.setItems(itemList);
        usersTableView.setItems(userList);
        transactionTableView.setItems(transactionList);

        // Add items table view to holder
        tableViewHolder.getChildren().add(itemsTableView);

        createItemsFields(); // Create additional UI elements

    }

    @FXML
    private void switchToItemsTableView() {
        tableViewHolder.getChildren().clear(); // Clear current content
        tableViewHolder.getChildren().add(itemsTableView); // Add items table view
        belowTableView.getChildren().clear(); // Clear buttons and text fields
        SetCategoryBox.getChildren().clear();
        RemoveCategoryBox.getChildren().clear();
        createItemsFields();
    }

    @FXML
    private void switchToUsersTableView() {
        tableViewHolder.getChildren().clear(); // Clear current content
        tableViewHolder.getChildren().add(usersTableView); // Add users table view
        belowTableView.getChildren().clear(); // Clear buttons and text fields
        SetCategoryBox.getChildren().clear();
        RemoveCategoryBox.getChildren().clear();
        createUsersFields();
    }
    @FXML
    private void switchToTransactionsTableView() {
        tableViewHolder.getChildren().clear(); // Clear current content
        tableViewHolder.getChildren().add(transactionTableView); // Add transactions table view
        belowTableView.getChildren().clear(); // Clear buttons and text fields
        SetCategoryBox.getChildren().clear();
        RemoveCategoryBox.getChildren().clear();
        createTransactionsFields();
    }

    private void createItemsFields() {
        //Input Fields
        textField1 = new TextField();
        textField1.setPromptText("Name");
        textField2 = new TextField();
        textField2.setPromptText("Price");
        TextField categoryField = new TextField();
        categoryField.setPromptText("Enter name of new category");
        addButton = new Button("Add");
        removeButton = new Button("Remove");
        Button setNewCategory = new Button("Add Category");
        Button RemoveNewCategory = new Button("Remove Category");

        //Event Handling
        addButton.setOnAction(event -> addItem());
        removeButton.setOnAction(event -> removeItem());
        setNewCategory.setOnAction(event -> addCategory(categoryField));

        // Create ComboBox for category
        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll(itemDatabase.getCategorySet());
        categoryComboBox.setPromptText("Category");
        System.out.println(itemDatabase.getCategorySet());
        RemoveNewCategory.setOnAction(event -> removeCategory(categoryComboBox));
        filePathField = new TextField();
        filePathField.setVisible(false);

        // Create a preview box for the selected image
        imageView = new ImageView();
        imageView.setFitWidth(300); // Set the width of the preview box
        imageView.setFitHeight(135); // Set the height of the preview box
        imageView.setPreserveRatio(true); // Maintain aspect ratio

        // Button to open file chooser
        Button selectImageButton = new Button("Select Image");
        selectImageButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Image File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                String imagePath = selectedFile.getAbsolutePath();
                filePathField.setText(imagePath);
                imageView.setImage(new Image("file:" + imagePath));
            }
        });

        // Create a StackPane to contain imageView and label
        StackPane imageStackPane = new StackPane();
        imageStackPane.setPrefWidth(200); // Set the width of the preview box
        imageStackPane.setPrefHeight(135); // Set the height of the preview box
        imageStackPane.getChildren().addAll(imageView, createNoImageLabel(imageView));
        imageStackPane.setAlignment(Pos.CENTER);

        // Set the alignment of the imageView within the StackPane to center
        StackPane.setAlignment(imageView, Pos.CENTER);

        // Add border to StackPane
        imageStackPane.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

        // Create a VBox to hold imageView, selectImageButton, and filePathField
        VBox imageBox = new VBox();
        imageBox.getChildren().addAll(imageStackPane, selectImageButton, filePathField);

        // Add buttons and text fields to the layout
        belowTableView.getChildren().addAll(addButton, removeButton, textField1, textField2, categoryComboBox, imageBox);
        SetCategoryBox.getChildren().addAll(categoryField,setNewCategory);
        //RemoveCategoryBox.getChildren().addAll(categoryComboBox,RemoveNewCategory);
    }

    public void addCategory(TextField categoryField){
        String category = categoryField.getText();
        // Check if any of the fields are empty
        if (category.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
            return;
        }
        itemDatabase.addCategory(category);
        showAlert(AlertType.INFORMATION,"Success","Successfully Added","Your new Category has been added successfully");
        tableViewHolder.getChildren().clear(); // Clear current content
        tableViewHolder.getChildren().add(itemsTableView); // Add items table view
        belowTableView.getChildren().clear(); // Clear buttons and text fields
        SetCategoryBox.getChildren().clear();
        RemoveCategoryBox.getChildren().clear();
        initializeController();
    }
    public void removeCategory(ComboBox<String> categoryComboBox){
        String category = categoryComboBox.getValue();
        // Check if any of the fields are empty
        if (category == null || category.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Missing Information", "Please select a category to remove.");
            return;
        }

        // Check if any item is assigned the category being removed
        for (Items item : itemDatabase.getObjectArray()) {
            if (item.getCategory().equals(category)) {
                showAlert(AlertType.ERROR, "Error", "Cannot Remove Category",
                        "Category cannot be removed as it is assigned to an item in the database.");
                return;
            }
        }
        // No items found with the category being removed, proceed with removal
        itemDatabase.removeCategory(category);
        showAlert(AlertType.INFORMATION,"Success","Successfully Removed","Your Category has been removed successfully");
        tableViewHolder.getChildren().clear(); // Clear current content
        tableViewHolder.getChildren().add(itemsTableView); // Add items table view
        belowTableView.getChildren().clear(); // Clear buttons and text fields
        SetCategoryBox.getChildren().clear();
        RemoveCategoryBox.getChildren().clear();
        initializeController();
    }


    public void addItem() {
        AddItemWindow addItemPopup = new AddItemWindow(this);
        Stage stagePopup = new Stage();
        try {
            addItemPopup.start(stagePopup);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void removeItem() {
        Items selectedItem = itemsTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            itemDatabase.removeObject(itemsTableView.getSelectionModel().getSelectedIndex());
            // Update the TableView
            itemList.remove(selectedItem);
            //Alert
            showAlert(AlertType.INFORMATION,"Success","Successfully Removed","Your Item has been removed successfully");
        }
    }


    // Helper method to clear the text fields
    private void clearItemsTextField() {
        textField1.clear();
        textField2.clear();
        filePathField.clear();
        imageView.setImage(null);
        categoryComboBox.getSelectionModel().clearSelection();
        categoryComboBox.setPromptText("Category");
    }
    private void createUsersFields() {
        //Input Fields
        addButton = new Button("Add");
        removeButton = new Button("Remove");
        textField1 = new TextField();
        textField1.setPromptText("Name");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        //Event Handling
        addButton.setOnAction(event -> addUser());
        removeButton.setOnAction(event -> removeUser());

        // Add buttons and text fields to the layout
        belowTableView.getChildren().addAll(addButton, removeButton, textField1, passwordField);
    }

    public void addUser() {
        // Get the input from the text fields
        String name = textField1.getText();
        String password = textField2.getText();

        // Create a new user with the input
        Users newUser = new Users(name, password);

        // Add the new user to the database
        userDatabase.addObject(newUser);
        //Updates the TableView
        userList.add(newUser);

        // Clear the text fields
        clearUserTextField();

        //Alert
        showAlert(AlertType.INFORMATION,"Success","Successfully Added","User has been added successfully");
    }

    public void removeUser() {
        Users selectedUser = usersTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userDatabase.removeObject(usersTableView.getSelectionModel().getSelectedIndex());
            // Update the TableView
            userList.remove(selectedUser);
            //Alert
            showAlert(AlertType.INFORMATION,"Success","Successfully Added","User has been removed successfully");
        }
    }

    // Helper method to clear User text fields
    private void clearUserTextField() {
        textField1.clear();
        textField2.clear();
    }
    public void showAlert(AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    // Validation for category
    private boolean isValidCategory(String category) {
        return category != null && (category.equalsIgnoreCase("Main Course") || category.equalsIgnoreCase("Dessert") || category.equalsIgnoreCase("Appetizers"));
    }
    // Method to create label when no image is selected
    private Label createNoImageLabel(ImageView imageView) {
        Label noImageLabel = new Label("No Image Selected");
        noImageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");
        noImageLabel.visibleProperty().bind(imageView.imageProperty().isNull());
        return noImageLabel;
    }
    private void createTransactionsFields() {
        addButton = new Button("Add");
        removeButton = new Button("Remove");
        addButton.setOnAction(event -> addTransaction());
        removeButton.setOnAction(event -> removeTransaction());

        userComboBox = new ComboBox<>(userList);
        userComboBox.setPromptText("Select a user...");

        textField1 = new TextField();
        textField1.setPromptText("Report");

        // Add buttons and text fields to the layout
        belowTableView.getChildren().addAll(addButton, removeButton, userComboBox,textField1);
    }

    public void addTransaction() {
        // Get the input from the text fields
        int userId = userComboBox.getValue().getUserID();
        String report = textField1.getText();

        // Check if any of the fields are empty
        if (report.isEmpty()) {
            showAlert(AlertType.ERROR, "Error", "Missing Information", "Please fill in all fields.");
            return;
        }
        // Create a new transaction
        Transaction newTransaction = new Transaction(userId,report);

        // Add the new transaction to the database
        transactionDatabase.addObject(newTransaction);
        //Updates the TableView
        transactionList.add(newTransaction);
        // Clear the text fields
        clearTransactionTextField();

        //Alert
        showAlert(AlertType.INFORMATION,"Success","Successfully Added","Transaction has been added successfully");
    }

    public void removeTransaction() {
        Transaction selectedTransaction = transactionTableView.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            transactionDatabase.removeObject(usersTableView.getSelectionModel().getSelectedIndex());
            // Update the TableView
            transactionList.remove(selectedTransaction);
            //Alert
            showAlert(AlertType.INFORMATION,"Success","Successfully Added","Transaction has been removed successfully");
        }
    }
    // Helper method to clear Transaction text fields
    private void clearTransactionTextField() {
        textField1.clear();
        textField2.clear();
    }
    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;
    }
}
class CustomDoubleStringConverter extends DoubleStringConverter {
    @Override
    public String toString(Double value) {
        if (value == null) {
            return "";
        } else {
            // Format the value to always display two decimal places
            return String.format("%.2f", value);
        }
    }
}
