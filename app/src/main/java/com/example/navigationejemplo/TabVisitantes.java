package com.example.navigationejemplo;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigationejemplo.Registros.ObtenerFecha;
import com.example.navigationejemplo.Registros.ObtenerHora;
import com.example.navigationejemplo.Utilidades.ConstantesBD;
import com.example.navigationejemplo.Utilidades.ConstantesServidor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabVisitantes extends Fragment{
    private RequestQueue queue;
    private RequestQueue queueE;
    private RequestQueue queueR;

    //public static EditText CIC;
    private final int REQUEST_ACCESS_FINE=0;
    private Button VbtnGu;
    private Dialog epicDialog;
    private TextView titleTv;
    private Button botonAceptar;

    //Vistas del Tab Visitantes
    public static EditText Cic;
    private EditText txtNombre;
    private EditText txtApPat;
    private EditText txtApMat;
    private EditText txtMotivo;
    private ImageView imgScan;

    private Spinner spinner;
    private EditText txtMarca;
    private EditText txtModelo;
    private EditText txtMatricula;
    private EditText txtColor;
    private static int TipoVehiculo=0;

    ObtenerFecha obtenerFecha=new ObtenerFecha();
    ObtenerHora obtenerHora=new ObtenerHora();
    private int codigo,idp;
    private String matricula;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_visitantes,container,false);

        //Construccion Vistas Tab Visitantes
        Cic=(EditText)view.findViewById(R.id.Cic);
        Cic.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        txtNombre=(EditText)view.findViewById(R.id.txtNombre);
        txtApPat=(EditText)view.findViewById(R.id.txtApPat);
        txtApMat=(EditText)view.findViewById(R.id.txtApMat);
        txtMotivo=(EditText)view.findViewById(R.id.txtMotivo);
        txtMarca=(EditText)view.findViewById(R.id.txtMarca);
        txtModelo=(EditText)view.findViewById(R.id.txtModelo);
        txtMatricula=(EditText)view.findViewById(R.id.txtMatricula);
        txtColor=(EditText)view.findViewById(R.id.txtColor);
        imgScan=(ImageView)view.findViewById(R.id.imgScan);

        spinner=(Spinner)view.findViewById(R.id.spinnerv);
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


        VbtnGu=(Button) view.findViewById(R.id.VbtnGu);
        botonAceptar=(Button)view.findViewById(R.id.PbtnAccept);
        epicDialog = new Dialog(getActivity());


       imgScan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Toast.makeText(getActivity(), "Mensaje desde Visitantes", Toast.LENGTH_SHORT).show();
               Intent intent=new Intent(getActivity(),ScannerdosActivity.class);
               getActivity().startActivity(intent);
           }
       });

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},REQUEST_ACCESS_FINE);
        }

        queue= Volley.newRequestQueue(getContext());
        queueE=Volley.newRequestQueue(getContext());
        queueR=Volley.newRequestQueue(getContext());

        VbtnGu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidarCamposVacios()){
                    openDialogVacios();
                    txtNombre.requestFocus();
                }else{
                    RegistrarVisitante();
                    RegistrarVisitanteInt();
                    RegistrarEntrada();
                    RegistrarEntrada();
                    LimpiarCampos();
                }

            }
        });

        return view;

    }

    private boolean ValidarCamposVacios(){
        if (txtNombre.getText().toString().equals("") || txtApPat.getText().toString().equals("") || txtApMat.getText().toString().equals("") || txtMotivo.getText().toString().equals("") ||
        Cic.getText().toString().equals("") || txtMarca.getText().toString().equals("") || txtModelo.getText().toString().equals("") || txtMatricula.getText().toString().equals("") || TipoVehiculo==0){
            return true;
        }else{
            return false;
        }
    }

    private void RegistrarEntrada(){
        String url="http://"+ ConstantesServidor.Visitantes +"/consultaIDPerteneceV.php?IDConductor="+
                Cic.getText().toString()+"&IDVehiculo="+txtMatricula.getText().toString();
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=response.getJSONObject("ConsultaPV");
                    idp=jsonObject.getInt("ID_PERTENECEV");
                    String fecha=obtenerFecha.FechaActual();
                    String hora=obtenerHora.HoraActual();

                    String url1="http://"+ConstantesServidor.Visitantes+"/registroEntradaV.php?Fecha="+fecha+"&HoraE="+hora+"&IDPertenece="+idp;
                    JsonObjectRequest request1=new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response1) {
                            try {
                                JSONObject jsonObject1=response1.getJSONObject("RegistroEV");
                                String Status=jsonObject1.getString("Status");
                                if (Status.equals("ENTRADAOK")){
                                    showPositivePopup();
                                }else{
                                    Toast.makeText(getContext(),"NO REGISTRO ENTRADA",Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            System.out.println("ERROR ENTRADA "+error.toString());
                            Toast.makeText(getContext(),"ERROR ENTRADA:"+error.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    queueR.add(request1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR OBTENER IDP: "+error.toString());
            }
        });
        queueE.add(request);
    }

    private void RegistrarVisitante(){
        codigo=Integer.valueOf(Cic.getText().toString());
        matricula=txtMatricula.getText().toString();

        String url="http://"+ConstantesServidor.Visitantes+"/registrosVisitantes.php?Cic="+codigo+
                " & Nombre="+txtNombre.getText().toString()+" & ApPat="+txtApPat.getText().toString()+
                " & ApMat="+txtApMat.getText().toString()+ " & Motivo="+txtMotivo.getText().toString()+
                " & Matricula="+matricula+ " & Marca="+txtMarca.getText().toString()+
                " & Modelo="+txtModelo.getText().toString()+" & Color="+txtColor.getText().toString()+
                " & TipoV="+TipoVehiculo;
        url=url.replace(" ","%20");

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(),"Registro exitoso Datos Visitante",Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"No se puedo registrar"+error.toString(),Toast.LENGTH_LONG).show();
                Log.i("ERROR: ",error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void RegistrarVisitanteInt(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(), "BdCDA", null, ConstantesBD.Version);
        SQLiteDatabase db=conn.getWritableDatabase();

        String insertVisitante="INSERT INTO "+ ConstantesBD.Table_Visitantes +"( "+ConstantesBD.columnid_Visitante+", "+ConstantesBD.columnNombreV+
                ", "+ConstantesBD.columnApPatV+", "+ConstantesBD.columnApMatV+", "+ConstantesBD.columnMotivo+","+ConstantesBD.columnTVehiculoVis+")" +
                " VALUES ( '"+Cic.getText().toString()+"','"+txtNombre.getText().toString()+"','"+txtApPat.getText().toString()+"','"+txtApMat.getText().toString()
                +"','"+txtMotivo.getText().toString()+"',"+TipoVehiculo+")";
        db.execSQL(insertVisitante);
        db.close();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]permissions, @NonNull int[]grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if(requestCode==REQUEST_ACCESS_FINE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(),"Permiso Concedido",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getActivity(),"Permiso Denegado",Toast.LENGTH_LONG).show();

            }
        }
    }

    public void showPositivePopup(){
        epicDialog.setContentView(R.layout.popup_positive);
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

    private void openDialogVacios(){
        DialogoCamposVaciosLogIn dialogoCamposVaciosLogIn=new DialogoCamposVaciosLogIn();
        dialogoCamposVaciosLogIn.show(getFragmentManager(), "Mensaje de Advertencia");
    }

    private void LimpiarCampos(){
        Cic.setText("");
        txtNombre.setText("");
        txtApPat.setText("");
        txtApMat.setText("");
        txtMotivo.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtMatricula.setText("");
        txtColor.setText("");
        spinner.setSelection(0);
    }

}
