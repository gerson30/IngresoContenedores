package com.example.ingresocontenedores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener  {
    //login
    EditText user, pass;
    Button btn_IniciarSesion, btn_Registrar;
    daoUsuario dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //variables para registro e ingreso de usuarios
        user = (EditText) findViewById(R.id.txt_Usuario);
        pass = (EditText) findViewById(R.id.txt_contrasena);
        btn_IniciarSesion = (Button) findViewById(R.id.btn_IniciarSes);
        //btn_Registrar = (Button) findViewById(R.id.btn_OpRegistrar);

        btn_IniciarSesion.setOnClickListener(this);
        //btn_Registrar.setOnClickListener(this);
        dao=new daoUsuario(this);


    }
    @Override
    public void onClick(View v) {
        //opcion para activity registrar usuario
        switch(v.getId()){
            //validación de iniciar sesión
            case R.id.btn_IniciarSes:
                String u=user.getText().toString();
                String p=pass.getText().toString();
                if(u.equals("")&&p.equals("")){
                    Toast.makeText(this,"Error: Campos vacíos", Toast.LENGTH_LONG).show();
                }else if(dao.login(u,p)==1) {
                    Usuario ux=dao.getUsuario(u,p);
                    Toast.makeText(this, "BIENVENIDO " + user.getText(), Toast.LENGTH_LONG).show();
                    //direccionar a pantalla inicio
                    Intent i2 = new Intent(Login.this, Home.class);
                    //se pasa Id a la actividad para poder colocar cual es el usuario que s elogue(nombre)
                    i2.putExtra("Id", ux.getId());
                    startActivity(i2);
                    finish();
                }else{
                    Toast.makeText(this, "USUARIO Y/O PASSWORD INCORRECTOS", Toast.LENGTH_LONG).show();

                }
                break;

            /*case R.id.btn_OpRegistrar:
                Intent i= new Intent(Login.this,RegistrarUsuario.class);
                startActivity(i);
                break;*/
        }

    }
}

