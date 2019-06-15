package com.example.navigationejemplo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.navigationejemplo.Recycler.DatosRecientes;
import com.example.navigationejemplo.Recycler.RecyclerAdapter;
import com.example.navigationejemplo.Utilidades.ConstantesBD;

import java.util.ArrayList;


public class FragmentRecientes extends Fragment {

    ArrayList<DatosRecientes> listDatos;
    RecyclerView recycler;

    ConexionSQLiteHelper conn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_fragment_recientes, container, false);

        conn=new ConexionSQLiteHelper(getContext(), "BdCDA", null, ConstantesBD.Version);

        listDatos=new ArrayList<>();
        recycler=view.findViewById(R.id.Recyclerid);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        consultar();
        RecyclerAdapter adapter=new RecyclerAdapter(listDatos);
        recycler.setAdapter(adapter);
        return view;

    }

    private void consultar() {
        boolean Exists=false;
        SQLiteDatabase db=conn.getReadableDatabase();
        DatosRecientes datosRecientes=null;
        Cursor cursor=db.rawQuery("SELECT "+ ConstantesBD.columnNombreV+","+ConstantesBD.columnApPatV+","+ConstantesBD.columnApMatV+","
                +ConstantesBD.columnid_Visitante+","+ConstantesBD.columnMotivo+","+ConstantesBD.columnTVehiculoVis+" FROM "+ ConstantesBD.Table_Visitantes,null);

        while (cursor.moveToNext()){
            datosRecientes=new DatosRecientes();
            datosRecientes.setNombre(cursor.getString(0));
            datosRecientes.setNombre(datosRecientes.getNombre()+" "+cursor.getString(1)+" ");
            datosRecientes.setNombre(datosRecientes.getNombre()+cursor.getString(2));
            datosRecientes.setCic("CIC: "+cursor.getString(3));
            datosRecientes.setInfo("Motivo: "+cursor.getString(4));
            int TV=cursor.getInt(5);
            if(TV==1){
                datosRecientes.setFoto(R.drawable.ic_icono_auto);
            }else{
                datosRecientes.setFoto(R.drawable.ic_icono_moto);
            }

            listDatos.add(datosRecientes);
            Exists=true;
        }

        Cursor cursor1=db.rawQuery("SELECT "+ConstantesBD.columnNombreC+","+ConstantesBD.columnApePat+","+ConstantesBD.columnApeMat+","+
                ConstantesBD.columnIDConductores+","+ConstantesBD.columnTConductor+","+ConstantesBD.columnTVehiculo+
                " FROM "+ConstantesBD.Table_Conductor,null);

        while (cursor1.moveToNext()){
            datosRecientes=new DatosRecientes();
            datosRecientes.setNombre(cursor1.getString(0));
            datosRecientes.setNombre(datosRecientes.getNombre()+" "+cursor1.getString(1)+" ");
            datosRecientes.setNombre(datosRecientes.getNombre()+ cursor1.getString(2));
            datosRecientes.setCic("N. Tarjeron: "+cursor1.getString(3));
            int TC=cursor1.getInt(4);
            if(TC==1){
                datosRecientes.setInfo("Tipo: Estudiante");
            }else{
                datosRecientes.setInfo("Tipo: Personal");
            }
            int TV=cursor1.getInt(5);
            Toast.makeText(getContext(),"TVVVV: "+TV,Toast.LENGTH_SHORT).show();
            if(TV==1){
                datosRecientes.setFoto(R.drawable.ic_icono_auto);
            }else{
                datosRecientes.setFoto(R.drawable.ic_icono_moto);
            }

            listDatos.add(datosRecientes);
            Exists=true;
        }

        if(!Exists){
            openDialogEmpty();
        }
    }

    private void openDialogEmpty(){
        DialogoRecientesEmpty dialogoRecientesEmpty=new DialogoRecientesEmpty();
        dialogoRecientesEmpty.show(getFragmentManager(),"Mensaje de Advertencia");
    }
}
