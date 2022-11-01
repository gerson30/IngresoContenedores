package com.example.ingresocontenedores;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetalleRegistroActivity extends AppCompatActivity {

    //vistas
    private CircleImageView imagePlaca, imageContenedor, imageSello;
    private TextView fechaIngreso, nombreAudi, placaVehiculo, addedTimeTv, updatedTimeTv;

    //ActionBar
    private ActionBar actionBar;

    //
    private String recordID;

    //BDHelper
    private DBHelper dbHelper;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_registro);

        //configurar la barra de acción con el título y el botón Atrás
        actionBar = getSupportActionBar();
        actionBar.setTitle("Detalle del Registro");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //obtener la identificación de registro del adaptador mediante la intención
        Intent intent = getIntent();
        recordID = intent.getStringExtra("RECORD_ID");

        //Inicializacion BD Helper Clase
        dbHelper = new DBHelper(this);

        //Inicializamos la vista

        fechaIngreso = findViewById(R.id.txt_FechaIngreso);
        placaVehiculo = findViewById(R.id.txt_Placa);
        nombreAudi = findViewById(R.id.txt_NombreAudit);
        imagePlaca = findViewById(R.id.btn_FotoPlaca);
        imageContenedor = findViewById(R.id.btn_FotoContenedor);
        imageSello = findViewById(R.id.btn_FotoSello);
        addedTimeTv = findViewById(R.id.addedTimeTv);
        updatedTimeTv = findViewById(R.id.updateTimeTv);

         showRecordDetails();
    }

    private void showRecordDetails() {
        //obtener detalles de registro
        //consulta para seleccionar el registro basado en la identificación del registro
        String selectQuery = " SELECT * FROM " + Constants.TABLE_NAME + " WHERE " + Constants.C_ID +" =\""+ recordID+"\"";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // mantener comprobando toda la base de datos para ese registro
        if (cursor.moveToFirst()){
            do {

                //Obtener datos
                String id = ""+ cursor.getInt(cursor.getColumnIndex(Constants.C_ID));
                String fecha = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_FECHA));
                String placa = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_PLACA_VEHICULO));
                String nombreAudit = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_NOMBRE_AUDIT));
                String fotoPlaca = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_FOTO_PLACA_VEHICULO));
                String fotoContenedor = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_FOTO_CONTENEDOR));
                String fotoSello = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_FOTO_SELLO));
                String timestampAdded = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_ADDED_TIMESTAMP));
                String timestampUpdated = ""+ cursor.getString(cursor.getColumnIndex(Constants.C_UPDATED_TIMESTAMP));

                //Convertir marca de tiempo a dd/mm/yyyy hh:mm por ejemplo 10/04/2020 08:22 AM
                Calendar calendar1 = Calendar.getInstance(Locale.getDefault());
                String timeAdded = ""+ DateFormat.format("dd/MM/yyyy", calendar1);

                Calendar calendar2 = Calendar.getInstance(Locale.getDefault());
                calendar2.setTimeInMillis(Long.parseLong(timestampUpdated));
                String timeupdated = ""+ DateFormat.format("dd/MM/yyyy", calendar2);

                //Establecer datos
                fechaIngreso.setText(fecha);
                placaVehiculo.setText(placa);
                nombreAudi.setText(nombreAudit);
                addedTimeTv.setText(timeAdded);
                updatedTimeTv.setText(timeupdated);

                /* si el usuario no adjunta la imagen, imageUri será nulo, por lo tanto,
                 configure una imagen predeterminada en ese caso
                imagen de placa*/
                if (fotoPlaca.equals("null")){
                    // no hay imagen en el registro, establecer predeterminado
                    imagePlaca.setImageResource(R.drawable.sin_fotos);
                }
                else {
                    // tener imagen en el registro

                    imagePlaca.setImageURI(Uri.parse(fotoPlaca));
                }

                /* si el usuario no adjunta la imagen, imageUri será nulo, por lo tanto,
                configure una imagen predeterminada en ese caso
                iagen de contened*/
                if (fotoContenedor.equals("null")){
                    // no hay imagen en el registro, establecer predeterminado
                    imageContenedor.setImageResource(R.drawable.sin_fotos);
                }
                else {
                    // tener imagen en el registro
                    imageContenedor.setImageURI(Uri.parse(fotoContenedor));
                }

                /* si el usuario no adjunta la imagen, imageUri será nulo, por lo tanto,
                configure una imagen predeterminada en ese caso
                imagen de sello*/
                if (fotoSello.equals("null")){
                    // no hay imagen en el registro, establecer predeterminado
                    imageSello.setImageResource(R.drawable.sin_fotos);
                }
                else {
                    // tener imagen en el registro
                    imageSello.setImageURI(Uri.parse(fotoSello));
                }


            }while(cursor.moveToNext());
        }
        db.close();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();//ir a la actividad anterior
        return super.onSupportNavigateUp();
    }
}