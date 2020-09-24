package com.nicholas.prac5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Page2 extends AppCompatActivity {
    private ImageView iv;
    private Button photoButton, contactButton;
    private TextView IdText, nameText, emailText, phoneText;
    private static final int REQUEST_THUMBNAIL = 1;
    private static final int REQUEST_CONTACT = 2;
    private Intent thumbnailPhotoIntent, contactIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page2);

        iv = findViewById(R.id.imageView);

        photoButton = findViewById(R.id.photoButton);
        contactButton = findViewById(R.id.contactButton);

        IdText = findViewById(R.id.IdText);
        nameText = findViewById(R.id.nameText);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);

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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
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

            Cursor c = Page2.this.getContentResolver().query(
                contactUri, queryFields, null, null, null);

            try {
                if(c.getCount() > 0) {
                    c.moveToFirst();
                    IdText.setText(c.getInt(0));
                    nameText.setText(c.getString(1));
                    phoneText.setText("PP");
                    /*emailText.setText(c.getString(2));
                    phoneText.setText(c.getString(3));*/
                }
            }
            finally
            {
                c.close();
            }
        }
    }
}