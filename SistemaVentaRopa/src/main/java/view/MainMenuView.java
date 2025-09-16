package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView {
    private Stage stage;

    public MainMenuView() {
        this.stage = new Stage();
        setupUI();
        setupEvents();
    }

    private void setupUI() {
        stage.setTitle("Sistema de Venta de Ropa Juvenil - Menú Principal");

        VBox root = new VBox(30);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #667eea 0%, #764ba2 100%);");

        // Título principal
        Label titulo = new Label("SISTEMA DE VENTA DE ROPA JUVENIL");
        titulo.setStyle("""
            -fx-font-size: 28px;
            -fx-font-weight: bold;
            -fx-text-fill: white;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 2);
        """);

        Label subtitulo = new Label("Seleccione el módulo que desea gestionar");
        subtitulo.setStyle("""
            -fx-font-size: 16px;
            -fx-text-fill: white;
            -fx-opacity: 0.9;
        """);

        // Grid de botones del menú
        GridPane menuGrid = new GridPane();
        menuGrid.setHgap(20);
        menuGrid.setVgap(20);
        menuGrid.setAlignment(Pos.CENTER);

        Button btnClientes = createMenuButton("👥 CLIENTES", "Gestionar información de clientes");
        Button btnProductos = createMenuButton("🛍️ PRODUCTOS", "Administrar inventario y catálogo");
        Button btnVentas = createMenuButton("💰 VENTAS", "Registrar y consultar ventas");
        Button btnFacturas = createMenuButton("🧾 FACTURAS", "Generar y gestionar facturas");
        Button btnReportes = createMenuButton("📊 REPORTES", "Visualizar estadísticas y reportes");
        Button btnSalir = createMenuButton("🚪 SALIR", "Cerrar la aplicación");

        // Agregar botones al grid
        menuGrid.add(btnClientes, 0, 0);
        menuGrid.add(btnProductos, 1, 0);
        menuGrid.add(btnVentas, 0, 1);
        menuGrid.add(btnFacturas, 1, 1);
        menuGrid.add(btnReportes, 0, 2);
        menuGrid.add(btnSalir, 1, 2);

        root.getChildren().addAll(titulo, subtitulo, menuGrid);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setResizable(false);
    }

    private Button createMenuButton(String texto, String descripcion) {
        Button button = new Button(texto);
        button.setPrefSize(300, 80);
        button.setStyle("""
            -fx-background-color: rgba(255, 255, 255, 0.9);
            -fx-text-fill: #2c3e50;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-background-radius: 15px;
            -fx-border-radius: 15px;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);
            -fx-cursor: hand;
        """);

        button.setOnMouseEntered(e -> button.setStyle("""
            -fx-background-color: white;
            -fx-text-fill: #2c3e50;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-background-radius: 15px;
            -fx-border-radius: 15px;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 5);
            -fx-cursor: hand;
            -fx-scale-x: 1.05;
            -fx-scale-y: 1.05;
        """));

        button.setOnMouseExited(e -> button.setStyle("""
            -fx-background-color: rgba(255, 255, 255, 0.9);
            -fx-text-fill: #2c3e50;
            -fx-font-size: 16px;
            -fx-font-weight: bold;
            -fx-background-radius: 15px;
            -fx-border-radius: 15px;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 3);
            -fx-cursor: hand;
            -fx-scale-x: 1.0;
            -fx-scale-y: 1.0;
        """));

        return button;
    }

    private void setupEvents() {
        // Configurar eventos de los botones
        setupButtonEvent("👥 CLIENTES", () -> {
            ClienteView clienteView = new ClienteView();
            clienteView.show();
        });

        setupButtonEvent("🛍️ PRODUCTOS", () -> {
            ProductoView productoView = new ProductoView();
            productoView.show();
        });

        setupButtonEvent("💰 VENTAS", () -> {
            // Por implementar
            mostrarMensajeEnDesarrollo("Módulo de Ventas");
        });

        setupButtonEvent("🧾 FACTURAS", () -> {
            // Por implementar
            mostrarMensajeEnDesarrollo("Módulo de Facturas");
        });

        setupButtonEvent("📊 REPORTES", () -> {
            // Por implementar
            mostrarMensajeEnDesarrollo("Módulo de Reportes");
        });

        setupButtonEvent("🚪 SALIR", () -> {
            stage.close();
            System.exit(0);
        });
    }

    private void setupButtonEvent(String buttonText, Runnable action) {
        stage.getScene().getRoot().lookupAll(".button").forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                if (button.getText().equals(buttonText)) {
                    button.setOnAction(e -> action.run());
                }
            }
        });
    }

    private void mostrarMensajeEnDesarrollo(String modulo) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("En Desarrollo");
        alert.setHeaderText(modulo + " - Próximamente");
        alert.setContentText("Este módulo está en desarrollo y estará disponible en una próxima versión.");
        alert.showAndWait();
    }

    public void show() {
        stage.show();
    }
}
