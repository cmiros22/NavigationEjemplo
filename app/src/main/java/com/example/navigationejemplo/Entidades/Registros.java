package com.example.navigationejemplo.Entidades;

public class Registros {
    int id_Registro;
    String Fecha;
    String Hora_Entrada;
    String Hora_Salida;

    public Registros(int id_Registro, String fecha, String hora_Entrada, String hora_Salida) {
        this.id_Registro = id_Registro;
        Fecha = fecha;
        Hora_Entrada = hora_Entrada;
        Hora_Salida = hora_Salida;
    }

    public int getId_Registro() {
        return id_Registro;
    }

    public void setId_Registro(int id_Registro) {
        this.id_Registro = id_Registro;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora_Entrada() {
        return Hora_Entrada;
    }

    public void setHora_Entrada(String hora_Entrada) {
        Hora_Entrada = hora_Entrada;
    }

    public String getHora_Salida() {
        return Hora_Salida;
    }

    public void setHora_Salida(String hora_Salida) {
        Hora_Salida = hora_Salida;
    }

}
