package modelo;

public class PlanPostPagoMinutos extends Plan {

    private int minNacionales;
    private double costoMinNacional;
    private int minInternacionales;
    private double costoMinInternacional;

    public PlanPostPagoMinutos(int minNacionales, double costoMinNacional, int minInternacionales, double costoMinInternacional) {
        super("Postpago Minutos");
        this.minNacionales = minNacionales;
        this.costoMinNacional = costoMinNacional;
        this.minInternacionales = minInternacionales;
        this.costoMinInternacional = costoMinInternacional;
    }

    @Override
    public double calcularPago() {
        return (minNacionales * costoMinNacional) + (minInternacionales * costoMinInternacional);
    }

    @Override
    public String toString() {
        return descripcion + ": " + calcularPago();
    }

    public int getMinNacionales() {
        return minNacionales;
    }

    public void setMinNacionales(int minNacionales) {
        this.minNacionales = minNacionales;
    }

    public double getCostoMinNacional() {
        return costoMinNacional;
    }

    public void setCostoMinNacional(double costoMinNacional) {
        this.costoMinNacional = costoMinNacional;
    }

    public int getMinInternacionales() {
        return minInternacionales;
    }

    public void setMinInternacionales(int minInternacionales) {
        this.minInternacionales = minInternacionales;
    }

    public double getCostoMinInternacional() {
        return costoMinInternacional;
    }

    public void setCostoMinInternacional(double costoMinInternacional) {
        this.costoMinInternacional = costoMinInternacional;
    }
    
    
}
