package com.bluebrand.fieldium.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.City;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Country;
import com.bluebrand.fieldium.view.Fields.FieldsListActivity;
import com.bluebrand.fieldium.view.company.CompaniesListActivity;
import com.bluebrand.network.InternetManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * Created by Farah Etmeh on 4/22/16.
 */
public class CountryAdapter extends ArrayAdapter<Country> {

//    Company mCompany;

    public CountryAdapter(Context context, int resource, List<Country> objects) {
        super(context, resource, objects);
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        if (convertView == null) {
            view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.country_item, null);
            holder = new ViewHolder();
            holder.textView_country_name = (TextView) view.findViewById(R.id.textView_countryName);
            holder.textView_country_image = (ImageView) view.findViewById(R.id.country_image);

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

        return view;
    }


    static class ViewHolder {
        TextView textView_country_name;
        ImageView textView_country_image;
    }
}
