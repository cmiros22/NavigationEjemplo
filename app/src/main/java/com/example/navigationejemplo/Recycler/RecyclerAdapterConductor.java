package com.example.navigationejemplo.Recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navigationejemplo.R;

import java.util.ArrayList;


public class RecyclerAdapterConductor extends RecyclerView.Adapter<RecyclerAdapterConductor.ViewHolderDatos> {

    ArrayList<DatosConductor> listDatos;


    public RecyclerAdapterConductor(ArrayList<DatosConductor> listDatos) {
        this.listDatos = listDatos;
    }

    //Enlazar Adaptador con los item que se quiera mostrar
    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_conductor,parent,false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {
        holder.etiNombre.setText(listDatos.get(position).getNombreCompleto());
        holder.etiTipo.setText(listDatos.get(position).getTipoConductor());
        //holder.etiTipo.setText(listDatos.get(position).getTipo());
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        TextView etiNombre, etiTipo;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            etiNombre=(TextView)itemView.findViewById(R.id.idNombreConductor);
            etiTipo=(TextView)itemView.findViewById(R.id.idTipoConductor);
        }

        public void asignarDatos(String datos) {

        }
    }

}
