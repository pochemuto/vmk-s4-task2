package org.pochemuto.planets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlanetsApplication extends Application
{
    public static void main( String[] args )
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(PlanetsApplication.class.getResource("/main.fxml"));
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
