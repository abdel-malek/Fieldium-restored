package com.bluebrand.fieldium.core.controller;

import android.content.Context;

import com.bluebrand.fieldium.core.API.APIModel;
import com.bluebrand.fieldium.core.API.URLBuilder;
import com.bluebrand.fieldium.core.model.Company;
import com.bluebrand.fieldium.core.model.Field;
import com.bluebrand.fieldium.core.parser.CompaniesListParser;
import com.bluebrand.fieldium.core.parser.FieldListParser;
import com.bluebrand.fieldium.core.parser.FieldParser;
import com.bluebrand.network.Controller;
import com.bluebrand.network.FaildCallback;
import com.bluebrand.network.RequestMethod;
import com.bluebrand.network.SuccessCallback;
import com.bluebrand.network.TradinosRequest;
import com.bluebrand.fieldium.core.parser.CompanyParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Farah Etmeh on 4/15/16.
 */
public class CompaniesController extends ParentController {


    public static CompaniesController getInstance(Controller controller) {
        return new CompaniesController(controller);
    }


    public CompaniesController(Context context, FaildCallback faildCallback) {
        super(context, faildCallback);
    }

    public CompaniesController(Controller controller) {
        super(controller.getmContext(), controller.getmFaildCallback());
    }


    public void ShowCompany(int companyId, SuccessCallback<Company> successCallback) {
        String url = new URLBuilder(APIModel.companies, "show").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new CompanyParser(), successCallback, getmFaildCallback());
        request.addParameter("company_id", String.valueOf(companyId));
        request.Call();
    }

    public void LoadCompanies(double longitude, double latitude, SuccessCallback<List<Company>> successCallback) {
        String url = new URLBuilder(APIModel.companies, "get_all").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new CompaniesListParser(), successCallback, getmFaildCallback());
//        request.addParameter("longitude", String.valueOf(longitude));
//        request.addParameter("latitude", String.valueOf(latitude));
        request.Call();
    }

    public void GetNearbyCompany(double longitude, double latitude, SuccessCallback<List<Company>> successCallback) {
        String url = new URLBuilder(APIModel.companies, "get_all").getURL();
        TradinosRequest request = new TradinosRequest(getmContext(), url, RequestMethod.Get, new CompaniesListParser(), successCallback, getmFaildCallback());
        request.addParameter("longitude", String.valueOf(longitude));
        request.addParameter("latitude", String.valueOf(latitude));
        addCountryToHeader(request);
        request.Call();
    }



}

