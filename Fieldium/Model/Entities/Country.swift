//
//  Country.swift
//  Fieldium
//
//  Created by Yahya Tabba on 7/30/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON


class Country : BaseEntity{

    var country_id : JSON!
    var en_name : JSON!
    var ar_name : JSON!
    var code : JSON!

    var currency : JSON!
    var en_currency_symbol : JSON!
    var ar_currency_symbol : JSON!
    var dialing_code : JSON!
    
    var timezone : JSON!
    var name : JSON!
    
    var image_url : String!
    
    // Mappable
    override func mapping(map: Map) {
        country_id <- map["country_id"]
        en_name <- map["en_name"]
        ar_name <- map["ar_name"]
        code <- map["code"]
        
        currency <- map["currency"]
        en_currency_symbol <- map["en_currency_symbol"]
        ar_currency_symbol <- map["ar_currency_symbol"]
        dialing_code <- map["dialing_code"]

        timezone <- map["timezone"]
        name <- map["name"]
        
        image_url <- map["image_url"]
    }
    
    
}
