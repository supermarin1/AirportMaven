package controller;

import database.DataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AirportMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        DataSource datasource =  DataSource.getDataSource();

        if(!datasource.open()){
            System.out.println("Can't open datasource");
            return;
        }

        String fxmlFile = "/fxml/view/AirportView.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));

        primaryStage.setTitle("Airport view");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
