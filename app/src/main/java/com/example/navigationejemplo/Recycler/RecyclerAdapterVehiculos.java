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


public class RecyclerAdapterVehiculos extends RecyclerView.Adapter<RecyclerAdapterVehiculos.ViewHolderDatos> implements View.OnClickListener {

    ArrayList<DatosVehiculo> listDatosVehiculos;
    private View.OnClickListener listener;


    public RecyclerAdapterVehiculos(ArrayList<DatosVehiculo> listDatosVehiculos) {
        this.listDatosVehiculos = listDatosVehiculos;
    }

    //Enlazar Adaptador con los item que se quiera mostrar
    @Override
    public ViewHolderDatos onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_vehiculos,parent,false);
        view.setOnClickListener(this);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderDatos holder, int position) {
        //holder.etiNombre.setText(listDatos.get(position).getNombre());
        holder.etiMatricula.setText(listDatosVehiculos.get(position).getMatricula());
        //holder.etiModelo.setText(listDatos.get(position).getInfo());
        holder.etiModelo.setText(listDatosVehiculos.get(position).getModelo());
        //holder.etiColor.setText(listDatos.get(position).getNombre());
        holder.etiColor.setText(listDatosVehiculos.get(position).getColor());
        //holder.foto.setImageResource(listDatos.get(position).getFoto());
        //holder.foto.setImageResource(listDatos.get(position).getFoto());
        int Foto=listDatosVehiculos.get(position).getFoto();
        if(Foto==1){
            holder.foto.setImageResource(R.drawable.ic_icono_auto);
        }else if(Foto==2){
            holder.foto.setImageResource(R.drawable.ic_icono_moto);
        }
    }

    @Override
    public int getItemCount() {
        return listDatosVehiculos.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }


    public class ViewHolderDatos extends RecyclerView.ViewHolder{
        TextView etiMatricula, etiModelo, etiColor;
        ImageView foto;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            etiMatricula=(TextView)itemView.findViewById(R.id.idMatriculaInternos);
            etiModelo=(TextView)itemView.findViewById(R.id.idModeloInternos);
            etiColor=(TextView)itemView.findViewById(R.id.idColorInternos);
            foto=(ImageView)itemView.findViewById(R.id.idTipoInternos);
        }

        public void asignarDatos(String datos) {

        }
    }
}
