package com.example.ingresocontenedores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrarUsuario extends AppCompatActivity implements View.OnClickListener {


    EditText us, pas,nom,ap;
    Button reg,can;
    daoUsuario dao;
    //registro de usuarios para el login
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        us=(EditText)findViewById(R.id.txt_RegUsuario);
        pas=(EditText)findViewById(R.id.txt_RegContrasena);
        nom=(EditText)findViewById(R.id.txt_RegNombre);
        ap=(EditText)findViewById(R.id.txt_ape);
        reg=(Button)findViewById(R.id.btn_RegistrarUsu);
        can=(Button)findViewById(R.id.btn_CancelarReg);
        reg.setOnClickListener(this);
        can.setOnClickListener(this);
        dao=new daoUsuario (this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_RegistrarUsu:
                Usuario u=new Usuario();
                u.setUsuario(us.getText().toString());
                u.setPassword(pas.getText().toString());
                u.setNombre(nom.getText().toString());
                u.setApellidos(ap.getText().toString());
                if(!u.isNull()){
                    Toast.makeText(this, "ERROR: Campos vacíos", Toast.LENGTH_LONG).show();
                }else if (dao.insertUsuario(u)){
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show();
                    //retornar a la vista principal de login
                    Intent i2=new Intent(RegistrarUsuario.this,Login.class);
                    startActivity(i2);
                    finish();
                }else{
                    Toast.makeText(this, "Usuario ya registrado!!!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_CancelarReg:
                Intent i=new Intent(RegistrarUsuario.this,Login.class);
                startActivity(i);
                finish();
                break;
        }
    }
}
