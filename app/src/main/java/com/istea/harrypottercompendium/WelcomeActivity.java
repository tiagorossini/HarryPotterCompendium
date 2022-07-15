package com.istea.harrypottercompendium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//En esta clase se almacena la vista principal del login
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Prueba de conexión con la base de datos
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Coding");

        // Leemos de la base de datos
        myRef.addValueEventListener(new ValueEventListener() {

            //Este metodo nos sirve cuando un dato es cambiado se logea
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String value = dataSnapshot.getValue(String.class);
                Log.d("HARRY_POTTER_COMPENDIUM", "Value is: " + value);
            }

            //Este metodo sirve para leer un error en la base de datos
            @Override
            public void onCancelled(DatabaseError error) {

                Log.w("HARRY_POTTER_COMPENDIUM", "Failed to read value.", error.toException());
            }
        });

    }

    //Estos botones son los que redirigen a las pantallas de ingreso y registro
    public void onButtonIngresarClicked(View view){
        Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
        startActivity(intent);
    }

    public void onButtonRegistrarseClicked(View view){
        Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
        startActivity(intent);

    }
}