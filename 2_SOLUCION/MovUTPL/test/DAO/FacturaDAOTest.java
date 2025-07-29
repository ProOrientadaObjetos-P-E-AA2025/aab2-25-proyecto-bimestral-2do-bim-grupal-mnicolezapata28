package DAO;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.PlanDAO;
import modelo.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class FacturaDAOTest {

    private FacturaDAO facturaDAO;
    private Cliente cliente;
    private Plan plan;

    @Before
    public void setUp() {
        facturaDAO = new FacturaDAO();

        ClienteDAO clienteDAO = new ClienteDAO();
        cliente = new Cliente("Ana María", "9999999999", "Santo Domingo", "ana@mail.com", new Dispositivo("Huawei", "P30", "0991122334"));
        clienteDAO.guardar(cliente);

        PlanDAO planDAO = new PlanDAO();
        plan = new PlanPostPagoMinutosMegasEconomico(100, 0.2, 3, 1.5, 10);
        plan.setDescripcion("Plan Económico Full");
        planDAO.guardar(plan);
    }

    @Test
    public void testGenerarYListarFacturas() {
        Factura f = new Factura(cliente.getId(), LocalDate.now().toString(), plan.calcularPago(), plan);
        boolean generado = facturaDAO.generarFactura(f);
        assertTrue(generado);

        List<Factura> lista = facturaDAO.listarFacturas();
        assertNotNull(lista);
        assertTrue(lista.size() > 0);
    }
}
