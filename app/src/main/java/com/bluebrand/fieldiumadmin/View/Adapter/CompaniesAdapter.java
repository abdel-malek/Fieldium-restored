package com.bluebrand.fieldiumadmin.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluebrand.fieldiumadmin.Model.Company;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.ApprovedReservationsActivity;
import com.etsy.android.grid.StaggeredGridView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by r.desouki on 2/22/2017.
 */

public class CompaniesAdapter extends ArrayAdapter<Company> {
    List<Company> companyList;
    int position;
    Context context;
    StaggeredGridView gridView;

    public CompaniesAdapter(Context context, int resource, List<Company> companyList,StaggeredGridView gridView) {
        super(context, resource);
        this.companyList = companyList;
        this.context=context;
        this.gridView=gridView;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.position = position;
        View view = ((LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.company_item, null);
        final Company company=getItem(position);
        Picasso.with(context).load(company.getLogo().getUrl()).memoryPolicy(MemoryPolicy.NO_STORE)
                .error(R.mipmap.add_photo)
                .placeholder(R.mipmap.add_photo)
                .into(((ImageView) view.findViewById(R.id.company_cover)));
        ((TextView)view.findViewById(R.id.company_name)).setText(company.getName());
        ((TextView)view.findViewById(R.id.company_address)).setText(company.getAddress());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context, ApprovedReservationsActivity.class);
                intent.putExtra("company_id",getItem(position).getID());
                context.startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public Company getItem(int position) {
        return companyList.get(position);
    }

    @Override
    public int getCount() {
        return companyList.size();
    }

    public List<Company> getCompanies() {
        return companyList;
    }

    public void setCompanies(List fields) {
        this.companyList = fields;
    }

}
