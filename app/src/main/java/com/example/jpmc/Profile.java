package com.example.jpmc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    TextView name,mail,phone;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = (TextView) findViewById(R.id.textView2);
        mail = (TextView) findViewById(R.id.textView4);
        phone = (TextView) findViewById(R.id.textView5);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uname = dataSnapshot.child("name").getValue().toString();
                String email = dataSnapshot.child("email").getValue().toString();
                String phoneNo = dataSnapshot.child("phoneno").getValue().toString();
               // String name = dataSnapshot.child("email").getValue().toString();

                name.setText(uname);
                mail.setText(email);
                phone.setText(phoneNo);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(, "Failed to read value.", error.toException());
            }
        });

    }
}