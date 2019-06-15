package com.example.navigationejemplo.Registros;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ObtenerHora {

    public String HoraActual(){
        Calendar c=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
        String hora=sdf.format(c.getTime());

        return hora;
    }

}
