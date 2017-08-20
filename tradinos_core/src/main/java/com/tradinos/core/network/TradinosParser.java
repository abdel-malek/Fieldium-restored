package com.tradinos.core.network;

import org.json.JSONException;

/**
 * Created by Farah Etmeh on 4/15/16.
 */

public interface TradinosParser<T>{

    public  T Parse(String text) throws JSONException;
}
