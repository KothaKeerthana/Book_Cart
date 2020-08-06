package com.example.jpmc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {

    TextView username,password,phone,email,login;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (TextView) findViewById(R.id.editTextTextPersonName);
        password = (TextView) findViewById(R.id.editTextTextPassword);
        phone = (TextView) findViewById(R.id.editTextPhone);
        email = (TextView) findViewById(R.id.editTextTextEmailAddress);
        btnsignup = (Button) findViewById(R.id.button);
        login = (TextView) findViewById(R.id.textView);
        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(GONE);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final String uname = username.getText().toString();
                final String pwd = password.getText().toString();
                final String phoneNo = phone.getText().toString();
                final String emailId = email.getText().toString();


                if (uname.isEmpty()) {
                    username.setError("Enter username");
                    username.requestFocus();
                }
                else if(emailId.isEmpty()){
                    email.setError("Please enter valid emailId");
                    email.requestFocus();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
                    email.setError("Invalid email address");
                    email.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if(emailId.isEmpty()&&pwd.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Fields are required", Toast.LENGTH_SHORT).show();
                }
                else if(!(emailId.isEmpty()&&pwd.isEmpty())) {
                    mAuth.createUserWithEmailAndPassword(emailId, pwd)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(GONE);
                                    if (task.isSuccessful()) {

                                        User user = new User();
                                        user.setEmail(emailId);
                                        user.setName(uname);
                                        user.setPassword(pwd);
                                        user.setPhoneno(phoneNo);

                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("Users");
                                        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);


                                        // Sign in success, update UI with the signed-in user's information


                                        Intent i = new Intent(getApplicationContext(),home.class);
                                        startActivity(i);
                                        Toast.makeText(getApplicationContext(),"Singup Successfull",Toast.LENGTH_LONG).show();
                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });

                }
                else
                    Toast.makeText(getApplicationContext(),"Signup error",Toast.LENGTH_LONG).show();


            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),loginpage.class);
                startActivity(i);
            }
        });





    }

}