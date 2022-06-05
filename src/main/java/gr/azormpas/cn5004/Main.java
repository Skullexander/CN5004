package gr.azormpas.cn5004;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Main
    extends Application
{
    public static Stage stage;
    public static DataController data = new DataController();

    @Override
    public void start(Stage primaryStage)
        throws IOException
    {
        HostServicesProvider.INSTANCE.init(getHostServices());
        stage = primaryStage;
        stage.setTitle("CN5004 - Ordering Application");
        isStageResizeable(false);
        loadScene("Login");
        stage.show();
    }

    public static Parent loadFXML(String fxml)
        throws IOException
    {
        return FXMLLoader.load(getResource(String.format("view/%s.fxml", fxml)));
    }

    public static void loadScene(String fxml)
        throws IOException
    {
        Parent pane = loadFXML(fxml);
        stage.setScene(new Scene(pane));
    }

    public static void isStageResizeable(boolean b)
    {
        stage.setResizable(b);
    }

    private static URL getResource(String path)
    {
        return Objects.requireNonNull(Main.class.getResource(path));
    }

    public static void main(String[] args)
    {
        launch();
    }
}
