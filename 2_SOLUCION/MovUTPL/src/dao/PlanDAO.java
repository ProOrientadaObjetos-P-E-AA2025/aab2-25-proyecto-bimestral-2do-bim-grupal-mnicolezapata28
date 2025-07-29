package dao;

import modelo.*;
import util.ConexionSQLite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {

    public int guardar(Plan plan) {
        String sql = """
            INSERT INTO plan (
                tipo, descripcion, minutos, costo_minuto, minutos_int, costo_minuto_int,
                gigas, costo_giga, tarifa_base, descuento
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, plan.getClass().getSimpleName());
            pstmt.setString(2, plan.getDescripcion());

            if (plan instanceof PlanPostPagoMinutos p) {
                pstmt.setInt(3, p.getMinNacionales());
                pstmt.setDouble(4, p.getCostoMinNacional());
                pstmt.setInt(5, p.getMinInternacionales());
                pstmt.setDouble(6, p.getCostoMinInternacional());
                pstmt.setNull(7, Types.REAL);
                pstmt.setNull(8, Types.REAL);
                pstmt.setNull(9, Types.REAL);
                pstmt.setNull(10, Types.REAL);
            } else if (plan instanceof PlanPostPagoMegas p) {
                pstmt.setNull(3, Types.INTEGER);
                pstmt.setNull(4, Types.REAL);
                pstmt.setNull(5, Types.INTEGER);
                pstmt.setNull(6, Types.REAL);
                pstmt.setDouble(7, p.getGigas());
                pstmt.setDouble(8, p.getCostoPorGiga());
                pstmt.setDouble(9, p.getTarifaBase());
                pstmt.setNull(10, Types.REAL);
            } else if (plan instanceof PlanPostPagoMinutosMegas p) {
                pstmt.setInt(3, p.getMinutos());
                pstmt.setDouble(4, p.getCostoMinuto());
                pstmt.setNull(5, Types.INTEGER);
                pstmt.setNull(6, Types.REAL);
                pstmt.setDouble(7, p.getGigas());
                pstmt.setDouble(8, p.getCostoPorGiga());
                pstmt.setNull(9, Types.REAL);
                pstmt.setNull(10, Types.REAL);
            } else if (plan instanceof PlanPostPagoMinutosMegasEconomico p) {
                pstmt.setInt(3, p.getMinutos());
                pstmt.setDouble(4, p.getCostoMinuto());
                pstmt.setNull(5, Types.INTEGER);
                pstmt.setNull(6, Types.REAL);
                pstmt.setDouble(7, p.getGigas());
                pstmt.setDouble(8, p.getCostoPorGiga());
                pstmt.setNull(9, Types.REAL);
                pstmt.setDouble(10, p.getDescuento());
            }

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                plan.setId(id);
                return id;
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar plan: " + e.getMessage());
        }

        return -1;
    }

    public boolean asignarPlanACliente(int clienteId, int planId) {
        String sql = "INSERT INTO cliente_plan (cliente_id, plan_id) VALUES (?, ?)";

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            pstmt.setInt(2, planId);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al asignar plan a cliente: " + e.getMessage());
        }
        return false;
    }

    public List<Plan> obtenerPlanesDeCliente(int clienteId) {
        List<Plan> planes = new ArrayList<>();

        String sql = """
            SELECT p.*
            FROM plan p
            INNER JOIN cliente_plan cp ON p.id = cp.plan_id
            WHERE cp.cliente_id = ?
        """;

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
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
                    planes.add(plan);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener planes: " + e.getMessage());
        }

        return planes;
    }

    public Plan buscarPlanPorId(int id) {
        String sql = "SELECT * FROM plan WHERE id = ?";

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
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
                    plan.setId(id);
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
