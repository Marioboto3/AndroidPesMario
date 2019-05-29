package com.example.androidpesmario;

public class Apuesta {

    private int idPartido;
    private String pronostico;
    private String importe;
    private String username;

    public Apuesta() {
    }

    public Apuesta(int idPartido, String pronostico, String importe, String username) {
        this.idPartido = idPartido;
        this.pronostico = pronostico;
        this.importe = importe;
        this.username = username;
    }

    public int getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(int idPartido) {
        this.idPartido = idPartido;
    }

    public String getPronostico() {
        return pronostico;
    }

    public void setPronostico(String pronostico) {
        this.pronostico = pronostico;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
