package ittepic.edu.practica1_u3barbosa;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Carro implements Serializable {
@Exclude private  String id;
    private String placa, color, marca,tipo;


    public Carro() {

    }

    public Carro(String placa, String color, String marca, String tipo) {
        this.placa = placa;
        this.color = color;
        this.marca = marca;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getColor() {
        return color;
    }

    public String getMarca(){return marca;}

    public String getTipo() {
        return tipo;
    }

}
