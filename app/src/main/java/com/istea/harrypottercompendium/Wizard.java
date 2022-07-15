package com.istea.harrypottercompendium;

import com.google.gson.annotations.SerializedName;


//Esta clase Wizard es la que nos servira para mapear los personajes traídos de la API
public class Wizard {
    @SerializedName("name")
    private String name;
    @SerializedName("species")
    private String species;
    @SerializedName("gender")
    private String gender;
    @SerializedName("house")
    private String house;
    @SerializedName("dateOfBirth")
    private String dateOfBirth;
    @SerializedName("wizard")
    private String wizard;
    @SerializedName("patronus")
    private String patronus;
    @SerializedName("hogwartsStudent")
    private String hogwartsStudent;
    @SerializedName("actor")
    private String actor;
    @SerializedName("image")
    private String image;

    public String getName() { return name; }

    public String getSpecies() { return species; }

    public String getGender() { return gender; }

    public String getHouse() { return house; }

    public String getDateOfBirth() { return dateOfBirth; }

    public String getWizard() { return wizard; }

    public String getPatronus() { return patronus; }

    public String getHogwartsStudent() { return hogwartsStudent; }

    public String getActor() { return actor; }

    public String getImage() { return image; }


    //Este metodo se utiliza para leer más correctamente las properties de la clase
    @Override
    public String toString() {
        return "Wizard{" +
                "name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", gender='" + gender + '\'' +
                ", house='" + house + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", wizard='" + wizard + '\'' +
                ", patronus='" + patronus + '\'' +
                ", hogwartsStudent='" + hogwartsStudent + '\'' +
                ", actor='" + actor + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
