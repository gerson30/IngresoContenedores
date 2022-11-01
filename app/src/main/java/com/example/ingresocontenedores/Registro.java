package com.example.ingresocontenedores;



import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registro extends AppCompatActivity {

    AwesomeValidation validacion;


    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 105;
    //variables para calendario
    private EditText efecha;
    private int dia, mes, ano;

    //View
    private CircleImageView imgPlaca, imgContenedor, imgSello;
    private EditText fechaIngresoEt, placaEt, nombreAuditEt;
    private Button Guardar;

    //actionbar
    private ActionBar actionBar;

    private static final int PICK_IMAGE_REQUEST = 104;
    private Bitmap gfoto;

    //Permiso de la clase Constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    //seleccion de imagen Constans
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;

    //matrices de permisos
    private String[] cameraPermissions; ///camara y almacenamiento
    private String[] storagePermissions;//solo almacenamiento

    //variables  (constain de datos para guardar)
    private Uri imagePlaca, imageContenedor, imageSello;
    private Uri uri1, uri2, uri3;
    private String id, fechaIngreso, placa, nombreaudit, addedTime, updatedTime;

    private boolean isEditMode = false;


    //db helper

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);


        //inicialización
        actionBar = getSupportActionBar();
        //Titulo
        actionBar.setTitle("AGREGAR REGISTRO");
        //Boton
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        fechaIngresoEt =  findViewById(R.id.txt_FechaIngreso);
        placaEt = findViewById(R.id.txt_Placa);
        nombreAuditEt = findViewById(R.id.txt_NombreAudit);
        imgPlaca = findViewById(R.id.btn_FotoPlaca);
        imgContenedor = findViewById(R.id.btn_FotoContenedor);
        imgSello = findViewById(R.id.btn_FotoSello);
        Guardar = findViewById(R.id.btn_Guardar);

        //aquí se realiza la validacion de cada EditText
        validacion = new AwesomeValidation(ValidationStyle.BASIC);

        /*validacion.addValidation(this,R.id.txt_FechaIngreso,
                RegexTemplate.NOT_EMPTY,R.string.invalid_fecha)*/;

        validacion.addValidation(this,R.id.txt_Placa,
                RegexTemplate.NOT_EMPTY,R.string.invalid_placa);

        validacion.addValidation(this,R.id.txt_NombreAudit,
                RegexTemplate.NOT_EMPTY,R.string.invalid_audit);

        validacion.addValidation(this,R.id.btn_FotoPlaca,
                RegexTemplate.NOT_EMPTY,R.string.invalid_fotoPlaca);

        validacion.addValidation(this,R.id.btn_FotoContenedor,
                RegexTemplate.NOT_EMPTY,R.string.invalid_fotoContenedor);

        validacion.addValidation(this,R.id.btn_FotoSello,
                RegexTemplate.NOT_EMPTY,R.string.invalid_fotoSello);
        Guardar.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (validacion.validate()) {
                    //redireccion a metodo input data
                    inputData();
                } else {
                    Toast.makeText(getApplicationContext()
                            , "VALIDACIÓN FALLIDA", Toast.LENGTH_SHORT).show();
                }
            }

        });

        //aquí se relaciona la opcion para editar
        //obtener los datos de la intencion (opcion vista de editar)
        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);

        //establer la vista de los datos (del boton editar)
        if (isEditMode) {

            //titulo (Actualizar datos)
            actionBar.setTitle("ACTUALIZAR REGISTRO");

            id = intent.getStringExtra("ID");
            fechaIngreso = intent.getStringExtra("FECHAINGRESO");
            placa = intent.getStringExtra("PLACAVEHICULO");
            nombreaudit = intent.getStringExtra("NOMBREAUDIT");
            imagePlaca = Uri.parse(intent.getStringExtra("FOTOPLACAVEHICULO"));
            imageContenedor = Uri.parse(intent.getStringExtra("FOTOCONTENEDOR"));
            imageSello = Uri.parse(intent.getStringExtra("FOTOSELLO"));
            addedTime = intent.getStringExtra("ADDED_TIME_STAMP");
            updatedTime = intent.getStringExtra("UPDATED_TIME_STAMP");

            //set View data
            fechaIngresoEt.setText(fechaIngreso);
            placaEt.setText(placa);
            nombreAuditEt.setText(nombreaudit);

            //sino se selecciona una imagen al agregr datos; el valor de la imagen ser "NULL"
            if (imagePlaca.toString().equals("null")) {
                //si no hay imagen , set default
                imgPlaca.setImageResource(R.drawable.sin_fotos);
            } else {

                imgPlaca.setImageURI(imagePlaca);
            }
            if (imageContenedor.toString().equals("null")) {
                //sino ahi imagen , set default
                imgContenedor.setImageResource(R.drawable.sin_fotos);
            } else {

                imgContenedor.setImageURI(imageContenedor);
            }
            //sino se selecciona una imagen al agregr datos; el valor de la imagen ser "NULL"
            if (imageSello.toString().equals("null")) {
                //sino ahi imagen , set default
                imgSello.setImageResource(R.drawable.sin_fotos);
            } else {

                imgSello.setImageURI(imageSello);
            }

        } else {
            //agregar datos
            actionBar.setTitle("AGREGAR REGISTRO");
        }

        //Inicializar BD Helper
        dbHelper = new DBHelper(this);

        //Inicializamos permisos arrays

        //camara
        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //almacenamiento de imágenes
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        imgPlaca.setOnClickListener(v -> {
            imagePickDialog();
        });
        imgContenedor.setOnClickListener(v -> {
            imagePickDialog();
        });
        imgSello.setOnClickListener(v -> {
            // muestra el cuadro de diálogo de selección de imagen
            imagePickDialog();
        });

        //Guardar.setOnClickListener(v -> inputData());

        //funcionalidad para edittext de fecha ingreso
        //efecha = (EditText) findViewById(R.id.txt_FechaIngreso);
        //efecha.setOnClickListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void inputData() {
        //get data
        //este es el formato para la fecha y hora
        DateTimeFormatter DateFormat =
                DateTimeFormatter
                        .ofPattern("EEEE, dd 'de' MMMM 'de' yyyy 'a las' hh:mm")

                        .withLocale(new Locale("es", "ES"));
        // se llama la variable DateFormat.format que es la que le da la estructura a la fecha y hora, luego se llama la libreria LocalDiteTame
        fechaIngreso = DateFormat.format(java.time.LocalDateTime.now());

        fechaIngreso = "" + fechaIngresoEt.getText().toString().trim();
        placa = "" + placaEt.getText().toString().trim();
        nombreaudit = "" + nombreAuditEt.getText().toString().trim();


        if (isEditMode) {
            //actualizar datos

            String timeStamp = "" + System.currentTimeMillis();
            dbHelper.updateRecord(
                    "" + id,
                    "" + fechaIngreso,
                    "" + placa,
                    "" + nombreaudit,
                    "" + imagePlaca,
                    "" + imageContenedor,
                    "" + imageSello,
                    "" + addedTime,//este dato no cambia fecha registro
                    "" + timeStamp//fecha de actualizacion cambia

             );

            Toast.makeText(this, "ACTUALIZANDO... ", Toast.LENGTH_SHORT).show();
        } else {
            //aqui se guardan los nuevos datos actualizados
            //guarda en la base de datos
            String timestamp = "" + System.currentTimeMillis();
            long id = dbHelper.insertRecord(
                    "" + fechaIngreso,
                    "" + placa,
                    "" + nombreaudit,
                    "" + imagePlaca,
                    "" + imageContenedor,
                    "" + imageSello,
                    "" + timestamp,
                    "" + timestamp
            );
        }

        Toast.makeText(this, "REGISTRO AGREGADO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
        Intent i3 = new Intent(Registro.this, IngresoVehiculo.class);
        startActivity(i3);
        finish();
    }

    //metodo de camara
    private void imagePickDialog() {
        //opciones para mostrar en el diálogo
        String[] options = {"CÁMARA", "GALERIA"};
        //diaolgo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Título
        builder.setTitle("SELECCIONE LA IMAGEN");
        //establecer elementos / opciones
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //manejar Clicks
                if (which == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        //permiso ya otorgado
                        PickFromCamera();
                    }
                } else if (which == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        //permiso ya otorgado
                        PickFromGallery();
                    }
                }
            }

        });
        //Crear /mostrar diálogo
        builder.create().show();
    }

    private void PickFromGallery() {
        //intento de elegir la imagen de la galería, la imagen se devolverá en el método onActivityResult
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void PickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "TÍTULO DE LA IMAGEN");
        values.put(MediaStore.Images.Media.DESCRIPTION, "DESCRIPCIÓN DE LA IMAGEN");
        //poner image URI
        imagePlaca = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        imageContenedor = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        imageSello = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //intento de abrir la cámara para la foto
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imagePlaca);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageContenedor);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageSello);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }


    private boolean checkStoragePermission() {
        //comprobar si el permiso de almacenamiento está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermission() {
        //solicita el permiso de almacenamento
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        //verifica si el permiso de la cámara esta habilidato o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission() {
        //solicita el permiso de la cámara
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//regrese haciendo clic en el boton de barra de acción
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //resultado del permiso permitido /denegadp
        switch (requestCode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted) {
                        //ambos permisos permitidos
                        PickFromCamera();
                    } else {
                        Toast.makeText(this, "SE REQUIERE PERMISOS DE CÁMARA Y ALMACENAMIENTO", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {

                    //si se permite devolver verdadero de lo contrario falso
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        //permiso de almacenamiento permitido
                        PickFromGallery();
                    } else {
                        Toast.makeText(this, "SE REQUIERE PERMISO DE ALMACENAMIENTO", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }

    //opción para mostrar calendario con fecha actual
    /*
    @Override
    public void onClick(View v) {
        if (v == efecha) {
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano = c.get(Calendar.YEAR);

            DatePickerDialog datepickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    efecha.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }
            }
                    //mostrar Date
                    , ano, mes, dia);
            datepickerDialog.show();
        }
        if (v == efecha) {

        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //image picked from camera or gallery will be received hare
        if (resultCode == RESULT_OK){
            //Image is picked
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //Picked from gallery

                //crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //Picked from camera
                //crop Image
                CropImage.activity(imagePlaca)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
                CropImage.activity(imageContenedor)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
                CropImage.activity(imageSello)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //Croped image received b
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if(uri1 == null){
                    uri1 = result.getUri();
                }
                else if(uri2 == null){
                    uri2 = result.getUri();
                }
                else if(uri3 == null){
                    uri3 = result.getUri();
                }

                if (resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    //este almacena en base de datos
                    imagePlaca = uri1;
                    //este almacena en la vista
                    imgPlaca.setImageURI(uri1);

                    //este almacena en base de datos
                    imageContenedor = uri2;
                    //este almacena en la vista
                    imgContenedor.setImageURI(uri2);

                    //este almacena en base de datos
                    imageSello = uri3;
                    //este almacena en la vista
                    imgSello.setImageURI(uri3);
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //ERROR
                    Exception error = result.getError();
                    Toast.makeText(this, " ERROR "+error, Toast.LENGTH_SHORT).show();
                }

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
