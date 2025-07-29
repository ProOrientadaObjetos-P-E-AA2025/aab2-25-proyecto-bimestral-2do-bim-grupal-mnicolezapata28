package controlador;

import dao.ClienteDAO;
import modelo.Cliente;

import java.util.List;

public class ClienteControlador {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    public boolean crearCliente(Cliente cliente) {
        return clienteDAO.guardar(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteDAO.listar();
    }

    public boolean actualizarCliente(Cliente cliente) {
        return clienteDAO.actualizar(cliente);
    }

    public boolean eliminarCliente(int idCliente) {
        return clienteDAO.eliminar(idCliente);
    }

    public Cliente buscarClientePorId(int id) {
        return clienteDAO.buscarPorId(id);
    }

}
