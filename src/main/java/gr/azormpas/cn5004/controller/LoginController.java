package gr.azormpas.cn5004.controller;

import gr.azormpas.cn5004.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    public void initialize()
    {
        try
        {
            Main.data.loadData();
            if(Main.data.getSettings().isRememberUser())
            {
                chkRemember.setSelected(true);
                fldUsername.setText(Main.data.getSettings().getLoadedUser().getUsername());
                Main.data.getSettings().getLoadedUser().setPassword(null);
                Main.data.saveSettings();
            }
            if (Main.data.getSettings().isUseDefaultData())
            {
                chkDefaultData.setSelected(true);
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void toggleDefaultData (ActionEvent ignoredEvent)
        throws IOException
    {
        Main.data.getSettings().setUseDefaultData(chkDefaultData.isSelected());
        Main.data.saveSettings();
    }

    public void toggleRemember (ActionEvent ignoredEvent)
        throws IOException
    {
        Main.data.getSettings().setRememberUser(chkRemember.isSelected());
        Main.data.saveSettings();
    }

    public void attemptLogin(ActionEvent ignoredEvent)
        throws IOException
    {
        if (Main.data.verifyUser(fldUsername.getText(), fldPassword.getText()))
        {
            rememberUser();
            Main.loadScene("Home");
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid Credentials");
            alert.show();
        }
    }

    private void rememberUser()
    {
        Main.data.getSettings().getLoadedUser().setUsername(fldUsername.getText());
        Main.data.getSettings().getLoadedUser().setPassword(fldPassword.getText());
        Main.data.getSettings().getLoadedUser().setLastLogin(String.valueOf(new java.util.Date()));
        Main.data.getUserLocation(fldUsername.getText());
    }
}
