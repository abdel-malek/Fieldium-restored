package com.tradinos.core.network;

import android.content.Context;
import android.util.Base64;

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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Farah on 4/22/16.
 */
public class TradinosRequest<T>  extends Request<JSONObject> {

    private SuccessCallback<T> successCallback;
    FaildCallback faildCallback;
    private Map<String, String> parameters;
    private Map<String, String> headers;
    private Context context;
    private TradinosParser<T> parser;
    private boolean authenticationRequired = false;

    String url = "";

    public TradinosRequest(Context context, String url, RequestMethod method, final TradinosParser<T> parser, SuccessCallback<T> successCallback, final FaildCallback faildCallback) {

        super(method == RequestMethod.Get ? Method.GET : Method.POST, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof AuthFailureError) {
                    faildCallback.OnFaild(Code.AuthenticationError, "Authentication Error !");
                } else if (error instanceof ServerError) {
                    faildCallback.OnFaild(Code.ServerError, "Server Error !");
                } else if (error instanceof NetworkError) {
                    faildCallback.OnFaild(Code.NetworkError, "Network Error !");
                } else if (error instanceof ParseError) {
                    faildCallback.OnFaild(Code.ParsingError, "Parsing Error !");
                } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    faildCallback.OnFaild(Code.TimeOutError, "Timeout Error !");
                }else {
                    faildCallback.OnFaild(Code.TimeOutError, "Unknown Server Error !");
                }
            }
        });
        this.url = url;

        this.context = context;
        parameters = new HashMap<>();
        headers = new HashMap<>();
        //String lang = Locale.getDefault().getLanguage() ;
        headers.put("Lang","en");
        this.successCallback = successCallback;
        this.faildCallback = faildCallback;
        this.parser = parser;

        if(this.getMethod() == Method.POST)
            this.parser = parser;this.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError){
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
           if(volleyError.networkResponse.statusCode == 401){
               return new AuthFailureError() ;
               }
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
           volleyError = error;
            }
       return volleyError;
    }


    public void turnOnAuthentication(String username, String password) {
        try {
            String authenticationValue = "Basic " +
                    String.valueOf(Base64.encodeToString((username + ":" + password).getBytes(), Base64.NO_WRAP));
            getHeaders().put("Authorization", authenticationValue);
            authenticationRequired = true;
        } catch (Exception e) {
        }
    }

    public void turnOffAuthentication() {
        authenticationRequired = false;
    }

    public void Call() {
        InternetManager.getInstance(getContext()).addToRequestQueue(this);
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

            JSONObject json = new JSONObject(data);
            return Response.success(
                    json,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    public void addParameter(String key, String value) {

        parameters.put(key, value);
        if (getMethod() == Method.GET) {
            if (parameters.size() == 0)
                url += "?"  ;
            else
                url += "&";

            url+= key + "=" + value ;

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
                if(!data.equals("null") && parser!=null)
                {
                    T result = parser.Parse(data);
                    successCallback.OnSuccess(result);
                }else {
                    String message = jsonObject.optString("message", "");
                    successCallback.OnSuccess((T) message);
                }
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
