package model;

import javafx.beans.property.*;

public class Cliente {
    private IntegerProperty id;
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty email;
    private StringProperty telefono;

    public Cliente() {
        this(0, "", "", "", "");
    }

    public Cliente(int id, String nombre, String apellido, String email, String telefono) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.email = new SimpleStringProperty(email);
        this.telefono = new SimpleStringProperty(telefono);
    }

    // Getters y Setters para ID
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    // Getters y Setters para Nombre
    public String getNombre() { return nombre.get(); }
    public void setNombre(String nombre) { this.nombre.set(nombre); }
    public StringProperty nombreProperty() { return nombre; }

    // Getters y Setters para Apellido
    public String getApellido() { return apellido.get(); }
    public void setApellido(String apellido) { this.apellido.set(apellido); }
    public StringProperty apellidoProperty() { return apellido; }

    // Getters y Setters para Email
    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
    public StringProperty emailProperty() { return email; }

    // Getters y Setters para Teléfono
    public String getTelefono() { return telefono.get(); }
    public void setTelefono(String telefono) { this.telefono.set(telefono); }
    public StringProperty telefonoProperty() { return telefono; }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + getId() +
                ", nombre='" + getNombre() + '\'' +
                ", apellido='" + getApellido() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefono='" + getTelefono() + '\'' +
                '}';
    }
}