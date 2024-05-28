package org.intelimusi.intelimusifxplus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Load FXML file và tạo Scene5
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        // Thiết lập Stage và hiển thị ứng dụng
        stage.setTitle("Music Player");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}