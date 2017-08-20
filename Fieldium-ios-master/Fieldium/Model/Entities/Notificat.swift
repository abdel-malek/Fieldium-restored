//
//  Notificat.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/20/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper

class Notificat : BaseEntity {
    
    var notification_time : String!
    var player_id : Int!
    var date : String!
    var content : String!
    
    var hour_rate : Double!
    var field_name : String!
    var logo_url : String!
    var address: String!
    
    var start : String!
    var duration : Double!
    var total : Double!
    
    var company_name : String!

    
    // Mappable
    override func mapping(map: Map) {
        notification_time <- map["notification_time"]
        player_id <- (map["player_id"],transformInt)
        date <- map["date"]
        content <- map["content"]
        
        field_name <- map["field_name"]
        hour_rate <- (map["hour_rate"],transformDouble)
        logo_url <- map["logo_url"]
        address <- map["address"]

        start <- map["start"]
        duration <- (map["duration"],transformDouble)
        total <- map["total"]
        
        company_name <- map["company_name"]
    }
    
    
    
}
