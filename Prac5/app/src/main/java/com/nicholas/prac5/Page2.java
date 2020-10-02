package com.nicholas.prac5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.service.controls.Control;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import java.io.File;

public class Page2 extends AppCompatActivity {
    private ImageView iv, bigPhotoView;
    private Button photoButton, contactButton, bigPhoto;
    private TextView IdText, nameText, emailText, phoneText;
    private static final int REQUEST_THUMBNAIL = 1;
    private static final int REQUEST_CONTACT = 2;
    private static final int REQUEST_PHOTO = 3;
    private Intent thumbnailPhotoIntent, contactIntent;
    private File photoFile;
    private Intent photoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        iv = findViewById(R.id.imageView);

        photoButton = findViewById(R.id.photoButton);
        contactButton = findViewById(R.id.contactButton);
        bigPhoto = findViewById(R.id.bigPhoto);

        IdText = findViewById(R.id.IdText);
        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);

        bigPhotoView = findViewById(R.id.bigPhotoView);


        photoFile = new File(getFilesDir(), "photo.jpg");
        photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri cameraUri = FileProvider.getUriForFile(
                this,
                "com.nicholas.prac5.fileprovider",
                photoFile);
        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        PackageManager pm = getPackageManager();

        for(ResolveInfo a : pm.queryIntentActivities(
                photoIntent,
                PackageManager.MATCH_DEFAULT_ONLY))
        {
            grantUriPermission(
                    a.activityInfo.packageName,
                    cameraUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thumbnailPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(thumbnailPhotoIntent, REQUEST_THUMBNAIL);
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactIntent = new Intent(
                        Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(contactIntent, REQUEST_CONTACT);
            }
        });

        bigPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(photoIntent, REQUEST_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        super.onActivityResult(requestCode, resultCode, resultIntent);

        if(resultCode == RESULT_OK &&
           requestCode == REQUEST_THUMBNAIL)
        {
            Bitmap thumbnail = (Bitmap) resultIntent.getExtras().get("data");
            iv.setImageBitmap(thumbnail);
        }
        else if(resultCode == RESULT_OK &&
                requestCode == REQUEST_CONTACT)
        {
            Uri contactUri = resultIntent.getData();

            String[] queryFields = new String[] {
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME
            };

            Cursor c = getContentResolver().query(
                contactUri, queryFields, null, null, null);

            try {
                if(c.getCount() > 0) {
                    c.moveToFirst();
                    int id = c.getInt(0);
                    IdText.setText("ID: " + id);
                    nameText.setText("Name: " + c.getString(1));

                    Cursor emailC = getContentResolver().query(
                            Email.CONTENT_URI,
                            new String[] {Email.ADDRESS},
                            Email.CONTACT_ID + " = ?",
                            new String[] {String.valueOf(id)},
                            null, null
                    );
                    try {
                        if(emailC.getCount() > 0) {
                            emailC.moveToFirst();
                            emailText.setText("Email: " + emailC.getString(0));
                        }
                    }
                    finally {
                        emailC.close();
                    }

                    Cursor phoneC = getContentResolver().query(
                            Phone.CONTENT_URI,
                            new String[] {Phone.NUMBER},
                            Phone.CONTACT_ID + " = ?",
                            new String[] {String.valueOf(id)},
                            null, null
                    );
                    try {
                        if(phoneC.getCount() > 0) {
                            phoneC.moveToFirst();
                            phoneText.setText("Phone: " + phoneC.getString(0));
                        }
                    }
                    finally {
                        emailC.close();
                    }
                }
            }
            finally
            {
                c.close();
            }
        }
        else if(resultCode == RESULT_OK &&
                requestCode == REQUEST_PHOTO)
        {
            Bitmap photo = BitmapFactory.decodeFile(photoFile.toString());
            bigPhotoView.setImageBitmap(photo);
        }
    }
}