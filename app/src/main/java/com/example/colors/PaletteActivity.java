package com.example.colors;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.colors.ui.palette.SectionsPagerAdapter;

public class PaletteActivity extends AppCompatActivity {
    final static String TAG = "PaletteFun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        Bundle extras = getIntent().getExtras();
        float redValue = extras.getFloat("Red Value");
        float greenValue = extras.getFloat("Green Value");
        float blueValue = extras.getFloat("Blue Value");


        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),redValue,greenValue,blueValue);
        //sectionsPagerAdapter.setUri(selectedImage);

        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}