//
//  CustomResponse.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/19/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import SwiftyJSON

import ObjectMapper
import AlamofireObjectMapper
import Alamofire


class CustomResponse : BaseEntity {
    
    var status : Bool = false
    var data : JSON!
    var message : String = ""
    
    var description : String{
        return "CustomResponse: { status: \(status), data: \(data!) , message : \(message) }"
    }

    override func mapping(map: Map) {
        status <- map["status"]
        data <- map["data"]
        message <- map["message"]
    }
    
    
}
