package model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;

public class Venta {
    private IntegerProperty id;
    private ObjectProperty<Cliente> cliente;
    private ObservableList<Producto> productos;
    private DoubleProperty total;
    private ObjectProperty<LocalDate> fecha;

    public Venta() {
        this(0, null, FXCollections.observableArrayList(), 0.0, LocalDate.now());
    }

    public Venta(int id, Cliente cliente, ObservableList<Producto> productos, double total, LocalDate fecha) {
        this.id = new SimpleIntegerProperty(id);
        this.cliente = new SimpleObjectProperty<>(cliente);
        this.productos = productos != null ? productos : FXCollections.observableArrayList();
        this.total = new SimpleDoubleProperty(total);
        this.fecha = new SimpleObjectProperty<>(fecha);
    }

    // Getters y Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public Cliente getCliente() {
        return cliente.get();
    }

    public void setCliente(Cliente cliente) {
        this.cliente.set(cliente);
    }

    public ObjectProperty<Cliente> clienteProperty() {
        return cliente;
    }

    public ObservableList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ObservableList<Producto> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total.get();
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public LocalDate getFecha() {
        return fecha.get();
    }

    public void setFecha(LocalDate fecha) {
        this.fecha.set(fecha);
    }

    public ObjectProperty<LocalDate> fechaProperty() {
        return fecha;
    }
}

