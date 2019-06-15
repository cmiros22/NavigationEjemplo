package com.example.navigationejemplo.Recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigationejemplo.R;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderDatos> {

    ArrayList<DatosRecientes> listDatos;

    public RecyclerAdapter(ArrayList<DatosRecientes> listDatos) {
        this.listDatos = listDatos;
    }

    //Enlazar Adaptador con los item que se quiera mostrar
    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewcard,parent,false);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {
        holder.etiNombre.setText(listDatos.get(position).getNombre());
        holder.etiCic.setText(listDatos.get(position).getCic());
        holder.etiInformacion.setText(listDatos.get(position).getInfo());
        holder.foto.setImageResource(listDatos.get(position).getFoto());
    }

    @Override
    public int getItemCount() {

        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        TextView etiNombre, etiInformacion, etiCic;
        ImageView foto;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            etiNombre=(TextView)itemView.findViewById(R.id.idNombre);
            etiCic=(TextView)itemView.findViewById(R.id.idCIC);
            etiInformacion=(TextView)itemView.findViewById(R.id.idInfo);
            foto=(ImageView)itemView.findViewById(R.id.idImagen);
        }

        public void asignarDatos(String datos) {

        }
    }
}
