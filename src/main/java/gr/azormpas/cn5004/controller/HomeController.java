package gr.azormpas.cn5004.controller;

import gr.azormpas.cn5004.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HomeController
{
    @FXML
    private VBox boxOptions;
    @FXML
    private Label txtName;
    @FXML
    private Button btnProfile, btnUserList, btnShopList, btnCustomerList, btnProductList, btnPurchaseList;
    private String type;

    public void initialize()
    {
        txtName.setText(getUsername());
        checkType();
        switch (type)
        {
            case("shop"):
                btnUserList.setVisible(false);
                btnShopList.setVisible(false);
                btnCustomerList.setVisible(false);
                break;
            case("customer"):
                btnUserList.setVisible(false);
                btnShopList.setVisible(false);
                btnCustomerList.setVisible(false);
                btnProductList.setVisible(false);
                break;
            case("none"):
                btnProfile.setVisible(false);
                btnUserList.setVisible(false);
                btnShopList.setVisible(false);
                btnCustomerList.setVisible(false);
                btnProductList.setVisible(false);
                btnPurchaseList.setVisible(false);
                break;
            default:
                break;
        }
        boxOptions.getChildren().removeIf(node -> !node.isVisible());
    }

    private String getUsername()
    {
        switch (Main.data.getLoggedUserType())
        {
            //case(0):
            //    return Main.data.getSettings().getLoadedUser().getUsername();
            case(1):
                return Main.data.getShops().get(Main.data.getLoggedUserLocation()).getShopName();
            case(2):
                return Main.data.getCustomers().get(Main.data.getLoggedUserLocation()).getCustomerName();
            default:
                return "none";
        }
    }

    private void checkType()
    {
        switch(Main.data.getLoggedUserType())
        {
            //case(0):
            //    type = "admin";
            //    break;
            case(1):
                type = "shop";
                break;
            case(2):
                type = "customer";
                break;
            default:
                type = "none";
                try
                {
                    new Alert(Alert.AlertType.ERROR, "User does not exist. Return to Verification Portal.").show();
                    Main.loadScene("Login");
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    public void viewProfile(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("Profile");
    }

    public void viewUserList(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("user/List");
    }

    public void viewShopList(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("shop/List");
    }

    public void viewCustomerList(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("customer/List");
    }

    public void viewProductList(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("product/List");
    }

    public void viewPurchaseList(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("purchase/List");
    }

    public void exit(ActionEvent ignoredEvent)
        throws IOException
    {
        if (!type.equals("none"))
        {
            Main.data.saveData();
        }
        Main.loadScene("Login");
    }
}
