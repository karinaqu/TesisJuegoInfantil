package com.example.karinaquimbiamba.juegoinfantil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //Definición de variables
    private Button buttonIngresar; // Definición de variable para el botón Ingresar
    private EditText editTextEmail; // Definición de variable para el texto de email
    private EditText editTextPassword; //Definición de variable para el texto de contraseña
    private TextView textViewRegistrar;//Definición de variable para el texto de Registro
    private ProgressDialog progressDialog; //Definición de variable de Dialogo
    private FirebaseAuth firebaseAuth; //Definción de variable de Authenticación

    //metodo creado por defecto al crear las pantallas
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Asociar variables definidas con los elementos de las pantallas
        buttonIngresar =(Button) findViewById(R.id.btnIngresar); //Asociación de la variable con el boton Ingresar
        editTextEmail =(EditText) findViewById(R.id.txtEmail); //Asociación de la variable con el cajón de texto correo
        editTextPassword =(EditText) findViewById(R.id.txtPassword); //Asociación de la variable con el cajón de texto contraseña
        textViewRegistrar =(TextView) findViewById(R.id.txtvRegistrar); //Asociación de la variable con el texto de Registro

        buttonIngresar.setOnClickListener(this); //Definción del evento clic sobre el botón Ingresar
        textViewRegistrar.setOnClickListener(this); //Definición de evento clic sobre el texto Registrar

        progressDialog= new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(),AreasActivity.class));

        }
    }
    //Metodo de Ingreso de Usuario
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim(); //Definción del tipo de variable email
        String password = editTextPassword.getText().toString().trim();// Definición de tipo de variable de contraseña
        //Condición para establecer un mensaje de ingreso de variabbles
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Ingrese el correo electrónico", Toast.LENGTH_SHORT).show();
            editTextEmail.setError("Es necesario ingresar el correo");
            editTextEmail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //Mensaje de aviso para ingresar contraseña
            Toast.makeText(this,"Ingrese la contraseña", Toast.LENGTH_SHORT).show();
            editTextPassword.setError("Es necesario ingresar la contraseña");
            editTextPassword.requestFocus();
            return;

        }
        progressDialog.setMessage("Ingresando al Sistema....."); //Mensaje de diálogo al entrar a la siguiente pantalla
        progressDialog.show();//Desplegar Mensaje de Diálogo

        //Autenticación de usuarios ingreso de contraseña y correo
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //Condición que establece que si es exitoso se cumpla la condición
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), AreasActivity.class));//Muestra pantalla de Áreas del usuario

                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Usuario o Contraseña incorrectos",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    //Implementación de método clic
    @Override
    public void onClick(View view) {
        //Condición de boton al dar clic
        if (view == buttonIngresar){
            userLogin();//Llamda al método
        }
        //condición de texto al dar clic sobre el
        if (view == textViewRegistrar){
            finish();
            startActivity(new Intent(this, MainActivity.class));//Abrir  pantalla para el registro

        }

    }
}
