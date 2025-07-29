package controlador;

import dao.PlanDAO;
import modelo.Plan;

import java.util.List;

public class PlanControlador {

    private final PlanDAO planDAO = new PlanDAO();

    public int registrarPlan(Plan plan) {
        return planDAO.guardar(plan);
    }

    public boolean asignarPlanACliente(int clienteId, int planId) {
        return planDAO.asignarPlanACliente(clienteId, planId);
    }

    public List<Plan> obtenerPlanesDeCliente(int clienteId) {
        return planDAO.obtenerPlanesDeCliente(clienteId);
    }

    public boolean puedeAsignarOtroPlan(int clienteId) {
        return obtenerPlanesDeCliente(clienteId).size() < 2;
    }

    public Plan buscarPlanPorId(int id) {
        return planDAO.buscarPlanPorId(id);
    }

}
