package com.example.ingresocontenedores;

public class Constants {
    //nombre base de datos
    public static final String DB_NAME = "INGRESO_CONTENEDORES";
    //version de base de datos
    public static final int DB_VERSION = 1;
    //nombre de la tabla
    public static final String TABLE_NAME = "IC_entrada";
    //columnas/campos de las tablas
    public static final String C_ID = "ID";
    public static final String C_FECHA = "FECHAINGRESO";
    public static final String C_PLACA_VEHICULO = "PLACAVEHICULO";
    public static final String C_NOMBRE_AUDIT = "NOMBREAUDIT";
    public static final String C_FOTO_PLACA_VEHICULO = "FOTOPLACAVEHICULO";
    public static final String C_FOTO_CONTENEDOR = "FOTOCONTENEDOR";
    public static final String C_FOTO_SELLO = "FOTOSELLO";
    public static final String C_ADDED_TIMESTAMP = "ADDED_TIME_STAMP";
    public static final String C_UPDATED_TIMESTAMP =  "UPDATED_TIME_STAMP";
    //crea la tabla Query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +"("
            + C_ID+ " INTEGER PRIMARY KEY,"
            + C_FECHA+ " TEXT,"
            + C_PLACA_VEHICULO+ " TEXT,"
            + C_NOMBRE_AUDIT+ " TEXT,"
             + C_FOTO_PLACA_VEHICULO+ " TEXT,"
             + C_FOTO_CONTENEDOR+ " TEXT,"
             + C_FOTO_SELLO+ " TEXT,"
            + C_ADDED_TIMESTAMP+ " TEXT,"
            + C_UPDATED_TIMESTAMP+ " TEXT"
            + ")";


}
