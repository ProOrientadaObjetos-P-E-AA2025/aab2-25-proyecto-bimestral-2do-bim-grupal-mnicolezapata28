package controlador;

import dao.FacturaDAO;
import dao.ClienteDAO;
import modelo.Cliente;
import modelo.Factura;
import modelo.Plan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FacturaControlador {

    private final FacturaDAO facturaDAO = new FacturaDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO(); 

    public boolean generarFactura(int clienteId, Plan plan) {
        double total = plan.calcularPago();
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Factura factura = new Factura(clienteId, fecha, total, plan);
        return facturaDAO.generarFactura(factura);
    }

    public List<Factura> listarFacturas() {
        return facturaDAO.listarFacturas();
    }

    public Cliente buscarClientePorId(int clienteId) {
        return clienteDAO.buscarPorId(clienteId); 
    }
}
