package modelo;

public class Factura {

    private int id;
    private String fecha;
    private double valorTotal;
    private int clienteId;
    private String clienteNombre;
    private String cedula;
    private Plan plan;

    public Factura(int clienteId, String fecha, double valorTotal, Plan plan) {
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.valorTotal = valorTotal;
        this.plan = plan;
    }

    public Factura(int id, String fecha, double valorTotal, String clienteNombre, String cedula, Plan plan) {
        this.id = id;
        this.fecha = fecha;
        this.valorTotal = valorTotal;
        this.clienteNombre = clienteNombre;
        this.cedula = cedula;
        this.plan = plan;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public int getClienteId() {
        return clienteId;
    }

    public Plan getPlan() {
        return plan;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "Factura #" + id + " | Cliente: " + clienteNombre + " [" + cedula + "]"
                + "\nFecha: " + fecha
                + "\nPlan: " + (plan != null ? plan.toString() : "Desconocido")
                + "\nTotal: $" + valorTotal;
    }
}
