package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Venta;
import model.Producto;

public class VentaController {
    private ObservableList<Venta> ventas;
    private int nextId = 1;

    public VentaController() {
        ventas = FXCollections.observableArrayList();
    }

    public ObservableList<Venta> getVentas() {
        return ventas;
    }

    public void agregarVenta(Venta venta) {
        if (venta.getId() == 0) {
            venta.setId(nextId++);
        }
        // Calcular total
        double total = venta.getProductos().stream()
                .mapToDouble(Producto::getPrecio)
                .sum();
        venta.setTotal(total);
        ventas.add(venta);
    }

    public void eliminarVenta(Venta venta) {
        ventas.remove(venta);
    }

    public double calcularTotal(ObservableList<Producto> productos) {
        return productos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();
    }
}
