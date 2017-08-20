package com.bluebrand.fieldium.core.model;

import java.io.Serializable;


/**
 * Created by Player on 5/29/2016.
 */
public class Country implements Serializable {

    private int id;
    private String ar_name;
    private String en_name;
    private String imageUrl;
    private String englishCurrency;
    private String arabicCurrency;
    private String dialingCode;

//    private String foreign_name;

    public Country() {
        this.id=0;
        this.ar_name="";
        this.en_name="";
        this.imageUrl="";
        this.arabicCurrency="";
        this.englishCurrency="";
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAr_name() {
        return ar_name;
    }

    public void setAr_name(String ar_name) {
        this.ar_name = ar_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getEnglishCurrency() {
        return englishCurrency;
    }

    public void setEnglishCurrency(String englishCurrency) {
        this.englishCurrency = englishCurrency;
    }

    public String getArabicCurrency() {
        return arabicCurrency;
    }

    public void setArabicCurrency(String arabicCurrency) {
        this.arabicCurrency = arabicCurrency;
    }

    public String getDialingCode() {
        return dialingCode;
    }

    public void setDialingCode(String dialingCode) {
        this.dialingCode = dialingCode;
    }

    @Override
    public boolean equals(Object o) {

        if(this.getId()==((Country)o).getId())
            return true;
        else return false;
    }
}
