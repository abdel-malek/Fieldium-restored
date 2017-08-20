package com.bluebrand.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by farah on 4/21/16.
 */
public class InternetManager {

    private static InternetManager mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private InternetManager(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static synchronized InternetManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new InternetManager(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.

            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        mRequestQueue.getCache().clear();
//        System.setProperty("http.keepAlive","false");

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req,String url) {
        getRequestQueue().getCache().remove(url);
        getRequestQueue().getCache().clear();
        getRequestQueue().add(req);
    }

    public void CancelAll (){
        getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }


    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}