package com.bluebrand.fieldiumadmin.Parser;


import com.bluebrand.fieldiumadmin.Model.RefusedMsg;
import com.tradinos.core.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by r.desouki on 8/24/2016.
 */
public class RefusedMsgParser implements TradinosParser<ArrayList<RefusedMsg>> {
    public RefusedMsgParser() {
    }

    @Override
    public ArrayList<RefusedMsg> Parse(String jsonText) throws JSONException {
        ArrayList<RefusedMsg> refusedMsgs;
        JSONArray jsonArray= new JSONArray(jsonText);
        refusedMsgs = Parse(jsonArray);
        return refusedMsgs;
    }

    public ArrayList<RefusedMsg> Parse(JSONArray jsonArray) throws JSONException {
        ArrayList<RefusedMsg> refusedMsgs = new ArrayList();
        for(int i=0;i<jsonArray.length();i++) {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            RefusedMsg refusedMsg = new RefusedMsg();
            refusedMsg.setId(jsonObject.getInt("id"));
            refusedMsg.setText(jsonObject.getString("message"));

            refusedMsgs.add(refusedMsg);
        }
        return refusedMsgs;
    }
}
