/**
 *
 * Sources:
 * https://android.jlelse.eu/androids-new-image-capture-from-a-camera-using-file-provider-dd178519a954
 * https://stackoverflow.com/questions/30896130/fileprovider-crash-npe-attempting-to-invoke-xmlresourceparser-on-a-null-string
 */

package com.example.colors;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivityTag";
    private static final int RECORD_REQUEST_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;
    public static final int PICK_IMAGE = 103;
    static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private Bitmap bitmap;
    protected String imageFilePath;
    private static final int REQUEST_CAPTURE_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button takePictureButton = (Button) findViewById(R.id.takePictureButton);
        Button uploadImageButton = (Button) findViewById(R.id.chooseImageButton);
        ImageView color1 = (ImageView) findViewById(R.id.color1);

        scrambleUI();

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
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    //request the permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                } else {
                    // Permission has already been granted
                    pickFromGallery();
                }
            }
        });

        if (!checkCameraHardware(this)) {
            Toast.makeText(this, "Your device doesn't support this app", Toast.LENGTH_SHORT).show();
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }

        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrambleUI();
            }
        });
    }

    public void scrambleUI() {
        ImageView color1 = (ImageView) findViewById(R.id.color1);
        ImageView color2 = (ImageView) findViewById(R.id.color2);
        ImageView color3 = (ImageView) findViewById(R.id.color3);
        Button takePictureButton = (Button) findViewById(R.id.takePictureButton);
        Button uploadImageButton = (Button) findViewById(R.id.chooseImageButton);
        Random rand = new Random();
        int r = rand.nextInt(256 - 1);
        int g = rand.nextInt(256 - 1);
        int b = rand.nextInt(256 - 1);
        
        color1.setBackgroundColor(Color.rgb(r, g, b));
        float[] baseHSV = new float[3];
        Color.RGBToHSV(r, g, b, baseHSV);
        float[] hsv = baseHSV;

        hsv[2] += .30f;
        hsv[1] -= .30f;
        color2.setBackgroundColor(Color.HSVToColor(hsv));
        hsv[1] += .30f;
        hsv[2] -= .50f;
        color3.setBackgroundColor(Color.HSVToColor(hsv));

        if(!setToWhite(r,g,b, baseHSV)) {
            takePictureButton.setTextColor(Color.BLACK);
            uploadImageButton.setTextColor(Color.BLACK);
        } else {
            takePictureButton.setTextColor(Color.WHITE);
            uploadImageButton.setTextColor(Color.WHITE);
        }
        takePictureButton.setBackgroundColor(Color.rgb(r,g,b));
        uploadImageButton.setBackgroundColor(Color.rgb(r,g,b));
    }

    public boolean setToWhite(int r, int g, int b, float[] hsv) {
        if(Color.luminance(Color.rgb(r,g,b)) < 0.6) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE: {
                    Uri selectedImage = Uri.fromFile(new File(imageFilePath));
                    Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                    intent.putExtra("selectedImage", selectedImage);
                    intent.putExtra("typeOfEntry", "CAMERA");
                    startActivity(intent);
                    break;
                }
                case PICK_IMAGE:
                    //data.getData return the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    Log.d(TAG,imgDecodableString);

                    Uri image = Uri.fromFile(new File(imgDecodableString));
                    Intent intent = new Intent(MainActivity.this, ImageActivity.class);
                    intent.putExtra("selectedImage", image);
                    intent.putExtra("typeOfEntry", "GALLERY");
                    startActivity(intent);

                    break;
            }
        }
    }


    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
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
        if (pictureIntent.resolveActivity(getPackageManager()) != null) {
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
    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, PICK_IMAGE);
    }
}


