package com.bluebrand.network;

import android.content.Context;
import android.net.Uri;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.protocol.HTTP;

//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.MultipartEntityBuilder;

public class PhotoMultipartRequest<T> extends TradinosRequest<T> {


    private static final String FILE_PART_NAME = "image";
    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();

    private final File mImageFile;
//    String name, email, address, games;
    Context context;

    public PhotoMultipartRequest(/*String playerName, String memail, String maddress, String mgames, */File imageFile, Context context, String url, RequestMethod method, final TradinosParser<T> parser, SuccessCallback<T> successCallback, final FaildCallback faildCallback) {
        super(context, url, method, parser, successCallback, faildCallback);
        mImageFile = imageFile;
    /*    name = playerName;
        email = memail;
        address = maddress;
        games = mgames;*/
        buildMultipartEntity();
    }

    /*   @Override
       public Map<String, String> getHeaders() throws AuthFailureError {
           Map<String, String> headers = super.getHeaders();

           if (headers == null
                   || headers.equals(Collections.emptyMap())) {
               headers = new HashMap<String, String>();
           }

           headers.put("Accept", "application/json");

           return headers;
       }
   */
    private void buildMultipartEntity() {
        if (mImageFile != null) {
            mBuilder.addBinaryBody(FILE_PART_NAME, mImageFile);
//            mBuilder.addTextBody("delete", String.valueOf(1));
        } else {
            mBuilder.addTextBody(FILE_PART_NAME, "");
            mBuilder.addTextBody("delete", String.valueOf(1));
        }
      /*  mBuilder.addTextBody("name", name, ContentType.create("text/plain", MIME.UTF8_CHARSET));
//        mBuilder.addTextBody("name", name);
        mBuilder.addTextBody("email", email);
        mBuilder.addTextBody("image_updated", String.valueOf(true));
        mBuilder.addTextBody("address", address, ContentType.create("text/plain", MIME.UTF8_CHARSET));
        mBuilder.addTextBody("prefered_games", games);*/
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mBuilder.setLaxMode().setBoundary("xx").setCharset(Charset.forName("UTF-8"));
  /*      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }*/
    }

    @Override
    public String getBodyContentType() {
        String contentTypeHeader = mBuilder.build().getContentType().getValue();
        return contentTypeHeader;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            mBuilder.build().writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
        }
        return bos.toByteArray();
    }

}