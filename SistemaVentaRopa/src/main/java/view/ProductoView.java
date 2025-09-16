package view;

import controller.ProductoController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Producto;

public class ProductoView {
    private Stage stage;
    private ProductoController productoController;
    private TableView<Producto> tablaProductos;
    private TextField txtNombre, txtCategoria, txtPrecio, txtStock;
    private Button btnAgregar, btnEditar, btnEliminar, btnLimpiar;
    private Producto productoSeleccionado;

    public ProductoView() {
        this.productoController = new ProductoController();
        this.stage = new Stage();
        setupUI();
        setupEvents();
        actualizarTabla();
    }

    private void setupUI() {
        stage.setTitle("Sistema de Ventas - Gestión de Productos");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Panel superior - Título
        Label titulo = new Label("GESTIÓN DE PRODUCTOS");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        HBox panelTitulo = new HBox(titulo);
        panelTitulo.setPadding(new Insets(0, 0, 20, 0));

        // Panel izquierdo - Formulario
        VBox formulario = createFormulario();

        // Panel derecho - Tabla
        VBox panelTabla = createTabla();

        // Panel de botones
        HBox panelBotones = createBotones();

        root.setTop(panelTitulo);
        root.setLeft(formulario);
        root.setCenter(panelTabla);
        root.setBottom(panelBotones);

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add("data:text/css," + getCSS());
        stage.setScene(scene);
    }

    private VBox createFormulario() {
        VBox formulario = new VBox(10);
        formulario.setPadding(new Insets(10));
        formulario.setPrefWidth(300);
        formulario.setStyle("-fx-background-color: #f8f9fa; -fx-background-radius: 10;");

        Label lblFormulario = new Label("DATOS DEL PRODUCTO");
        lblFormulario.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del producto");
        txtNombre.getStyleClass().add("custom-text-field");

        txtCategoria = new TextField();
        txtCategoria.setPromptText("Categoría");
        txtCategoria.getStyleClass().add("custom-text-field");

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio (0.00)");
        txtPrecio.getStyleClass().add("custom-text-field");

        txtStock = new TextField();
        txtStock.setPromptText("Cantidad en stock");
        txtStock.getStyleClass().add("custom-text-field");

        formulario.getChildren().addAll(
                lblFormulario,
                new Label("Nombre:"), txtNombre,
                new Label("Categoría:"), txtCategoria,
                new Label("Precio:"), txtPrecio,
                new Label("Stock:"), txtStock
        );

        return formulario;
    }

