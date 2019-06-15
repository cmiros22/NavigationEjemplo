package com.example.navigationejemplo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.navigationejemplo.DialogosSesion.DialogoSoloGuardias;
import com.example.navigationejemplo.DialogosSesion.DialogoUPIncorrecto;
import com.example.navigationejemplo.Utilidades.ConstantesServidor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{
    private Button btnSignUp;
    private EditText txtUser;
    private TextInputEditText txtPass;
    private ProgressDialog progreso;

    private Dialog epicDialog;
    private Button botonAceptar;
    private TextView titleTv;

    private boolean StateConnection=false;
    RequestQueue rq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this, "BdCDA", null, 1);

        epicDialog = new Dialog(this);

        btnSignUp=(Button)findViewById(R.id.btnSignup);
        txtUser=(EditText)findViewById(R.id.txtUsuario);

        txtPass=(TextInputEditText)findViewById(R.id.txtPassword);
        rq= Volley.newRequestQueue(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //para prueba, apartir de aqui
                Intent intent=new Intent(getApplicationContext(), MenuPActivity.class);
                startActivity(intent);
                //hasta aqui.
                /*if(VerificarInternet()){
                    if(VerificarVacios()){
                        openDialogVacios();
                    }else{
                        iniciarsesion();
                    }
                }else{
                    openDialogoVerificarConexion();
                }*/
            }
        });

    }
    private boolean VerificarVacios(){
        if(txtUser.getText().toString().equals("") || txtPass.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    private boolean VerificarInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void iniciarsesion(){
        String url="http://"+ ConstantesServidor.Raiz +"/inicioUsuariosServidor.php?Username="+txtUser.getText().toString()+
                "&Password="+txtPass.getText().toString();

        progreso=new ProgressDialog(this);
        progreso.setMessage("Iniciando Sesión...");
        progreso.show();

        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.optJSONArray("datos");
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    String TipoUser=jsonObject.getString("TUsuario");
                    Toast.makeText(getApplicationContext(),"TUsuario: "+TipoUser,Toast.LENGTH_SHORT).show();

                    if(TipoUser.equals("Guardia de Seguridad")){
                        progreso.hide();
                        Intent intent=new Intent(getApplicationContext(), MenuPActivity.class);
                        startActivity(intent);
                        LimpiarCampos();
                    } else if(TipoUser.equals("NOTFOUND")){
                        progreso.hide();
                        openDialogIncorrecto();
                        LimpiarCampos();
                    }else if(!TipoUser.equals("Guardia de Seguridad")){
                        progreso.hide();
                        openDialogSoloG();
                        LimpiarCampos();
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
        rq.add(request);
    }

    private void LimpiarCampos(){
        txtUser.setText("");
        txtPass.setText("");
        txtUser.requestFocus();
    }

    private void openDialogIncorrecto(){
        DialogoUPIncorrecto dialogoSesion=new DialogoUPIncorrecto();
        dialogoSesion.show(getSupportFragmentManager(),"Ejemplo Dialog");
    }

    private void openDialogSoloG(){
        DialogoSoloGuardias dialogoSoloGuardias=new DialogoSoloGuardias();
        dialogoSoloGuardias.show(getSupportFragmentManager(), "Mensaje de Advertencia");
    }

    private void openDialogoVerificarConexion(){
        DialogoVerificarConexion dialogoVerificarConexion=new DialogoVerificarConexion();
        dialogoVerificarConexion.show(getSupportFragmentManager(), "Mensaje de Advertencia");
    }

    private void openDialogVacios(){
        DialogoCamposVaciosLogIn dialogoCamposVaciosLogIn=new DialogoCamposVaciosLogIn();
        dialogoCamposVaciosLogIn.show(getSupportFragmentManager(), "Mensaje de Advertencia");
    }

}
