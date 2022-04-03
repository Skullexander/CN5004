package gr.azormpas.cn5004.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class LoginController
{
    @FXML
    private TextField fldUsername;
    @FXML
    private PasswordField fldPassword;
    @FXML
    private CheckBox chkCredLogin;
    @FXML
    private Button btnLogin;

    @FXML
    public void initialize()
    {
        btnLogin.setText("Click here...");
    }

    public void attemptLogin(ActionEvent event) throws IOException
    {
        //Check credentials with user DB

        //If exists, check entry level

        //Open "Home" scene with given entry level

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Home.fxml")))));
        stage.show();
    }
}
