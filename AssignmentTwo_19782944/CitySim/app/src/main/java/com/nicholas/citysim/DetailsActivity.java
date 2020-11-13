package com.nicholas.citysim;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nicholas.citysim.model.GameData;
import com.nicholas.citysim.model.MapElement;
/*------------------------------------------------------------
* File: DetailsActivity.java
* Author: Nicholas Klvana-Hooper
* Created: 30/10/2020
* Modified: 9/11/2020
* Purpose: Details activity, contains all stuff to do with the details screen
 -------------------------------------------------------------*/

public class DetailsActivity extends AppCompatActivity {
    private int row, col;
    private GameData gData;
    private TextView coords, structure;
    private Button pic, save;
    private EditText name;
    private MapElement curr;
    private static final int REQUEST_THUMBNAIL = 1; //ID number for thumbnail photo
    private Intent thumbnailPhotoIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle data = getIntent().getExtras();
        //Got to get the row and column of the selected map fragment from GameActivity
        row = data.getInt("ROW");
        col = data.getInt("COL");
        gData = GameData.getInstance(); // Singleton!!! Woo
        curr = gData.getMap()[row][col];

        //Find and set all the views
        coords = findViewById(R.id.coords);
        structure = findViewById(R.id.structureView);
        pic = findViewById(R.id.picture);
        name = findViewById(R.id.nameText);
        save = findViewById(R.id.saveButton);

        //Print outputs to their respective views
        coords.setText("Co-ordinates: " + col + "," + row);
        structure.setText(curr.getStructure().typeExport());
        name.setText(curr.getStructure().getName());

        //Save button returns to the game activity
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curr.getStructure().setName(name.getText().toString());
                Intent intent = new Intent(DetailsActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        //Picture button takes a photo of the thumbnail
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thumbnailPhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(thumbnailPhotoIntent, REQUEST_THUMBNAIL);
            }
        });
    }

    //Method for taking the thumbnail photo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        super.onActivityResult(requestCode, resultCode, resultIntent);

        //If the request was okay and was a thumbnail request set it
        if(resultCode == RESULT_OK &&
                requestCode == REQUEST_THUMBNAIL)
        {
            Bitmap thumbnail = (Bitmap) resultIntent.getExtras().get("data"); //get the bitmap
            curr.setImage(thumbnail); //set image to map element
        }

    }

    /* Submodule: getIntent
     * Import: c(Context), row(int), col(int)
     * Export: intent (Intent)
     * Assertion: Get an intent for this activity and bundle data inside it
     */
    public static Intent getIntent(Context c, int row, int col) {
        Intent intent = new Intent(c, DetailsActivity.class);
        intent.putExtra("ROW", row);
        intent.putExtra("COL", col);
        return intent;
    }
}