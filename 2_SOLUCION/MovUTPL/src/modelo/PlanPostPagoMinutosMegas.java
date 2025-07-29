package modelo;

public class PlanPostPagoMinutosMegas extends Plan {
    private int minutos;
    private double costoMinuto;
    private double gigas;
    private double costoPorGiga;

    public PlanPostPagoMinutosMegas(int minutos, double costoMinuto, double gigas, double costoPorGiga) {
        super("Postpago Minutos + Megas");
        this.minutos = minutos;
        this.costoMinuto = costoMinuto;
        this.gigas = gigas;
        this.costoPorGiga = costoPorGiga;
    }

    @Override
    public double calcularPago() {
        return (minutos * costoMinuto) + (gigas * costoPorGiga);
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
    
    
}
