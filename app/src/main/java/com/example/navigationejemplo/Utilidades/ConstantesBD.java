package com.example.navigationejemplo.Utilidades;

public class ConstantesBD {

    public static final  int Version=15;
    //Constante campos de tabla Usuarios
    public static final String Table_Usuarios="Usuarios";
    public static final String columnUsername="Username";
    public static final String columnPassword="Password";
    public static final String CREATE_TABLE_Usuarios="CREATE TABLE "+Table_Usuarios+" ("+columnUsername+" TEXT PRIMARY KEY, "+columnPassword+" TEXT)";

    //Constante campos de tabla conductores
    public static final String Table_Conductor="Conductor";
    public static final String columnIDConduct="ID";
    public static final String columnIDConductores="IDCONDUCTORES";
    public static final String columnNombreC="NOMBRE";
    public static final String columnApePat="APPAT";
    public static final String columnApeMat="APMAT";
    public static final String columnTConductor="TCONDUCTOR";
    public static final String columnTVehiculo="TVEHICULO";
    public static final String CREATE_TABLE_CONDUCTORES="CREATE TABLE "+Table_Conductor+" ("+columnIDConduct+" INTEGER PRIMARY KEY AUTOINCREMENT, "+columnIDConductores+" TEXT, "+columnNombreC+" TEXT, "+columnApePat+" TEXT, "+columnApeMat+" TEXT, "+columnTConductor+" INTEGER, "+columnTVehiculo+" INTEGER)";

    //Constante campos de tabla Estudiantes
    public static final String Table_Estudiantes="Estudiantes";
    public static final String columnN_Control="N_Control";
    public static final String columnNombre="Nombre";
    public static final String columnApPat="ApPat";
    public static final String columnApMat="ApMat";
    //Constantes para la creacion de Tabla Estudiantes
    public static final String CREATE_TABLE_Estudiantes="CREATE TABLE "+Table_Estudiantes+" ("+columnN_Control+" TEXT PRIMARY KEY, "+columnNombre+" TEXT, "+columnApPat+" TEXT, "+columnApMat+" TEXT)";


    //Constante campos de tabla Visitantes
    public static final String Table_Visitantes="Visitantes";
    public static final String columnid_Visitante="CIC"; //CIC
    public static final String columnNombreV="Nombre";
    public static final String columnApPatV="ApPat";
    public static final String columnApMatV="ApMat";
    public static final String columnMotivo="Motivo";
    public static final String columnTVehiculoVis="Tvehiculo";
    //Constantes para la creacion de Tabla Visitantes
    public static final String CREATE_TABLE_Visitantes="CREATE TABLE "+Table_Visitantes+" ("+columnid_Visitante+" TEXT PRIMARY KEY, "+columnNombreV+" TEXT, "+columnApPatV+" TEXT, "+columnApMatV+" TEXT, "+columnMotivo+" TEXT, "+columnTVehiculoVis+" INTEGER)";

    //Constante campos de tabla VehiculosVisitantes
    public static final String Table_VehiculosVis="VehiculoVis";
    public static final String columnIDConductorVis="IDConductor";
    public static final String columnMatriculaVis="Matricula";
    public static final String columnMarcaVis="Marca";
    public static final String columnModeloVis="Modelo";
    public static final String columnColorVis="Color";
    public static final String columnIDTipoVehiculoVis="ID_TIPOVEHICULOV";
    //Constantes para la creacion de Tabla Vehiculos
    public static final String CREATE_TABLE_VehiculosVis="CREATE TABLE "+Table_VehiculosVis+" ("+columnMatriculaVis+" TEXT PRIMARY KEY, "+columnIDConductorVis+"TEXT "+columnMarcaVis+" TEXT, "+columnModeloVis+" TEXT, "+columnColorVis+" TEXT, "+columnIDTipoVehiculoVis+" INTEGER)";

    //Constante campos de tabla PerteneceVis
    public static final String Table_PerteneceVis="PerteneceVis";
    public static final String columnIDPerteneceV="ID_PERTENECEV";
    public static final String columnIDConductor="ID_CONDUCTOR";
    public static final String columnIDVehiculo="ID_VEHICULO";
    //Constante para la creacion de Tabla PerteneceVis
    public static final String CREATE_TABLE_PerteneceVis="CREATE TABLE "+Table_PerteneceVis+" ("+columnIDPerteneceV+" INTEGER AUTOINCREMENT PRIMARY KEY, "+columnIDConductor+" TEXT, "+columnIDVehiculo+" TEXT)";

    //Constante campos de tabla Personal
    public static final String Table_Personal="Personal";
    public static final String columnN_Trabajador="N_Trabajador";
    public static final String columnNombre3="Nombre";
    public static final String columnApPat3="ApPat";
    public static final String columnApMat3="ApMat";
    //Constantes para la creacion de Tabla Personal
    public static final String CREATE_TABLE_Personal="CREATE TABLE "+Table_Personal+" ("+columnN_Trabajador+" TEXT PRIMARY KEY, "+columnNombre3+" TEXT, "+columnApPat3+" TEXT, "+columnApMat3+" TEXT)";


    //Constante campos de tabla Vehiculos
    public static final String Table_Vehiculos="Vehiculos";
    public static final String columnMatricula="Matricula";
    public static final String columnMarca="Marca";
    public static final String columnModelo="Modelo";
    public static final String columnColor="Color";
    //Constantes para la creacion de Tabla Vehiculos
    public static final String CREATE_TABLE_Vehiculos="CREATE TABLE "+Table_Vehiculos+" ("+columnMatricula+" TEXT PRIMARY KEY, "+columnMarca+" TEXT, "+columnModelo+" TEXT, "+columnColor+" TEXT)";



    //Constante campos de tabla Registros
    public static final String Table_Registros="Registros";
    public static final String columnid_Registro="id_Registro";
    public static final String columnFecha="Fecha";
    public static final String columnHora_Entrada="Hora_Entrada";
    public static final String columnHora_Salida="Hora_Salida";
    //Constantes para la creacion de Tabla Registros
    public static final String CREATE_TABLE_Registros="CREATE TABLE "+Table_Registros+" ("+columnid_Registro+" INTEGER PRIMARY KEY, "+columnFecha+" TEXT, "+columnHora_Entrada+" TEXT, "+columnHora_Salida+" TEXT)";



    //Constante para eliminar Tablas
    public static final String DROP_TABLE_Usuarios="DROP TABLE IF EXISTS Usuarios";
    public static final String DROP_TABLE_Estudiantes="DROP TABLE IF EXISTS Estudiantes";
    public static final String DROP_TABLE_Visitantes="DROP TABLE IF EXISTS Visitantes";
    public static final String DROP_TABLE_VehiculoVis="DROP TABLE IF EXISTS VehiculoVis";
    public static final String DROP_TABLE_PerteneceVis="DROP TABLE IF EXISTS PerteneceVis";
    public static final String DROP_TABLE_Personal="DROP TABLE IF EXISTS Personal";
    public static final String DROP_TABLE_Vehiculos="DROP TABLE IF EXISTS Vehiculos";
    public static final String DROP_TABLE_Registros="DROP TABLE IF EXISTS Registros";
    public static final String DROP_TABLE_Conductor="DROP TABLE IF EXISTS Conductor";
}
