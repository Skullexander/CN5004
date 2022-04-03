package gr.azormpas.cn5004.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HomeController
{
    @FXML
    private BorderPane baseView;

    Alert a = new Alert(Alert.AlertType.NONE);
    AnchorPane sideView = new AnchorPane();

    public void testAction(ActionEvent event)
    {
        if (!Objects.equals("Test", a.getTitle())) createAlert(event);
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.show();
    }

    public void testAction2(ActionEvent event)
    {
        if (!Objects.equals("Test", a.getTitle())) createAlert(event);
        a.setAlertType(Alert.AlertType.ERROR);
        a.show();
    }

    public void testAction3(ActionEvent event)
    {
        if (!Objects.equals("Test", a.getTitle())) createAlert(event);
        a.setAlertType(Alert.AlertType.WARNING);
        a.show();
    }

    public void viewCRUD(ActionEvent event)
        throws IOException
    {
        if (sideView.getChildren().isEmpty())
        {
            createSideView("Edit");
        }
        else
        {
            clearSideView();
            createSideView("Edit");
        }
        baseView.getScene().getWindow().sizeToScene();
    }

    public void viewList(ActionEvent event)
        throws IOException
    {
        if (sideView.getChildren().isEmpty())
        {
            createSideView("List");
        }
        else
        {
            clearSideView();
            createSideView("List");
        }
        baseView.getScene().getWindow().sizeToScene();
    }

    public void exit(ActionEvent event)
        throws IOException
    {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")))));
        stage.show();
    }

    private void createAlert(ActionEvent e)
    {
        setModality(e);
        a.setTitle("Test");
        a.setHeaderText("Button works!");
        a.setContentText("This button is in working condition");
    }

    private void createSideView(String view)
        throws IOException
    {
        sideView.getChildren().add(setSideView(view));
        baseView.rightProperty().set(sideView);
    }

    private void setModality (ActionEvent e)
    {
        a.initOwner(((Button) e.getSource()).getScene().getWindow());
        a.initModality(Modality.WINDOW_MODAL);
    }

    private Node setSideView (String view)
        throws IOException
    {
        return FXMLLoader.load(Objects.requireNonNull(getClass().getResource(String.format("/view/%s.fxml", view))));
    }

    private void clearSideView ()
    {
        sideView.getChildren().clear();
    }
}
