package com.example.colors;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.AnnotateImageResponse;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.ColorInfo;
import com.google.api.services.vision.v1.model.DominantColorsAnnotation;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.ImageProperties;
import com.google.api.services.vision.v1.model.SafeSearchAnnotation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisionAPIHandler  {
    // wrapper class for all of our flickr API related members
    private static final String CLOUD_VISION_API_KEY = "AIzaSyDmfCtWQSrC7z8RPGARSwgvcdxSQ-SmS44";
    static final String TAG = "VisionAPI";
    private List<AnnotateImageRequest> annotateImageRequests;
    public List<String> propertiesList;
    List<PictureProperties> picturePropertiesList;

    ImageActivity imageActivity; // for callbacks, because our code
    // is going to run asynchronously

    public VisionAPIHandler(ImageActivity imageActivity){
        this.imageActivity = imageActivity;
    }
    public Image convertToImage(Uri imageUri){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(imageUri.getPath());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        Image image = new Image();
        image.encodeContent(imageBytes);

        return image;
    }

    public void callVisionAPI(Uri imageUri){
        Image image = convertToImage(imageUri);

        Feature desiredFeature = new Feature();
        desiredFeature.setType("IMAGE_PROPERTIES");
        annotateImageRequests = new ArrayList<>();

        AnnotateImageRequest request = new AnnotateImageRequest();
        request.setImage(image);
        request.setFeatures(Arrays.asList(desiredFeature));
        annotateImageRequests.add(request);

        VisionAPIHandlerAsyncTask visionAPIHandlerAsyncTask = new VisionAPIHandlerAsyncTask();
        visionAPIHandlerAsyncTask.execute(imageUri);
    }
    public List returnList(){
        return propertiesList;
    }


    class VisionAPIHandlerAsyncTask extends AsyncTask<Object,Void,List<PictureProperties>>{
        @Override
        protected List<PictureProperties> doInBackground(Object... params) {
            try {
                HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                VisionRequestInitializer requestInitializer = new VisionRequestInitializer(CLOUD_VISION_API_KEY);

                Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                builder.setVisionRequestInitializer(requestInitializer);

                Vision vision = builder.build();

                BatchAnnotateImagesRequest batchAnnotateImagesRequest = new BatchAnnotateImagesRequest();
                batchAnnotateImagesRequest.setRequests(annotateImageRequests);

                Vision.Images.Annotate annotateRequest = vision.images().annotate(batchAnnotateImagesRequest);
                annotateRequest.setDisableGZipContent(true);
                BatchAnnotateImagesResponse response = annotateRequest.execute();

                //return convertResponseToString(response);
                return convertResponseToPicturePropertiesList(response);
            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(List<PictureProperties> result) {
            //imageActivity.receivedPropertiesList(getListOfProperties(splitImageProperties(result)));
            imageActivity.receivedPicturePropertiesList(result);

//            Log.d(TAG,)
//            visionAPIData.setText(result);
//            imageUploadProgress.setVisibility(View.INVISIBLE);
        }
    }
    private String convertResponseToString(BatchAnnotateImagesResponse response) {
        AnnotateImageResponse imageResponses = response.getResponses().get(0);

        String message = "";
        ImageProperties imageProperties = imageResponses.getImagePropertiesAnnotation();
        message = getImageProperty(imageProperties);

        return message;
    }
    private List<PictureProperties> convertResponseToPicturePropertiesList(BatchAnnotateImagesResponse response) {
        AnnotateImageResponse imageResponses = response.getResponses().get(0);

        List<PictureProperties> list = null;
        ImageProperties imageProperties = imageResponses.getImagePropertiesAnnotation();
        list = getImagePropertyList(imageProperties);

        return list;
    }
    private String[] splitImageProperties(String message){
        String[] lines = message.split("\\s*\\r?\\n\\s*");
        for (String line : lines) {
            Log.d(TAG, "getListOfProperties: " + line);
        }
        return lines;
    }

    public List getListOfProperties(String[] lines){
        List<String> list = new ArrayList<>(lines.length);
        list.addAll(Arrays.asList(lines));
        return list;
    }

    private String getImageProperty(ImageProperties imageProperties) {
        String message = "";
        DominantColorsAnnotation colors = imageProperties.getDominantColors();
        for (ColorInfo color : colors.getColors()) {
            message = message + "" + color.getPixelFraction() + " - " + color.getColor().getRed() + " - " + color.getColor().getGreen() + " - " + color.getColor().getBlue();
            message = message + "\n";
//            PictureProperties pictureProperties = new PictureProperties(color.getPixelFraction(),color.getColor().getRed(),color.getColor().getBlue(),color.getColor().getGreen());
//            picturePropertiesList.add(pictureProperties);
        }
        return message;
    }
    private List<PictureProperties> getImagePropertyList(ImageProperties imageProperties) {
        List<PictureProperties> list = new ArrayList<>();
        DominantColorsAnnotation colors = imageProperties.getDominantColors();
        for (ColorInfo color : colors.getColors()) {
            PictureProperties pictureProperties = new PictureProperties(color.getPixelFraction(),color.getColor().getRed(),color.getColor().getGreen(),color.getColor().getBlue());
            //Log.d(TAG, pictureProperties.toString());
            if(pictureProperties!=null) {
                list.add(pictureProperties);
            }
        }
        return list;
    }
}

