package com.bluebrand.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
/**
 * Created by Farah on 4/22/16.
 */
public class TradinosRequest<T> extends Request<JSONObject> {

    private SuccessCallback<T> successCallback;
    FaildCallback faildCallback;
    private Map<String, String> parameters;
    private Map<String, String> headers;
    private Context context;
    private TradinosParser<T> parser;
    private boolean authenticationRequired = false;

    String url = "";

    public TradinosRequest(Context context, String url, RequestMethod method, final TradinosParser<T> parser, SuccessCallback<T> successCallback, final FaildCallback faildCallback) {

        super(method == RequestMethod.Get ? com.android.volley.Request.Method.GET : com.android.volley.Request.Method.POST, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof AuthFailureError) {
                    faildCallback.OnFaild(Code.AuthenticationError,"Authentication Error !");
                } else if (error instanceof ServerError) {
                    faildCallback.OnFaild(Code.ServerError, "Server Error !");
                } else if (error instanceof NetworkError) {
                    faildCallback.OnFaild(Code.NetworkError, "Network Error !");
                } else if (error instanceof ParseError) {
                    faildCallback.OnFaild(Code.ParsingError, "Parsing Error !");
                } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    faildCallback.OnFaild(Code.TimeOutError, "Timeout Error !");
                } else {
                    faildCallback.OnFaild(Code.ServerError, error.getMessage());
                }

            }
        });
        this.url = url;

        this.context = context;
        parameters = new HashMap<>();
        headers = new HashMap<>();
        this.successCallback = successCallback;
        this.faildCallback = faildCallback;
        this.parser = parser;

        String lang = Locale.getDefault().getLanguage();
        headers.put("lang", lang.equals("ar") ? "ar" : "en");
        if (this.getMethod() == Method.POST)
            this.setRetryPolicy(new VolleyRetryPolicy(30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        else  this.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, -1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        System.setProperty("http.keepAlive","false");

    }


    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {

        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {

            if(volleyError.networkResponse.statusCode == 401){
                return new AuthFailureError() ;
            }

            try {
                String response = new String(volleyError.networkResponse.data) ;
                JSONObject jsonObject = new JSONObject( response ) ;
                boolean status = jsonObject.getBoolean("status");
                if (status) {
                } else {
                    String message = jsonObject.getString("error");
                    volleyError = new VolleyError(message) ;
                }
            } catch (JSONException e) {
                volleyError = new ParseError();
            }

        }
        return volleyError;
    }


    public void turnOnAuthentication(String username, String password) {
        try {
            String authenticationValue = "Basic " +
                    String.valueOf(Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP));
            getHeaders().put("Authorization", authenticationValue/*"Basic OTk0NzI5NDU4OmM1ZTY3MmY1MWQ5MjM3OWFkOTk1MDU3Y2RkZWIxZGM0"*/);
            authenticationRequired = true;
        } catch (Exception e) {
        }
    }

//    @Override
//    protected VolleyError parseNetworkError(VolleyError volleyError){
//        if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
//            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
//            volleyError = error;
//        }
//
//        return volleyError;
//    }


    public void turnOffAuthentication() {
        authenticationRequired = false;
    }

    private long mRequestStartTime;

    public void Call() {
        mRequestStartTime = System.currentTimeMillis();
        this.setShouldCache(false);
//                System.setProperty("http.keepAlive","false");

        InternetManager.getInstance(getContext()).addToRequestQueue(this,url);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

       try {
            String data = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;

            Log.d("execution_time", "execution_time = " + totalRequestTime);
            try {

                JSONObject json = new JSONObject(data);
                return Response.success(
                        json,
                        HttpHeaderParser.parseCacheHeaders(response));

            } catch (JSONException ex) {
                return Response.error(new ParseError(ex));
            }
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }

    }
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }
    public void addParameter(String key, String value) {

        parameters.put(key, value);
        if (getMethod() == Method.GET) {
            if (parameters.size() == 0)
                url += "?";
            else
                url += "&";

            url += key + "=" + value;

        }

    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }


    @Override
    protected void deliverResponse(JSONObject jsonObject) {
        try {
            boolean status = jsonObject.getBoolean("status");
            if (status) {
                String data = jsonObject.optString("data", "");
                if (!data.equals("")&& parser !=null) {
                    T result = (T) parser.Parse(data);
                    successCallback.OnSuccess(result);
                } else {
                    faildCallback.OnFaild(Code.NoData, "Empty data !");
                }
//                String message=jsonObject.getString("message");
            } else {
                String message = jsonObject.getString("message");
                faildCallback.OnFaild(Code.ServerError, message);
            }
        } catch (JSONException e) {
            faildCallback.OnFaild(Code.ParsingError, "Parsing Error");
        }
    }

    public boolean isAuthenticationRequired() {
        return authenticationRequired;
    }

    public void setAuthenticationRequired(boolean authenticationRequired) {
        this.authenticationRequired = authenticationRequired;
    }

}
