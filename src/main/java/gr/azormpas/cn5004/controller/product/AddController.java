package gr.azormpas.cn5004.controller.product;

import gr.azormpas.cn5004.Main;
import gr.azormpas.cn5004.model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddController
{
    @FXML
    private TextField fldName, fldCost;
    @FXML
    private TextArea fldInfo;
    @FXML
    private CheckBox chkIsAvailable;

    public void confirm(ActionEvent ignoredEvent)
        throws IOException
    {
        if (fldName.getText().isBlank())
        {
            new Alert(AlertType.ERROR, "Product name is empty or blank.").show();
        }
        else if (fldCost.getText().isBlank() && fldCost.getText().matches("^\\d{1,8}\\.\\d{2}$"))
        {
            new Alert(AlertType.ERROR, "Product cost invalid. Mandatory use of exactly two decimals (example: 12.00)").show();
        }
        else
        {
            Main.data.getShops()
                     .get(Main.data.getLoggedUserLocation())
                     .getInventory()
                     .add(new Product(
                         Main.data.getShops().get(Main.data.getLoggedUserLocation()),
                         fldName.getText(),
                         Double.parseDouble(fldCost.getText()),
                         chkIsAvailable.isSelected()));

            Main.data.getShops()
                     .get(Main.data.getLoggedUserLocation())
                     .getInventory()
                     .get(Main.data.getShops()
                                   .get(Main.data.getLoggedUserLocation())
                                   .getInventory().size()-1)
                     .setInfo(fldInfo.getText());
        }
    }

    public void cancelAdd(ActionEvent ignoredEvent)
    {
        btnCancel.getScene().getWindow().hide();
    }
}
