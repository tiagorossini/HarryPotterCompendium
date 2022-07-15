package com.istea.harrypottercompendium;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

//Interface para implementar el endpoint de llamada a la API
public interface HPApi {

    @GET("characters")
    Call<List<Wizard>> getWizards();

    @GET("hechizos")
    Call<List<Spell>> getSpells();
}
