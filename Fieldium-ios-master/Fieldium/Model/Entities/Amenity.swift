//
//  Amenity.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/20/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper

class Amenity: BaseEntity {
    
    var amenity_id : Int!
    var name : String!
    var en_name : String!
    var ar_name : String!
    var image : String!
    
    var image_url : String!

    
    // Mappable
    override func mapping(map: Map) {
        amenity_id <- (map["amenity_id"], transformInt)
        name <- map["name"]
        en_name <- map["en_name"]
        ar_name <- map["ar_name"]
        image <- map["image"]
        image_url <- map["image_url"]

    }

    
}
