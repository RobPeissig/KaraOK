package com.example.karaok;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    MaterialButton loginbtn;
    FirebaseAuth mAuth;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.username_log);
        editTextPassword = findViewById(R.id.password_log);

        TextView registerAcc = (TextView) findViewById(R.id.registerAcc);
        TextView forgotPass = (TextView) findViewById(R.id.forgotpass);

        loginbtn = (MaterialButton) findViewById(R.id.loginbtn);

        //admin and admin

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Login Successful.",
                                            Toast.LENGTH_SHORT).show();
                                    openSongSelection();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        registerAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountRegister();
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.sendPasswordResetEmail(editTextEmail.getText().toString()).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Reset Email Sent.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Reset Email Failed to Send. Please check entered email is valid.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
            }
        });
    }
    public void openSongSelection(){
        Intent intent = new Intent(this, SongSelection.class);
        startActivity(intent);
    }

    public void openAccountRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}