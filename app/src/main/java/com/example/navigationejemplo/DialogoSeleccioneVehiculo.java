package com.example.navigationejemplo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;

public class DialogoSeleccioneVehiculo extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Advertencia").setMessage("Seleccionar Vehiculo").setIcon(R.drawable.ic_icono_info).setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TabInternos.txtBuscar.requestFocus();
            }
        });
        return builder.create();
    }

}
