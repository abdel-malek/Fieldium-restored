package  com.tradinos.core.network;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import com.android.volley.AuthFailureError;
import com.android.volley.VolleyLog;

public class PhotoMultipartRequest<T> extends TradinosRequest<T> {


    private static final String FILE_PART_NAME = "image";
    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();

    private final File mImageFile;
    Context context ;
    public PhotoMultipartRequest(File imageFile , Context context, String url, RequestMethod method, final TradinosParser<T> parser, SuccessCallback<T> successCallback, final FaildCallback faildCallback) {
        super(context , url , method ,parser,successCallback,faildCallback );
        mImageFile = imageFile;
        buildMultipartEntity();
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (headers == null
                || headers.equals(Collections.emptyMap())) {
            headers = new HashMap<String, String>();
        }

        headers.put("Accept", "application/json");

        return headers;
    }

    private void buildMultipartEntity()
    {
        mBuilder.addBinaryBody(FILE_PART_NAME, mImageFile);
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mBuilder.setLaxMode().setBoundary("xx").setCharset(Charset.forName("UTF-8"));
    }

    @Override
    public String getBodyContentType()
    {
        String contentTypeHeader = mBuilder.build().getContentType().getValue();
        return contentTypeHeader;
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            mBuilder.build().writeTo(bos);
        }
        catch (IOException e)
        {
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
        }

        return bos.toByteArray();
    }

}