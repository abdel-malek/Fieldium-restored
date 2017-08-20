package com.bluebrand.fieldiumadmin.View.Utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import com.bluebrand.fieldiumadmin.Model.Image;
import com.bluebrand.fieldiumadmin.R;

/**
 * Created by User on 7/1/2016.
 */
public class ImageFragment extends Fragment {


    public static ImageFragment newInstance(Image image) {
        ImageFragment menuFragment =  new ImageFragment();
        Bundle args = new Bundle();
        args.putSerializable("image", image);
        menuFragment.setArguments(args);
        return  menuFragment ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.image, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Image image = (Image) getArguments().getSerializable("image");
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) view ;
        imageView.setBackgroundResource(R.mipmap.add_photo);

        if(!image.equals("")) {
            try {
                Picasso.with(getContext())
                        .load(image.getUrl()).memoryPolicy(MemoryPolicy.NO_STORE)
                        .error(R.mipmap.add_photo)
                        .placeholder(R.mipmap.add_photo)
                        .into(imageView);
            }catch (Exception e){}
        }
    }
}
