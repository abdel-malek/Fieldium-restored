package com.bluebrand.fieldium.core.API;

/**
 * Created by Farah Etmeh on 4/22/16.
 */
public enum APIModel {
     categories , bookings,vouchers, notifications, products,devices,site,players,companies,fields,areas,countries,games,searches;

    @Override
    public String toString () {
        switch (this){
            case vouchers:
                return "vouchers";
            case categories:
                return "categories";
            case companies:
                return "companies";
            case bookings:
                return "bookings";
            case searches:
                return "searches";
            case notifications:
                return "notifications";
            case products:
                return "products";
            case devices:
                return "devices";
            case site:
                return "site";
            case players:
                return "players";
            case games:
                return "games";
            case fields:
                return "fields";
            case areas:
                return "areas";
            case countries:
                return "countries";
            default:
                return  "" ;
        }
    }
}

