package org.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RestaurantController {

    // Dinner items
    @FXML private CheckBox chkPap, chkRice, chkChinese;
    // Dessert items
    @FXML private CheckBox chkCustard, chkChocolate, chkCupcake;
    // Drinks ComboBox
    @FXML private ComboBox<String> cmbDrinks;

    @FXML private TextField txtTotal, txtTendered, txtChange;
    @FXML private Label lblTime;

    private double total = 0;

    @FXML
    public void initialize() {
        // Add listeners to checkboxes
        chkPap.setOnAction(e -> calculateTotal());
        chkRice.setOnAction(e -> calculateTotal());
        chkChinese.setOnAction(e -> calculateTotal());
        chkCustard.setOnAction(e -> calculateTotal());
        chkChocolate.setOnAction(e -> calculateTotal());
        chkCupcake.setOnAction(e -> calculateTotal());

        // Add drinks to ComboBox
        cmbDrinks.getItems().addAll(
                "Water - M10", "Coke - M15", "Sprite - M15", "Juice - M20"
        );

        cmbDrinks.setOnAction(e -> calculateTotal());
    }

    @FXML
    public void calculateTotal() {
        total = 0;

        // Dinner
        if (chkPap.isSelected()) total += 70;
        if (chkRice.isSelected()) total += 85;
        if (chkChinese.isSelected()) total += 120;

        // Dessert
        if (chkCustard.isSelected()) total += 40;
        if (chkChocolate.isSelected()) total += 35;
        if (chkCupcake.isSelected()) total += 25;

        // Drinks
        String drink = cmbDrinks.getValue();
        if (drink != null) {
            if (drink.contains("Water")) total += 10;
            else if (drink.contains("Coke")) total += 15;
            else if (drink.contains("Sprite")) total += 15;
            else if (drink.contains("Juice")) total += 20;
        }

        txtTotal.setText(String.format("M %.2f", total));
    }

    @FXML
    public void calculateChange() {
        try {
            double tendered = Double.parseDouble(txtTendered.getText());

            if (tendered < total) {
                showAlert("Amount tendered is less than total!");
                return;
            }

            double change = tendered - total;
            txtChange.setText(String.format("M %.2f", change));

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

            String time = now.format(formatter);
            lblTime.setText("Transaction Time: " + time);

            saveToFile(time, tendered, change);

        } catch (NumberFormatException e) {
            showAlert("Enter valid numeric amount!");
        }
    }

    private void saveToFile(String time, double tendered, double change) {
        try (FileWriter writer = new FileWriter("RestaurantTransactions.txt", true)) {
            writer.write("========= RESTAURANT RECEIPT =========\n");
            writer.write("Time: " + time + "\n");
            writer.write("Total: M " + total + "\n");
            writer.write("Tendered: M " + tendered + "\n");
            writer.write("Change: M " + change + "\n");
            writer.write("======================================\n\n");
        } catch (IOException e) {
            showAlert("Error saving transaction!");
        }
    }

    @FXML
    public void reset() {
        chkPap.setSelected(false);
        chkRice.setSelected(false);
        chkChinese.setSelected(false);
        chkCustard.setSelected(false);
        chkChocolate.setSelected(false);
        chkCupcake.setSelected(false);
        cmbDrinks.setValue(null);

        txtTotal.clear();
        txtTendered.clear();
        txtChange.clear();
        lblTime.setText("Transaction Time: ");
        total = 0;
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}