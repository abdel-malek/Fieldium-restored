package com.bluebrand.fieldium.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.Image;

/**
 * Created by Player on 7/1/2016.
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
//        imageView.setBackgroundResource(R.drawable.default_image);

        if(!image.equals("")) {
            try {
                Picasso.with(getContext())
                        .load(image.getUrl()).memoryPolicy(MemoryPolicy.NO_STORE)
                        .error(R.drawable.default_image)
                        .placeholder(R.drawable.default_image)
                        .into(imageView);

                // Glide
                /*.skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)*/
            }catch (Exception e){}
        }
    }
}
