package ittepic.edu.practica1_u3barbosa;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class propietario implements Serializable {

    @Exclude private  String id;

    private String domicilio, edad,nacionalidad,nombre;

    public propietario() {
    }


    public propietario(String domicilio,String edad,String nacionalidad,String nombre){
        this.nombre=nombre;
        this.domicilio=domicilio;
        this.edad=edad;
        this.nacionalidad=nacionalidad;

    }
    public String getNombre(){
        return nombre;
    }
    public String getDomicilio(){return domicilio;}


    public String getEdad() {
        return edad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
