//
//  BaseManager.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/19/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation

class BaseManager {
    
    
}


class BaseManager2 {
    static var shared = BaseManager2()
    
    
    func times(open : String , close : String) -> [String]{
        
        let o = open.components(separatedBy: ":")
        let c = close.components(separatedBy: ":")
        
        var arr = [Int]()
        
        if c[0].toInt < o[0].toInt {
            
            for i in 0..<24-o[0].toInt {
                arr.append((24 - i))
            }
            
            for i in 0...c[0].toInt{
                arr.append(i)
            }
            
            for i in 0...23{
                if !arr.contains(i) {
                    arr.append(i)
                }
            }
            
            
            arr.sort()
            
            
            var t = [String]()
            for i in arr{
                
                if i < 12{
                    t.append(i.toString + " AM")
                }else{
                    t.append(i.toString + " PM")
                }
            }
            
            
            for i in 0..<t.count{
                let h = t[i].components(separatedBy: " ")

                if  (h[1] == "PM" && h[0] == "24") ||  (h[1] == "AM" && h[0] == "0"){
                    t[i] = "12 AM"
                }
                
                if  h[1] == "PM" && (h[0] != "24" && h[0] != "12"){
                    t[i] = "\(Int(h[0])! - 12) PM"
                }
            }
            
            
            return t
        }else{
            return times2(open: open, close: close)
        }
    }
    
    func times2(open : String , close : String) -> [String]{
        var numbers = [String]()
        numbers.append("12")
        
        for i in 1...11 {
            let string = String(i)
            numbers.append(string)
        }
        
        var am = numbers.map { $0 + " AM" }
        var pm = numbers.map { $0 + " PM" }
        pm.append("12 AM") // TO DO
        
        //        am.append("Noon")
        //        pm.removeFirst()
        
        let o = open.components(separatedBy: ":")
        let c = close.components(separatedBy: ":")
        
        print("open : \(open) - close : \(close)")
        
        print(o[0])
        
        for i in 0..<Int(o[0])!{
            print(i)
            if am.count > 0{ // TO DO 1.1
                am.removeFirst()
            }else if pm.count > 0 {
                pm.removeFirst()
            }
        }
        
        if !(o[1] == c[1] && Int(o[0])! > Int(c[0])!){
            for _ in 0..<(24 - Int(c[0])!){
                print("REMOVE PM \(24 - Int(c[0])!)")
                pm.removeLast()
            }
        }
        
        
        
        return am + pm
    }
    
    
    
    
}
