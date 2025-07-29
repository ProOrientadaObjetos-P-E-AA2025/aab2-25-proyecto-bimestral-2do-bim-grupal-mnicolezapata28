package modelo;

public class Dispositivo {

    private String marca;
    private String modelo;
    private String numero;

    public Dispositivo(String marca, String modelo, String numero) {
        this.marca = marca;
        this.modelo = modelo;
        this.numero = numero;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getNumero() {
        return numero;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return marca + " " + modelo + " - " + numero;
    }
}
