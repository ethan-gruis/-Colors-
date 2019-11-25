package com.example.colors;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_image);
        ImageView image = (ImageView) findViewById(R.id.colorImage);
        Button startPaletteButton = (Button) findViewById(R.id.paletteButton);
        String entry = extras.getString("typeOfEntry");
        Toast.makeText(this, entry, Toast.LENGTH_SHORT).show();
        switch(entry) {
            case "CAMERA":
                Bitmap bitmap = (Bitmap) extras.getParcelable("bitmap");
                image.setImageBitmap(bitmap);
                break;
            case "GALLERY":
                Uri selectedImage = (Uri) extras.getParcelable("selectedImage");
                image.setImageURI(selectedImage);
                break;
            default:
                Toast.makeText(this, "ERROR: Image not loaded", Toast.LENGTH_SHORT).show();
                break;
        }




        startPaletteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageActivity.this, PaletteActivity.class);
                startActivity(intent);
            }
        });
    }
}
