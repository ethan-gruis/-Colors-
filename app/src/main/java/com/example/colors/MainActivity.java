/**
 *
 * Sources:
 * https://android.jlelse.eu/androids-new-image-capture-from-a-camera-using-file-provider-dd178519a954
 * https://stackoverflow.com/questions/30896130/fileprovider-crash-npe-attempting-to-invoke-xmlresourceparser-on-a-null-string
 */

package com.example.colors;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivityTag";
    private static final int RECORD_REQUEST_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    public static final int PICK_IMAGE = 103;
    private Bitmap bitmap;
    protected String imageFilePath;
    private static final int REQUEST_CAPTURE_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button takePictureButton = (Button) findViewById(R.id.takePictureButton);
        Button uploadImageButton = (Button) findViewById(R.id.chooseImageButton);


        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                openCameraIntent();
            }
        });

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        if(!checkCameraHardware(this)) {
            Toast.makeText(this, "Your device doesn't support this app", Toast.LENGTH_SHORT).show();
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory( Intent.CATEGORY_HOME );
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Button tabButton = (Button) findViewById(R.id.takePictureButton);
//        if (checkPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            tabButton.setVisibility(View.VISIBLE);
//        } else {
//            tabButton.setVisibility(View.INVISIBLE);
//            makeRequest(Manifest.permission.CAMERA);
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE: {
                    Uri selectedImage = Uri.fromFile(new File(imageFilePath));
                    Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                    intent.putExtra("selectedImage", selectedImage);
                    intent.putExtra("typeOfEntry", "CAMERA");
                    startActivity(intent);
                    break;
                }
                case PICK_IMAGE: {
                    Uri selectedImage = data.getData();
                    Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                    intent.putExtra("selectedImage", selectedImage);
                    intent.putExtra("typeOfEntry", "GALLERY");
                    startActivity(intent);
                    break;
                }
            }
        }
    }


    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if(pictureIntent.resolveActivity(getPackageManager()) != null){
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.colors.provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(pictureIntent,
                        CAMERA_REQUEST_CODE);
            }
        }
    }
}
