package com.example.navigationejemplo.Entidades;

public class Vehiculos {
    String Matricula;
    String Marca;
    String Modelo;
    String Color;
    int TipoVehiculo;

    public Vehiculos(String matricula, String marca, String modelo, String color, int tipoVehiculo) {
        Matricula = matricula;
        Marca = marca;
        Modelo = modelo;
        Color = color;
        TipoVehiculo = tipoVehiculo;
    }

    public Vehiculos(){

    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String marca) {
        Marca = marca;
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

    public int getTipoVehiculo() {
        return TipoVehiculo;
    }

    public void setTipoVehiculo(int tipoVehiculo) {
        TipoVehiculo = tipoVehiculo;
    }
}
