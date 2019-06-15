package com.example.navigationejemplo.Entidades;

public class Conductor {
    String Nombre;
    String ApPat;
    String ApMat;
    int Tipo;

    public Conductor(){

    }

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
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
