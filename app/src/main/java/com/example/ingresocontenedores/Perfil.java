package com.example.ingresocontenedores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Perfil extends AppCompatActivity implements View.OnClickListener {

    //login
    EditText user, pass;
    Button btn_IniciarSesion, btn_Registrar;
    //botones de login
    Button btnEditar, btnEliminar, btnMostrar, btnSalir;
    /*variable para llamar nombre
    TextView nombre;
    int id= 0;
    Usuario u;
    daoUsuario dao;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfiles);

        //mostrar datos de usuario (nombre y apellidos)
        btnEditar=(Button) findViewById(R.id.btn_Editar);
        btnEliminar=(Button) findViewById(R.id.btn_Eliminar);
        btnMostrar=(Button) findViewById(R.id.btn_Mostrar);
        btnSalir=(Button) findViewById(R.id.btn_Editar);
        btnEditar.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
        btnMostrar.setOnClickListener(this);
        btnSalir.setOnClickListener(this);

        /*nombre = (TextView) findViewById(R.id.nombrePerfil);

        Bundle b = getIntent().getExtras();
        id = b.getInt("Id");
        dao = new daoUsuario(this);
        u = dao.getUsuarioById(id);
        Toast.makeText(this, "prueba... " + u.getNombre(), Toast.LENGTH_SHORT).show();
        nombre.setText(u.getNombre()+" "+u.getApellidos());
        */

    }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_Editar:
                    //Intent a = new Intent(Perfil.this, EditarPerfil.class);
                    //startActivity(a);
                    break;
                case R.id.btn_Eliminar:
                    break;
                case R.id.btn_Mostrar:
                    Intent c = new Intent(Perfil.this, MostrarPerfil.class);
                    startActivity(c);
                    break;
                case R.id.btn_Salir:
                    Intent i2 = new Intent(Perfil.this, Login.class);
                    //se pasa Id a la actividad para poder colocar cual es el usuario que s elogue(nombre)
                    startActivity(i2);
                    finish();
                    break;
            }
        }
    }
