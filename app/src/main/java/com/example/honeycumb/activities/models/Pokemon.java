package com.example.honeycumb.activities.models;

public class Pokemon {
    private int numero;//las imagenes vienen con un numero se desliga el numero de la url
    private String name;
    private String url;


    public int getNumero() {
        
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
