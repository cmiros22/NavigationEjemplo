package com.example.navigationejemplo.Recycler;

public class DatosRecientes {
    private String nombre;
    private String cic;
    private String info;
    private int foto;

    public DatosRecientes(){

    }

    public DatosRecientes(String nombre, String cic, String info, int foto) {
        this.nombre = nombre;
        this.cic=cic;
        this.info = info;
        this.foto = foto;
    }

    public String getNombre() {

        return nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
    }

    public String getCic() {

        return cic;
    }

    public void setCic(String cic) {

        this.cic = cic;
    }

    public String getInfo() {

        return info;
    }

    public void setInfo(String info) {

        this.info = info;
    }

    public int getFoto() {

        return foto;
    }

    public void setFoto(int foto) {

        this.foto = foto;
    }
}
