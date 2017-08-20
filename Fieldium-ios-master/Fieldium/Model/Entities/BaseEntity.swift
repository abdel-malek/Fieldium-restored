//
//  BaseEntity.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/19/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import ObjectMapper
import SwiftyJSON


class BaseEntity : Mappable{
    
    public func mapping(map: Map) {
        
    }
    
    public required init?(map: Map) {
        
    }
    
    init() {
        
    }
    
    
    let transformInt = TransformOf<Int, String>(fromJSON: { (value: String?) -> Int? in
        // transform value from String? to Int?
        return Int(value!)
    }, toJSON: { (value: Int?) -> String? in
        // transform value from Int? to String?
        if let value = value {
            return String(value)
        }
        return nil
    })
    
    let transformDouble = TransformOf<Double, String>(fromJSON: { (value: String?) -> Double? in
        // transform value from String? to Int?
        return Double(value!)
    }, toJSON: { (value: Double?) -> String? in
        // transform value from Int? to String?
        if let value = value {
            return String(value)
        }
        return nil
    })
    
    
    let transformIMGS = TransformOf<[IMG], [Any]>(fromJSON: { (value: [Any]?) -> [IMG]? in
        var imgs = [IMG]()
        if let v = value{
            let js = JSON(v)
            for im in js.arrayValue{
                let m = IMG(JSON: im.dictionaryObject!)
                imgs.append(m!)
            }
            return imgs
        }else{
            return nil
        }
        
    }, toJSON: { (value: [IMG]?) -> [Any]? in
        
        if let value = value {
            return []
        }
        return nil
    })
    
    let transformAmenites = TransformOf<[Amenity], [Any]>(fromJSON: { (value: [Any]?) -> [Amenity]? in
        var res = [Amenity]()
        if let v = value{
            let js = JSON(v)
            for im in js.arrayValue{
                let m = Amenity(JSON: im.dictionaryObject!)
                res.append(m!)
            }
            return res
        }else{
            return nil
        }
        
    }, toJSON: { (value: [Amenity]?) -> [Any]? in
        
        if let value = value {
            return []
        }
        return nil
    })

    let transformGames = TransformOf<[Game], [Any]>(fromJSON: { (value: [Any]?) -> [Game]? in
        var res = [Game]()
        if let v = value{
            let js = JSON(v)
            for im in js.arrayValue{
                let m = Game(JSON: im.dictionaryObject!)
                res.append(m!)
            }
            return res
        }else{
            return nil
        }
        
    }, toJSON: { (value: [Game]?) -> [Any]? in
        
        if let value = value {
            return []
        }
        return nil
    })

    
}


