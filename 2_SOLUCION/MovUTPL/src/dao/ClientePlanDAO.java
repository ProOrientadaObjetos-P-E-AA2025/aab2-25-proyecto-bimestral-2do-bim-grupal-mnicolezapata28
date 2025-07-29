package dao;

import util.ConexionSQLite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientePlanDAO {

    public boolean asignarPlan(int clienteId, int planId) {
        if (contarPlanesPorCliente(clienteId) >= 2) {
            System.out.println("El cliente ya tiene 2 planes asignados.");
            return false;
        }

        String sql = """
            INSERT INTO cliente_plan (cliente_id, plan_id)
            VALUES (?, ?)
        """;

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            pstmt.setInt(2, planId);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al asignar plan: " + e.getMessage());
        }

        return false;
    }

    public int contarPlanesPorCliente(int clienteId) {
        String sql = "SELECT COUNT(*) FROM cliente_plan WHERE cliente_id = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al contar planes: " + e.getMessage());
        }

        return 0;
    }

    public List<Integer> obtenerIdsPlanesDeCliente(int clienteId) {
        List<Integer> planIds = new ArrayList<>();
        String sql = "SELECT plan_id FROM cliente_plan WHERE cliente_id = ?";

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                planIds.add(rs.getInt("plan_id"));
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener planes del cliente: " + e.getMessage());
        }

        return planIds;
    }

    public boolean eliminarPlanesDeCliente(int clienteId) {
        String sql = "DELETE FROM cliente_plan WHERE cliente_id = ?";

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar planes del cliente: " + e.getMessage());
        }

        return false;
    }

    public boolean existeRelacion(int clienteId, int planId) {
        String sql = "SELECT 1 FROM cliente_plan WHERE cliente_id = ? AND plan_id = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clienteId);
            pstmt.setInt(2, planId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Error al verificar relación cliente-plan: " + e.getMessage());
        }

        return false;
    }

}
