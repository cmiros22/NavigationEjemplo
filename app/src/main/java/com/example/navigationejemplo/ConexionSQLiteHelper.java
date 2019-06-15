package com.example.navigationejemplo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.navigationejemplo.Utilidades.ConstantesBD;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConstantesBD.CREATE_TABLE_Visitantes);
        db.execSQL(ConstantesBD.CREATE_TABLE_CONDUCTORES);
        //db.execSQL(ConstantesBD.CREATE_TABLE_VehiculosVis);
        //db.execSQL(ConstantesBD.CREATE_TABLE_PerteneceVis);
        //db.execSQL(ConstantesBD.CREATE_TABLE_Usuarios);
        //db.execSQL(ConstantesBD.CREATE_TABLE_Estudiantes);
        //db.execSQL(ConstantesBD.CREATE_TABLE_Personal);
        //db.execSQL(ConstantesBD.CREATE_TABLE_Vehiculos);
        //db.execSQL(ConstantesBD.CREATE_TABLE_Registros);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ConstantesBD.DROP_TABLE_Visitantes);
        db.execSQL(ConstantesBD.DROP_TABLE_Conductor);
        onCreate(db);
        //db.execSQL(ConstantesBD.DROP_TABLE_VehiculoVis);
        //db.execSQL(ConstantesBD.DROP_TABLE_PerteneceVis);
        /*db.execSQL(ConstantesBD.DROP_TABLE_Usuarios);
        db.execSQL(ConstantesBD.DROP_TABLE_Estudiantes);
        db.execSQL(ConstantesBD.DROP_TABLE_Visitantes);
        db.execSQL(ConstantesBD.DROP_TABLE_Personal);
        db.execSQL(ConstantesBD.DROP_TABLE_Vehiculos);
        db.execSQL(ConstantesBD.DROP_TABLE_Registros);
        onCreate(db);*/
    }

}
