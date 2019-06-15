package com.example.navigationejemplo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class DialogoSalidaRegistrada extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder dialogo=new AlertDialog.Builder(getActivity());
        dialogo.setTitle("Registro de Salida").setMessage("Fecha: "+TabSalida.fecha+"\n\nHora: "+TabSalida.hora).setIcon(R.drawable.ic_icon_listo);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TabInternos.txtBuscar.setText("");
            }
        });
        return dialogo.create();
    }

}
