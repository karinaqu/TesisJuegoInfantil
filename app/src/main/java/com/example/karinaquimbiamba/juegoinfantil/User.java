package com.example.karinaquimbiamba.juegoinfantil;

public class User {

    public String name, email;//declaraación de atributos nombre y correo
    public String edad;//definción de atributo edad

    public User(){ //definición e metodo usuario sin atributos por defecto

    }

    public User(String name, String email, String edad){//definición de metodo con atributos para ser llamado
        this.name = name;//referenciar objeto nombre
        this.email= email;//Referenciar objeto correo
        this.edad= edad;//Referenciar objeto edad
    }

}
