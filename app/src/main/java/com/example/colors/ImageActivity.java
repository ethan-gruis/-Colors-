package com.example.colors;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {
    protected Uri selectedImage;
    private final String TAG = "ImageTAG";
    private List<PictureProperties> picturePropertiesList;
    protected ImageView color1;
    protected ImageView color2;
    protected ImageView color3;
    protected ImageView color4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_image);

        ImageView image = (ImageView) findViewById(R.id.parentImage);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        color1 = (ImageView) findViewById(R.id.colorImage1);
        color2 = (ImageView) findViewById(R.id.colorImage2);
        color3 = (ImageView) findViewById(R.id.colorImage3);
        color4 = (ImageView) findViewById(R.id.colorImage4);

        color1.setClickable(true);
        color2.setClickable(true);
        color3.setClickable(true);
        color4.setClickable(true);
        color1.setOnClickListener(this);
        color2.setOnClickListener(this);
        color3.setOnClickListener(this);
        color4.setOnClickListener(this);


        // both options (CAMERA and GALLERY) now pass a URI object
        selectedImage = (Uri) extras.getParcelable("selectedImage");
        image.setImageURI(selectedImage);
        VisionAPIHandler visionAPIHandler = new VisionAPIHandler(this);
        visionAPIHandler.callVisionAPI(selectedImage);
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // get a reference to the MenuInflater
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.image_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ImageActivity.this, PaletteActivity.class);
        PictureProperties pictureProperties = (PictureProperties) v.getTag();
        Log.d(TAG, "onClick: " + pictureProperties.toString());
        intent.putExtra("Red Value",pictureProperties.getRedValue());
        intent.putExtra("Green Value",pictureProperties.getGreenValue());
        intent.putExtra("Blue Value",pictureProperties.getBlueValue());
        startActivity(intent);
    }

    public void receivedPicturePropertiesList(List<PictureProperties> list){
        picturePropertiesList = list;
        String temp = Integer.toString(picturePropertiesList.size());
        Log.d(TAG,"List size" + temp);
        //Log.d(TAG,picturePropertiesList.get(1).toString());
        Collections.sort(picturePropertiesList);
        for(int i = 0; i < picturePropertiesList.size();i++){
            Log.d(TAG,"Item " + i + picturePropertiesList.get(i).toString());
        }

        // Set imageView to colors
        color1.setBackgroundColor(Color.HSVToColor(convertRGBtoHSV(picturePropertiesList.get(0))));
        color1.setTag(picturePropertiesList.get(0));
        color2.setBackgroundColor(Color.HSVToColor(convertRGBtoHSV(picturePropertiesList.get(1))));
        color2.setTag(picturePropertiesList.get(1));
        color3.setBackgroundColor(Color.HSVToColor(convertRGBtoHSV(picturePropertiesList.get(2))));
        color3.setTag(picturePropertiesList.get(2));
        color4.setBackgroundColor(Color.HSVToColor(convertRGBtoHSV(picturePropertiesList.get(3))));
        color4.setTag(picturePropertiesList.get(3));

    }
    public float[] convertRGBtoHSV(PictureProperties pictureProperties){
        float[] hsv = new float[3];

        final int r = (int)pictureProperties.getRedValue();
        final int g = (int)pictureProperties.getGreenValue();
        final int b = (int)pictureProperties.getBlueValue();
        Color.RGBToHSV(r, g, b, hsv);

        return hsv;
    }
}
