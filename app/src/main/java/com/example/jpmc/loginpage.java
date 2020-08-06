package com.example.jpmc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class loginpage extends AppCompatActivity {
    TextView mail,pass,signup;
    Button login;
    FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        mail = (TextView) findViewById(R.id.editTextTextEmailAddress2);
        pass = (TextView) findViewById(R.id.editTextTextPassword2);
        signup = (TextView) findViewById(R.id.textView3);
        login = (Button) findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final String emailId,pwd;
                emailId = mail.getText().toString();
                pwd = pass.getText().toString();
                if(emailId.isEmpty()){
                    mail.setError("Please enter valid emailId");
                    mail.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
                    mail.setError("Invalid email address");
                    mail.requestFocus();
                }
                else if(pwd.isEmpty()){
                    pass.setError("Please enter your password");
                    pass.requestFocus();
                }
                else if(emailId.isEmpty()&&pwd.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Fields are required", Toast.LENGTH_SHORT).show();
                }
                else if(!(emailId.isEmpty()&&pwd.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(emailId, pwd)
                            .addOnCompleteListener(loginpage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        Toast.makeText(getApplicationContext(),"Login suceesfull",Toast.LENGTH_LONG).show();

                                        Intent i = new Intent(loginpage.this,home.class);
                                        startActivity(i);
                                        //FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(loginpage.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }
                else
                    Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_LONG).show();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }
}