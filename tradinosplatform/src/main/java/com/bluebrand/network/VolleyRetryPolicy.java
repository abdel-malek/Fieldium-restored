package com.bluebrand.network;

import com.android.volley.DefaultRetryPolicy;

/**
 * Created by Developer19 on 2/16/2017.
 */

public class VolleyRetryPolicy extends DefaultRetryPolicy {

    public VolleyRetryPolicy(int initialTimeoutMs, int maxNumRetries, float backoffMultiplier) {
        super(initialTimeoutMs, maxNumRetries, backoffMultiplier);
    }

    @Override
    protected boolean hasAttemptRemaining() {
        return false;
    }
}