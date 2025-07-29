package modelo;

public class PlanPostPagoMinutosMegasEconomico extends Plan {

    private int minutos;
    private double costoMinuto;
    private double gigas;
    private double costoPorGiga;
    private double descuento; // en porcentaje

    public PlanPostPagoMinutosMegasEconomico(int minutos, double costoMinuto, double gigas, double costoPorGiga, double descuento) {
        super("Postpago Económico");
        this.minutos = minutos;
        this.costoMinuto = costoMinuto;
        this.gigas = gigas;
        this.costoPorGiga = costoPorGiga;
        this.descuento = descuento;
    }

    @Override
    public double calcularPago() {
        double total = (minutos * costoMinuto) + (gigas * costoPorGiga);
        return total - (total * descuento / 100);
    }

    @Override
    public String toString() {
        return descripcion + ": " + calcularPago();
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public double getCostoMinuto() {
        return costoMinuto;
    }

    public void setCostoMinuto(double costoMinuto) {
        this.costoMinuto = costoMinuto;
    }

    public double getGigas() {
        return gigas;
    }

    public void setGigas(double gigas) {
        this.gigas = gigas;
    }

    public double getCostoPorGiga() {
        return costoPorGiga;
    }

    public void setCostoPorGiga(double costoPorGiga) {
        this.costoPorGiga = costoPorGiga;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

}
