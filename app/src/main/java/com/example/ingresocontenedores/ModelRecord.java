package com.example.ingresocontenedores;

/* Modelo de la clase para RecyclerView*/
public class ModelRecord {
    //Variables
    String id, fechaIngreso, placaVehiculo, nombreAudit, fotoPlacaVehiculo, fotoContenedor, fotoSello, addedTime;

    //Constructor

    public ModelRecord(String id, String fechaIngreso, String placaVehiculo, String nombreAudit, String fotoPlacaVehiculo,
                       String fotoContenedor, String fotoSello, String addedTime, String updatedTime) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
        this.placaVehiculo = placaVehiculo;
        this.nombreAudit = nombreAudit;
        this.fotoPlacaVehiculo = fotoPlacaVehiculo;
        this.fotoContenedor = fotoContenedor;
        this.fotoSello = fotoSello;
        this.addedTime = addedTime;
        this.updatedTime = updatedTime;
    }



    //Getter y Setter


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public String getNombreAudit() {
        return nombreAudit;
    }

    public void setNombreAudit(String nombreAudit) {
        this.nombreAudit = nombreAudit;
    }

    public String getFotoPlacaVehiculo() {
        return fotoPlacaVehiculo;
    }

    public void setFotoPlacaVehiculo(String fotoPlacaVehiculo) {
        this.fotoPlacaVehiculo = fotoPlacaVehiculo;
    }

    public String getFotoContenedor() {
        return fotoContenedor;
    }

    public void setFotoContenedor(String fotoContenedor) {
        this.fotoContenedor = fotoContenedor;
    }

    public String getFotoSello() {
        return fotoSello;
    }

    public void setFotoSello(String fotoSello) {
        this.fotoSello = fotoSello;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    String updatedTime;



}
