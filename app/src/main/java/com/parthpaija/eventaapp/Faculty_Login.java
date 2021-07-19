package com.parthpaija.eventaapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Faculty_Login extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 7;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mEventName;
    private ImageView mimageView;
    private ProgressBar mpb;

    private Uri mImageuri;
    private StorageReference mstorageref;
    private DatabaseReference mdatabaseReference;
    private StorageTask muploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty__login);

        mButtonChooseImage = (Button) findViewById(R.id.btn_choosephoto);
        mButtonUpload = (Button) findViewById(R.id.btn_upload);
        mEventName = (EditText) findViewById(R.id.edt_eventname);
        mimageView = (ImageView) findViewById(R.id.imageView);
        mpb = (ProgressBar) findViewById(R.id.pb);

        mstorageref = FirebaseStorage.getInstance().getReference("uploads/");
        mdatabaseReference = FirebaseDatabase.getInstance().getReference("uploads");


        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFile();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadsFile();
            }
        });

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadsFile() {
        if (mImageuri != null) {

            final StorageReference fileReference = mstorageref.child(System.currentTimeMillis() + "." + getFileExtension(mImageuri));
            muploadTask = fileReference.putFile(mImageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mpb.setProgress(0);
                                }
                            }, 500);

                            /*Task<Uri> urltask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urltask.isSuccessful());
                            Uri downloaduri = urltask.getResult();
                            Log.d("pathu","OnSucsess:firebase download url:"+downloaduri.toString());
                            Upload upload = new Upload(mEventName.getText().toString(),downloaduri.toString());
                            String uploadid = mdatabaseReference.push().getKey();
                            mdatabaseReference.child(uploadid).setValue(upload);*/

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Upload upload = new Upload(mEventName.getText().toString().trim(), uri.toString());
                                    Log.d("pathu------>", uri.toString());
                                    String uploadid = mdatabaseReference.push().getKey();
                                    mdatabaseReference.child(uploadid).setValue(upload);
                                    Toast.makeText(Faculty_Login.this, "Upload sucseesfully...,", Toast.LENGTH_SHORT).show();
                                }
                            });

                            /*Toast.makeText(Faculty_Login.this,"Upload sucsessfully...",Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(mEventName.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());
                            String uploadid = mdatabaseReference.push().getKey();
                            mdatabaseReference.child(uploadid).setValue(upload);*/
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Faculty_Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mpb.setProgress((int) progress);

                        }
                    });

        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }

    private void openFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageuri = data.getData();

            Picasso.get().load(mImageuri).into(mimageView);
            //Picasso.get().load(mImageuri).into(mimageView);

        }
    }
}
