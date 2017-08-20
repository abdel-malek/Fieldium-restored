package com.bluebrand.fieldium.view.adapter;

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


public class CountryAdapterFirstScreen extends ArrayAdapter<Country> {

    //    Company mCompany;
    private int selectedIndex;

    public CountryAdapterFirstScreen(Context context, int resource, List<Country> objects) {
        super(context, resource, objects);
        selectedIndex = -1;
    }

    @Override
    public int getViewTypeCount() {
        int n = getCount();
        if (n == 0)
            return 1;
        else
            return n;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setSelectedIndex(int ind) {
        selectedIndex = ind;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if (convertView == null) {
            view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.country_item_first_screen, null);
            holder = new ViewHolder();
            holder.textView_country_name = (TextView) view.findViewById(R.id.textView_countryName);
            holder.textView_country_image = (ImageView) view.findViewById(R.id.country_image);
            holder.textView_check_image = (ImageView) view.findViewById(R.id.checkMark_imageView);

            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        final Country country = (Country) getItem(position);
//
        String lang = Locale.getDefault().getLanguage();
        if (lang.equals("ar"))
            holder.textView_country_name.setText(country.getAr_name());
        else
            holder.textView_country_name.setText(country.getEn_name());
        try {
            ImageLoader mImageLoader = InternetManager.getInstance(getContext()).getImageLoader();
            mImageLoader.get(country.getImageUrl(), ImageLoader.getImageListener(holder.textView_country_image,
                    R.drawable.default_image, R.drawable.default_image));
        } catch (Exception e) {
            holder.textView_country_image.setImageResource(R.drawable.default_image);
        }
        if (selectedIndex != -1 && position == selectedIndex) {
            holder.textView_check_image.setVisibility(View.VISIBLE);
        } else {
            holder.textView_check_image.setVisibility(View.GONE);
        }

        return view;
    }


    static class ViewHolder {
        TextView textView_country_name;
        ImageView textView_country_image;
        ImageView textView_check_image;
    }
}
