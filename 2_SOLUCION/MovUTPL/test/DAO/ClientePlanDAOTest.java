package DAO;

import dao.ClienteDAO;
import dao.ClientePlanDAO;
import dao.PlanDAO;
import modelo.Cliente;
import modelo.Dispositivo;
import modelo.PlanPostPagoMegas;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClientePlanDAOTest {
    private ClientePlanDAO clientePlanDAO;
    private Cliente cliente;
    private int planId;

    @Before
    public void setUp() {
        clientePlanDAO = new ClientePlanDAO();

        ClienteDAO clienteDAO = new ClienteDAO();
        cliente = new Cliente("Juan Lopez", "1122334455", "Ambato", "juan@mail.com", new Dispositivo("Motorola", "Edge", "0999988776"));
        clienteDAO.guardar(cliente);

        PlanDAO planDAO = new PlanDAO();
        PlanPostPagoMegas plan = new PlanPostPagoMegas(10, 2.5, 5.0);
        plan.setDescripcion("Plan Test");
        planId = planDAO.guardar(plan);
    }

    @Test
    public void testAsignarYContarPlanes() {
        boolean asignado = clientePlanDAO.asignarPlan(cliente.getId(), planId);
        assertTrue(asignado);

        int cuenta = clientePlanDAO.contarPlanesPorCliente(cliente.getId());
        assertTrue(cuenta >= 1);
    }

    @Test
    public void testObtenerYEliminarRelacion() {
        clientePlanDAO.asignarPlan(cliente.getId(), planId);
        assertTrue(clientePlanDAO.existeRelacion(cliente.getId(), planId));
        assertTrue(clientePlanDAO.eliminarPlanesDeCliente(cliente.getId()));
    }
}
