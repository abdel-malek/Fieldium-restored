//
//  Offer.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/19/17.
//  Copyright © 2017 Yahya Tabba. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON


class Offer : BaseEntity{
    
    var all_games : JSON!
    
    var creation_date : String!
    var description : String!
    var description_ar : String!
    var description_en : String!
    var expiry_date : String!
    var offer_id : JSON!
    
    var public_field : JSON!
    var set_of_minutes : JSON!
    
    var start_date : String!
    var title_ar : String!
    var title_en : String!
    
    var valid : JSON!
    var valid_days : JSON!
    
    var voucher_from_hour : JSON!
    var voucher_start_after : JSON!
    var voucher_to_hour : JSON!
    var voucher_type : JSON!
    var voucher_value : JSON!
    var booked_hours : JSON!

    var companies = [Company]()
    var games = [Game]()
    
    
    // Mappable
    override func mapping(map: Map) {

        all_games <- map["all_games"]
        creation_date <- map["creation_date"]
        description <- map["description"]
        description_ar <- map["description_ar"]
        description_en <- map["description_en"]
        expiry_date <- map["expiry_date"]
        offer_id <- map["offer_id"]
        public_field <- map["public_field"]
        set_of_minutes <- map["set_of_minutes"]
        start_date <- map["start_date"]
        title_ar <- map["title_ar"]
        title_en <- map["title_en"]
        valid <- map["valid"]
        valid_days <- map["valid_days"]
        voucher_from_hour <- map["voucher_from_hour"]
        voucher_start_after <- map["voucher_start_after"]
        voucher_to_hour <- map["voucher_to_hour"]
        voucher_type <- map["voucher_type"]
        voucher_value <- map["voucher_value"]
        booked_hours <- map["booked_hours"]
        companies <- map["companies"]
        games <- map["games"]


    }

}
