package com.bluebrand.fieldiumadmin.View.Adapter;

/**
 * Created by r.desouki on 5/18/2016.
 */

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.bluebrand.fieldiumadmin.Model.RefusedMsg;
import com.bluebrand.fieldiumadmin.R;

import java.util.List;


public class RefusedMesgAdapter extends ArrayAdapter<RefusedMsg> {

    List<RefusedMsg> refusedMsgs;
    int position;
    Context context;
    int oneChecked=0;
    int selectedID=0;
    String selectedMsg="";

    public RefusedMesgAdapter(Context context, int resource, List<RefusedMsg> refusedMsgs) {
        super(context, resource);
        this.refusedMsgs = refusedMsgs;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        this.position=position;
        View view = ((LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.refuse_message, null);

        final RefusedMsg item = getItem(position);

        final CheckBox checkBox=(CheckBox) view.findViewById(R.id.mesg_checkBox);
        TextView mesg_textView=(TextView)view.findViewById(R.id.mesg_textView);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                {
                    if(oneChecked==1)
                        checkBox.setChecked(false);
                    else {
                        oneChecked++;
                        selectedID = item.getId();
                    }
                }
                else
                {
                    selectedID=0;
                    oneChecked--;
                }
            }
        });

        View.OnClickListener onClickListener=new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                {
                    checkBox.setChecked(false);
                    oneChecked--;
                    selectedID=0;
                }
                else
                {
                    if(oneChecked==1)
                        checkBox.setChecked(false);
                    else {
                        oneChecked++;
                        checkBox.setChecked(true);
                        selectedID=item.getId();
                    }

                }
            }
        };

        mesg_textView.setOnClickListener(onClickListener);

        mesg_textView.setText(item.getText());
        if(item.getId()==0)
        {
            final EditText editText=new EditText(context);
            editText.setWidth(500);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    selectedMsg=editText.getText().toString();
                    if(oneChecked!=1)
                    {
                        checkBox.setChecked(true);
                        oneChecked++;
                    }
                }
            });


            ((ViewGroup)mesg_textView.getParent()).addView(editText);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) editText.getLayoutParams();
            p.setMargins(30, 0, 30, 0);
/*
            v.requestLayout();
*/
        }
        return view;
    }

    @Override
    public RefusedMsg getItem(int position) {
        return refusedMsgs.get(position);
    }

    @Override
    public int getCount() {
        return refusedMsgs.size();
    }

    public List<RefusedMsg> getOrders()
    {
        return refusedMsgs;
    }

    public void setOrders(List refusedMsgs)
    {
        this.refusedMsgs = refusedMsgs;
    }

    public int getSelectedMsgId(){
        return selectedID;
    }

    public String getSelectedMsgString(){
        return selectedMsg;
    }
}
