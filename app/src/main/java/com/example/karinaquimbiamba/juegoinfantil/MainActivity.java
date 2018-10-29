package com.example.karinaquimbiamba.juegoinfantil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.flags.impl.DataUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {//implemetación de evento clic

    private Button buttonRegister; //Definción de variable botón para registrar
    private EditText editTextEmail; //Definición de variable de texto para ingresar el email
    private EditText editTextPassword; //Definción de variable de texto para el ingreso de contraseña
    private TextView textViewLogin; //Definición de variable de texto para acceso al login
    private EditText editTextNombre; // Definición de variable de texto para ingreso de nombre
    private EditText editTextEdad; // Definición de variable de texto para ingreso de nombre

    private ProgressDialog progressDialog; //Definición de variable de Dialogo
    private FirebaseAuth firebaseAuth; //Definición de variable para registro de usuarios en firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) { //metodo creado pro defecto
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog= new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(),AreasActivity.class));

        }

        buttonRegister =(Button) findViewById(R.id.btnRegistrar);
        editTextEmail =(EditText) findViewById(R.id.txtEmail);
        editTextPassword =(EditText) findViewById(R.id.txtPassword);
        textViewLogin =(TextView) findViewById(R.id.txtvLogin);
        editTextNombre= (EditText) findViewById(R.id.txtNombre);
        editTextEdad= (EditText) findViewById(R.id.txtEdad);
        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser()!=null){

        }
    }

    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String name = editTextNombre.getText().toString().trim();
        final String edad = editTextEdad.getText().toString().trim(); //Definción del tipo de variable edad

        if (name.isEmpty()){
            Toast.makeText(this,"Ingrese el nombre", Toast.LENGTH_SHORT).show();
            editTextNombre.setError("Es necesario ingresar el nombre");
            editTextNombre.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Ingrese un correo electronico", Toast.LENGTH_SHORT).show();
            editTextEmail.setError("Es necesario ingresar el correo");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Ingrese un correo válido");
            editTextEmail.requestFocus();
            return;
        }


        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Ingrese la contraseña", Toast.LENGTH_SHORT).show();
            editTextPassword.setError("Es necesario ingresar la contraseña");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            Toast.makeText(this,"Ingrese su contraseña", Toast.LENGTH_SHORT).show();
            editTextPassword.setError("Contraseña debe tener por lo menos 6 caracteres");
            editTextPassword.requestFocus();
            return;
        }
        if (edad.isEmpty()){
            Toast.makeText(this,"Ingrese la edad", Toast.LENGTH_SHORT).show();
            editTextEdad.setError("Es necesario ingresar la edad");
            editTextEdad.requestFocus();
            return;
        }

        progressDialog.setMessage("Registrando Usuario.....");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(name,email,edad);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "Registro exitoso",Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),AreasActivity.class));
                                    }else {

                                    }

                                }
                            });

                        }else {
                            Toast.makeText(MainActivity.this, "Could not register, please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister){
            registerUser();
        }
        if (view == textViewLogin){
            startActivity(new Intent(this,LoginActivity.class));

        }

    }
}
