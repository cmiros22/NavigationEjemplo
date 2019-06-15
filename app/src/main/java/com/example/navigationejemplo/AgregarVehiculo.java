package com.example.navigationejemplo;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigationejemplo.Utilidades.ConstantesServidor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AgregarVehiculo extends Fragment {
    public static TextView txtResultado;

    private EditText txtResul;
    private EditText txtMatricula;
    private EditText txtMarca;
    private EditText txtModelo;
    private EditText txtColor;

    private Button btnGuardar;
    private Button btnBuscar;
    private Spinner spinner;

    private Dialog epicDialog;
    private TextView titleTv;
    private Button botonAceptar;

    private RequestQueue queue;
    private RequestQueue queue2;

    private static int TipoVehiculo=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_nuevo_vehiculo,container,false);

        spinner=(Spinner)view.findViewById(R.id.spinnerav);
        List<String> tiposvehiculos=new ArrayList<>();
        tiposvehiculos.add(0,"Seleccione Tipo Vehiculo");
        tiposvehiculos.add("Automovil");
        tiposvehiculos.add("Motocicleta");
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter=new ArrayAdapter(getContext(),R.layout.spinner_item_vehiculos, tiposvehiculos);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Seleccione Tipo Vehiculo")){

                }else{
                    int item=parent.getSelectedItemPosition();
                    TipoVehiculo=item;
                    Toast.makeText(parent.getContext(),"Seleccionador: "+item,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        epicDialog = new Dialog(getActivity());

        queue= Volley.newRequestQueue(getContext());
        queue2= Volley.newRequestQueue(getContext());

        txtResul=(EditText)view.findViewById(R.id.txtResultado);
        txtResul.setRawInputType(InputType.TYPE_CLASS_NUMBER);

        txtMatricula=(EditText)view.findViewById(R.id.txtMatricula);
        //txtMatricula.setBackgroundColor(Color.TRANSPARENT);
        txtMarca=(EditText)view.findViewById(R.id.txtMarca);
        txtModelo=(EditText)view.findViewById(R.id.txtModelo);
        txtColor=(EditText)view.findViewById(R.id.txtColor);

        DisableEdit();

        btnBuscar=(Button)view.findViewById(R.id.btnBuscar);
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuscarConductor();
            }
        });


        btnGuardar=(Button)view.findViewById(R.id.btnGu);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Click Guardar AGV",Toast.LENGTH_SHORT).show();
                //EnableEdit();
                AgregarVehiculo();
            }
        });

        return view;
    }

    private void BuscarConductor(){
        String url="http://192.168.1.77/proyecto/buscarConductor.php?Codigo="+txtResul.getText().toString();

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=response.getJSONObject("EncontradoC");
                    String status=jsonObject.getString("Status");

                    if (status.equals("FOUNDOK")){
                        openDialogEncontrado();
                        EnableEdit();
                        spinner.requestFocus();
                    }else{
                        openDialogNoEncontrado();
                        DisableEdit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR CONDUCTOR: "+error.toString());
                Toast.makeText(getContext(),"ERROR CONDUCTOR: "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    private void AgregarVehiculo(){
        String url="http://"+ ConstantesServidor.Raiz +"/agregarVehiculo.php?Codigo="+txtResul.getText().toString()
                +"&Matricula="+txtMatricula.getText().toString()+"&Marca="+txtMarca.getText().toString()
                +"&Modelo="+txtModelo.getText().toString()+"&Color="+txtColor.getText().toString()+"&TipoV="+TipoVehiculo;

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final JSONObject jsonObject=response.getJSONObject("VAgregado");
                    String status=jsonObject.getString("Estado");
                    if (status.equals("REGISTROOK")){
                        Toast.makeText(getContext(),"VEHICULO REGISTRADO OK",Toast.LENGTH_SHORT).show();
                        showAddVehiclePopup();
                        VaciarCampos();
                    }else{
                        Toast.makeText(getContext(),"ERROR AL REGISTRAR",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue2.add(request);
    }

    private void DisableEdit(){
        txtMatricula.setEnabled(false);
        txtMarca.setEnabled(false);
        txtModelo.setEnabled(false);
        txtColor.setEnabled(false);
    }

    private void EnableEdit(){
        txtMatricula.setEnabled(true);
        txtMarca.setEnabled(true);
        txtModelo.setEnabled(true);
        txtColor.setEnabled(true);
    }

    private void VaciarCampos(){
        txtResul.setText("");
        spinner.setSelection(0);
        txtMatricula.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtColor.setText("");
        txtResul.requestFocus();
    }

    private void openDialogNoEncontrado(){
        DialogoNoEncontradoConductor dialogoNoEncontradoConductor=new DialogoNoEncontradoConductor();
        dialogoNoEncontradoConductor.show(getFragmentManager(),"Conductor No Encontrado");
    }

    private void openDialogEncontrado(){
        DialogoEncontradoConductor dialogoEncontradoConductor=new DialogoEncontradoConductor();
        dialogoEncontradoConductor.show(getFragmentManager(),"Conductor Encontrado");
    }

    public void showAddVehiclePopup(){
        epicDialog.setContentView(R.layout.popup_vagregado);
        botonAceptar = (Button) epicDialog.findViewById(R.id.PbtnAccept);
        titleTv = (TextView) epicDialog.findViewById(R.id.titleTV);
        botonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
}
