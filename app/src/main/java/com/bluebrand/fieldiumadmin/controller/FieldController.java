package com.bluebrand.fieldiumadmin.controller;

import android.content.Context;

import com.tradinos.core.network.Code;
import com.tradinos.core.network.Controller;
import com.tradinos.core.network.FaildCallback;
import com.tradinos.core.network.PhotoMultipartRequest;
import com.tradinos.core.network.RequestMethod;
import com.tradinos.core.network.SuccessCallback;
import com.tradinos.core.network.TradinosRequest;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.bluebrand.fieldiumadmin.API.APIModel;
import com.bluebrand.fieldiumadmin.API.URLBuilder;
import com.bluebrand.fieldiumadmin.Model.Amenity;
import com.bluebrand.fieldiumadmin.Model.Area;
import com.bluebrand.fieldiumadmin.Model.Company;
import com.bluebrand.fieldiumadmin.Model.Field;
import com.bluebrand.fieldiumadmin.Model.Game;
import com.bluebrand.fieldiumadmin.Model.Image;
import com.bluebrand.fieldiumadmin.Parser.AmenityListParser;
import com.bluebrand.fieldiumadmin.Parser.AreaListParser;
import com.bluebrand.fieldiumadmin.Parser.CompanyImageParser;
import com.bluebrand.fieldiumadmin.Parser.CompanyParesr;
import com.bluebrand.fieldiumadmin.Parser.FieldListParser;
import com.bluebrand.fieldiumadmin.Parser.FieldParser;
import com.bluebrand.fieldiumadmin.Parser.GameListParser;
import com.bluebrand.fieldiumadmin.Parser.ImageParser;
import com.bluebrand.fieldiumadmin.Parser.StringParser;

/**
 * Created by r.desouki on 12/15/2016.
 */
public class FieldController extends ParentController {
    public static FieldController getInstance (Controller controller) {
        return new FieldController(controller);
    }

    public FieldController(Controller controller) {
        super(controller.getmContext(), controller.getmFaildCallback());
    }

