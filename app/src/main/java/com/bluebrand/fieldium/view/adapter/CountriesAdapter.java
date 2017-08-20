package com.bluebrand.fieldium.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.Country;
import com.bluebrand.network.InternetManager;

import java.util.List;
import java.util.Locale;

/**
 * Created by Player on 7/19/2016.
 */
public class CountriesAdapter extends ArrayAdapter<Country> {
    LayoutInflater inflater;
    public CountriesAdapter(Context context, int resource, List<Country> objects) {
        super(context, resource, objects);
        inflater = ((Activity) context).getLayoutInflater();
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Country getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.country_spinner_getview_item, parent, false);
         /*   view = (View) ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                    R.layout.country_spinner_item, parent, false);*/
            holder = new ViewHolder();
//            holder.country_name = (TextView) view.findViewById(R.id.textView_countryName);
//            holder.country_code = (TextView) view.findViewById(R.id.textView_countryCode);
            holder.country_img = (ImageView) view.findViewById(R.id.country_image);
            view.setTag(holder);
        } else {
            view = (View) convertView;
            holder = (ViewHolder) view.getTag();
        }
        Country country = (Country) getItem(position);
    /*    if (Locale.getDefault().getLanguage().equals("ar"))
            holder.country_name.setText(country.getAr_name());
        else holder.country_name.setText(country.getEn_name());*/
//        holder.country_code.setText(country.getDialingCode());
        try {
            ImageLoader mImageLoader = InternetManager.getInstance(getContext()).getImageLoader();
            mImageLoader.get(country.getImageUrl(), ImageLoader.getImageListener(holder.country_img,
                    R.drawable.default_image, R.drawable.default_image));
        } catch (Exception e) {
            holder.country_img.setImageResource(R.drawable.default_image);
        }
        /*        View textView = (View) super.getView(position, convertView, parent);
        if (Locale.getDefault().getLanguage().equals("ar"))
            ((TextView)textView.findViewById(R.id.textView_countryName)).setText(getItem(position).getAr_name());
        else  ((TextView)textView.findViewById(R.id.textView_countryName)).setText(getItem(position).getEn_name());
        return textView;*/
        return view;

    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.country_spinner_item, parent, false);
  /*          view = (View) ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                    R.layout.country_spinner_item, parent, false);*/
            holder = new ViewHolder();
            holder.country_name = (TextView) view.findViewById(R.id.textView_countryName);
            holder.country_code = (TextView) view.findViewById(R.id.textView_countryCode);
            holder.country_img = (ImageView) view.findViewById(R.id.country_image);
            view.setTag(holder);
        } else {
            view = (View) convertView;
            holder = (ViewHolder) view.getTag();
        }
        Country country = (Country) getItem(position);
        if (Locale.getDefault().getLanguage().equals("ar"))
            holder.country_name.setText(country.getAr_name());
        else holder.country_name.setText(country.getEn_name());
        holder.country_code.setText(" "+country.getDialingCode());
        try {
            ImageLoader mImageLoader = InternetManager.getInstance(getContext()).getImageLoader();
            mImageLoader.get(country.getImageUrl(), ImageLoader.getImageListener(holder.country_img,
                    R.drawable.default_image, R.drawable.default_image));
        } catch (Exception e) {
            holder.country_img.setImageResource(R.drawable.default_image);
        }
        return view;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    static class ViewHolder {
        TextView country_name, country_code;
        ImageView country_img;
    }
}
