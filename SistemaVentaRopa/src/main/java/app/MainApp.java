package app;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainMenuView;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Mostrar menú principal en lugar de ir directamente a clientes
        MainMenuView mainMenu = new MainMenuView();
        mainMenu.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
