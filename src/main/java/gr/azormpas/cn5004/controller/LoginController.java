package gr.azormpas.cn5004.controller;

import gr.azormpas.cn5004.Main;
import gr.azormpas.cn5004.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private CheckBox chkRemember;
    @FXML
    private CheckBox chkDefaultData;
    @FXML
    private Button btnLogin;

    private boolean isRememberChecked = false;
    private boolean isDefaultDataChecked = false;
    protected User loggedUser;

    @FXML
    public void initialize()
    {
        loggedUser = new User();
        if(isRememberChecked)
        {
            chkRemember.setSelected(true);
            fldUsername.setText(loggedUser.getUsername());
            fldPassword.setText(loggedUser.getPassword());
        }
        if (isDefaultDataChecked)
        {
            chkDefaultData.setSelected(true);
        }
    }

    public void toggleDefaultData (ActionEvent actionEvent)
    {
        isDefaultDataChecked = chkDefaultData.isSelected();
    }

    public void toggleRemember (ActionEvent ignoredEvent) throws IOException
    {
        isRememberChecked = chkRemember.isSelected();
    }

    public void attemptLogin(ActionEvent event) throws IOException
    {
        if (Main.data.verifyUser(fldUsername.getText(), fldPassword.getText()))
        {
            loggedUser.setUsername(fldUsername.getText());
            loggedUser.setPassword(fldPassword.getText());
            loggedUser.setLastLogin(String.valueOf(new java.util.Date()));
            Main.loadScene("Home");
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Credentials");
            alert.show();
        }
    }
}
