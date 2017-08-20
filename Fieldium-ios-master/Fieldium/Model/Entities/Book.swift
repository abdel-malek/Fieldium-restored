//
//  Book.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/19/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON

class Book: BaseEntity {
    
    var booking_id : Int!
    var field_id : Int!
    var player_id : Int!
    var company_id : Int!
    var user_id : Int!
    var state_id : Int!
    var creation_date : String!
    var date : String!
    var start : String!
    var duration : Int!
    var total : Double!
    var notes : String!
    var manually : Int!
    var deleted : Int!
    var cancellation_reason : String!
    
    var hour_rate : Double!
    var field_name : String!
    var logo_url : String!
    var address: String!
    
    var imgBack : String!
    var company_name : String!
    
    var game_type_name : String!
    
    var voucher : JSON!
    var voucher_value : JSON!
    var voucher_type : JSON!
    var subtotal : JSON!
    
    var en_currency_symbol : String!
    var ar_currency_symbol : String!
    
    // Mappable
    override func mapping(map: Map) {
        booking_id <- (map["booking_id"],transformInt)
        field_id <- (map["field_id"],transformInt)
        company_id <- (map["company_id"],transformInt)
        player_id <- (map["player_id"],transformInt)
        user_id <- (map["user_id"])
        state_id <- (map["state_id"],transformInt)
        creation_date <- map["creation_date"]
        date <- map["date"]
        start <- map["start"]
        duration <- (map["duration"],transformInt)
        total <- (map["total"],transformDouble)
        notes <- map["notes"]
        manually <- (map["manually"],transformInt)
        deleted <- (map["deleted"],transformInt)
        cancellation_reason <- map["cancellation_reason"]
        
        field_name <- map["field_name"]
        hour_rate <- (map["hour_rate"],transformDouble)
        logo_url <- map["logo_url"]
        address <- map["address"]
        company_name <- map["company_name"]
        game_type_name <- map["game_type_name"]
        
        voucher <- map["voucher"]
        voucher_value <- map["voucher_value"]
        voucher_type <- map["voucher_type"]
        subtotal <- map["subtotal"]
        
        en_currency_symbol <- map["en_currency_symbol"]
        ar_currency_symbol <- map["ar_currency_symbol"]

        
    }
}
