//
//  IMG.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/22/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper

class IMG : BaseEntity {
    
var image_id : String!
var field_id : String!
var name : String!
var image_url : String!

    
// Mappable
override func mapping(map: Map) {
    image_id <- map["image_id"]
    field_id <- map["field_id"]
    name <- map["name"]
    image_url <- map["image_url"]

}

}
