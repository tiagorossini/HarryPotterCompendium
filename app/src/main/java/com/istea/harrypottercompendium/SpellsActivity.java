package com.istea.harrypottercompendium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.istea.harrypottercompendium.databinding.ActivitySpellsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpellsActivity extends DrawerBaseActivity {

    List<Spell> spellsList = new ArrayList<>();
    List<Spell> spellsFilterList = new ArrayList<>();
    ActivitySpellsBinding activitySpellsBinding;
    EditText searchSpellName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySpellsBinding = ActivitySpellsBinding.inflate(getLayoutInflater());
        setContentView(activitySpellsBinding.getRoot());
        allocateActivityTitle("Hechizos");

        searchSpellName = (EditText) findViewById(R.id.search_text_spell);
        HPApi hpApi = ApiAdapter.getInstanceBis().create(HPApi.class); //Obtenemos la instancia de la API a traves de nuestra clase Api Adapter

        Call<List<Spell>> call = hpApi.getSpells(); // Generamos una instancia de la call al endpoint de la interface

        call.enqueue(new Callback<List<Spell>>() {

            //En este metodo se realiza la llamada a la API en caso de ser exitoso se recorre la respuesta de la API para cargar las listas de hechizos
            // En caso de que falle la API se muestra un mensaje de error
            @Override
            public void onResponse(Call<List<Spell>> call, Response<List<Spell>> response) {
                if (response.isSuccessful()){
                    for (Spell s : response.body())
                    {

                        spellsList.add(s);
                        spellsFilterList.add(s);

                    }
                    processCards();
                }
            }

            @Override
            public void onFailure(Call<List<Spell>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Ha ocurrido un error conectar con el servidor",Toast.LENGTH_LONG).show();
            }
        });
    }

    // Este metodo se encarga de tomar la lista de hechizos y arma las "cards" en donde se muestran
    private void processCards(){
        LinearLayout layout = findViewById(R.id.spells_card_container);
        layout.removeAllViews();
        for (Spell s : spellsFilterList)
        {
            View view = getLayoutInflater().inflate(R.layout.card_spell,null);
            ((TextView) view.findViewById(R.id.post_card_spell_name)).setText(s.getName());
            ((TextView) view.findViewById(R.id.post_card_spell_use)).setText(s.getUso());

            layout.addView(view);
        }
    }

    // Este es el botón de busqueda por nombre de los hechizos
    public void buttonSearchSpellNameClicked(View view){
        String spellName = searchSpellName.getText().toString().trim();

        searchFilter(spellName);

    }

    // En este metodo tomamos el texto ingresado por el usuario para la busqueda y buscamos sus coincidencias en la lista de hechizos
    // para retornar los resultados
    private void searchFilter(String busqueda){
        spellsFilterList.clear();
        if(busqueda.isEmpty()){
            for (Spell s : spellsList){

                spellsFilterList.add(s);

            }
        }
        else{
            for (Spell s : spellsList){
                if (s.getName().toLowerCase().contains(busqueda.toLowerCase())){
                    spellsFilterList.add(s);
                }
            }
        }

        processCards();
    }
}