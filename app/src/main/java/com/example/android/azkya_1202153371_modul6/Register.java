package com.example.android.azkya_1202153371_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText inputEmail;
    private EditText inputPass;
    private String email;
    private String password;
    DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth = FirebaseAuth.getInstance();
        inputEmail = (EditText)findViewById(R.id.txtEmail);
        inputPass = (EditText)findViewById(R.id.txtPass);
        email = inputEmail.getText().toString().trim();
        password = inputPass.getText().toString().trim();
        databaseUser = FirebaseDatabase.getInstance().getReference(Home.table3);
    }

    public void signup(View view){
        final ProgressDialog progressDialog = ProgressDialog.show(Register.this, "Please wait...", "Processing...", true);
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter email address/password!", Toast.LENGTH_SHORT).show();
            return;
        }else if((password.length() < 6)){
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }else{
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(Register.this, "Success!" + task.isSuccessful(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(Register.this, "Sign Up failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            } else {
                                String id = auth.getUid();
                                String[] username = email.split("@");
                                User user = new User(id, username[0], email);
                                databaseUser.child(id).setValue(user);
                                startActivity(new Intent(Register.this, MainActivity.class));
                                finish();
                            }
                        }
                    });
        }
    }
}
