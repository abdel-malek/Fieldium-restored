package com.bluebrand.fieldium.view.other;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by malek on 5/12/16.
 */
public class TextUtils {

    public static Typeface getFont (Context context) {
         return  Typeface.createFromAsset(context.getAssets(), "SourceSansPro-Regular.ttf");

    }
}
