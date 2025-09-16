package view;

import controller.ClienteController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Cliente;

public class ClienteView {
    private Stage stage;
    private ClienteController clienteController;
    private TableView<Cliente> tablaClientes;
    private TextField txtNombre, txtApellido, txtEmail, txtTelefono;
    private Button btnAgregar, btnEditar, btnEliminar, btnLimpiar;
    private Cliente clienteSeleccionado;

    public ClienteView() {
        this.clienteController = new ClienteController();
        this.stage = new Stage();
        setupUI();
        setupEvents();
        actualizarTabla();
    }

    private void setupUI() {
        stage.setTitle("Sistema de Ventas - Gestión de Clientes");

        // Layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Panel superior - Título
        Label titulo = new Label("GESTIÓN DE CLIENTES");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        HBox panelTitulo = new HBox(titulo);
        panelTitulo.setPadding(new Insets(0, 0, 20, 0));

        // Panel izquierdo - Formulario
        VBox formulario = createFormulario();

        // Panel derecho - Tabla
        VBox panelTabla = createTabla();

        // Panel de botones
        HBox panelBotones = createBotones();

        // Organizar layout
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

        Label lblFormulario = new Label("DATOS DEL CLIENTE");
        lblFormulario.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        // Campos del formulario
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre");
        txtNombre.getStyleClass().add("custom-text-field");

        txtApellido = new TextField();
        txtApellido.setPromptText("Apellido");
        txtApellido.getStyleClass().add("custom-text-field");

        txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.getStyleClass().add("custom-text-field");

        txtTelefono = new TextField();
        txtTelefono.setPromptText("Teléfono");
        txtTelefono.getStyleClass().add("custom-text-field");

        formulario.getChildren().addAll(
                lblFormulario,
                new Label("Nombre:"), txtNombre,
                new Label("Apellido:"), txtApellido,
                new Label("Email:"), txtEmail,
                new Label("Teléfono:"), txtTelefono
        );

        return formulario;
    }

    private VBox createTabla() {
        VBox panelTabla = new VBox(10);
        panelTabla.setPadding(new Insets(10));

        Label lblTabla = new Label("LISTA DE CLIENTES");
        lblTabla.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        tablaClientes = new TableView<>();
        tablaClientes.getStyleClass().add("custom-table");

        // Columnas de la tabla
        TableColumn<Cliente, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(120);

        TableColumn<Cliente, String> colApellido = new TableColumn<>("Apellido");
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colApellido.setPrefWidth(120);

        TableColumn<Cliente, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(180);

        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Teléfono");
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTelefono.setPrefWidth(120);

        tablaClientes.getColumns().addAll(colId, colNombre, colApellido, colEmail, colTelefono);

        panelTabla.getChildren().addAll(lblTabla, tablaClientes);
        return panelTabla;
    }

    private HBox createBotones() {
        HBox panelBotones = new HBox(10);
        panelBotones.setPadding(new Insets(10, 0, 0, 0));

        btnAgregar = new Button("Agregar Cliente");
        btnAgregar.getStyleClass().add("btn-primary");

        btnEditar = new Button("Editar Cliente");
        btnEditar.getStyleClass().add("btn-secondary");
        btnEditar.setDisable(true);

        btnEliminar = new Button("Eliminar Cliente");
        btnEliminar.getStyleClass().add("btn-danger");
        btnEliminar.setDisable(true);

        btnLimpiar = new Button("Limpiar Campos");
        btnLimpiar.getStyleClass().add("btn-outline");

        panelBotones.getChildren().addAll(btnAgregar, btnEditar, btnEliminar, btnLimpiar);
        return panelBotones;
    }

    private void setupEvents() {
        // Evento de selección en la tabla
        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                clienteSeleccionado = newSelection;
                cargarDatosEnFormulario(newSelection);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
            } else {
                clienteSeleccionado = null;
                btnEditar.setDisable(true);
                btnEliminar.setDisable(true);
            }
        });

        // Eventos de los botones
        btnAgregar.setOnAction(e -> agregarCliente());
        btnEditar.setOnAction(e -> editarCliente());
        btnEliminar.setOnAction(e -> eliminarCliente());
        btnLimpiar.setOnAction(e -> limpiarCampos());
    }

    private void cargarDatosEnFormulario(Cliente cliente) {
        txtNombre.setText(cliente.getNombre());
        txtApellido.setText(cliente.getApellido());
        txtEmail.setText(cliente.getEmail());
        txtTelefono.setText(cliente.getTelefono());
    }

    private void agregarCliente() {
        if (validarCampos()) {
            Cliente nuevoCliente = new Cliente(
                    0, // El ID se asigna automáticamente
                    txtNombre.getText().trim(),
                    txtApellido.getText().trim(),
                    txtEmail.getText().trim(),
                    txtTelefono.getText().trim()
            );

            if (clienteController.existeEmail(nuevoCliente.getEmail(), null)) {
                mostrarAlerta("Error", "Ya existe un cliente con ese email.", Alert.AlertType.ERROR);
                return;
            }

            clienteController.agregarCliente(nuevoCliente);
            actualizarTabla();
            limpiarCampos();
            mostrarAlerta("Éxito", "Cliente agregado correctamente.", Alert.AlertType.INFORMATION);
        }
    }

    private void editarCliente() {
        if (clienteSeleccionado != null && validarCampos()) {
            Cliente clienteActualizado = new Cliente(
                    clienteSeleccionado.getId(),
                    txtNombre.getText().trim(),
                    txtApellido.getText().trim(),
                    txtEmail.getText().trim(),
                    txtTelefono.getText().trim()
            );

            if (clienteController.existeEmail(clienteActualizado.getEmail(), clienteSeleccionado)) {
                mostrarAlerta("Error", "Ya existe otro cliente con ese email.", Alert.AlertType.ERROR);
                return;
            }

            clienteController.actualizarCliente(clienteSeleccionado, clienteActualizado);
            actualizarTabla();
            limpiarCampos();
            mostrarAlerta("Éxito", "Cliente actualizado correctamente.", Alert.AlertType.INFORMATION);
        }
    }

    private void eliminarCliente() {
        if (clienteSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Está seguro de eliminar este cliente?");
            confirmacion.setContentText("Esta acción no se puede deshacer.");

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                clienteController.eliminarCliente(clienteSeleccionado);
                actualizarTabla();
                limpiarCampos();
                mostrarAlerta("Éxito", "Cliente eliminado correctamente.", Alert.AlertType.INFORMATION);
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtApellido.clear();
        txtEmail.clear();
        txtTelefono.clear();
        tablaClientes.getSelectionModel().clearSelection();
        clienteSeleccionado = null;
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() ||
                txtApellido.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() ||
                txtTelefono.getText().trim().isEmpty()) {

            mostrarAlerta("Error", "Todos los campos son obligatorios.", Alert.AlertType.ERROR);
            return false;
        }

        // Validar formato de email
        if (!txtEmail.getText().trim().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            mostrarAlerta("Error", "Formato de email inválido.", Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void actualizarTabla() {
        tablaClientes.setItems(clienteController.getClientes());
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