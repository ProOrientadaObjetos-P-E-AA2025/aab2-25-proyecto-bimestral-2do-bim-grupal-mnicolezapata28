package dao;

import modelo.Cliente;
import modelo.Dispositivo;
import util.ConexionSQLite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public boolean guardar(Cliente cliente) {
        String sql = """
        INSERT INTO cliente (nombres, cedula_pasaporte, ciudad, correo, marca, modelo, numero)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, cliente.getNombres());
            pstmt.setString(2, cliente.getCedulaPasaporte());
            pstmt.setString(3, cliente.getCiudad());
            pstmt.setString(4, cliente.getCorreo());

            Dispositivo d = cliente.getDispositivo();
            pstmt.setString(5, d.getMarca());
            pstmt.setString(6, d.getModelo());
            pstmt.setString(7, d.getNumero());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getInt(1));
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar cliente: " + e.getMessage());
        }

        return false;
    }

    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conn = ConexionSQLite.conectar(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Dispositivo d = new Dispositivo(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("numero")
                );

                Cliente c = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("cedula_pasaporte"),
                        rs.getString("ciudad"),
                        rs.getString("correo"),
                        d
                );

                clientes.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    public boolean actualizar(Cliente cliente) {
        String sql = """
        UPDATE cliente
        SET nombres = ?, cedula_pasaporte = ?, ciudad = ?, correo = ?, marca = ?, modelo = ?, numero = ?
        WHERE id = ?
        """;

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombres());
            pstmt.setString(2, cliente.getCedulaPasaporte());
            pstmt.setString(3, cliente.getCiudad());
            pstmt.setString(4, cliente.getCorreo());

            Dispositivo d = cliente.getDispositivo();
            pstmt.setString(5, d.getMarca());
            pstmt.setString(6, d.getModelo());
            pstmt.setString(7, d.getNumero());

            pstmt.setInt(8, cliente.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
        }

        return false;
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }

        return false;
    }

    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (Connection conn = ConexionSQLite.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombres"),
                        rs.getString("cedula_pasaporte"),
                        rs.getString("ciudad"),
                        rs.getString("correo"),
                        new Dispositivo(
                                rs.getString("marca"),
                                rs.getString("modelo"),
                                rs.getString("numero")
                        )
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
        }
        return null;
    }

}
