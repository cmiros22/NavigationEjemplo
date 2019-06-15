package com.example.navigationejemplo.Entidades;

public class Visitantes {
    int id_Visitante;
    String Nombre;
    String ApPat;
    String ApMat;
    String Motivo;

    public Visitantes(){

    }

    public Visitantes(int id_Visitante, String nombre, String apPat, String apMat, String motivo) {
        this.id_Visitante = id_Visitante;
        Nombre = nombre;
        ApPat = apPat;
        ApMat = apMat;
        Motivo = motivo;
    }

    public int getId_Visitante() {
        return id_Visitante;
    }

    public void setId_Visitante(int id_Visitante) {
        this.id_Visitante = id_Visitante;
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

    public String getMotivo() {
        return Motivo;
    }

    public void setMotivo(String motivo) {
        Motivo = motivo;
    }
}
