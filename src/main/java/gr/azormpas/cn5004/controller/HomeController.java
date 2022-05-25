package gr.azormpas.cn5004.controller;

import gr.azormpas.cn5004.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class HomeController
{
    @FXML
    private BorderPane baseView;
    @FXML
    private VBox boxOptions;
    @FXML
    private Label txtName;
    @FXML
    private Button btnProfile, btnUserList, btnShopList, btnCustomerList, btnProductList, btnPurchaseList;
    private final AnchorPane sideView = new AnchorPane();
    private String type;
    private Alert alert;

    AnchorPane sideView = new AnchorPane();

    public void viewCRUD(ActionEvent ignoredEvent)
        throws IOException
    {
        if (!sideView.getChildren().isEmpty()) clearSideView();
        createSideView("Edit");
        baseView.getScene().getWindow().sizeToScene();
    }

    public void viewList(ActionEvent ignoredEvent)
        throws IOException
    {
        if (!sideView.getChildren().isEmpty()) clearSideView();
        createSideView("List");
        baseView.getScene().getWindow().sizeToScene();
    }

    public void exit(ActionEvent ignoredEvent)
        throws IOException
    {
        Main.loadScene("Login");
    }


    private void createSideView(String view)
        throws IOException
    {
        if (!sideView.getChildren().isEmpty()) clearSideView();
        sideView.getChildren().add(setSideView(String.format("%s/%s",type ,view)));
        baseView.rightProperty().set(sideView);
        resizeView();
    }

    private Node setSideView(String view)
        throws IOException
    {
        return Main.loadFXML(view);
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