    private VBox createTabla() {
        VBox panelTabla = new VBox(10);
        panelTabla.setPadding(new Insets(10));

        Label lblTabla = new Label("INVENTARIO DE PRODUCTOS");
        lblTabla.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        tablaProductos = new TableView<>();
        tablaProductos.getStyleClass().add("custom-table");

        TableColumn<Producto, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Producto, String> colNombre = new TableColumn<>("Producto");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(200);

        TableColumn<Producto, String> colCategoria = new TableColumn<>("Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colCategoria.setPrefWidth(120);

        TableColumn<Producto, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPrecio.setPrefWidth(100);

        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStock.setPrefWidth(80);

        tablaProductos.getColumns().addAll(colId, colNombre, colCategoria, colPrecio, colStock);

        panelTabla.getChildren().addAll(lblTabla, tablaProductos);
        return panelTabla;
    }

    private HBox createBotones() {
        HBox panelBotones = new HBox(10);
        panelBotones.setPadding(new Insets(10, 0, 0, 0));

        btnAgregar = new Button("Agregar Producto");
        btnAgregar.getStyleClass().add("btn-primary");

        btnEditar = new Button("Editar Producto");
        btnEditar.getStyleClass().add("btn-secondary");
        btnEditar.setDisable(true);

        btnEliminar = new Button("Eliminar Producto");
        btnEliminar.getStyleClass().add("btn-danger");
        btnEliminar.setDisable(true);

        btnLimpiar = new Button("Limpiar Campos");
        btnLimpiar.getStyleClass().add("btn-outline");

        panelBotones.getChildren().addAll(btnAgregar, btnEditar, btnEliminar, btnLimpiar);
        return panelBotones;
    }

    private void setupEvents() {
        tablaProductos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                productoSeleccionado = newSelection;
                cargarDatosEnFormulario(newSelection);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
            } else {
                productoSeleccionado = null;
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
            }
        });

        btnAgregar.setOnAction(e -> agregarProducto());
        btnEditar.setOnAction(e -> editarProducto());
        btnEliminar.setOnAction(e -> eliminarProducto());
        btnLimpiar.setOnAction(e -> limpiarCampos());
    }

    private void cargarDatosEnFormulario(Producto producto) {
        txtNombre.setText(producto.getNombre());
        txtCategoria.setText(producto.getCategoria());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtStock.setText(String.valueOf(producto.getStock()));
    }

    private void agregarProducto() {
        if (validarCampos()) {
            try {
                Producto nuevoProducto = new Producto(
                        0,
                        txtNombre.getText().trim(),
                        txtCategoria.getText().trim(),
                        Double.parseDouble(txtPrecio.getText().trim()),
                        Integer.parseInt(txtStock.getText().trim())
                );

                productoController.agregarProducto(nuevoProducto);
                actualizarTabla();
                limpiarCampos();
                mostrarAlerta("Éxito", "Producto agregado correctamente.", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Formato numérico inválido en precio o stock.", Alert.AlertType.ERROR);
            }
        }
    }

    private void editarProducto() {
        if (productoSeleccionado != null && validarCampos()) {
            try {
                Producto productoActualizado = new Producto(
                        productoSeleccionado.getId(),
                        txtNombre.getText().trim(),
                        txtCategoria.getText().trim(),
                        Double.parseDouble(txtPrecio.getText().trim()),
                        Integer.parseInt(txtStock.getText().trim())
                );

                productoController.actualizarProducto(productoSeleccionado, productoActualizado);
                actualizarTabla();
                limpiarCampos();
                mostrarAlerta("Éxito", "Producto actualizado correctamente.", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                mostrarAlerta("Error", "Formato numérico inválido en precio o stock.", Alert.AlertType.ERROR);
            }
        }
    }

    private void eliminarProducto() {
        if (productoSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este producto?");
            confirmacion.setContentText("Esta acción no se puede deshacer.");

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                productoController.eliminarProducto(productoSeleccionado);
                actualizarTabla();
                limpiarCampos();
                mostrarAlerta("Éxito", "Producto eliminado correctamente.", Alert.AlertType.INFORMATION);
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtCategoria.clear();
        txtPrecio.clear();
        txtStock.clear();
        tablaProductos.getSelectionModel().clearSelection();
        productoSeleccionado = null;
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
                txtCategoria.getText().trim().isEmpty() ||
                txtPrecio.getText().trim().isEmpty() ||
                txtStock.getText().trim().isEmpty()) {

            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return false;
        }

        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock = Integer.parseInt(txtStock.getText().trim());

            if (precio < 0) {
                mostrarAlerta("Error", "El precio no puede ser negativo.", Alert.AlertType.ERROR);
                return false;
            }

            if (stock < 0) {
                mostrarAlerta("Error", "El stock no puede ser negativo.", Alert.AlertType.ERROR);
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "Precio y stock deben ser valores numéricos válidos.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void actualizarTabla() {
        tablaProductos.setItems(productoController.getProductos());
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private String getCSS() {
        return """
        .custom-text-field {
            -fx-padding: 8px;
            -fx-font-size: 13px;
            -fx-background-color: white;
            -fx-border-color: #ddd;
            -fx-border-radius: 5px;
            -fx-background-radius: 5px;
        }
        
        .custom-text-field:focused {
            -fx-border-color: #3498db;
            -fx-effect: dropshadow(gaussian, rgba(52, 152, 219, 0.3), 5, 0, 0, 0);
        }
        
        .btn-primary {
            -fx-background-color: #3498db;
            -fx-text-fill: white;
            -fx-padding: 10px 20px;
            -fx-font-size: 13px;
            -fx-font-weight: bold;
            -fx-background-radius: 5px;
            -fx-cursor: hand;
        }
        
        .btn-primary:hover {
            -fx-background-color: #2980b9;
        }
        
        .btn-secondary {
            -fx-background-color: #95a5a6;
            -fx-text-fill: white;
            -fx-padding: 10px 20px;
            -fx-font-size: 13px;
            -fx-font-weight: bold;
            -fx-background-radius: 5px;
            -fx-cursor: hand;
        }
        
        .btn-secondary:hover {
            -fx-background-color: #7f8c8d;
        }
        
        .btn-danger {
            -fx-background-color: #e74c3c;
            -fx-text-fill: white;
            -fx-padding: 10px 20px;
            -fx-font-size: 13px;
            -fx-font-weight: bold;
            -fx-background-radius: 5px;
            -fx-cursor: hand;
        }
        
        .btn-danger:hover {
            -fx-background-color: #c0392b;
        }
        
        .btn-outline {
            -fx-background-color: transparent;
            -fx-text-fill: #34495e;
            -fx-border-color: #34495e;
            -fx-border-width: 2px;
            -fx-padding: 8px 18px;
            -fx-font-size: 13px;
            -fx-font-weight: bold;
            -fx-background-radius: 5px;
            -fx-border-radius: 5px;
            -fx-cursor: hand;
        }
        
        .btn-outline:hover {
            -fx-background-color: #34495e;
            -fx-text-fill: white;
        }
        
        .custom-table {
            -fx-background-color: white;
            -fx-table-color: white;
            -fx-table-ripple-color: transparent;
            -fx-padding: 5px;
        }
        
        .custom-table .column-header {
            -fx-background-color: #34495e;
            -fx-text-fill: white;
            -fx-font-weight: bold;
            -fx-font-size: 13px;
            -fx-padding: 10px 5px;
        }
        
        .custom-table .table-row-cell:selected {
            -fx-background-color: #3498db;
            -fx-text-fill: white;
        }
        
        .custom-table .table-row-cell:hover {
            -fx-background-color: #ecf0f1;
        }
        """;
    }

    public void show() {
        stage.show();
    }
}
