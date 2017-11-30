package com.delycomp.tourismperu.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.delycomp.tourismperu.R;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class SpaceFhoto extends Activity  {
    ImageViewTouch aux;

    public static final String EXTRA_SPACE_PHOTO = "SpacePhotoActivity.SPACE_PHOTO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_fhoto);
         aux = (ImageViewTouch) findViewById(R.id.imageXXX) ;
//
        Bundle extras = getIntent().getExtras();
        String spacePhoto = extras.get(EXTRA_SPACE_PHOTO).toString();
        Glide.with(this)
                .load(spacePhoto)
                .fitCenter()
                .into(aux);
    }


}
