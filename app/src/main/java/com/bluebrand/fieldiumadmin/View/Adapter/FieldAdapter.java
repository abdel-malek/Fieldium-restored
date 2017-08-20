package com.bluebrand.fieldiumadmin.View.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tradinos.core.network.SuccessCallback;

import java.util.List;

import com.bluebrand.fieldiumadmin.Model.Field;
import com.bluebrand.fieldiumadmin.MyApplication;
import com.bluebrand.fieldiumadmin.R;
import com.bluebrand.fieldiumadmin.View.MyFieldsActivity;
import com.bluebrand.fieldiumadmin.controller.FieldController;

/**
 * Created by r.desouki on 1/10/2017.
 */
public class FieldAdapter extends ArrayAdapter<Field> {

    ListView listView;
    List<Field> fields;
    int position;
    Context context;
    public FieldAdapter(Context context, int resource, List<Field> fields, ListView listView) {
        super(context, resource);
        this.fields = fields;
        this.listView = listView;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.position = position;
        View view = ((LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.field_listview_item, null);

        Field field = getItem(position);
        TextView fieldName_textView = (TextView) view.findViewById(R.id.fieldName_textView);
        TextView fieldType_textView = (TextView) view.findViewById(R.id.fieldType_textView);
        ImageView deleteField_imageView=(ImageView)view.findViewById(R.id.deleteField_imageView);
        deleteField_imageView.setImageBitmap(getBitmapFromResources(context.getResources(), R.mipmap.delete));

        ImageView field_imageView=(ImageView)view.findViewById(R.id.field_imageView);
        field_imageView.setImageBitmap(getBitmapFromResources(context.getResources(), R.mipmap.field_grey));

        fieldName_textView.setText(String.valueOf(field.getName()));
        String types="";
        for(int i=0;i<field.getGames().size();i++){
            if(i==0)
                types=field.getGames().get(i).getName();
            else
                types+=", "+field.getGames().get(i).getName();
        }
        fieldType_textView.setText(types);

        deleteField_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setMessage("Do you really want to delete "+fields.get(position).getName()+" ?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                ((MyFieldsActivity)context).ShowProgressDialog();
                                FieldController.getInstance(((MyFieldsActivity)context).getmController()).deleteField(fields.get(position).getField_id(), new SuccessCallback<String>() {
                                    @Override
                                    public void OnSuccess(String result) {
                                        ((MyFieldsActivity)context).HideProgressDialog();
                                        ((MyFieldsActivity)context).fieldsChanged=true;
                                        fields.remove(position);
                                        ((MyApplication)((MyFieldsActivity)context).getApplication()).setMyFields(fields);
                                        notifyDataSetChanged();
                                    }
                                });
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });



        return view;
    }

    @Override
    public Field getItem(int position) {
        return fields.get(position);
    }

    @Override
    public int getCount() {
        return fields.size();
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List fields) {
        this.fields = fields;
    }

    public static Bitmap getBitmapFromResources(Resources resources, int resImage) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inSampleSize = 1;
        options.inScaled = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeResource(resources, resImage, options);
    }
}
