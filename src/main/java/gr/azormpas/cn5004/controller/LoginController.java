package gr.azormpas.cn5004.controller;

import gr.azormpas.cn5004.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

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

        Main.loadScene("Home");
    }
}
