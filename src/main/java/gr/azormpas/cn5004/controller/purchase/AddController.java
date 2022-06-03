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
        if (btnShop.getSelectionModel().isEmpty())
        {
            new Alert(Alert.AlertType.ERROR, "Shop is empty or blank.").show();
        }
        else if (btnItem.getSelectionModel().isEmpty())
        {
            new Alert(Alert.AlertType.ERROR, "Item is empty or black.").show();
        }
        else if(fldAmount.getText().isBlank() || !fldAmount.getText().matches("^[1-9]\\d*$"))
        {
            new Alert(Alert.AlertType.ERROR, "Item amount is invalid.").show();
        }
        else
        {
            Main.data.getPurchases()
                     .add(new Purchase(
                         Main.data.getCustomers().get(Main.data.getLoggedUserLocation()),
                         Integer.parseInt(fldAmount.getText()),
                         btnItem.getSelectionModel().getSelectedItem(),
                         calculateTotal()));

            new Alert(Alert.AlertType.INFORMATION, String.format("Purchase no. %d was created successfully", Main.data.getPurchases().get(Main.data.getPurchases().size()-1).getID())).show();
            Main.loadScene("purchase/List");
        }
    }

    public void calculate(ActionEvent ignoredEvent)
    {
        txtTotal.setText(String.format("%.2f", calculateTotal()));
    }

    private double calculateTotal()
    {
        return (btnItem.getSelectionModel().getSelectedItem().getCost() * Integer.parseInt(fldAmount.getText()));
    }

    public void exit(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("purchase/List");
    }
}
