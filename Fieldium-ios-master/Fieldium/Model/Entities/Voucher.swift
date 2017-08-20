//
//  Voucher.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/11/17.
//  Copyright © 2017 Yahya Tabba. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Voucher : BaseEntity {
    
    var all_games : JSON!
    var voucher_id : JSON!
    var type : JSON!
    var value : JSON!
    var valid : JSON!
    
    var voucher : String!
    var creation_date : String!
    var from_hour : String!
    var to_hour : String!
    var start_date : String!
    var expiry_date : String!

    var description_en : String!
    var description_ar : String!

    var companies = [Company]()
    var games = [Game]()
    var message : String!

    
    // Mappable
    override func mapping(map: Map) {
        all_games <- map["all_games"]
        voucher_id <- map["voucher_id"]
        type <- map["type"]
        value <- map["value"]
        valid <- map["valid"]
        
        voucher <- map["voucher"]
        creation_date <- map["creation_date"]
        from_hour <- map["from_hour"]
        to_hour <- map["to_hour"]
        start_date <- map["start_date"]
        expiry_date <- map["expiry_date"]
        
        description_en <- map["description_en"]
        description_ar <- map["description_ar"]
        
        companies <- map["companies"]
        games <- map["games"]
        
        message <- map["message"]
    }
    
}
