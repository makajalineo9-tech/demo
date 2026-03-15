package org.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class LoginController {

    @FXML
    public TextField txtUsername;

    @FXML
    public PasswordField txtPassword;

    @FXML
    public Label lblMessage;

    @FXML
    public void login(ActionEvent event) {

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if(username.equals("Teddy") && password.equals("1234")){

            lblMessage.setText("Login Successful");

            try {

                Parent root = FXMLLoader.load(getClass().getResource("restaurant.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            lblMessage.setText("Invalid Login");

        }
    }

    @FXML
    public void clear() {
        txtUsername.clear();
        txtPassword.clear();
        lblMessage.setText("");
    }
}