package com.example.honeycumb.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.honeycumb.R;
import com.example.honeycumb.activities.Providers.UserProviders;
import com.example.honeycumb.activities.Providers.AuthProviders;
import com.example.honeycumb.activities.Utils.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    CircleImageView mCircleImageViewBack;
    TextInputEditText mTextInputEditTextUsername, mTextInputEditTextEmailRegister,mTextInputEditTextPasswordRegister,mTextInputEditTextPasswordConfirms;
    Button mbuttonRegister;
    AuthProviders mAuthProviders;
    UserProviders mUserProviders;
    AlertDialog mAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mCircleImageViewBack=findViewById(R.id.circleimgblack); /*busca el identificador para instanciar*/
        mTextInputEditTextUsername=findViewById(R.id.TextInputEditTextUsername);
        mTextInputEditTextEmailRegister=findViewById(R.id.TextInputEditTextEmailRegister);
        mTextInputEditTextPasswordRegister=findViewById(R.id.TextInputEditTextPasswordRegister);
        mTextInputEditTextPasswordConfirms=findViewById(R.id.TextInputEditTextPasswordConfirms);
        mbuttonRegister=findViewById(R.id.buttonRegister);

        mAuthProviders=new AuthProviders();
        mUserProviders = new UserProviders();

        mAlertDialog= new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espera un momento por favor")
                .setCancelable(false)
                .build();

        mbuttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        mCircleImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); /*metodo finish nos lleva a la pantalla anterior y finaliza la actividad*/
            }
        });
    }


    private void register() {
        String username=mTextInputEditTextUsername.getText().toString();
        String emailRegister=mTextInputEditTextEmailRegister.getText().toString();
        String passwordRegister=mTextInputEditTextPasswordRegister.getText().toString();
        String  passwordconfirm=mTextInputEditTextPasswordConfirms.getText().toString();

        if(!username.isEmpty() && !emailRegister.isEmpty() && !passwordRegister.isEmpty() && !passwordconfirm.isEmpty()){
            if(isEmailValid(emailRegister)){
                if(passwordRegister.equals(passwordconfirm)){
                    if(passwordRegister.length()>=6){/*validacion de los datos ingresados*/
                        createuser(emailRegister, passwordRegister, username);
                        Toast.makeText(this, "Todos los campos son validos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Correo no es valido", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
        }
    }



    private void createuser(final String emailRegister, String passwordRegister,final String username) {/*no se pone final a contraseña para poderse cambiar despues*/
        mAuthProviders.register(emailRegister,passwordRegister).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mAlertDialog.show();
                if (task.isSuccessful()) {/*si la tarea se realizo */
                    String id = mAuthProviders.getUid();/*Asignar la id(se cre la documentacion o colecion )*/
                    User user= new User();
                    user.setId(id);
                    user.setEmail(emailRegister);
                    user.setUsername(username);
                    user.setPassword(passwordRegister);
                    mUserProviders.create(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mAlertDialog.dismiss();
                            if(task.isSuccessful() ) {
                                Toast.makeText(RegisterActivity.this, "EL usuario se almaceno correcamente", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else{
                                Toast.makeText(RegisterActivity.this, "No se pudo almacenar el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });/*se envian los datos*/
                    Toast.makeText(RegisterActivity.this, "El usuario se creo correctamente", Toast.LENGTH_SHORT).show();

                }else{
                    mAlertDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario en la base de datos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    public boolean isEmailValid(String emailRegister) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);/* metodo de validacion del correo*/
        Matcher matcher = pattern.matcher(emailRegister);
        return matcher.matches();
    }
}