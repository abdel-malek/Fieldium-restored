package com.bluebrand.fieldiumadmin.View.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import com.bluebrand.fieldiumadmin.Model.Image;

/**
 * Created by User on 7/1/2016.
 */
public class ImageAdapter extends FragmentStatePagerAdapter
{
    List<Image> imageUrls  ;
    public ImageAdapter(List<Image> imageUrls , FragmentManager fm) {
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