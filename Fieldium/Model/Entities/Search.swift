//
//  Search.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/20/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper

class Search: BaseEntity {
    
    var search_record_id : Int!
    var player_id : Int!
    var token : String!
    var area_id : String!
    var game_type_id : Int!
    var start : Int!
    var date : String!
    var duration : String!
    var search_date : Int!
    var text : Int!
    
    
    // Mappable
    override func mapping(map: Map) {
        search_record_id <- map["search_record_id"]
        player_id <- map["player_id"]
        token <- map["token"]
        area_id <- map["area_id"]
        game_type_id <- map["game_type_id"]
        start <- map["start"]
        date <- map["date"]
        duration <- map["duration"]
        search_date <- map["search_date"]
        text <- map["text"]
    }
    
    
}
