package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Producto;

public class ProductoController {
    private ObservableList<Producto> productos;
    private int nextId = 1;

    public ProductoController() {
        productos = FXCollections.observableArrayList();
        initializeData();
    }

    private void initializeData() {
        // Datos de ejemplo
        agregarProducto(new Producto(nextId++, "Camiseta Juvenil", "Camisetas", 25.99, 50));
        agregarProducto(new Producto(nextId++, "Jean Skinny", "Pantalones", 45.50, 30));
        agregarProducto(new Producto(nextId++, "Sudadera con Capucha", "Sudaderas", 65.00, 20));
    }

    public ObservableList<Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(Producto producto) {
        if (producto.getId() == 0) {
            producto.setId(nextId++);
        }
        productos.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public void actualizarProducto(Producto productoOriginal, Producto productoActualizado) {
        int index = productos.indexOf(productoOriginal);
        if (index >= 0) {
            productoActualizado.setId(productoOriginal.getId());
            productos.set(index, productoActualizado);
        }
    }

    public void actualizarStock(Producto producto, int nuevaCantidad) {
        producto.setStock(nuevaCantidad);
    }
}
