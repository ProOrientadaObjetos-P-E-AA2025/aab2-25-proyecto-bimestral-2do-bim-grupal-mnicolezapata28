package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionSQLite {

    private static final String URL = "jdbc:sqlite:db/movutpl.db";

    public static Connection conectar() {
        try {
            Connection conn = DriverManager.getConnection(URL);
            crearTablas(conn);
            return conn;
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }

    private static void crearTablas(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS cliente (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombres TEXT NOT NULL,
                cedula_pasaporte TEXT NOT NULL,
                ciudad TEXT NOT NULL,
                correo TEXT NOT NULL,
                marca TEXT NOT NULL,
                modelo TEXT NOT NULL,
                numero TEXT NOT NULL
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS plan (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                tipo TEXT NOT NULL,
                descripcion TEXT,
                minutos INTEGER,            
                costo_minuto REAL,
                minutos_int INTEGER,
                costo_minuto_int REAL,
                gigas REAL,
                costo_giga REAL,
                tarifa_base REAL,
                descuento REAL
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS cliente_plan (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cliente_id INTEGER NOT NULL,
                plan_id INTEGER NOT NULL,
                FOREIGN KEY (cliente_id) REFERENCES cliente(id),
                FOREIGN KEY (plan_id) REFERENCES plan(id)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS factura (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cliente_id INTEGER NOT NULL,
                plan_id INTEGER NOT NULL,
                fecha TEXT NOT NULL,
                valor_total REAL NOT NULL,
                FOREIGN KEY (cliente_id) REFERENCES cliente(id),
                FOREIGN KEY (plan_id) REFERENCES plan(id)
            );
        """);

        stmt.close();
    }
}
