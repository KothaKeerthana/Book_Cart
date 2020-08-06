package com.example.jpmc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


public class Add_item extends AppCompatActivity {

    ImageView add_book;
    TextView bname, bauthor, bdesc;
    RatingBar brate;
    Button add_bk;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    private Uri filePath;
    public static final String TAG="TAG";

    NotificationCompat.Builder notification;

    // uri indicating where the image will be picked from

    private final int PICK_IMAGE_REQUEST = 71;
    //PICK_IMAGE_REQUEST is the request code defined as an instance variable.

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        add_book = (ImageView) findViewById(R.id.imageView2);
        bname = (TextView) findViewById(R.id.editTextTextPersonName2);
        bauthor = (TextView) findViewById(R.id.editTextTextPersonName3);
        bdesc = (TextView) findViewById(R.id.editTextTextPersonName4);
        brate = (RatingBar) findViewById(R.id.ratingBar);
        add_bk = (Button) findViewById(R.id.button3);
        progressBar = findViewById(R.id.progressBar2);

        progressBar.setVisibility(View.GONE);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("Books").child(UUID.randomUUID().toString());

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseimage();

            }
        });
        add_bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Book is uploaded",Toast.LENGTH_LONG).show();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setSmallIcon(R.drawable.ic_baseline_notifications_24);
                builder.setContentTitle("IFOREYE NOTIFICATION");
                builder.setContentText("Hurray! New book is uploaded");
                NotificationManager manager = (NotificationManager) getApplication().getSystemService((getApplicationContext().NOTIFICATION_SERVICE));
                manager.notify(0,builder.build());
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Add item is clicked",Toast.LENGTH_SHORT).show();
                if(filePath!=null)
                {
                    Toast.makeText(getApplicationContext(),"Entered firebase",Toast.LENGTH_SHORT).show();
//                    final ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
//                    progressDialog.setTitle("uploading ...");
//                   progressDialog.show();

                   Log.d(TAG,"IMatge uploading");

                    final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString() + ".jpg");
                    Task<Uri> uriTask =
                            ref.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if(!task.isSuccessful())
                                    {
                                        throw task.getException();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "entered image uploading", Toast.LENGTH_SHORT).show();

                                        return ref.getDownloadUrl();
                                    }

                                    }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                      if(task.isSuccessful())
                                      {
                                          final String imageUrl = task.getResult().toString();
                                          final String bkname = bname.getText().toString();
                                          final String bkauthor = bauthor.getText().toString();
                                          final String bkdesc = bdesc.getText().toString();
                                          final HashMap<String, Object> map = new HashMap<>();
                                            map.put("Title", bkname);
                                            map.put("Author", bkauthor);
                                            map.put("Description", bkdesc);
                                            map.put("ImageUrl", imageUrl);
                                            myRef.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                 if(task.isSuccessful()){




                                                     Intent i = new Intent(getApplicationContext(),home.class);
                                                     startActivity(i);
                                                    // progressDialog.dismiss();
                                                 }
                                                 else
                                                 {
                                                     Toast.makeText(getApplicationContext(),"uploading is failed",Toast.LENGTH_LONG).show();
                                                 }
                                                }
                                            });

                                      }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
                                }
                            });

                }
            }
        });


    }
    private void chooseimage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        //startActivityForResult is used to receive the result, which is the selected image

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                add_book.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}



// ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//@Override
//public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//        Toast.makeText(getApplicationContext(), "Book added successfully", Toast.LENGTH_LONG).show();
//final String bkname = bname.getText().toString();
//final String bkauthor = bauthor.getText().toString();
//final String bkdesc = bdesc.getText().toString();
//final String uploadId = ref.getDownloadUrl().toString();
//
//final HashMap<String, Object> map = new HashMap<>();
//        map.put("Title", bkname);
//        map.put("Author", bkauthor);
//        map.put("Description", bkdesc);
//        map.put("ImageUrl", uploadId);
//
//        myRef.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//@Override
//public void onComplete(@NonNull Task<Void> task) {
//        progressBar.setVisibility(View.GONE);
//        if (task.isSuccessful()) {
//        Toast.makeText(getApplicationContext(), "Hurray, Book is uploaded", Toast.LENGTH_LONG).show();
//        Intent i = new Intent(getApplicationContext(), home.class);
//        startActivity(i);
//        } else
//        Toast.makeText(getApplicationContext(), "OOPS, Upload failed", Toast.LENGTH_LONG).show();
//
//
//        }
//        });
//
//        }
//        }).addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        Toast.makeText(Add_item.this, "Book Upload failed", Toast.LENGTH_SHORT).show();
//        }
//        });
//
























