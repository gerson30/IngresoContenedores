package com.example.ingresocontenedores;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

//clase DataBase Helper que contiene todos los métodos crud
public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //crea la tabla de la base de datos
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //actualizar la base de datos (si hay alguna estructura, cambie la version de db)

        //0artar la tabla anterior si existe
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        //crear tabla de nuevo
        onCreate(db);
    }

    //insertar datos a la base de datos
    public long insertRecord(String fechaIngreso, String Placa, String NombreAudit, String FotoPlaca,
                             String FotoContenedor, String FotoSello, String AddedTime, String updateTime) {
        //get database grabable porque queremos escribir datos

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //la identificacion se insertará automáticamente cuando configuremos AUTOINCREMENTO  en la consulta

        //insertar datos

        values.put(Constants.C_FECHA, fechaIngreso);
        values.put(Constants.C_PLACA_VEHICULO, Placa);
        values.put(Constants.C_NOMBRE_AUDIT, NombreAudit);
        values.put(Constants.C_FOTO_PLACA_VEHICULO, FotoPlaca);
        values.put(Constants.C_FOTO_CONTENEDOR, FotoContenedor);
        values.put(Constants.C_FOTO_SELLO, FotoSello);
        values.put(Constants.C_ADDED_TIMESTAMP, AddedTime);
        values.put(Constants.C_UPDATED_TIMESTAMP, updateTime);

        //insertar fila, devolverá la identificación del registro guardado
        long id = db.insert(Constants.TABLE_NAME, null, values);
        String selectQuery = " SELECT * FROM " + Constants.TABLE_NAME;


        Cursor cursor = db.rawQuery(selectQuery, null);
        System.out.println(cursor);

        // cerrar db Connection
        db.close();

        //devuelve la identificacion del resgistro insertado
        return id;

    }

    //Obtener todos datos
    public ArrayList<ModelRecord> getAllRecords(String orderBy) {
        // la orden de consulta permitirá ordenar los datos más nuevo / más antiguo primero, nombre ascendente / descendente
        // devolverá la lista o registros ya que hemos utilizado return tipo ArrayList <ModelRecord>

        ArrayList<ModelRecord> recordsList = new ArrayList<>();
        // consulta para seleccionar registros
        String selectQuery = " SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // recorrer todos los registros y agregarlos a la lista
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") ModelRecord modelRecord = new ModelRecord(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_FECHA)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PLACA_VEHICULO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_NOMBRE_AUDIT)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_FOTO_PLACA_VEHICULO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_FOTO_CONTENEDOR)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_FOTO_SELLO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP)));

                // Añadir registro a la list
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }

        //cierre de conexión db

        db.close();

        //retorna la lista
        System.out.println(recordsList);
        return recordsList;
    }

    //Buscar todos datos
    public ArrayList<ModelRecord> buscarRegistro(String query) {
        // la orden de consulta permitirá ordenar los datos más nuevo / más antiguo primero, nombre ascendente / descendente
        // devolverá la lista o registros ya que hemos utilizado return tipo ArrayList <ModelRecord>

        ArrayList<ModelRecord> recordsList = new ArrayList<>();
        // consulta para seleccionar registros
        String selectQuery = " SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_PLACA_VEHICULO + " LIKE '%" + query + "%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // recorrer todos los registros y agregarlos a la lista
        if (cursor.moveToFirst()) {
            do {
                ModelRecord modelRecord = new ModelRecord(
                        "" + cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_FECHA)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_PLACA_VEHICULO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_NOMBRE_AUDIT)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_FOTO_PLACA_VEHICULO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_FOTO_CONTENEDOR)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_FOTO_SELLO)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP)),
                        "" + cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP)));

                // Añadir registro a la list
                recordsList.add(modelRecord);
            } while (cursor.moveToNext());
        }

        //cierre de conexión db

        db.close();

        //retorna la lista
        return recordsList;
    }

    //Eliminar dato usando Id
    public void deleteData(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.C_ID + " = ?", new String[]{id});
        db.close();
    }

    /*
    //Eliminar todos los datos
    public void deleteAllData(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(" DELETE FROM " + Constants.TABLE_NAME);
        db.close();
    }*/
    //Obtener el numero de registros
    public int getRecordsCount() {
        String countQuery = " SELECT * FROM " + Constants.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        return count;
    }

    //Actualizar datos a la base de datos
    public void updateRecord(String id, String fechaIngreso, String Placa, String NombreAudit, String FotoPlaca,
                             String FotoContenedor, String FotoSello, String AddedTime, String updateTime) {
        //get database grabable porque queremos escribir datos

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        //la identificacion se insertará automáticamente cuando configuremos AUTOINCREMENTO  en la consulta

        //insertar datos

        values.put(Constants.C_FECHA, fechaIngreso);
        values.put(Constants.C_PLACA_VEHICULO, Placa);
        values.put(Constants.C_NOMBRE_AUDIT, NombreAudit);
        values.put(Constants.C_FOTO_PLACA_VEHICULO, FotoPlaca);
        values.put(Constants.C_FOTO_CONTENEDOR, FotoContenedor);
        values.put(Constants.C_FOTO_SELLO, FotoSello);
        values.put(Constants.C_ADDED_TIMESTAMP, AddedTime);
        values.put(Constants.C_UPDATED_TIMESTAMP, updateTime);

        //update datos
        db.update(Constants.TABLE_NAME, values, Constants.C_ID + " =? ", new String[]{id});

        // cerrar db Connection
        db.close();


    }



}
