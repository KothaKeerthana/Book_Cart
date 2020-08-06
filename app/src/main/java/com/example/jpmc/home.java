package com.example.jpmc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class home extends AppCompatActivity {
    Button logout;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthstateListener;
    FirebaseUser user;
    Toolbar toolbar;
    ImageView bimg;
    TextView btitle,bauthor,bdesc;
    //ListView lv;
    //private ArrayList<String> arrayList = new ArrayList<>();
    //ArrayAdapter<String> adapter;
    FirebaseDatabase database;
    DatabaseReference myRef;
   private List<Bookk>mbooks;

    private RecyclerView mRecyclerview;
    BookAdapter adapter;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.user)
        {
            Intent i = new Intent(getApplicationContext(),Profile.class);
            startActivity(i);
        }
        else if(id == R.id.add_item) {
            Log.i("Heloo","Heello");

            Intent i = new Intent(getApplicationContext(),Add_item.class);
            startActivity(i);
            Toast.makeText(this, "Add an item", Toast.LENGTH_SHORT).show();
        }
        else if(id == R.id.uploadPdf) {
            Toast.makeText(getApplicationContext(), "UploadPDF", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), Upload_PDF.class);
            startActivity(i);
        }
        else if(id == R.id.search)
            Toast.makeText(getApplicationContext(),"Search here",Toast.LENGTH_LONG).show();
        else if(id == R.id.settings)
            Toast.makeText(getApplicationContext(),"Setting here!",Toast.LENGTH_LONG).show();
        else if(id == R.id.logout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent isign = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(isign);
        }


        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btitle = findViewById(R.id.textView7);
        bauthor = findViewById(R.id.textView8);
        bdesc = findViewById(R.id.textView9);
        bimg = findViewById(R.id.imageView4);
       // lv = findViewById(R.id.listview);
        user = FirebaseAuth.getInstance().getCurrentUser();

       database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Books");

       // adapter = new BookAdapter(getApplicationContext(),R.layout.lists,);
     //   lv.setAdapter(adapter);


       mRecyclerview = findViewById(R.id.recyclerview);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mbooks = new ArrayList<Bookk>();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot postSnapshot : snapshot.getChildren()) {

                    String a =postSnapshot.child("Author").getValue().toString();
                    String t = postSnapshot.child("Title").getValue().toString();
                    String d = postSnapshot.child("Description").getValue().toString();
                    String i = postSnapshot.child("ImageUrl").getValue().toString();

                   Bookk books = new Bookk(t,a,d,i);
                        mbooks.add(books);

                }

                adapter = new BookAdapter(getApplicationContext(),mbooks);
                mRecyclerview.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });




        /*myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //String btitle = snapshot.child("Title").getValue.toSring;
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

         */





      //  logout = (Button) findViewById(R.id.button3);
    }
}