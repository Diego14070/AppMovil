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
import com.example.honeycumb.activities.Providers.AuthProviders;
import com.example.honeycumb.activities.Providers.UserProviders;
import com.example.honeycumb.activities.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import dmax.dialog.SpotsDialog;

public class CompletprofileActivity extends AppCompatActivity {
    TextInputEditText mTextInputEditTextUsernameC;
    Button mButtonConfirm;
    AuthProviders mAuthProviders;
    UserProviders mUserProviders;
    AlertDialog mAlertDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completprofile);

        mButtonConfirm = findViewById(R.id.buttonConfirm);
        mTextInputEditTextUsernameC = findViewById(R.id.TextInputEditTextUsernameC);

        mAuthProviders = new AuthProviders();
        mUserProviders = new UserProviders();

        mAlertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espera un momento por favor")
                .setCancelable(false)
                .build();

        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String username=mTextInputEditTextUsernameC.getText().toString();
        if(!username.isEmpty()){
            update(username);
        }else{
            Toast.makeText(this, "Ingrese el nombre", Toast.LENGTH_SHORT).show();
        }
    }

    private void update(String username) {
        mAlertDialog.show();
        String id=mAuthProviders.getUid();

        User user=new User();
        user.setUsername(username);
        user.setId(id);
        /*User user=new User();
        user.setUsername(username);
        user.setEmail(id);*/
        mUserProviders.updateC(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mAlertDialog.dismiss();
                if (task.isSuccessful()){
                    Intent intent =new Intent (CompletprofileActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(CompletprofileActivity.this, "No se pudo crear perfil", Toast.LENGTH_SHORT).show();
                }
            }
        });
    mAlertDialog.dismiss();
    }
}