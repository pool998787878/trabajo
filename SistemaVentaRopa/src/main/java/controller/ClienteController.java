package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Cliente;

public class ClienteController {
    private ObservableList<Cliente> clientes;
    private int nextId = 1;

    public ClienteController() {
        clientes = FXCollections.observableArrayList();
        initializeData();
    }

    private void initializeData() {
        // Datos de ejemplo
        agregarCliente(new Cliente(nextId++, "Juan", "Pérez", "juan.perez@email.com", "987654321"));
        agregarCliente(new Cliente(nextId++, "María", "García", "maria.garcia@email.com", "987654322"));
        agregarCliente(new Cliente(nextId++, "Carlos", "López", "carlos.lopez@email.com", "987654323"));
    }

    public ObservableList<Cliente> getClientes() {
        return clientes;
    }

    public void agregarCliente(Cliente cliente) {
        if (cliente.getId() == 0) {
            cliente.setId(nextId++);
        }
        clientes.add(cliente);
    }

    public void eliminarCliente(Cliente cliente) {
        clientes.remove(cliente);
    }

    public void actualizarCliente(Cliente clienteOriginal, Cliente clienteActualizado) {
        int index = clientes.indexOf(clienteOriginal);
        if (index >= 0) {
            clienteActualizado.setId(clienteOriginal.getId());
            clientes.set(index, clienteActualizado);
        }
    }

    public Cliente buscarClientePorId(int id) {
        return clientes.stream()
                .filter(cliente -> cliente.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public boolean existeEmail(String email, Cliente clienteExcluir) {
        return clientes.stream()
                .filter(cliente -> !cliente.equals(clienteExcluir))
                .anyMatch(cliente -> cliente.getEmail().equalsIgnoreCase(email));
    }
}
