package com.example.colors;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {
    protected Uri selectedImage;
    private final String TAG = "ImageTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_image);
        ImageView image = (ImageView) findViewById(R.id.parentImage);
        ImageView color1 = (ImageView) findViewById(R.id.colorImage1);
        ImageView color2 = (ImageView) findViewById(R.id.colorImage2);
        ImageView color3 = (ImageView) findViewById(R.id.colorImage3);
        ImageView color4 = (ImageView) findViewById(R.id.colorImage4);
        color1.setClickable(true);
        color2.setClickable(true);
        color3.setClickable(true);
        color4.setClickable(true);
        color1.setOnClickListener(this);
        color2.setOnClickListener(this);
        color3.setOnClickListener(this);
        color4.setOnClickListener(this);

//        String entry = extras.getString("typeOfEntry");
//        Toast.makeText(this, entry, Toast.LENGTH_SHORT).show();

        // both options (CAMERA and GALLERY) now pass a URI object
        selectedImage = (Uri) extras.getParcelable("selectedImage");
        image.setImageURI(selectedImage);
        VisionAPIHandler visionAPIHandler = new VisionAPIHandler(this);
        visionAPIHandler.callVisionAPI(selectedImage);
//        switch(entry) {
//            case "CAMERA":
//                Bitmap bitmap = (Bitmap) extras.getParcelable("bitmap");
//                image.setImageBitmap(bitmap);
//                break;
//            case "GALLERY":
//                Uri selectedImage = (Uri) extras.getParcelable("selectedImage");
//                image.setImageURI(selectedImage);
//                break;
//            default:
//                Toast.makeText(this, "ERROR: Image not loaded", Toast.LENGTH_SHORT).show();
//                break;
//        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ImageActivity.this, PaletteActivity.class);
        startActivity(intent);
    }
    public void receivedPropertiesList(List<String> list) {
        List<String> imageProperties = list;
        Log.d(TAG,imageProperties.toString());
    }
}