    public FieldController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }



    public void getCompanyInfo (int comp_id,SuccessCallback<Company> successCallback) {
        String url= new URLBuilder(APIModel.companies,"show").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new CompanyParesr(), successCallback,getmFaildCallback()) ;
        request.addParameter("company_id",String.valueOf(comp_id));
        request.Call();
    }

    public void updateCompanyInfo (Map<String,String>map ,SuccessCallback<Company> successCallback) {
        String url= new URLBuilder(APIModel.companies,"update").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post,new CompanyParesr(), successCallback,getmFaildCallback()) ;
        request.addParameter("company_id",map.get("company_id"));
        request.addParameter("name",map.get("name"));
        request.addParameter("phone",map.get("phone"));
        request.addParameter("description",map.get("description"));
        request.addParameter("address",map.get("address"));
        request.addParameter("longitude",map.get("longitude"));
        request.addParameter("latitude",map.get("latitude"));
        request.addParameter("area_id",map.get("area_id"));
        request.addParameter("image",map.get("image"));
        request.addParameter("logo",map.get("logo"));

        authenticationRequired(request);
        request.Call();
    }

    public void updateCompanyLocation(int comanyID, double lon, double lat, SuccessCallback<Company> successCallback) {
        String url= new URLBuilder(APIModel.companies,"update_location").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post,new CompanyParesr(), successCallback,getmFaildCallback()) ;
        request.addParameter("company_id",String.valueOf(comanyID));
        request.addParameter("longitude",String.valueOf(lon));
        request.addParameter("latitude",String.valueOf(lat));

        authenticationRequired(request);
        request.Call();
    }

    public void getFields (int comp_id,SuccessCallback<List<Field>> successCallback) {
        String url= new URLBuilder(APIModel.fields,"get_by_company").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new FieldListParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("company_id",String.valueOf(comp_id));
        authenticationRequired(request);
        request.Call();
    }

    public void getAmenities (SuccessCallback<List<Amenity>> successCallback) {
        String url= new URLBuilder(APIModel.amenities,"get_all").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new AmenityListParser(), successCallback,getmFaildCallback()) ;
        authenticationRequired(request);
        request.Call();
    }

    public void getGames (SuccessCallback<List<Game>> successCallback) {
        String url= new URLBuilder(APIModel.games,"get_all").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new GameListParser(), successCallback,getmFaildCallback()) ;
        authenticationRequired(request);
        request.Call();
    }

    public void getAreas (SuccessCallback<List<Area>> successCallback) {
        String url= new URLBuilder(APIModel.areas,"get_all").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new AreaListParser(), successCallback,getmFaildCallback()) ;
        authenticationRequired(request);
        request.Call();
    }

    public void createField (Map<String,String>map,List<Image>images ,SuccessCallback<Field> successCallback) {
        String url= new URLBuilder(APIModel.fields,"create").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post,new FieldParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("company_id",map.get("company_id"));
        request.addParameter("name",map.get("name"));
        request.addParameter("hour_rate",map.get("hour_rate"));
        request.addParameter("open_time",map.get("open_time"));
        request.addParameter("close_time",map.get("close_time"));
        request.addParameter("phone",map.get("phone"));
        request.addParameter("description",map.get("description"));
        request.addParameter("area_x",map.get("area_x"));
        request.addParameter("area_y",map.get("area_y"));
        request.addParameter("max_capacity",map.get("max_capacity"));
        request.addParameter("auto_confirm",map.get("auto_confirm"));
        request.addParameter("amenities",map.get("amenities"));
        request.addParameter("games_types",map.get("games_types"));
        if(map.containsKey("ChildsFieldsIds"))
            request.addParameter("children",map.get("ChildsFieldsIds"));

        for (int i=0;i<images.size();i++)
        {
            request.addParameter("images["+i+"]",String.valueOf(images.get(i).getId()));

        }
        authenticationRequired(request);
        request.Call();
    }



    public void updateField (Map<String,String>map,List<Image>images ,SuccessCallback<Field> successCallback) {
        String url= new URLBuilder(APIModel.fields,"update").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Post,new FieldParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("field_id",map.get("field_id"));
        request.addParameter("company_id",map.get("company_id"));
        request.addParameter("name",map.get("name"));
        request.addParameter("hour_rate",map.get("hour_rate"));
        request.addParameter("open_time",map.get("open_time"));
        request.addParameter("close_time",map.get("close_time"));
        request.addParameter("phone",map.get("phone"));
        request.addParameter("description",map.get("description"));
        request.addParameter("area_x",map.get("area_x"));
        request.addParameter("area_y",map.get("area_y"));
        request.addParameter("auto_confirm",map.get("auto_confirm"));
        request.addParameter("max_capacity",map.get("max_capacity"));
        request.addParameter("amenities",map.get("amenities"));
        request.addParameter("games_types",map.get("games_types"));
        if(map.containsKey("ChildsFieldsIds"))
            request.addParameter("children",map.get("ChildsFieldsIds"));
        for (int i=0;i<images.size();i++)
        {
            request.addParameter("images["+i+"]",String.valueOf(images.get(i).getId()));

        }
        authenticationRequired(request);
        request.Call();
    }

    public void deleteField (int ID,SuccessCallback<String> successCallback) {
        String url= new URLBuilder(APIModel.fields,"delete").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new StringParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("field_id",String.valueOf(ID));
        authenticationRequired(request);
        request.Call();
    }


    public void uploadImage (final int index , File image , final SuccessCallback<Image> successCallback , final FaildCallback faildCallback){
        String url= new URLBuilder(APIModel.fields,"upload_image").getURL();
        PhotoMultipartRequest photoMultipartRequest =  new PhotoMultipartRequest(image, getmContext(), url, RequestMethod.Post, new ImageParser(), new SuccessCallback<Image>() {
            @Override
            public void OnSuccess(Image result) {
                result.setIndex(index);
                successCallback.OnSuccess(result);
            }
        }, new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message) {
                faildCallback.OnFaild(errorCode,String.valueOf(index));
            }
        }) ;
        authenticationRequired(photoMultipartRequest);
        photoMultipartRequest.Call();
    }

    public void uploadCompanyImage (final int index , File image , final SuccessCallback<Image> successCallback , final FaildCallback faildCallback){
        String url= new URLBuilder(APIModel.companies,"upload_image").getURL();
        PhotoMultipartRequest photoMultipartRequest =  new PhotoMultipartRequest(image, getmContext(), url, RequestMethod.Post, new CompanyImageParser(), new SuccessCallback<Image>() {
            @Override
            public void OnSuccess(Image result) {
                result.setIndex(index);
                successCallback.OnSuccess(result);
            }
        }, new FaildCallback() {
            @Override
            public void OnFaild(Code errorCode, String Message) {
                faildCallback.OnFaild(errorCode,String.valueOf(index));
            }
        }) ;
        authenticationRequired(photoMultipartRequest);
        photoMultipartRequest.Call();
    }
}