package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Offer;
import com.bluebrand.fieldium.core.model.Voucher;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by b.srour on 6/27/2016.
 */
public class OffersListParser implements TradinosParser<ArrayList<Offer>> {
    @Override
    public ArrayList<Offer> Parse(String data) throws JSONException {

        OfferParser offerParser = new OfferParser();
        ArrayList<Offer> offers =new ArrayList<>();

        JSONArray voucherArray = new JSONArray(data);
        for (int i = 0; i < voucherArray.length(); i++) {
            offers.add(offerParser.Parse(voucherArray.getString(i)));
        }
        return offers;
    }
}
