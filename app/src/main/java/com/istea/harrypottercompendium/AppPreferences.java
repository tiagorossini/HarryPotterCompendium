package com.istea.harrypottercompendium;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//Aún no implementada!!! En esta clase seteamos el manejo de las Shared Preferences de nuestra app, para en un futuro utilizar una lista de favoritos.

public class AppPreferences {

    private static final String WIZARDS_KEY = "wizards";

    private static final Gson GSON = new Gson();

    private static SharedPreferences preferences;

    private static SharedPreferences getPreferences(Context context){
        if (preferences == null){
            preferences = context.getApplicationContext().getSharedPreferences("app_preferences", Context.MODE_PRIVATE);
        }
        return  preferences;
    }

    //Metodo para agregar un personaje a la lista de favoritos
    public static void addFavorite(Context context, Wizard wizard){
        List<Wizard> wizardList = getFavorites(context);

        for(Wizard w : wizardList){
            if (w.getName().equals(wizard.getName())){
                return;
            }
        }
        wizardList.add(wizard);
        String json = GSON.toJson(wizardList);
        getPreferences(context).edit().putString(WIZARDS_KEY, json).apply();
    }

    //Metodo para obtener la lista de favoritos desde las Shared Preferences
    public static List<Wizard> getFavorites(Context context){
        String obtainedPreference = getPreferences(context).getString(WIZARDS_KEY, null);

        if (obtainedPreference == null){
            return new ArrayList<Wizard>();
        }
        else{
            Type listType = new TypeToken<ArrayList<Wizard>>(){}.getType();
            return GSON.fromJson(obtainedPreference, listType);
        }
    }

    //Metodo para borrar un personaje a la lista de favoritos
    public static void deleteFavorite(Context context, Wizard wizard){
        List<Wizard> wizardList = getFavorites(context);

        for(Wizard w : wizardList){
            if (w.getName().equals(wizard.getName())){
                wizardList.remove(w);
                break;
            }
        }
        String json = GSON.toJson(wizardList);
        getPreferences(context).edit().putString(WIZARDS_KEY, json).apply();
    }

}
