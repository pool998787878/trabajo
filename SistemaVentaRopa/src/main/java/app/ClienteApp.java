package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ClienteView;

public class ClienteApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        ClienteView clienteView = new ClienteView();
        clienteView.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
