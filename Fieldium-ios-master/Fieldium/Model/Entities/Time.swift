//
//  Time.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/3/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import DropDown


class Time {
    
    static var selected : Time!
    
    
    var date : String!
    var duration : String!
    var start : String!
    
    
    static func DateToString2(date: Date) -> String{
        let dateFormatter = DateFormatter()
        dateFormatter.locale =  NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
        dateFormatter.dateFormat = "hh:mm a"
        
        let date = dateFormatter.string(from: date)
        return date
    }
    
    static func StringToDate(dateString : String) -> Date?{
        let dateFormatter = DateFormatter()
        dateFormatter.locale =  NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
        dateFormatter.dateFormat = "yyyy-MM-dd"
        let date = dateFormatter.date(from: dateString)
        
//        print("TT22 : \(date)")
        
        return date//?.add(minutes: 120)
    }
    
    
    static func StringToDate3(time: String) -> Date{
        let dateFormatter = DateFormatter()
        dateFormatter.locale =  NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
        dateFormatter.dateFormat = "hh:mm a"
        
        var date = dateFormatter.date(from: time)!
        date = date.add(minutes: 120)
        
        return date
    }
    
    
    
    static func DateToString(date: Date) -> String{
        let dateFormatter = DateFormatter()
        dateFormatter.locale =  NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
        dateFormatter.dateFormat = "yyyy-MM-dd"
        
        let date = dateFormatter.string(from: date)
        return date
    }
    
    static var dateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        formatter.locale =  NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
        return formatter
    }()
    
    
    
    static func convert12To24(time : String) -> String{
        //for convert
        let dateFormatter = DateFormatter()
        dateFormatter.locale =  NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
        dateFormatter.dateFormat = "hh:mm a"
        let date = dateFormatter.date(from: time)
        
        dateFormatter.dateFormat = "HH:mm:ss"
        
        if time == "24:00:00"{
            return "24:00:00"
        }
        let date24 = dateFormatter.string(from: date!)
        
        return date24
    }
    
    static func convert24To12(time : String) -> String{
        //for convert
        let dateFormatter = DateFormatter()
        dateFormatter.locale =  NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
        dateFormatter.dateFormat = "HH:mm:ss"
        let date = dateFormatter.date(from: time)
        
        dateFormatter.dateFormat = "h:mm a"
        
        if date != nil{
            let date24 = dateFormatter.string(from: date!)
            
            return date24
        }else{
            return time
        }
        
    }
    
    static func convert24To12_2(time : String) -> String{
        //for convert
        let dateFormatter = DateFormatter()
        dateFormatter.locale =  NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
        dateFormatter.dateFormat = "HH"
        let date = dateFormatter.date(from: time)
        
        dateFormatter.dateFormat = "h"
        
        if date != nil{
            let date24 = dateFormatter.string(from: date!)
            
            return date24
        }else{
            return time
        }
    }
    
    
    // TO DO 1.1
    // set time for activate wating minutes
    static func addTimer(_ minutes : Int) {
        UserDefaults.standard.set(Date().add(minutes: minutes), forKey: "timerActive")
    }
    
    static func getTimer() -> Int{
        if let m = UserDefaults.standard.value(forKey: "timerActive"){
            if let d = m as? Date{
                let s = Date().secondsEarlier(than: d)
                
                if s < 0 {
                    return 0
                }
                
                return s
            }
        }
        
        return 0
    }
    
    
}
