package com.example.ingresocontenedores;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class IngresoVehiculo extends AppCompatActivity {


    //Views
    private FloatingActionButton fab;

    //RecyclerView (llama la VISTA donde quedará  cada dato guardado)
    private RecyclerView listaGuardada;

    //Base de datos (DB) Helper
    private DBHelper dbHelper;



    //Action Bar
    ActionBar actionBar;

    //Ordenar Opciones del menú
    String orderByNewest = Constants.C_ID + " DESC";
    String orderByOldest = Constants.C_ID + " ASC";
    String orderByTitleAsc = Constants.C_ID + " ASC";
    String orderByTitleDesc = Constants.C_ID + " DESC";

    //para actualizar registros, actualiza con la ultmima opción de ordenación elegida
    String currentOrderByStatus = orderByNewest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //Inicializar Vista
        fab = (FloatingActionButton) findViewById(R.id.fab_btn);
        listaGuardada = (RecyclerView) findViewById(R.id.listaGuardada);

        //Inicializamos Clase de base de datos DBHelper
        dbHelper = new DBHelper(this);

        //Inicializacion ActionBar
        actionBar = getSupportActionBar();
        actionBar.setTitle("Registros");

        // Cargando Registros
        cargarRegistrosVistas(orderByNewest);



        // Click para Iniciar a añadir y grabar en la activity de editar
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Iniciar la Activity
                Intent intent = new Intent(IngresoVehiculo.this, Registro.class);
                intent.putExtra("isEditMode", false);//desea establecer nuevos datos, set false.
                startActivity(intent);
            }
        });

    }

    //metodo para cargar
    private void cargarRegistrosVistas(String orderBy) {
        currentOrderByStatus = orderBy;
        AdapterRecord adapterRecord = new AdapterRecord(IngresoVehiculo.this,
                dbHelper.getAllRecords(orderBy));

        listaGuardada.setAdapter(adapterRecord);

        //Establecer el numero de Registros
        actionBar.setSubtitle("Total: "+dbHelper.getRecordsCount());
    }

    private void buscarRegistros(String query){
        AdapterRecord adapterRecord = new AdapterRecord(IngresoVehiculo.this,
                dbHelper.buscarRegistro(query));

        listaGuardada.setAdapter(adapterRecord);
    }



    // Refresca o actualiza la lista de registros
    @Override
    protected void onResume(){
        super.onResume();
        cargarRegistrosVistas(currentOrderByStatus);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //inflate menu
        getMenuInflater().inflate(R.menu.main, menu);

        //searchView
        MenuItem item = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // buscar cuando se hace clic en el botón de búsqueda en el teclado
                buscarRegistros(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // busca mientras escribes
                buscarRegistros(newText);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoout:
                Intent intent=new Intent(IngresoVehiculo.this,Login.class);
                Toast.makeText(this, "SESIÓN FINALIZADA", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        //maneja elementos del menu
        int id = item.getItemId();
        if (id==R.id.action_sort){
            //Mostrar opciones de ordenación (Mostrr Diálogo)
            sortOptionDialog();
        }/*esta opcion permite eliminar todos los registroselse if (id==R.id.action_delete_all){
            //eliminar todos los datos
            dbHelper.deleteAllData();
            onResume();
        }
        return super.onOptionsItemSelected(item);
    }

    //opciones para mostrar en el Dialogo
    private void sortOptionDialog() {
        String[] options = {"Fecha Ascendente", "Fecha Descendente", "El más Nuevo ", "Más Antiguo"};
        //Dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ordenar Por").setItems(options, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    cargarRegistrosVistas(orderByTitleAsc);
                }
                if(which == 1){
                    cargarRegistrosVistas(orderByTitleDesc);
                }
                if(which == 2){
                    cargarRegistrosVistas(orderByNewest);
                }
                if(which == 3){
                    cargarRegistrosVistas(orderByOldest);
                }
            }
        }).create().show();//mostrar dialogo
    }
    */


}