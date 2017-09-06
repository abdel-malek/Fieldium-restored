package com.bluebrand.fieldium.core.API;

/**
 * Created by Farah Etmeh on 4/22/16.
 */
 public class URLBuilder {

    private static final String SERVER_URL ="http://fieldium2.tradinos.com/index.php";

//    private static final String SERVER_URL ="http://field-dash.tradinos.com/index.php";
//    private static final String SERVER_URL ="http://blog.tradinos.com/index.php";
//    private static final String SERVER_URL ="http://192.168.1.7/fieldium-server/index.php";

    private static final boolean RE_WRITING_URL = true;

    private APIModel model ;
    private String action ;
    private APIFormat format ;

    public URLBuilder(APIModel model, String action) {
        this.model = model ;
        this.action = action ;
        this.format = APIFormat.JSON ;
    }

    public URLBuilder(APIModel model, String action, APIFormat format) {
        this.model = model ;
        this.action = action ;
        this.format = format ;
    }

    public String getURL() {
        String url ;
        if(RE_WRITING_URL)
            url = SERVER_URL + "/" + model.toString() + "/" + action +"/format/"+format.toString()+"?" ;
        else
            url = SERVER_URL + "?method=" +model.toString() + "." + action +"&format="+format.toString() ;

        return url ;
    }



}