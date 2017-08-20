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
import com.bluebrand.fieldium.core.controller.UserUtils;
import com.bluebrand.fieldium.view.Fields.FieldsListActivity;
import com.bluebrand.fieldium.view.company.CompaniesListActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.bluebrand.fieldium.R;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.network.InternetManager;

import java.util.List;
import java.util.Locale;

/**
 * Created by Farah Etmeh on 4/22/16.
 */
public class CompanyAdapter extends ArrayAdapter<Company> {

//    Company mCompany;

    public CompanyAdapter(Context context, int resource, List<Company> objects) {
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
            view = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.company_item, null);
            holder = new ViewHolder();
            holder.textView_company_name = (TextView) view.findViewById(R.id.company_name);
            holder.textView_fields_count = (TextView) view.findViewById(R.id.fields_count);
            holder.textView_companyImage = (ImageView) view.findViewById(R.id.company_image);
            holder.textView_logoImage = (ImageView) view.findViewById(R.id.logo_image);
            holder.textView_viewOnMap = (ImageView) view.findViewById(R.id.view_on_map);
            holder.textView_company_address = (TextView) view.findViewById(R.id.company_address);
            holder.textView_startPriceFrom = (TextView) view.findViewById(R.id.startPriceFrom);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

        final Company company = getItem(position);
//        mCompany = company;

//        holder.textView_country_name.setTypeface(TextUtils.getFont(getContext()));
        holder.textView_company_name.setText(company.getName());
        holder.textView_company_address.setText(company.getAddress().getName());
        holder.textView_fields_count.setText(String.valueOf(company.getFieldCount()) + getContext().getResources().getString(R.string.fields));
        String currency=((CompaniesListActivity)getContext()).getCurrency();

        holder.textView_startPriceFrom.setText(getContext().getResources().getString(R.string.starts_from)+" "
                + company.getPriceStartFrom() + " " + currency);


        if (!company.getImageURL().equals("")) {
       /*     try {
                Picasso.with(getContext())
                        .load(company.getImageURL()).memoryPolicy(MemoryPolicy.NO_STORE)
                        .error(android.R.color.transparent)
                        .placeholder()
                        .into(holder.textView_country_image);
            } catch (Exception e) {

            }*/
            try {
                ImageLoader mImageLoader = InternetManager.getInstance(getContext()).getImageLoader();
                mImageLoader.get(company.getImageURL(), ImageLoader.getImageListener(holder.textView_companyImage,
                        R.drawable.default_image, R.drawable.default_image));
            } catch (Exception e) {
                holder.textView_companyImage.setImageResource(R.drawable.default_image);
            }

        }
        if (!company.getLogoURL().equals("")) {
            try {
                Picasso.with(getContext())
                        .load(company.getLogoURL()).memoryPolicy(MemoryPolicy.NO_STORE)
                        .error(android.R.color.transparent)
                        .placeholder(android.R.color.transparent)
                        .into(holder.textView_logoImage);
            } catch (Exception e) {

            }
        }
        ////// assign click events
        holder.textView_viewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), com.bluebrand.fieldium.view.Fields.MapActivity.class);
                i.putExtra("label", company.getName());
                i.putExtra("address", company.getAddress());
                ((CompaniesListActivity) getContext()).startActivity(i);
            }
        });

        holder.textView_company_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFieldsListActivity(company);

            }
        });
        holder.textView_logoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFieldsListActivity(company);

            }
        });
        holder.textView_company_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFieldsListActivity(company);

            }
        });
        holder.textView_companyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFieldsListActivity(company);
            }
        });

        return view;
    }

    public void showFieldsListActivity(Company company) {
        Intent intent = new Intent(getContext(), FieldsListActivity.class);
        intent.putExtra("company_id", company.getId());
        intent.putExtra("company_name", company.getName());
        ((CompaniesListActivity) getContext()).startActivityForResult(intent,11);
    }

    static class ViewHolder {
        TextView textView_company_name;
        TextView textView_company_address;
        TextView textView_fields_count;
        TextView textView_startPriceFrom;
        ImageView textView_companyImage;
        ImageView textView_logoImage;
        ImageView textView_viewOnMap;


    }
}
