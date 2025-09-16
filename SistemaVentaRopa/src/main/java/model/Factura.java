package model;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Factura {
    private IntegerProperty id;
    private ObjectProperty<Venta> venta;
    private ObjectProperty<LocalDate> fecha;
    private DoubleProperty montoTotal;

    public Factura() {
        this(0, null, LocalDate.now(), 0.0);
    }

    public Factura(int id, Venta venta, LocalDate fecha, double montoTotal) {
        this.id = new SimpleIntegerProperty(id);
        this.venta = new SimpleObjectProperty<>(venta);
        this.fecha = new SimpleObjectProperty<>(fecha);
        this.montoTotal = new SimpleDoubleProperty(montoTotal);
    }

    // Getters y Setters
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public Venta getVenta() { return venta.get(); }
    public void setVenta(Venta venta) { this.venta.set(venta); }
    public ObjectProperty<Venta> ventaProperty() { return venta; }

    public LocalDate getFecha() { return fecha.get(); }
    public void setFecha(LocalDate fecha) { this.fecha.set(fecha); }
    public ObjectProperty<LocalDate> fechaProperty() { return fecha; }

    public double getMontoTotal() { return montoTotal.get(); }
    public void setMontoTotal(double montoTotal) { this.montoTotal.set(montoTotal); }
    public DoubleProperty montoTotalProperty() { return montoTotal; }
}