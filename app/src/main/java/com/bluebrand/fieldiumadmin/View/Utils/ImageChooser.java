/*
 * Copyright 2015 Farbod Salamat-Zadeh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bluebrand.fieldiumadmin.View.Utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.bluebrand.fieldiumadmin.Model.Image;
import com.bluebrand.fieldiumadmin.R;

public class ImageChooser extends LinearLayout {

    private ImageView imageView ;
    ProgressBar progressBar ;
    ImageButton button_cancel ;
    TextView textView_label ;
    private LoadingListener loadingListener;
    private Boolean isLoading = false ;
    int defaultImage ;
    private Image image ;

    public ImageChooser(Context context) {
        this(context, null);
    }

    public ImageChooser(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context, attrs);
    }

    private void initializeViews(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.ImageChooser,
                0,
                0);
        String labelText = typedArray.getString(R.styleable.ImageChooser_imageLabel);
        defaultImage = typedArray.getResourceId(R.styleable.ImageChooser_defaultImage,R.mipmap.add_photo);
        typedArray.recycle();

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_image_chooser, this, true);
        setImageView((ImageView) findViewById(R.id.imageView));
        button_cancel = (ImageButton) findViewById(R.id.button_cancel) ;
        progressBar = (ProgressBar) findViewById(R.id.progress_bar) ;
        textView_label = (TextView) findViewById(R.id.label) ;

        getImageView().setImageResource(defaultImage);
        textView_label.setText(labelText);

        setOrientation(LinearLayout.VERTICAL);
        setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setPadding(10,10,10,10);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoadingListener().OnChooseImage();
                }
        });

        button_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getLoadingListener().onCancel();
                progressBar.setVisibility(View.GONE);
                image = null ;
                getImageView().setImageResource(defaultImage);
                button_cancel.setVisibility(View.GONE);
            }
        });
    }

    public void StartLoading (Uri imageUri){
//        getImageView().setImageURI(imageUri);
        setLoading(true);
        progressBar.setVisibility(View.VISIBLE);
        setImage(null);

    }

    public void FinishLoading (Boolean success, Image image){

        setLoading(false);
        progressBar.setVisibility(View.GONE);
        if(success) {
            button_cancel.setVisibility(View.VISIBLE);
            this.setImage(image);
        }else {
            getImageView().setImageResource(defaultImage);
            this.setImage(null);
        }
    }

    public LoadingListener getLoadingListener() {
        return loadingListener;
    }

    public void setLoadingListener(LoadingListener loadingListener) {
        this.loadingListener = loadingListener;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Boolean isLoading() {
        return isLoading;
    }

    public void setLoading(Boolean loading) {
        isLoading = loading;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public interface LoadingListener {
        void onCancel();
        void OnChooseImage();
    }

}