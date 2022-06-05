package gr.azormpas.cn5004.controller.user;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ShopController
{
    @FXML
    private TextField fldShopName;
    @FXML
    private ChoiceBox<String> btnType;
    @FXML
    private TextArea fldDetails;

    public void initialize()
    {
        btnType.setItems(FXCollections.observableArrayList("Producer", "Mixed", "Retailer"));
    }

    public TextField getFldShopName()
    {
        return fldShopName;
    }

    public ChoiceBox<String> getBtnType()
    {
        return btnType;
    }

    public TextArea getFldDetails()
    {
        return fldDetails;
    }
}
