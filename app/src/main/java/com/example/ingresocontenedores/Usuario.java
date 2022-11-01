package com.example.ingresocontenedores;

public class Usuario {
    int id;
    String Nombre, Apellidos, Usuario, Password;


    //constructor ID
    public Usuario() {
    }

    //Constructor nombre,apellidos,usuario,password
    public Usuario(String nombre, String apellidos, String usuario, String password) {
        Nombre = nombre;
        Apellidos = apellidos;
        Usuario = usuario;
        Password = password;
    }

    public boolean isNull(){
        if (Nombre.equals("")&&Apellidos.equals("")&&Password.equals("")){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", Nombre='" + Nombre + '\'' +
                ", Apellidos='" + Apellidos + '\'' +
                ", Usuario='" + Usuario + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }

    //gett and Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos (String apellidos) {
        Apellidos = apellidos;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
