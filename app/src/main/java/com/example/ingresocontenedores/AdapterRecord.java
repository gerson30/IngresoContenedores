package com.example.ingresocontenedores;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecord extends RecyclerView.Adapter<AdapterRecord.HolderRecord>{

    //Variables
    private Context context;
    private ArrayList<ModelRecord> recordsList;



    //DB helper
    DBHelper dbHelper;

    //Constructor
    public AdapterRecord(Context context, ArrayList<ModelRecord> recordsList){
        this.context = context;
        this.recordsList = recordsList;
        dbHelper = new DBHelper(context);
    }

    @NonNull
    @Override
    public HolderRecord onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.registro_fila, parent, false);

        return new HolderRecord(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderRecord holder, @SuppressLint("RecyclerView") int position) {

        // obtener datos, establecer datos, ver clics en el método

        //Obtener datos
        ModelRecord model = recordsList.get(position);
        final String id = model.getId();
        String fechaIngreso = model.getFechaIngreso();
        String placaVehiculo = model.getPlacaVehiculo();
        String nombreAudit = model.getNombreAudit();
        String fotoPlacaVehiculo = model.getFotoPlacaVehiculo();
        String fotoContenedor = model.getFotoContenedor();
        String fotoSello = model.getFotoSello();
        String addedTime = model.getAddedTime();
        String updatedTime = model.getUpdatedTime();

        //Establecer Datos
        holder.fechaIngreso.setText(fechaIngreso);
        holder.placaVehiculo.setText(placaVehiculo);
        holder.nombreAudit.setText(nombreAudit);

        // si el usuario no adjunta la imagen, imageUri será nulo, por lo tanto,
        // configure una imagen predeterminada en ese caso

        //fotoPlacaVehiculo
        if (fotoPlacaVehiculo.equals("null")){
            // no hay imagen en el registro, establecer predeterminado
            holder.imagePlaca.setImageResource(R.drawable.sin_fotos);
        }
        else {
            // tener imagen en el registro
            holder.imagePlaca.setImageURI(Uri.parse(fotoPlacaVehiculo));
        }

        //fotoContenedor
        if (fotoContenedor.equals("null")){
            // no hay imagen en el registro, establecer predeterminado
            holder.imageContenedor.setImageResource(R.drawable.sin_fotos);
        }
        else {
            // tener imagen en el registro
            holder.imageContenedor.setImageURI(Uri.parse(fotoContenedor));
        }

        //
        if (fotoSello.equals("null")){
            // no hay imagen en el registro, establecer predeterminado
            holder.imageSello.setImageResource(R.drawable.sin_fotos);
        }
        else {
            // tener imagen en el registro
            holder.imageSello.setImageURI(Uri.parse(fotoSello));
        }

        // manejar clicks de elementos (ir a la actividad de detalle registro)

        holder.itemView.setOnClickListener(v -> {
            ////Pasar el id del registro a la siguiente actividad para mostrar los detalles del registro
            Intent intent = new Intent(context, DetalleRegistroActivity.class);
            intent.putExtra("RECORD_ID", id);
            context.startActivity(intent);
        });

        //manejar clicks de botones (mostrar opciones como editar, eliminar)

        holder.moreBtn.setOnClickListener(v -> {
            //mostrar el menu de opciones
            showMoreDialog(
                    "" +position,
                    "" +id,
                    "" +fechaIngreso,
                    "" +placaVehiculo,
                    "" +nombreAudit,
                    "" +fotoPlacaVehiculo,
                    "" +fotoContenedor,
                    "" +fotoSello,
                    "" +addedTime,
                    "" +updatedTime);
        });
    }
    //dialogo de editar y eliminar
    public void showMoreDialog( String position, final String id, final String fechaIngreso, final String placaVehiculo, final String nombreAudit,
                                final String fotoPlacaVehiculo, final String fotoContenedor, final String fotoSello, final String addedTime, final String updatedTime){
        //opciones para mostrar en el dialogo
        String [] options = {"Editar", "Eliminar"};
        //Dialogo
        AlertDialog.Builder alertMore = new AlertDialog.Builder(context);
        //Agregar elementos al dialogo
        alertMore.setItems(options, (dialog, which) -> {
            if (which == 0){
                //si hace click en editar
                //iniciará la actividad para actualizar los registros existentes
                Intent intent = new Intent(context, Registro.class);
                intent.putExtra("ID", id);
                intent.putExtra("FECHAINGRESO", fechaIngreso);
                intent.putExtra("PLACAVEHICULO", placaVehiculo);
                intent.putExtra("NOMBREAUDIT", nombreAudit);
                intent.putExtra("FOTOPLACAVEHICULO", fotoPlacaVehiculo);
                intent.putExtra("FOTOCONTENEDOR", fotoContenedor);
                intent.putExtra("FOTOSELLO", fotoSello);
                intent.putExtra("ADDEDTIME", addedTime);
                intent.putExtra("UPDATEDTIME", updatedTime);
                intent.putExtra("isEditMode", true);//necesita para establecer datos existente
                context.startActivity(intent);
            }
            else if (which == 1){
                //opciones para mostrar en el dialogo
                String [] options2 = {"Si", "No"};
                //Dialogo
                AlertDialog.Builder alertDelete = new AlertDialog.Builder(context);
                alertDelete.setTitle("ATENCIÓN");
                alertDelete.setMessage("¿Está seguro que desea eliminar este registro?");
                //opciones para mostrar en el dialogo
                alertDelete.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //si elimina será borrado de la base de datos
                        dbHelper.deleteData(id);
                        //Aviso emergente donde muestra el comentario mas el Id que se se llama con la variable Message dentro de CharSequence
                        CharSequence Message = "Se elimino el registro con placa:  " + placaVehiculo;
                        Toast.makeText(context, Message, Toast.LENGTH_LONG).show();
                        //actualizar registros llamando actividades en el método de reanudar
                        ((IngresoVehiculo)context).onResume();
                    }
                });
                alertDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDelete.create().show();
            }

        });
        //Mostrar el dialogo
        alertMore.create().show();
    }

                /*//Agregar elementos al dialogo
                alertDelete.setItems(options2, (dialog2, which2) -> {
                    if (which2 == 0){
                        //Hace click en la opcion eliminar
                        dbHelper.deleteData(id);
                        //actualizar registros llamando actividades en el método de reanudar
                        ((IngresoVehiculo)context).onResume();
                    }
                    else if (which2 == 1){

                    }*/



    @Override
    public int getItemCount() {
        return recordsList.size();// devuelve el tamaño de la lista / número o registros
    }

    class HolderRecord extends RecyclerView.ViewHolder{
        //vistas
        ImageView imagePlaca, imageContenedor, imageSello;
        TextView fechaIngreso, placaVehiculo, nombreAudit;
        ImageButton moreBtn;
        public HolderRecord(@NonNull View itemView){
            super(itemView);

            //Inicializamos la vistas

            fechaIngreso = itemView.findViewById(R.id.txt_FechaIngreso);
            placaVehiculo = itemView.findViewById(R.id.txt_Placa);
            nombreAudit = itemView.findViewById(R.id.txt_NombreAudit);
            imagePlaca = itemView.findViewById(R.id.btn_FotoPlaca);
            imageContenedor = itemView.findViewById(R.id.btn_FotoContenedor);
            imageSello = itemView.findViewById(R.id.btn_FotoSello);
            moreBtn = itemView.findViewById(R.id.btn_mas);

        }
    }
}
