package com.bluebrand.fieldium.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bluebrand.fieldium.core.model.City;
import com.bluebrand.fieldium.R;

import java.util.List;

/**
 * Created by Player on 7/19/2016.
 */
public class AreaAdapter extends ArrayAdapter<City> {
    public AreaAdapter(Context context, int resource, List<City> objects) {
        super(context, resource, objects);
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public City getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) super.getView(position, convertView, parent);
        textView.setText(getItem(position).getName());
        return textView;

    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = (TextView) ((LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE)).inflate(
                    R.layout.spinner_item, parent, false);
        } else {
            textView = (TextView) convertView;
        }
        textView.setText(getItem(position).getName());
        return textView;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


}
