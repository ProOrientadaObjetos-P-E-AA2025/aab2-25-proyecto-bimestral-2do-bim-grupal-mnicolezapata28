package modelo;

public class PlanPostPagoMegas extends Plan {

    private double gigas;
    private double costoPorGiga;
    private double tarifaBase;

    public PlanPostPagoMegas(double gigas, double costoPorGiga, double tarifaBase) {
        super("Postpago Megas");
        this.gigas = gigas;
        this.costoPorGiga = costoPorGiga;
        this.tarifaBase = tarifaBase;
    }

    @Override
    public double calcularPago() {
        return tarifaBase + (gigas * costoPorGiga);
    }

    @Override
    public String toString() {
        return descripcion + ": " + calcularPago();
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

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }
    
}
