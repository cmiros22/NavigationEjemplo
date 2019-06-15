package com.example.navigationejemplo;


import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.example.navigationejemplo.Utilidades.ConstantesServidor;
import com.google.android.gms.vision.CameraSource;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class TabSalida extends Fragment implements ZXingScannerView.ResultHandler{
    public static TextView txtResultado;

    private RequestQueue queue;

    private final int REQUEST_ACCESS_FINE=0;
    private LinearLayout qrCameraLayout;
    public static boolean mAutoFocus = false;

    public static EditText txtCodigo;
    private ImageView imgScan;
    private Button btnBuscar;

    ObtenerHora obtenerHora;
    ObtenerFecha obtenerFecha;
    public static String hora,fecha;

    private Dialog epicDialog;
    private Button botonAceptar;
    private TextView titleTv;

    //nuevos
    private CameraSource cameraSource;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private String token = "";
    private String tokenanterior = "";
    private TextView txt1;

    private static int width,height;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view=inflater.inflate(R.layout.fragment_salida,container,false);


        txtCodigo=(EditText)view.findViewById(R.id.txtCodigo);
        imgScan=(ImageView)view.findViewById(R.id.imgScanS);
        txtCodigo.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        btnBuscar=(Button)view.findViewById(R.id.btnBuscarS);
        epicDialog = new Dialog(getActivity());


        imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Mensaje desde Salida", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getActivity(),ScannerSalidaActivity.class);
                getActivity().startActivity(intent);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Mensaje del boton Buscar Salida",Toast.LENGTH_LONG).show();
                queue= Volley.newRequestQueue(getContext());
                obtenerHora=new ObtenerHora();
                obtenerFecha=new ObtenerFecha();
                RegistrarSalida();
                //txtCodigo.setText("");
            }
        });
        return view;
    }

    private void RegistrarSalida(){
        hora=obtenerHora.HoraActual();
        fecha=obtenerFecha.FechaActual();
        Toast.makeText(getContext(),"Hora es: "+hora,Toast.LENGTH_LONG).show();

        String url="http://"+ ConstantesServidor.Raiz +"/registroSalida.php?Codigo="+Integer.valueOf(txtCodigo.getText().toString())+
                "&HoraSalida="+hora;
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject=response.getJSONObject("RegistroS");
                    String Estado=jsonObject.getString("Estado");
                    Toast.makeText(getContext(),"ESTADO ES: "+Estado,Toast.LENGTH_SHORT).show();

                    if (!Estado.equals("FAILED")){
                        openDialogSalida();
                        txtCodigo.setText("");
                    }else{
                        showNegativePopup();
                        txtCodigo.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(),"ERROR DENTRO TRY",Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERROR GENREAL: "+error.toString());
                Toast.makeText(getContext(),"ERROR GENERAL: "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

    private void openDialogSalida(){
        DialogoSalidaRegistrada dialogoSalidaRegistrada=new DialogoSalidaRegistrada();
        dialogoSalidaRegistrada.show(getFragmentManager(),"Mensaje de Salida");
    }

    public void showNegativePopup(){
        epicDialog.setContentView(R.layout.popup_negative);
        botonAceptar = (Button) epicDialog.findViewById(R.id.btnAccept);
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

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getActivity(), "Mensaje: "+rawResult.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]permissions, @NonNull int[]grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if(requestCode==REQUEST_ACCESS_FINE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(), "Permiso concedido", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
