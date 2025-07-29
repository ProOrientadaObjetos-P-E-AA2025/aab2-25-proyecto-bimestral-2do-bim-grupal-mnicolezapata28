package dao;

import modelo.Factura;
import modelo.Plan;
import modelo.PlanPostPagoMegas;
import modelo.PlanPostPagoMinutos;
import modelo.PlanPostPagoMinutosMegas;
import modelo.PlanPostPagoMinutosMegasEconomico;
import util.ConexionSQLite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {

    private final PlanDAO planDAO = new PlanDAO();
    private final ClientePlanDAO clientePlanDAO = new ClientePlanDAO();

    public boolean generarFactura(Factura factura) {
        int clienteId = factura.getClienteId();
        int planId = factura.getPlan().getId();

        if (!clientePlanDAO.existeRelacion(clienteId, planId)) {
            if (clientePlanDAO.contarPlanesPorCliente(clienteId) >= 2) {
                System.out.println("No se puede generar factura. El cliente ya tiene 2 planes asignados.");
                return false;
            }

            if (!clientePlanDAO.asignarPlan(clienteId, planId)) {
                System.out.println("Error al asignar el plan al cliente antes de facturar.");
                return false;
            }
        }

        String sql = """
        INSERT INTO factura (cliente_id, plan_id, fecha, valor_total)
        VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            pstmt.setInt(2, planId);
            pstmt.setString(3, factura.getFecha());
            pstmt.setDouble(4, factura.getValorTotal());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al generar factura: " + e.getMessage());
        }

        return false;
    }

    public List<Factura> listarFacturas() {
        List<Factura> facturas = new ArrayList<>();

        String sql = """
        SELECT f.id, f.fecha, f.valor_total,
               f.cliente_id,
               c.nombres AS cliente_nombre, 
               c.cedula_pasaporte,
               p.tipo, p.descripcion, p.id AS plan_id
        FROM factura f
        INNER JOIN cliente c ON f.cliente_id = c.id
        INNER JOIN plan p ON f.plan_id = p.id
        """;

        try (Connection conn = ConexionSQLite.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int planId = rs.getInt("plan_id");
                Plan plan = buscarPlanPorId(planId);

                Factura factura = new Factura(
                        rs.getInt("id"),
                        rs.getString("fecha"),
                        rs.getDouble("valor_total"),
                        rs.getString("cliente_nombre"),
                        rs.getString("cedula_pasaporte"),
                        plan
                );

                factura.setClienteId(rs.getInt("cliente_id"));
                facturas.add(factura);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar facturas: " + e.getMessage());
        }

        return facturas;
    }

    public Plan buscarPlanPorId(int planId) {
        String sql = "SELECT * FROM plan WHERE id = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, planId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String tipo = rs.getString("tipo");

                Plan plan = switch (tipo) {
                    case "PlanPostPagoMinutos" ->
                        new PlanPostPagoMinutos(
                        rs.getInt("minutos"),
                        rs.getDouble("costo_minuto"),
                        rs.getInt("minutos_int"),
                        rs.getDouble("costo_minuto_int")
                        );
                    case "PlanPostPagoMegas" ->
                        new PlanPostPagoMegas(
                        rs.getDouble("gigas"),
                        rs.getDouble("costo_giga"),
                        rs.getDouble("tarifa_base")
                        );
                    case "PlanPostPagoMinutosMegas" ->
                        new PlanPostPagoMinutosMegas(
                        rs.getInt("minutos"),
                        rs.getDouble("costo_minuto"),
                        rs.getDouble("gigas"),
                        rs.getDouble("costo_giga")
                        );
                    case "PlanPostPagoMinutosMegasEconomico" ->
                        new PlanPostPagoMinutosMegasEconomico(
                        rs.getInt("minutos"),
                        rs.getDouble("costo_minuto"),
                        rs.getDouble("gigas"),
                        rs.getDouble("costo_giga"),
                        rs.getDouble("descuento")
                        );
                    default ->
                        null;
                };

                if (plan != null) {
                    plan.setId(rs.getInt("id"));
                    plan.setDescripcion(rs.getString("descripcion"));
                }

                return plan;
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar plan por ID: " + e.getMessage());
        }

        return null;
    }

}
