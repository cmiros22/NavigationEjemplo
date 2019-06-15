package com.example.navigationejemplo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerdosActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private final int REQUEST_ACCESS_FINE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_scanner);
        mScannerView=new ZXingScannerView(this);
        mScannerView.setAutoFocus(true);
        //mScannerView.setFlash(true);
        setContentView(mScannerView);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_ACCESS_FINE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]permissions, @NonNull int[]grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if(requestCode==REQUEST_ACCESS_FINE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permiso Concedido",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"Permiso Denegado",Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause(){
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        //Log.v(TAG, rawResult.getText());
        //Log.v(TAG, rawResult.getBarcodeFormat().toString());
        Vibrator vib = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        vib.vibrate(100);
        TabVisitantes.Cic.setText(rawResult.getText());
        //mScannerView.resumeCameraPreview(this);
        onBackPressed();
    }
}
