//
//  Area.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/20/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper

class Area: BaseEntity {
    
    var area_id : Int!
    var name : String!
    var en_name : String!
    var ar_name : String!
    var active : Int!
    
    
    // Mappable
    override func mapping(map: Map) {
        area_id <- (map["area_id"], transformInt)
        name <- map["name"]
        en_name <- map["en_name"]
        ar_name <- map["ar_name"]
        active <- (map["active"],transformInt)
    }

}

class industry : BaseEntity {
    
    var industry_id : Int!
    var industry_base_id : Int!
    var name : String!
    var Count1 : Int!
    var Count2 : Int!
    
    
    // Mappable
    override func mapping(map: Map) {
        industry_id <- (map[""],transformInt)

        industry_base_id <- (map["industry_base_id"], transformInt)
        name <- (map["industry_id"])
        Count1 <- (map["Count1"], transformInt)
        Count2 <- (map["Count2"], transformInt)
        
    }
}
