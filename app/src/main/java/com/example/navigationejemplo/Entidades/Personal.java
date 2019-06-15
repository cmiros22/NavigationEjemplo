package com.example.navigationejemplo.Entidades;

public class Personal {
    String N_Trabajador;
    String Nombre;
    String ApPat;
    String ApMat;

    public Personal(String n_Trabajador, String nombre, String apPat, String apMat) {
        N_Trabajador = n_Trabajador;
        Nombre = nombre;
        ApPat = apPat;
        ApMat = apMat;
    }

    public String getN_Trabajador() {
        return N_Trabajador;
    }

    public void setN_Trabajador(String n_Trabajador) {
        N_Trabajador = n_Trabajador;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApPat() {
        return ApPat;
    }

    public void setApPat(String apPat) {
        ApPat = apPat;
    }

    public String getApMat() {
        return ApMat;
    }

    public void setApMat(String apMat) {
        ApMat = apMat;
    }
}
