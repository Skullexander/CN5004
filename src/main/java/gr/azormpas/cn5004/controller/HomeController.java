package gr.azormpas.cn5004.controller;

import gr.azormpas.cn5004.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HomeController
{
    @FXML
    private BorderPane baseView;

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
