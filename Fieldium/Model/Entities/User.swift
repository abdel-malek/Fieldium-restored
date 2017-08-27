//
//  User.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/19/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation

class User : NSObject, NSCoding{
    
    static let KEY_USER_ME : String = "me"
    
    var user_id : Int!
    var name : String!
    var username : String!
    var password : String!
    var phone : String!
    var email : String!
    var profile_picture : String!
    var company_id : Int!
    var role_id : Int!
    var active : Int!
    
    var server_id : String!
    
    var address : String!
    var player_id : String!
    var token : String!
    
    var prefered_games = [Int]()
    var prefered_gamesNames = [String]()
    var profile_picture_url : String!
    
    // COUNTRIES
    var country_id : String!
    var en_name : String!
    var ar_name : String!
    var code : String!
    var currency : String!
    var en_currency_symbol : String!
    var ar_currency_symbol : String!
    var dialing_code : String!
    var timezone : String!
    
    //MARK: Local data
    var statues_key: String? = USER_STATUES.NEW_USER.rawValue
    
    enum USER_STATUES : String {
        case NEW_USER = "new"
        case USER_SKIPED_LOGIN = "skiped"
        case USER_PENDING_VERIFICATION = "pending"
        case USER_LOGGED_IN = "verified"
        case USER_REGISTERED = "registered"
    }
    
    //MARK: chache current user
    static func getCurrentUser() -> User
    {
        if let archivedData = UserDefaults.standard.object(forKey: KEY_USER_ME) as? Data
        {
            let me: User = (NSKeyedUnarchiver.unarchiveObject(with: archivedData) as? User)!
            return me
        }
        return User()
    }
    
    static func saveMe(me : User)
    {
        let archivedObject = NSKeyedArchiver.archivedData(withRootObject: me)
        UserDefaults.standard.set(archivedObject, forKey: KEY_USER_ME)
        UserDefaults.standard.synchronize()
    }
    
    static func clearMe()
    {
        UserDefaults.standard.removeObject(forKey: KEY_USER_ME)
    }
    
    var printUser : String{
        var s = ""
        s += "user_id: \(user_id)" + "\n"
        s += "name: \(name)" + "\n"
        s += "username: \(username)" + "\n"
        s += "password: \(password)" + "\n"
        s += "phone: \(phone)" + "\n"
        s += "email: \(email)" + "\n"
        s += "profile_picture: \(profile_picture)" + "\n"
        s += "company_id: \(company_id)" + "\n"
        s += "role_id: \(role_id)" + "\n"
        s += "active: \(active)" + "\n"
        s += "server_id: \(server_id)" + "\n"

        
        return s
    }
    
    override init(){
        
    }
    
    required init(coder decoder: NSCoder) {
        self.user_id = decoder.decodeObject(forKey: "user_id") as? Int
        self.name = decoder.decodeObject(forKey: "name") as? String
        self.username = decoder.decodeObject(forKey: "username") as? String
        self.password = decoder.decodeObject(forKey: "password") as? String
        self.phone = decoder.decodeObject(forKey: "phone") as? String
        self.email = decoder.decodeObject(forKey: "email") as? String
        self.profile_picture = decoder.decodeObject(forKey: "profile_picture") as? String
        self.company_id = decoder.decodeObject(forKey: "company_id") as? Int
        self.role_id = decoder.decodeObject(forKey: "role_id") as? Int
        self.active = decoder.decodeObject(forKey: "active") as? Int
        self.server_id = decoder.decodeObject(forKey: "server_id") as? String
        self.address = decoder.decodeObject(forKey: "address") as? String
        self.profile_picture_url = decoder.decodeObject(forKey: "profile_picture_url") as? String
        self.statues_key = decoder.decodeObject(forKey: "statues_key") as? String
        
        if let prefered = decoder.decodeObject(forKey: "prefered_games") as? [Int] {
            self.prefered_games = prefered
        }
        if let prefered = decoder.decodeObject(forKey: "prefered_gamesNames") as? [String] {
            self.prefered_gamesNames = prefered
        }
        
        // COUNTRIES
        self.country_id = decoder.decodeObject(forKey: "country_id") as? String
        self.en_name = decoder.decodeObject(forKey: "en_name") as? String
        self.ar_name = decoder.decodeObject(forKey: "ar_name") as? String
        self.code = decoder.decodeObject(forKey: "code") as? String
        self.currency = decoder.decodeObject(forKey: "currency") as? String
        self.en_currency_symbol = decoder.decodeObject(forKey: "en_currency_symbol") as? String
        self.ar_currency_symbol = decoder.decodeObject(forKey: "ar_currency_symbol") as? String
        self.dialing_code = decoder.decodeObject(forKey: "dialing_code") as? String
        self.timezone = decoder.decodeObject(forKey: "timezone") as? String

    }
    
    
    func encode(with coder: NSCoder) {
        coder.encode(user_id, forKey: "user_id")
        coder.encode(name, forKey: "name")
        coder.encode(username, forKey: "username")
        coder.encode(password, forKey: "password")
        coder.encode(phone, forKey: "phone")
        coder.encode(email, forKey: "email")
        coder.encode(profile_picture, forKey: "profile_picture")
        coder.encode(company_id, forKey: "company_id")
        coder.encode(role_id, forKey: "role_id")
        coder.encode(active, forKey: "active")
        coder.encode(server_id, forKey: "server_id")
        coder.encode(address, forKey: "address")
        coder.encode(prefered_games, forKey: "prefered_games")
        coder.encode(prefered_gamesNames, forKey: "prefered_gamesNames")
        coder.encode(profile_picture_url, forKey: "profile_picture_url")
        coder.encode(statues_key, forKey: "statues_key")
        
        coder.encode(country_id, forKey: "country_id")
        coder.encode(en_name, forKey: "en_name")
        coder.encode(ar_name, forKey: "ar_name")
        coder.encode(code, forKey: "code")
        coder.encode(currency, forKey: "currency")
        coder.encode(en_currency_symbol, forKey: "en_currency_symbol")
        coder.encode(ar_currency_symbol, forKey: "ar_currency_symbol")
        coder.encode(dialing_code, forKey: "dialing_code")
        coder.encode(timezone, forKey: "timezone")
    }
    
    
}
