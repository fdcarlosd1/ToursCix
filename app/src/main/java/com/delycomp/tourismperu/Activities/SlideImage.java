package com.delycomp.tourismperu.Activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.delycomp.tourismperu.Adaptadores.ViewPageAdapter;
import com.delycomp.tourismperu.R;

import java.util.ArrayList;

public class SlideImage extends AppCompatActivity {
    ViewPager viewPager;
    public static final String EXTRA_LISTA_IMAGES = "SpacePhotoActivity.SPACE_PHOTO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_image);

        ArrayList<String> test = getIntent().getStringArrayListExtra(EXTRA_LISTA_IMAGES);
        Log.d("cantidad de strin",""+ test.size());

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPageAdapter viewPagerAdapter = new ViewPageAdapter(this,test);
        viewPager.setAdapter(viewPagerAdapter);
    }
}
