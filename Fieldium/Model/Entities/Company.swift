//
//  Company.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/19/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import MapKit


class Company: BaseEntity {
 
    var company_id : Int!
    var name : String!
    var en_name : String!
    var ar_name : String!
    var phone : String!
    var address : String!
    var en_address : String!
    var ar_address : String!
    var longitude : Double!
    var latitude : Double!
    var area_id : String!
    var area_name : String!
    var logo : String!
    var image : String!
    var description : String!
    var en_description : String!
    var ar_description : String!
    var deleted : String!
    var fields_number : String!
    
    var image_url : String!
    var logo_url : String!
    var starts_from : String!
        
    // Mappable
    override func mapping(map: Map) {
        company_id <- (map["company_id"], transformInt)
        name <- map["name"]
        en_name <- map["en_name"]
        ar_name <- map["ar_name"]
        phone <- map["phone"]
        address <- map["address"]
        en_address <- map["en_address"]
        ar_address <- map["ar_address"]
        longitude <- (map["longitude"],transformDouble)
        latitude <- (map["latitude"],transformDouble)
        area_id <- map["area_id"]
        area_name <- map["area_name"]
        
        logo <- map["logo"]
        description <- map["description"]
        en_description <- map["en_description"]
        ar_description <- map["ar_description"]
        deleted <- map["deleted"]
        
        fields_number <- map["fields_number"]
        
        image_url <- map["image_url"]
        logo_url <- map["logo_url"]
        starts_from <- map["starts_from"]
        
    }
    
}


class CompanyAnnotation: NSObject, MKAnnotation {
    
    var coordinate: CLLocationCoordinate2D
    var title: String!
    var imageURL: String!
   var id: Int!
    
    
    init(coordinate: CLLocationCoordinate2D) {
        self.coordinate = coordinate
    }
}
