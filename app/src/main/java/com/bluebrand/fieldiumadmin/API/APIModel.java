package com.bluebrand.fieldiumadmin.API;

/**
 * Created by farah on 4/22/16.
 */
public enum APIModel {
    users,
    bookings,
    fields,
    amenities,
    games,
    areas,
    companies,
    reports,
    site
    ;

    @Override
    public String toString () {
        switch (this){
            case users :
                return "users" ;
            case bookings:
                return "bookings";
            case fields:
                return "fields";
            case amenities:
                return "amenities";
            case games:
                return "games";
            case companies:
                return "companies";
            case areas:
                return "areas";
            case reports:
                return "reports";
            case site:
                return  "site";
            default:
                return  "" ;
        }
    }
}

