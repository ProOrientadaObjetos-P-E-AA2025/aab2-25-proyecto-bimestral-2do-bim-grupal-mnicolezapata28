package DAO;

import dao.PlanDAO;
import modelo.Plan;
import modelo.PlanPostPagoMinutos;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PlanDAOTest {

    private PlanDAO planDAO;

    @Before
    public void setUp() {
        planDAO = new PlanDAO();
    }

    @Test
    public void testGuardarYBuscarPlan() {
        PlanPostPagoMinutos plan = new PlanPostPagoMinutos(100, 0.2, 50, 0.5);
        plan.setDescripcion("Plan Minutos Internacional");
        int id = planDAO.guardar(plan);
        assertTrue(id > 0);

        Plan recuperado = planDAO.buscarPlanPorId(id);
        assertNotNull(recuperado);
        assertEquals("Plan Minutos Internacional", recuperado.getDescripcion());
    }

    @Test
    public void testObtenerPlanesDeCliente() {
        // Dependerá de que ya haya relaciones registradas
        List<Plan> planes = planDAO.obtenerPlanesDeCliente(1); // Cambiar ID según tus datos
        assertNotNull(planes);
    }
}
