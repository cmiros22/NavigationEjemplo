package com.example.navigationejemplo.Recycler;

public class DatosVehiculo {
    private String Matricula;
    private String Modelo;
    private String Color;
    private int Foto;

    public DatosVehiculo(String matricula, String modelo, String color, int foto) {
        Matricula = matricula;
        Modelo = modelo;
        Color = color;
        Foto = foto;
    }

    public DatosVehiculo(){

    }

    public int getFoto() {
        return Foto;
    }

    public void setFoto(int foto) {
        Foto = foto;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getModelo() {
        return Modelo;
    }

    public void setModelo(String modelo) {
        Modelo = modelo;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }
}
