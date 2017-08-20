//
//  Field.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/19/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Field : BaseEntity {
    
    var field_id : Int!
    var name : String!
    var en_name : String!
    var ar_name : String!
    var phone : String!
    var company_id : String!
    var hour_rate : JSON!
    var open_time : String!
    var close_time : String!
    var en_description : String!
    var ar_description : String!
    var description : String!
    var area_x : JSON!
    var area_y : JSON!
    var max_capacity : JSON!
    var featured_place : String!
    var deleted : Int!
    
    var amenities = [Amenity]()
    var games = [Game]()
    var imgs = [IMG]()
    
    var logo_url : String!
    var latitude : JSON!
    var longitude : JSON!
    var company_name : String!
    var address : String!
    
    
    // Mappable
    override func mapping(map: Map) {
        field_id <- (map["field_id"], transformInt)
        name <- map["name"]
        en_name <- map["en_name"]
        ar_name <- map["ar_name"]
        phone <- map["phone"]
        company_id <- map["company_id"]
        hour_rate <- (map["hour_rate"])
        open_time <- map["open_time"]
        close_time <- map["close_time"]
        en_description <- map["en_description"]
        ar_description <- map["ar_description"]
        description <- map["description"]
        area_x <- (map["area_x"])
        area_y <- (map["area_y"])
        max_capacity <- (map["max_capacity"])
        featured_place <- map["featured_place"]
        deleted <- map["deleted"]
        
        logo_url <- map["logo_url"]
        latitude <- (map["latitude"])
        longitude <- (map["longitude"])
        company_name <- map["company_name"]
        address <- map["address"]
        
        imgs <- map["images"]//,transformIMGS)
        amenities <- map["amenities"]//,transformAmenites)
        games <- map["games"]//,transformGames)
        
        
    }
    
}

