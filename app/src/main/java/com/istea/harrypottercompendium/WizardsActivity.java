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

import com.istea.harrypottercompendium.databinding.ActivityWizardsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//En esta clase manejamos la lógica para cargar el listado de Personajes
public class WizardsActivity extends DrawerBaseActivity {
    List<Wizard> wizardList = new ArrayList<>();
    List<Wizard> wizardFilterList = new ArrayList<>();
    ActivityWizardsBinding activityWizardsBinding;
    EditText searchWizardName;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWizardsBinding = ActivityWizardsBinding.inflate(getLayoutInflater());
        setContentView(activityWizardsBinding.getRoot());
        allocateActivityTitle("Personajes");

        searchWizardName = (EditText) findViewById(R.id.search_text_wizard);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_wizard);
        HPApi hpApi = ApiAdapter.getInstance().create(HPApi.class); //Obtenemos la instancia de la API a traves de nuestra clase Api Adapter

        Call<List<Wizard>> call = hpApi.getWizards(); // Generamos una instancia de la call al endpoint de la interface

        call.enqueue(new Callback<List<Wizard>>() {

            //En este metodo se realiza la llamada a la API en caso de ser exitoso se recorre la respuesta de la API para cargar las listas de personaje
            // En caso de que falle la API se muestra un mensaje de error
            @Override
            public void onResponse(Call<List<Wizard>> call, Response<List<Wizard>> response) {
                if (response.isSuccessful()){
                    for (Wizard w : response.body())
                    {
                        if (!w.getImage().isEmpty())
                        {
                            wizardList.add(w);
                            wizardFilterList.add(w);
                        }
                    }
                    processCards();
                }
            }

            @Override
            public void onFailure(Call<List<Wizard>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Ha ocurrido un error conectar con el servidor",Toast.LENGTH_LONG).show();
            }
        });
    }

    // Este metodo se encarga de tomar la lista de personajes y armar las "cards" en donde se muestran
    // tiene las validaciones necesarias para cuando un campo no tiene información que no lo muestre
    private void processCards(){
        LinearLayout layout = findViewById(R.id.wizards_card_container);
        layout.removeAllViews();
        for (Wizard w : wizardFilterList)
        {
            View view = getLayoutInflater().inflate(R.layout.card,null);
            ImageView image = view.findViewById(R.id.post_card_image);
            Picasso.get().load(w.getImage()).fit().into(image);
            ((TextView) view.findViewById(R.id.post_card_wizard_name)).setText(w.getName());

            if(w.getGender().equalsIgnoreCase("male"))
                ((TextView) view.findViewById(R.id.post_card_wizard_gender)).setText("Masculino");
            else if(w.getGender().equalsIgnoreCase("female"))
                ((TextView) view.findViewById(R.id.post_card_wizard_gender)).setText("Femenino");
            else
                ((TextView) view.findViewById(R.id.post_card_wizard_gender)).setText(w.getGender());

            if (!w.getHouse().isEmpty())
                ((TextView) view.findViewById(R.id.post_card_wizard_house_text)).setText(w.getHouse());
            else
                ((LinearLayout) view.findViewById(R.id.post_card_wizard_house)).setVisibility(View.GONE);

            if (!w.getDateOfBirth().isEmpty())
                ((TextView) view.findViewById(R.id.post_card_wizard_birthday_text)).setText(w.getDateOfBirth());
            else
                ((LinearLayout) view.findViewById(R.id.post_card_wizard_birthday)).setVisibility(View.GONE);

            if (!w.getPatronus().isEmpty())
                ((TextView) view.findViewById(R.id.post_card_wizard_patronus_text)).setText(w.getPatronus());
            else
                ((LinearLayout) view.findViewById(R.id.post_card_wizard_patronus)).setVisibility(View.GONE);

            if (!w.getActor().isEmpty())
                ((TextView) view.findViewById(R.id.post_card_wizard_actor_text)).setText(w.getActor());
            else
                ((LinearLayout) view.findViewById(R.id.post_card_wizard_actor)).setVisibility(View.GONE);

            //Esta es la funcionalidad de Favoritos
            view.findViewById(R.id.favButton).setOnClickListener(e -> {
                AppPreferences.addFavorite(getApplicationContext(), w);
                Toast.makeText(getApplicationContext(),"Añadido a favoritos",Toast.LENGTH_LONG).show();
            });
            layout.addView(view);
        }
    }

    // Este es el botón de busqueda por nombre de los personajes
    public void buttonSearchWizardNameClicked(View view){
        String wizardName = searchWizardName.getText().toString().trim();

            progressBar.setVisibility(View.VISIBLE);
            searchFilter(wizardName);
            progressBar.setVisibility(View.GONE);
    }

    // En este metodo tomamos el texto ingresado por el usuario para la busqueda y buscamos sus coincidencias en la lista de personajes
    // para retornar los resultados
    private void searchFilter(String busqueda){
        wizardFilterList.clear();
        if(busqueda.isEmpty()){
            for (Wizard w : wizardList){

                wizardFilterList.add(w);

            }
        }
        else{
            for (Wizard w : wizardList){
                if (w.getName().toLowerCase().contains(busqueda.toLowerCase())){
                    wizardFilterList.add(w);
                }
            }
        }

        processCards();
    }


}