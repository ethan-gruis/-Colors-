package com.example.colors;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.Float;

import androidx.annotation.NonNull;

public class PictureProperties implements Comparable<PictureProperties>{
    private float pixelPercentage;
    private float redValue;
    private float blueValue;
    private float greenValue;

    public PictureProperties(float pixelPercentage, float redValue, float greenValue,float blueValue) {
        this.pixelPercentage = pixelPercentage;
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;
    }

    public float getPixelPercentage() {
        return pixelPercentage;
    }

    public void setPixelPercentage(float pixelPercentage) {
        this.pixelPercentage = pixelPercentage;
    }

    public float getRedValue() {
        return redValue;
    }

    public void setRedValue(float redValue) {
        this.redValue = redValue;
    }

    public float getBlueValue() {
        return blueValue;
    }

    public void setBlueValue(float blueValue) {
        this.blueValue = blueValue;
    }

    public float getGreenValue() {
        return greenValue;
    }

    public void setGreenValue(float greenValue) {
        this.greenValue = greenValue;
    }

    @NonNull
    @Override
    public String toString() {
        return "Pixel Percentage: " + this.pixelPercentage +"\n"
                + "Red Value: " + this.redValue +"\n"
                + "Green Value: " + this.greenValue +"\n"
                + "Blue Value: " + this.blueValue;

    }

    @Override
    public int compareTo(PictureProperties o) {
        return Float.compare((o.getPixelPercentage()),this.getPixelPercentage());
    }
}
