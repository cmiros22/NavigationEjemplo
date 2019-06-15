package com.example.navigationejemplo.Recycler;

public class DatosConductor {
    private String NombreCompleto;
    private String TipoConductor;

    public DatosConductor(String nombreCompleto) {
        NombreCompleto = nombreCompleto;
    }

    public DatosConductor(){

    }

    public String getTipoConductor() {
        return TipoConductor;
    }

    public void setTipoConductor(String tipoConductor) {
        TipoConductor = tipoConductor;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        NombreCompleto = nombreCompleto;
    }
}
