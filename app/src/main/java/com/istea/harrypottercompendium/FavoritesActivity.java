package com.istea.harrypottercompendium;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.istea.harrypottercompendium.databinding.ActivityFavoritesBinding;
import com.istea.harrypottercompendium.databinding.ActivityWizardsBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends DrawerBaseActivity {

    ActivityFavoritesBinding activityFavoritesBinding;
    EditText searchWizardName;


    List<Wizard> favList = new ArrayList<>();
    List<Wizard> favFilterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFavoritesBinding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        setContentView(activityFavoritesBinding.getRoot());
        allocateActivityTitle("Favoritos");

        searchWizardName = (EditText) findViewById(R.id.search_text_favorites);
        //favList = AppPreferences.getFavorites(getApplicationContext());
        //favFilterList = AppPreferences.getFavorites(getApplicationContext());

        for (Wizard w : AppPreferences.getFavorites(getApplicationContext()))
        {
            if (!w.getImage().isEmpty())
            {
                favList.add(w);
                favFilterList.add(w);
            }
        }

        processCards();
    }

    private void processCards(){
        LinearLayout layout = findViewById(R.id.favorites_card_container);
        layout.removeAllViews();
        for (Wizard w : favFilterList)
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

            view.findViewById(R.id.favButton).setOnClickListener(e -> {
                AppPreferences.deleteFavorite(getApplicationContext(), w);
                Toast.makeText(getApplicationContext(),"Quitado de favoritos",Toast.LENGTH_LONG).show();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            });

            layout.addView(view);
        }
    }

    public void buttonSearchWizardNameClicked(View view){
        String wizardName = searchWizardName.getText().toString().trim();

        searchFilter(wizardName);

    }

    private void searchFilter(String busqueda){
        favFilterList.clear();
        if(busqueda.isEmpty()){
            for (Wizard w : favList){

                favFilterList.add(w);

            }
        }
        else{
            for (Wizard w : favList){
                if (w.getName().toLowerCase().contains(busqueda.toLowerCase())){
                    favFilterList.add(w);
                }
            }
        }

        processCards();
    }
}