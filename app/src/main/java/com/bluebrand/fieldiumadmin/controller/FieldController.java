package com.bluebrand.fieldiumadmin.controller;

import android.content.Context;

import com.bluebrand.fieldiumadmin.Parser.CompanyListParser;
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


    public void searchCompanies (String name,SuccessCallback<List<Company>> successCallback) {
        String url= new URLBuilder(APIModel.searches,"search").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new CompanyListParser(), successCallback,getmFaildCallback()) ;
        request.addParameter("name",name);
        request.Call();
    }

    public void getAllCompanies (SuccessCallback<List<Company>> successCallback) {
        String url= new URLBuilder(APIModel.companies,"get_all").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new CompanyListParser(), successCallback,getmFaildCallback()) ;
        request.Call();
    }

    public void getCompanyInfo (int comp_id,SuccessCallback<Company> successCallback) {
        String url= new URLBuilder(APIModel.companies,"show").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(),url, RequestMethod.Get,new CompanyParesr(), successCallback,getmFaildCallback()) ;
        request.addParameter("company_id",String.valueOf(comp_id));
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


}