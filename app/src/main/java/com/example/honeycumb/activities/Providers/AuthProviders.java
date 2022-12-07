package com.example.honeycumb.activities.Providers;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class AuthProviders {
    private FirebaseAuth mAuth;
    /*authentication*/

    public AuthProviders() {
        mAuth=FirebaseAuth.getInstance();
    }


    public String getEmail(){
        if(mAuth.getCurrentUser() !=null){
            return mAuth.getCurrentUser().getEmail();
        }else{
            return null;
        }
    }

    public Task<AuthResult>login (String email, String password){
        return mAuth.signInWithEmailAndPassword(email,password);
    }

    public Task<AuthResult>register(String emailRegister, String passwordRegister){
        return mAuth.createUserWithEmailAndPassword(emailRegister,passwordRegister);
    }


    public Task<AuthResult> googlelogin (GoogleSignInAccount googleSignInAccount){
        AuthCredential credential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
        return mAuth.signInWithCredential(credential);/*que si las credenciales estan siendo autenticadas por nuestras base de datos*/
    }


    public String getUid(){
        if(mAuth.getCurrentUser() !=null){
            return mAuth.getCurrentUser().getUid();
        }else {
            return null;
        }
    }


}
