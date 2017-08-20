package com.bluebrand.fieldium.core.parser;

import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Voucher;
import com.bluebrand.network.TradinosParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by b.srour on 6/27/2016.
 */
public class VouchersListParser implements TradinosParser<ArrayList<Voucher>> {
    @Override
    public ArrayList<Voucher> Parse(String data) throws JSONException {

        VoucherParser voucherParser = new VoucherParser();
        ArrayList<Voucher> vouchers =new ArrayList<>();

        JSONArray voucherArray = new JSONArray(data);
        for (int i = 0; i < voucherArray.length(); i++) {
            vouchers.add(voucherParser.Parse(voucherArray.getString(i)));
        }
        return vouchers;
    }
}
