package com.bluebrand.fieldium.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.Game;

import java.util.List;

/**
 * Created by Player on 7/19/2016.
 */
public class GamesAdapter extends ArrayAdapter<Game> {
    public GamesAdapter(Context context, int resource, List<Game> objects) {
        super(context, resource, objects);
    }


    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Game getItem(int position) {
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
