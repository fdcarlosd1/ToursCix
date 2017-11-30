package com.delycomp.tourismperu.Adaptadores;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.delycomp.tourismperu.R;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by CarlosDavid on 29/11/2017.
 */

public class ViewPageAdapter  extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    List<String> listImages = new ArrayList<>();
    public ViewPageAdapter(Context context) {
        this.context = context;
    }

    public ViewPageAdapter(Context context, List<String> listImages) {
        this.context = context;
        this.listImages = listImages;
    }

    @Override
    public int getCount() {
        return listImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view  = layoutInflater.inflate(R.layout.slide_loyout,null);
        ImageView imageView = (ImageViewTouch) view.findViewById(R.id.imageToSlide);
        Log.d("AUX", " "+listImages.get(position));
        Glide.with(context)
                .load(listImages.get(position))
                .fitCenter()
                .placeholder(R.drawable.loadingfull)
                .into(imageView);

        ViewPager vp= (ViewPager) container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp=(ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
