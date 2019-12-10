package com.example.colors;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

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
        PictureProperties pictureProperties = (PictureProperties) v.getTag();
        Log.d(TAG, "onClick: " + pictureProperties.toString());
        intent.putExtra("Red Value",pictureProperties.getRedValue());
        intent.putExtra("Green Value",pictureProperties.getGreenValue());
        intent.putExtra("Blue Value",pictureProperties.getBlueValue());
        startActivity(intent);
    }
    public void receivedPropertiesList(List<String> list) {
        List<String> imageProperties = list;
        String temp = Integer.toString(imageProperties.size());
        Log.d(TAG,temp);
        Log.d(TAG,imageProperties.get(1));
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
        final int r = (int)picturePropertiesList.get(0).getRedValue();
        final int g = (int)picturePropertiesList.get(0).getGreenValue();
        final int b = (int)picturePropertiesList.get(0).getBlueValue();
        float[] hsv = new float[3];
        Color.RGBToHSV(r, g, b, hsv);
        color1.setBackgroundColor(Color.HSVToColor(hsv));
        color1.setTag(picturePropertiesList.get(0));

        color2.setBackgroundColor(Color.rgb(list.get(1).getRedValue(),list.get(1).getGreenValue(),list.get(1).getBlueValue()));
        color2.setTag(picturePropertiesList.get(1));
        color3.setBackgroundColor(Color.rgb(list.get(2).getRedValue(),list.get(2).getGreenValue(),list.get(2).getBlueValue()));
        color3.setTag(picturePropertiesList.get(2));
        color4.setBackgroundColor(Color.rgb(list.get(3).getRedValue(),list.get(3).getGreenValue(),list.get(3).getBlueValue()));
        color4.setTag(picturePropertiesList.get(3));
    }
}
