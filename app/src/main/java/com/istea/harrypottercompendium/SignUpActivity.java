package com.istea.harrypottercompendium;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


//Esta clase contiene la lógica para registrarse utilizando la funcionalidad de Firebase
public class SignUpActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextUserName;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextUserName = (EditText)findViewById(R.id.editTextUserName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
    }


    // Una vez presionado el botón, valida que los campos cumplan ciertos requisitos y crea el usuario con los datos brindados a través de
    // la funcionalidad de Firebase para hacerlo, luego tiene dos accionar, dependiendo el resultado lanza un mensaje u otro
    public void signupButtonClicked(View v){
        String txtEmail = editTextEmail.getText().toString().trim();
        String txtPassword = editTextPassword.getText().toString().trim();
        String txtUserName = editTextUserName.getText().toString().trim();


        if (txtEmail.isEmpty()){
            editTextEmail.setError("Debe completar el campo Email!");
            editTextEmail.requestFocus();
            return;
        }

        if (txtUserName.isEmpty()){
            editTextUserName.setError("Debe completar el campo Nombre de Usuario!");
            editTextUserName.requestFocus();
            return;
        }

        if (txtPassword.isEmpty() || txtPassword.length() < 6){
            editTextPassword.setError("La contraseña debe tener al menos 6 caracteres");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //Creamos el usuario con los datos brindados por el mismo
        mAuth.createUserWithEmailAndPassword(txtEmail,txtPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //En el caso que sea un registro exitoso guardamos los datos del usuario en la base de datos

                            User user = new User(txtEmail,txtUserName,txtPassword);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(SignUpActivity.this,"Usuario registrado exitosamente", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                            else{
                                                Toast.makeText(SignUpActivity.this,"Error al registrar usuario", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        }
                        else{
                            Toast.makeText(SignUpActivity.this,"Error al registrar usuario", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}