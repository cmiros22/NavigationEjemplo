package com.example.navigationejemplo;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigationejemplo.Entidades.Conductor;
import com.example.navigationejemplo.Recycler.DatosConductor;
import com.example.navigationejemplo.Recycler.DatosPertenece;
import com.example.navigationejemplo.Recycler.DatosVehiculo;
import com.example.navigationejemplo.Recycler.RecyclerAdapterConductor;
import com.example.navigationejemplo.Recycler.RecyclerAdapterVehiculos;
import com.example.navigationejemplo.Registros.ObtenerFecha;
import com.example.navigationejemplo.Registros.ObtenerHora;
import com.example.navigationejemplo.Utilidades.ConstantesBD;
import com.example.navigationejemplo.Utilidades.ConstantesServidor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TabInternos extends Fragment {
    //Declaraciones para el recycler de Conductor
    RecyclerView recyclerConductor;
    RecyclerAdapterConductor adapterConductor;
    ArrayList<DatosConductor> listDatosConductor;

    //Declaraciones para el recycler de Vehiculo
    RecyclerView recyclerVehiculo;
    RecyclerAdapterVehiculos adapterVehiculos;
    ArrayList<DatosVehiculo> listDatosVehiculo;


    ConexionSQLiteHelper conn;

    private ImageView imgScan;
    private Button btnSearch,IbtnGu;
    private Dialog epicDialog;
    private TextView titleTv;
    private Button botonAceptar;
    private EditText txtMarca, txtModelo, txtMatricula, txtNombre;
    public static EditText txtBuscar;

    private ProgressDialog progreso;

    private RequestQueue queue;
    private RequestQueue queueV;
    private RequestQueue queueP;
    private RequestQueue queueE;
    private RequestQueue queueR;

    private String nombrecompleto="";
    private int tipoconductor;

    ObtenerFecha obtenerFecha;
    ObtenerHora obtenerHora;
    public static String Matricula="";
    public int IDPertenece=0;

    private static String IDC="",N="",APT="",AMT="";
    private static int TI=0,TV=0;
    DatosPertenece datosPertenece=new DatosPertenece();
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view;
        view=inflater.inflate(R.layout.fragment_internos,container,false);

        conn=new ConexionSQLiteHelper(getContext(), "BdCDA", null, ConstantesBD.Version);

        //Declaraciones para el recycler de Conductor
        listDatosConductor=new ArrayList<>();
        recyclerConductor=view.findViewById(R.id.RecycleridConductor);
        recyclerConductor.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        adapterConductor=new RecyclerAdapterConductor(listDatosConductor);
        recyclerConductor.setAdapter(adapterConductor);

        //Declaraciones para el recycler de Vehiculo
        listDatosVehiculo=new ArrayList<>();
        recyclerVehiculo=view.findViewById(R.id.RecycleridVehiculos);
        recyclerVehiculo.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapterVehiculos=new RecyclerAdapterVehiculos(listDatosVehiculo);
        adapterVehiculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Seleccion: "+
                        listDatosVehiculo.get
                                (recyclerVehiculo.getChildAdapterPosition(v)).getMatricula(),Toast.LENGTH_LONG).show();

                Matricula=listDatosVehiculo.get(recyclerVehiculo.getChildAdapterPosition(v)).getMatricula();
                openDialogVehiculoSeleccionado();
            }
        });
        recyclerVehiculo.setAdapter(adapterVehiculos);

        imgScan=(ImageView)view.findViewById(R.id.imgScan);
        btnSearch=(Button)view.findViewById(R.id.btnBuscar);
        IbtnGu=(Button)view.findViewById(R.id.IbtnGu);
        botonAceptar=(Button)view.findViewById(R.id.PbtnAccept);
        txtBuscar=(EditText)view.findViewById(R.id.txtCampoBuscar);
        txtBuscar.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        txtBuscar.setText("");

        txtNombre=(EditText)view.findViewById(R.id.txtNombre);
        txtMarca=(EditText)view.findViewById(R.id.txtMarca);
        txtModelo=(EditText)view.findViewById(R.id.txtModelo);
        txtMatricula=(EditText)view.findViewById(R.id.txtMatricula);

        epicDialog = new Dialog(getActivity());

        imgScan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Mensaje desde Internos", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),ScannerActivity.class);
                getActivity().startActivity(intent);
            }
        });
        queue= Volley.newRequestQueue(getContext());
        queueV=Volley.newRequestQueue(getContext());
        queueP=Volley.newRequestQueue(getContext());
        queueE=Volley.newRequestQueue(getContext());
        queueR=Volley.newRequestQueue(getContext());

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtBuscar.getText().toString().equals("")){
                    openDialogBuscarVacio();
                }else{
                    progreso=new ProgressDialog(getContext());
                    progreso.setMessage("Obteniendo Datos...");
                    progreso.show();
                    Consultar(); //BUSCA AL CONDUCTOR Y AL VEHICULO
                }
            }
        });


        IbtnGu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerFecha=new ObtenerFecha();
                obtenerHora=new ObtenerHora();
                if(txtBuscar.getText().toString().equals("")){
                    txtBuscar.setText("");
                    openDialogBuscarVacio();
                }else{
                    if(Matricula.equals("")){
                        openDialogSeleccioneVehiculo();
                    }else{
                        RegistrarConductor();
                        RegistrarEntrada(); //REGISTRA LA ENTRADA DEL VEHICULO
                        limpiar();
                    }
                }
            }
        });
        return view;
    }

    private void RegistrarE(int IDP){
        final String fecha=obtenerFecha.FechaActual();
        final String hora=obtenerHora.HoraActual();

        Toast.makeText(getContext(),"Registrar E IDP VALe: "+IDP,Toast.LENGTH_SHORT).show();
        String urlR="http://"+ ConstantesServidor.Internos +"/registroEntrada.php?IDPertenece="+IDP+"&Fecha="+fecha+"&HoraE="+hora;
        JsonObjectRequest requestR=new JsonObjectRequest(Request.Method.GET, urlR, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=response.getJSONObject("RegistroE");
                    String est=jsonObject.getString("Estado");
                    Toast.makeText(getContext(),"Estado es: "+est,Toast.LENGTH_SHORT).show();
                    if (est.equals("ENTRADAOK")){
                        showPositivePopup();
                    }
                } catch (JSONException e) {
                    System.out.println("EEROR: "+e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"EEROR VOLLER FINAL: "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        queueR.add(requestR);
    }

    private void RegistrarEntrada(){
        String url="http://"+ConstantesServidor.Internos+"/consultaIDPertenece.php?IDConductor="+Integer.valueOf(txtBuscar.getText().toString())+"&IDVehiculo="+Matricula;


        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final JSONObject jsonObject=response.getJSONObject("ConsultaP");
                    int IDP=jsonObject.getInt("ID_PERTENECE");
                    Toast.makeText(getContext(),"VALOR IDP: "+IDP,Toast.LENGTH_SHORT).show();

                    if(IDP>0){
                        Toast.makeText(getContext(),"IDP mayor 0",Toast.LENGTH_SHORT).show();
                        //Segunda ACCION
                        RegistrarE(IDP);

                    }else{
                        Toast.makeText(getContext(),"No se Obtuvo IDP",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR AL OBTENER IDP: "+error.toString());
                Toast.makeText(getContext(),"ERROR AL OBTENER IDP",Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println("ANTES DE ADD");
        queueE.add(request);
    }

    private void ObtenerVehiculos(){
        String url2="http://"+ConstantesServidor.Internos+"/buscarVehiculo.php?Tarjeton="+txtBuscar.getText().toString();

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try{
                    listDatosVehiculo.clear();
                    DatosVehiculo datosVehiculo=null;
                    JSONArray jsonArray=response.getJSONArray("datosV");

                    for (int i=0; i<jsonArray.length(); i++){
                        System.out.println("LA VARIABLE I VALE: "+i);
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        datosVehiculo=new DatosVehiculo();
                        datosVehiculo.setMatricula(jsonObject.getString("MATRICULA"));
                        System.out.println("Matricula: "+datosVehiculo.getMatricula());
                        datosVehiculo.setModelo(jsonObject.getString("MODELO"));
                        System.out.println("Modelo: "+datosVehiculo.getModelo());
                        datosVehiculo.setColor(jsonObject.getString("COLOR"));
                        System.out.println("Color: "+datosVehiculo.getColor());
                        datosVehiculo.setFoto(jsonObject.getInt("ID_TIPOVEHICULO"));
                        TV=datosVehiculo.getFoto();
                        System.out.println("Foto: "+datosVehiculo.getFoto());
                        listDatosVehiculo.add(datosVehiculo);
                    }
                    adapterVehiculos.notifyDataSetChanged();

                }catch (JSONException e){
                    e.printStackTrace();
                    listDatosVehiculo.clear();
                    adapterVehiculos.notifyDataSetChanged();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listDatosVehiculo.clear();
            }
        });
        queueV.add(request);
    }

    private void Consultar(){
        String url="http://"+ConstantesServidor.Internos+"/buscarConductor.php?Tarjeton="+txtBuscar.getText().toString();

        final DatosConductor datosConductor=new DatosConductor();
        final Conductor conductor=new Conductor();

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    listDatosConductor.clear();
                    progreso.hide();

                    JSONObject jsonObject=response.getJSONObject("datosC");

                    conductor.setNombre(jsonObject.getString("NOMBRE"));
                    IDC=txtBuscar.getText().toString();
                    conductor.setApPat(jsonObject.getString("APPAT"));
                    N=conductor.getNombre();
                    conductor.setApMat(jsonObject.getString("APMAT"));
                    APT=conductor.getApPat();
                    conductor.setTipo(jsonObject.getInt("TCONDUCTOR"));
                    AMT=conductor.getApMat();
                    TI=conductor.getTipo();

                    nombrecompleto=conductor.getNombre()+" "+conductor.getApPat()+" "+conductor.getApMat();
                    datosConductor.setNombreCompleto(nombrecompleto);

                    tipoconductor=conductor.getTipo();
                    if (tipoconductor==1){
                        datosConductor.setTipoConductor("Estudiante");
                    }else if(tipoconductor==2){
                        datosConductor.setTipoConductor("Personal");
                    }
                    //Invocar al adaptador de Conductor
                    listDatosConductor.add(datosConductor);
                    adapterConductor.notifyDataSetChanged();

                    ObtenerVehiculos(); //Buscar el vehiculo del conductor

                } catch (JSONException e) {
                    listDatosConductor.clear();
                    adapterConductor.notifyDataSetChanged();
                    listDatosVehiculo.clear();
                    adapterVehiculos.notifyDataSetChanged();

                    e.printStackTrace();
                    openDialog();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listDatosConductor.clear();
                progreso.hide();
            }
        });
        adapterConductor.notifyDataSetChanged();

        queue.add(request);
    }

    private void RegistrarConductor(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(getContext(), "BdCDA", null, ConstantesBD.Version);
        SQLiteDatabase db=conn.getWritableDatabase();
        Toast.makeText(getContext(),"ID: "+IDC+"N: "+N+"APT: "+APT+"AMT: "+AMT+"TV: "+TV,Toast.LENGTH_SHORT).show();
        System.out.println("TV: "+TV);

        String insertVisitante="INSERT INTO "+ ConstantesBD.Table_Conductor +"( "+ConstantesBD.columnIDConductores+", "+ConstantesBD.columnNombreC+
                ", "+ConstantesBD.columnApePat+", "+ConstantesBD.columnApeMat+", "+ConstantesBD.columnTConductor+","+ConstantesBD.columnTVehiculo+")"+
                " VALUES ( '"+IDC+"','"+N+"','"+APT+"','"+AMT+"',"+TI+",'"+TV+"')";
        db.execSQL(insertVisitante);
        db.close();
    }

    private void limpiar(){
        txtBuscar.setText("");
        listDatosVehiculo.clear();
        listDatosConductor.clear();
        adapterConductor.notifyDataSetChanged();
        adapterVehiculos.notifyDataSetChanged();
        txtBuscar.requestFocus();
    }

    private void openDialog(){
        DialogoNoRegistrado dialogoBuscarInternos=new DialogoNoRegistrado();
        dialogoBuscarInternos.show(getFragmentManager(), "Mensaje Advertencia");
    }

    private void openDialogBuscarVacio(){
        DialogoBuscarVacio dialogoBuscarVacio=new DialogoBuscarVacio();
        dialogoBuscarVacio.show(getFragmentManager(), "Mensaje Advertencia");
    }

    private void openDialogVehiculoSeleccionado(){
        DialogoVehiculoSeleccionado dialogoVehiculoSeleccionado=new DialogoVehiculoSeleccionado();
        dialogoVehiculoSeleccionado.show(getFragmentManager(),"Vehiculo Seleccionado");
    }

    private void openDialogSeleccioneVehiculo(){
        DialogoSeleccioneVehiculo dialogoSeleccioneVehiculo=new DialogoSeleccioneVehiculo();
        dialogoSeleccioneVehiculo.show(getFragmentManager(),"Matricula Vacia");
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

}
