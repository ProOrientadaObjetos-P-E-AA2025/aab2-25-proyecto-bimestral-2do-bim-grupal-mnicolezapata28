package DAO;

import dao.ClienteDAO;
import modelo.Cliente;
import modelo.Dispositivo;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ClienteDAOTest {
    private ClienteDAO clienteDAO;

    @Before
    public void setUp() {
        clienteDAO = new ClienteDAO();
    }

    @Test
    public void testGuardarYBuscarCliente() {
        Cliente c = new Cliente("Carlos Perez", "1234567890", "Loja", "cperez@mail.com", new Dispositivo("Xiaomi", "Redmi", "0991234567"));
        boolean guardado = clienteDAO.guardar(c);
        assertTrue(guardado);
        assertTrue(c.getId() > 0);

        Cliente recuperado = clienteDAO.buscarPorId(c.getId());
        assertNotNull(recuperado);
        assertEquals("Carlos Perez", recuperado.getNombres());
    }

    @Test
    public void testActualizarCliente() {
        Cliente c = new Cliente("Lucia Soto", "8888888888", "Quito", "lucia@mail.com", new Dispositivo("Nokia", "3310", "0981122334"));
        clienteDAO.guardar(c);
        c.setCiudad("Guayaquil");
        boolean actualizado = clienteDAO.actualizar(c);
        assertTrue(actualizado);

        Cliente actualizadoC = clienteDAO.buscarPorId(c.getId());
        assertEquals("Guayaquil", actualizadoC.getCiudad());
    }

    @Test
    public void testEliminarCliente() {
        Cliente c = new Cliente("Miguel Torres", "7777777777", "Cuenca", "miguel@mail.com", new Dispositivo("Sony", "Xperia", "0987654321"));
        clienteDAO.guardar(c);
        boolean eliminado = clienteDAO.eliminar(c.getId());
        assertTrue(eliminado);

        Cliente eliminadoC = clienteDAO.buscarPorId(c.getId());
        assertNull(eliminadoC);
    }

    @Test
    public void testListarClientes() {
        List<Cliente> clientes = clienteDAO.listar();
        assertNotNull(clientes);
    }
}
