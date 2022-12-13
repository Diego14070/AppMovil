package com.example.honeycumb.activities.Utils.models;

public class Pokemon {
    private int number;//las imagenes vienen con un numero se desliga el numero de la url
    private String name;
    private String url;


    public int getNumber() {
        String [] urlparte= url.split("/"); //separar de la url
        return Integer.parseInt(urlparte[urlparte.length-1]);// definir desde que posicion los traera
    }

    public void setNumber(int numero) {
        this.number = numero;
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
