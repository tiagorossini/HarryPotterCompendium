package com.istea.harrypottercompendium;

//Clase User para el manejo de los usuarios
public class User {
    public String email;
    public String userName;
    public String password;

    public User(){ }

    public User(String email,String userName, String password){
        this.email = email;
        this.userName = userName;
        this.password = password;
    }


}
