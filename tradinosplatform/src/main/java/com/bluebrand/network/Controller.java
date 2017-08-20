package com.bluebrand.network;

import android.content.Context;

/**
 * Created by malek on 4/15/16.
 */
public  class Controller {
    private Context mContext ;
    private FaildCallback mFaildCallback ;


    public Controller (Context context , FaildCallback faildCallback){
        this.mContext = context ;
        this.mFaildCallback = faildCallback ;

    }

    public Context getmContext() {
        return mContext;
    }

    public FaildCallback getmFaildCallback() {
        return mFaildCallback;
    }


}
