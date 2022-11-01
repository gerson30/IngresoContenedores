package com.example.ingresocontenedores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Home extends AppCompatActivity {

    private CardView ing;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Inicializar Vista
        ing = (CardView) findViewById(R.id.ingresoVehiculo);


        // Click para Iniciar a añadir y grabar en la activity de editara
        ing.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Iniciar la Activity
                Intent intent = new Intent(Home.this, IngresoVehiculo.class);
                startActivity(intent);
                finish();

                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "Ingreso vehículo", Toast.LENGTH_SHORT);

                toast1.show();

            }
        });






    }
}
