package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Factura;

public class FacturaController {
    private ObservableList<Factura> facturas;
    private int nextId = 1;

    public FacturaController() {
        facturas = FXCollections.observableArrayList();
    }

    public ObservableList<Factura> getFacturas() {
        return facturas;
    }

    public void agregarFactura(Factura factura) {
        if (factura.getId() == 0) {
            factura.setId(nextId++);
        }
        facturas.add(factura);
    }

    public void eliminarFactura(Factura factura) {
        facturas.remove(factura);
    }
}