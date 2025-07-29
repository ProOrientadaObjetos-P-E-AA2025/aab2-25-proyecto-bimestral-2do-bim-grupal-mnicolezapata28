package modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private int id;
    private String nombres;
    private String cedulaPasaporte;
    private String ciudad;
    private String correo;
    private Dispositivo dispositivo;
    private List<Plan> planes;

    public Cliente(String nombres, String cedulaPasaporte, String ciudad, String correo, Dispositivo dispositivo) {
        this.nombres = nombres;
        this.cedulaPasaporte = cedulaPasaporte;
        this.ciudad = ciudad;
        this.correo = correo;
        this.dispositivo = dispositivo;
        this.planes = new ArrayList<>();
    }

    public Cliente(int id, String nombres, String cedulaPasaporte, String ciudad, String correo, Dispositivo dispositivo) {
        this.id = id;
        this.nombres = nombres;
        this.cedulaPasaporte = cedulaPasaporte;
        this.ciudad = ciudad;
        this.correo = correo;
        this.dispositivo = dispositivo;
        this.planes = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public String getCedulaPasaporte() {
        return cedulaPasaporte;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCorreo() {
        return correo;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public List<Plan> getPlanes() {
        return planes;
    }

    public void agregarPlan(Plan plan) {
        if (planes.size() < 2) {
            planes.add(plan);
        }
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setCedulaPasaporte(String cedulaPasaporte) {
        this.cedulaPasaporte = cedulaPasaporte;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public void setPlanes(List<Plan> planes) {
        this.planes = planes;
    }

    
    @Override
    public String toString() {
        return nombres + " | " + cedulaPasaporte + " | " + ciudad + " | " + dispositivo.toString();
    }
}
