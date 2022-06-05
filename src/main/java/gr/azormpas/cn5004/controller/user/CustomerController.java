package gr.azormpas.cn5004.controller.user;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CustomerController
{
    @FXML
    private TextField fldFullName, fldAddress, fldCountry;

    public TextField getFldFullName()
    {
        return fldFullName;
    }

    public TextField getFldAddress()
    {
        return fldAddress;
    }

    public TextField getFldCountry()
    {
        return fldCountry;
    }
}
