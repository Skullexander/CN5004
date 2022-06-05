package gr.azormpas.cn5004.controller;

import gr.azormpas.cn5004.Main;
import gr.azormpas.cn5004.controller.user.CustomerController;
import gr.azormpas.cn5004.controller.user.ShopController;
import gr.azormpas.cn5004.model.Customer;
import gr.azormpas.cn5004.model.Shop;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class RegisterController
{
    @FXML
    private BorderPane baseView;
    @FXML
    private AnchorPane sideView;
    @FXML
    private TextField fldUsername;
    @FXML
    private PasswordField fldPassword, fldConfirm;
    @FXML
    private CheckBox chkShop, chkCustomer;
    private CustomerController customerController = new CustomerController();
    private ShopController shopController = new ShopController();

    public void createUser(ActionEvent ignoredEvent)
    {
        if (!Main.data.hasUser(fldUsername.getText()) && !fldUsername.getText().isBlank())
        {
            if (checkPassword() && !fldPassword.getText().isBlank())
            {
                if (chkShop.isSelected())
                {
                    createShop();
                }
                else if (chkCustomer.isSelected())
                {
                    createCustomer();
                }
                else
                {
                    new Alert(Alert.AlertType.ERROR, "No User type selected!").show();
                }
            }
            else
            {
                new Alert(Alert.AlertType.ERROR, "Password mismatched!").show();
            }
        }
        else
        {
            new Alert(Alert.AlertType.ERROR, "Username is blank or already exists!").show();
        }
    }

    private void createShop()
    {
        if (!shopController.getFldShopName().getText().isBlank())
        {
            if (!shopController.getBtnType().getSelectionModel().getSelectedItem().isBlank())
            {
                Main.data.getShops()
                         .add(new Shop(fldUsername.getText(),
                                       fldPassword.getText(),
                                       shopController.getFldShopName().getText(),
                                       shopController.getBtnType().getSelectionModel().getSelectedItem()));
                if (!shopController.getFldDetails().getText().isBlank())
                {
                    Main.data.getShops()
                             .get(Main.data.getShops().size()-1)
                             .setDetails(shopController.getFldDetails().getText());
                }
                new Alert(Alert.AlertType.CONFIRMATION, "Shop User successfully created!").show();
                try
                {
                    Main.data.saveData();
                    Main.loadScene("Login");
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                new Alert(Alert.AlertType.ERROR, "Shop Type must not be empty!").show();
            }
        }
        else
        {
            new Alert(Alert.AlertType.ERROR, "Shop Name must not be empty!").show();
        }
    }

    private void createCustomer()
    {
        if (!customerController.getFldFullName().getText().isBlank())
        {
            if (!customerController.getFldAddress().getText().isBlank())
            {
                if (!customerController.getFldCountry().getText().isBlank())
                {
                    Main.data.getCustomers()
                             .add(new Customer(fldUsername.getText(),
                                               fldPassword.getText(),
                                               customerController.getFldFullName().getText(),
                                               customerController.getFldAddress().getText(),
                                               customerController.getFldCountry().getText()));
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer User successfully created!").show();
                    try
                    {
                        Main.data.saveData();
                        Main.loadScene("Login");
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
                else
                {
                    new Alert(Alert.AlertType.ERROR, "Customer Address Number must not be empty!").show();
                }
            }
            else
            {
                new Alert(Alert.AlertType.ERROR, "Customer Address must not be empty!").show();
            }
        }
        else
        {
            new Alert(Alert.AlertType.ERROR, "Customer Name must not be empty!").show();
        }
    }

    private boolean checkPassword()
    {
        return fldConfirm.getText().equals(fldPassword.getText());
    }


    public void viewUserShop(ActionEvent ignoredEvent)
    {
        if (chkCustomer.isSelected())
        {
            chkCustomer.setSelected(false);
            clearSideView();
        }
        shopController = new ShopController();
        createSideView("Shop");
    }

    public void viewUserCustomer(ActionEvent ignoredEvent)
    {
        if (chkShop.isSelected())
        {
            chkShop.setSelected(false);
            clearSideView();
        }
        customerController = new CustomerController();
        createSideView("Customer");
    }

    public void viewLogin(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("Login");
    }

    private void createSideView(String view)
    {
        sideView.getChildren().add(setSideView(String.format("user/%s", view)));
        baseView.rightProperty().set(sideView);
        resizeView();
    }

    private Node setSideView(String view)
    {
        try
        {
            if (chkShop.isSelected())
            {
                return Main.loadFXML(view, shopController);
            }
            else
            {
                return Main.loadFXML(view, customerController);
            }

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void clearSideView()
    {
        sideView.getChildren().clear();
    }

    private void resizeView()
    {
        baseView.getScene().getWindow().sizeToScene();
    }
}
