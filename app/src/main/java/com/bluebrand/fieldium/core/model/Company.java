package com.bluebrand.fieldium.core.model;



import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Farah Etmeh on 4/15/16.
 */
public class Company implements Serializable {

    private int id;
    private String name;
    private String phone;
    private Address address;
    private String description;
    private int fieldCount;
    private BigDecimal priceStartFrom=new BigDecimal(0);
//    private BigDecimal price;
    private int imageRes;
    private String imageURL;
    private String logoURL;
    /*private ArrayList<Company> subCategories ;
    private ArrayList<Field> fields;*/
//    private Company mparentCatgory;

    public Company() {

    }

    public Company(int id, String name, String description, BigDecimal price, int imageRes) {
        this.id = id;
        this.name = name;
        this.description = description;
//        this.price = price;
        this.imageRes = imageRes;
        /*fields = new ArrayList<>() ;
        subCategories = new ArrayList<>() ;*/

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public int getFieldCount() {
        return fieldCount;
    }

    public void setFieldCount(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
/*

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
*/

    public BigDecimal getPriceStartFrom() {
        return priceStartFrom;
    }

    public void setPriceStartFrom(BigDecimal priceStartFrom) {
        this.priceStartFrom = priceStartFrom;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int image) {
        this.imageRes = image;
    }

/*    public ArrayList<Company> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<Company> subCategories) {
        this.subCategories = subCategories;
    }
    public void addSubcategory (Company subCompanies) {
        if (subCategories == null )
            subCategories = new ArrayList<>() ;
        subCategories.add(subCompanies);
    }

    public void addProduct (Field field) {
        if (getFields() == null ) {
            fields = new ArrayList<>() ;
        }
        getFields().add(field);
    }*/

/*    public ArrayList<Field> getFields() {
        return fields;
    }*/
/*
    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }*/

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
 /*   public boolean hasSubCategories (){
        if (subCategories == null || subCategories.size()==0)
            return false ;
        return true;
    }*/

   /* public Company getMparentCatgory() {
        return mparentCatgory;
    }

    public void setMparentCatgory(Company mparentCatgory) {
        this.mparentCatgory = mparentCatgory;
    }

    public String getParentsPath() {
        String path = "";
        if (mparentCatgory != null) {
            path += mparentCatgory.getParentsPath();
            if (mparentCatgory.getMparentCatgory() != null)
                path += " - ";
            path += getName();

        }
        return path;
    }
*/
   /* public void linkChilds () {
        if(subCategories !=null)
            for(int i=0;i<subCategories.size();i++){
                subCategories.get(i).setMparentCatgory(this);
            }

        if(fields !=null)
            for(int i = 0; i< fields.size(); i++){
                fields.get(i).setMparentCatgory(this);
            }
    }*/
}
