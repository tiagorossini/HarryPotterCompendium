package com.istea.harrypottercompendium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//Esta clase contiene la lógica de logeo a través de Firebase
public class SignInActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    TextView textViewOlvido, textViewRegistrarse;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editTextEmail = (EditText) findViewById(R.id.editTextEmailSignIn);
        editTextPassword = (EditText) findViewById(R.id.editTextPasswordSignIn);

        textViewOlvido = (TextView) findViewById(R.id.txtForgotPasswordSignIn);
        textViewRegistrarse = (TextView) findViewById(R.id.txtRegisterSignIn);

        progressBar = (ProgressBar) findViewById(R.id.progressBarSignIn);

        mAuth = FirebaseAuth.getInstance();
    }

    //Redirige a la pantalla de recuperar contraseña
    public void txtSignInOlvidoContrasenaClicked(View view){
        Intent intent = new Intent(SignInActivity.this,ForgotPasswordActivity.class);
        startActivity(intent);
    }

    //Redirige a la pantalla de registrarse
    public void txtSignInRegistrarseClicked(View view){
        Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    //Valida que el email y la contraseña cumplan los requisitos necesarios y luego a traves de una función de Firebase realiza el Sign In,
    //siendo esta correcta o invalida tendrá dos accionares distintos, cuando se logea redirige a la pantalla principal de personajes y cuando no muestra un toast de error
    public void buttonSignInIngresarClicked(View view){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Debe ingresar un email válido");
            editTextEmail.requestFocus();
            return;
        }

        if (editTextPassword.length() < 6){
            editTextPassword.setError("La contraseña debe ser mayor a 6 caracteres");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setClass(getBaseContext(), WizardsActivity.class);
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignInActivity.this,"Usuario logeado correctamente", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignInActivity.this,"Error al intentar logear el usuario", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}