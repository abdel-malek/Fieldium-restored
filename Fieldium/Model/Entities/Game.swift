//
//  Game.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/20/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper

class Game: BaseEntity {
    
    var game_type_id : Int!
    var name : String!
    var image : String!
    
    var image_url : String!
    
    var increament_factor : Int!
    var minimum_duration : Int!
    
    // Mappable
    override func mapping(map: Map) {
        game_type_id <- (map["game_type_id"],transformInt)
        name <- map["name"]
        image <- map["image"]
        image_url <- map["image_url"]
        
        increament_factor <- (map["increament_factor"],transformInt)
        minimum_duration <- (map["minimum_duration"],transformInt)
        
    }

}
