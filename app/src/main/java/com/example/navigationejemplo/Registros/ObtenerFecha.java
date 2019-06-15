package com.example.navigationejemplo.Registros;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ObtenerFecha {

    public String FechaActual(){
        Calendar c=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String fecha=sdf.format(c.getTime());

        return fecha;
    }

}
