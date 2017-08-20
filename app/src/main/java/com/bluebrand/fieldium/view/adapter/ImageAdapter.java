package com.bluebrand.fieldium.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bluebrand.fieldium.core.model.Image;
import com.bluebrand.fieldium.view.ImageFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Player on 7/1/2016.
 */
public class ImageAdapter extends FragmentStatePagerAdapter
{
    List<Image> imageUrls  ;
    public ImageAdapter(ArrayList<Image> imageUrls , FragmentManager fm) {
        super(fm);
        this.imageUrls = imageUrls;
    }

    @Override
    public Fragment getItem(int position) {
        ImageFragment menuFragment =  ImageFragment.newInstance(imageUrls.get(position));
        return menuFragment;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

}