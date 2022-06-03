package gr.azormpas.cn5004.controller.purchase;

import gr.azormpas.cn5004.Main;
import gr.azormpas.cn5004.model.Product;
import gr.azormpas.cn5004.model.Purchase;
import gr.azormpas.cn5004.model.Shop;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AddController
{
    @FXML
    private ChoiceBox<Shop> btnShop;
    @FXML
    private ChoiceBox<Product> btnItem;
    @FXML
    private TextField fldAmount;
    @FXML
    private Label txtTotal;

    public void initialize()
    {
        ObservableList<Product> listProducts = FXCollections.observableArrayList();
        ObservableList<Shop> listShops = FXCollections.observableArrayList(Main.data.getShops());
        btnItem.setDisable(true);
        btnShop.setItems(listShops);
        btnShop.setOnAction(event ->
                            {
                                btnItem.setDisable(false);
                                listProducts.clear();
                                listProducts.addAll(btnShop.getSelectionModel().getSelectedItem().getInventory());
                                btnItem.setItems(listProducts);
                            });
        btnItem.setOnAction(event -> txtTotal.setText("--.--"));
    }

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
            Main.data.getProducts()
                .add(new Product(
                    Main.data.getShops().get(Main.data.getLoggedUserLocation()),
                    fldName.getText(),
                    Double.parseDouble(fldCost.getText()),
                    chkIsAvailable.isSelected()));

            Main.data.getProducts()
                .get(Main.data.getProducts().size()-1)
                .setInfo(fldInfo.getText());
            new Alert(AlertType.INFORMATION, String.format("Product %s was created successfully", fldName.getText())).show();
            Main.loadScene("product/List");
        }
    }

    public void exit(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("purchase/List");
    }
}
