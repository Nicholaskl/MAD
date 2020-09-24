package com.nicholas.prac5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Button callButton, locButton, moreButton;
    private EditText phoneInput, longInput, latInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callButton = findViewById(R.id.callButton);
        locButton = findViewById(R.id.locButton);
        moreButton = findViewById(R.id.moreButton);

        phoneInput = findViewById(R.id.phoneInput);
        longInput = findViewById(R.id.longInput);
        latInput = findViewById(R.id.latInput);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int phone = Integer.parseInt(phoneInput.getText().toString());
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
            }
        });

        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double longitude = Double.parseDouble(longInput.getText().toString());
                double latitude = Double.parseDouble(latInput.getText().toString());

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + latitude + "," + longitude)));
            }
        });

        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Page2.class));
            }
        });
    }


}