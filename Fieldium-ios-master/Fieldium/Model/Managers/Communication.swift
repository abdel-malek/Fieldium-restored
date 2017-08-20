//
//  Communication.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/19/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import Alamofire
import AlamofireObjectMapper

import SwiftyJSON
import ObjectMapper

let _ConnectionErrorNotification = "ConnectionErrorNotification"
let _RequestErrorNotificationReceived = "requestErrorNotification"
let _AuthorizationErrorReceived = "notAuthorizedNotification"
let notic = NotificationCenter.default

typealias ServiceResponse = (JSON?, NSError?) -> Void


class Communication: BaseManager {
    
    static let shared = Communication()
    
    let encodingQuery = URLEncoding(destination: .queryString)
    let encodingBody = URLEncoding(destination: .httpBody)
    
    //MARK: API urls
    
//    let baseURL = "http://field-dash.Tradinos UG.com/index.php"
    let baseURL = "http://fieldium2.Tradinos.com/index.php"
    let registerURL = "/players/register/format/json"
    let verifyURL = "/players/verify/format/json"
    let vcodeURL = "/players/request_verification_code/format/json"
    let updateURL = "/players/update/format/json"
    let deactiveURL = "/players/deactive/format/json"
    let refTokenURL = "/players/refresh_token/format/json"
    let show_companyURL = "/companies/show/format/json"
    let get_all_companyURL = "/companies/get_all/format/json"
    let get_nearby_companiesURL = "/companies/get_nearby_companies/format/json"
    let show_field_URL = "/fields/show/format/json"
    let get_fields_by_companyURL = "/fields/get_by_company/format/json"
    let get_by_company_with_timingURL = "/fields/get_by_company_with_timing/format/json"
    let check_availabilityURL = "/fields/check_availability/format/json"
    let get_featured_placesURL = "/fields/get_featured_places/format/json"
    let create_bookURL = "/bookings/create/format/json"
    let my_bookingsURL = "/bookings/my_bookings/format/json"
    let show_bookURL = "/bookings/show/format/json"
    let get_notificationsURL = "/notifications/get_notifications/format/json"
    let get_searchesURL = "/searches/get_searches/format/json"
    let searchURL = "/searches/search/format/json"
    let get_all_gamesURL = "/games/get_all/format/json"
    let get_all_areasURL = "/areas/get_all/format/json"
    let get_all_amenitiesURL = "/amenities/get_all/format/json"
    let get_upcoming_bookingURL = "/bookings/upcoming_booking/format/json"
    let upcoming_and_last_bookingsURL = "/bookings/upcoming_and_last_bookings/format/json"
    let contactUsURL = "/players/contact_us/format/json"
    let aboutURL = "/site/about/format/json"
    let upload_image = "/players/upload_image/format/json"
    
    // COUNRTIES
    let get_all_countriesURL = "/countries/get_all/format/json"
    let get_countryURL = "/countries/get/format/json"
    
    // VOUCHERS
    let vouchers_check_validityURL = "/vouchers/check_validity/format/json"
    let vouchers_showURL = "/vouchers/show/format/json"
    let vouchers_my_vouchersURL = "/vouchers/my_vouchers/format/json"
    
    let site_homeURL = "/site/home/format/json"

    
    
    func register(name: String, phone : String,country : Country){
        let params = ["name" : name ,"phone" : phone, "country" : country.country_id.stringValue,"os" : "ios"]
        let url = URL(string: "\(baseURL)\(registerURL)")!
        
        Alamofire.request(url, method: .post, parameters: params, encoding: encodingBody, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    let user : User = User.getCurrentUser()
                    user.phone = value.data!["phone"].stringValue
                    user.server_id = value.data!["server_id"].stringValue
                    
                    user.email = value.data!["email"].stringValue
                    user.name = value.data!["name"].stringValue
                    user.player_id = value.data!["player_id"].stringValue
                    user.profile_picture = value.data!["profile_picture"].stringValue
                    user.address = value.data!["address"].stringValue
                    user.profile_picture_url = value.data!["profile_picture_url"].stringValue
                    user.statues_key = User.USER_STATUES.USER_PENDING_VERIFICATION.rawValue
                    user.profile_picture = value.data!["profile_picture"].stringValue
                    
                    user.country_id = country.country_id.stringValue
                    user.en_name = country.en_name.stringValue
                    user.ar_name = country.ar_name.stringValue
                    user.code = country.code.stringValue
                    user.currency = country.currency.stringValue
                    user.en_currency_symbol = country.en_currency_symbol.stringValue
                    user.ar_currency_symbol = country.ar_currency_symbol.stringValue
                    user.dialing_code = country.dialing_code.stringValue
                    user.timezone = country.timezone.stringValue
                    
                    User.saveMe(me: user)
                    
                    // TO DO 1.1
                    Time.addTimer(0)
                    
                    notic.post(name: _goToVerify.not, object: nil)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    

    
    
    func verify(code : String) {
        
        let me = User.getCurrentUser()
        
        print(me.server_id)
        
        let params = ["phone" : me.phone!, "verification_code" : code, "os" : "ios"]
        let url = URL(string: "\(baseURL)\(verifyURL)")!
        
        Alamofire.request(url, method: .post, parameters: params, encoding: encodingBody, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue)
            print(ss!)
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    print(value.data)
                    let user = User.getCurrentUser()
                    user.phone = value.data!["phone"].stringValue
                    user.email = value.data!["email"].stringValue
                    user.name = value.data!["name"].stringValue
                    user.address = value.data!["address"].stringValue
                    user.profile_picture_url = value.data!["profile_picture_url"].stringValue
                    user.profile_picture = value.data!["profile_picture"].stringValue
                    
                    var res = [Int]()
                    var res2 = [String]()
                    
                    for im in value.data.dictionaryValue["prefered_games"]!.arrayValue{
                        let m = Game(JSON: im.dictionaryObject!)
                        
                        res.append(m!.game_type_id!)
                        res2.append(m!.name!)
                    }
                    
                    user.prefered_games = res
                    user.prefered_gamesNames = res2
                    user.statues_key = User.USER_STATUES.USER_REGISTERED.rawValue
                    
                    User.saveMe(me: user)
                    
                    notic.post(name: _goToHome.not, object: nil)
                    
                    self.refresh_token(callback: { (res, err) in
                        print("REFRESH : \(String(describing: res?.rawString()))")
                    })
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    func request_verification_code(callback : @escaping (String) -> Void){
        
        let me = User.getCurrentUser()
        
        let params = ["phone" : me.phone!, "server_id" : me.server_id!]
        let url = URL(string: "\(baseURL)\(vcodeURL)")!
        
        Alamofire.request(url, method: .post, parameters: params, encoding: encodingBody, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            if let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue){
                print(ss)
            }
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    
                    Time.addTimer(15)
                    callback(value.message)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    func update_player(email : String, name: String, profile_picture : String, address : String, prefered_games : [Int], callback : @escaping ServiceResponse){
        
        
        var params : [String : Any] = ["email" : email, "name" : name, "address" : address, "prefered_games" : prefered_games]
        
        if  profile_picture != ""{
            params["profile_picture"] = profile_picture
        }
        
        
        
        let url = URL(string: "\(baseURL)\(updateURL)")!
        let auth = getHeaders()
        
        
        Alamofire.request(url, method: .post, parameters: params, encoding: encodingBody, headers: auth).responseObject { (response : DataResponse<CustomResponse>) in
            
            if let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue){
                print(ss)
            }
            
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    let user = User.getCurrentUser()
                    user.phone = value.data!["phone"].stringValue
                    
                    user.email = value.data!["email"].stringValue
                    user.address = value.data!["address"].stringValue
                    user.name = value.data!["name"].stringValue
                    user.player_id = value.data!["player_id"].stringValue
                    user.profile_picture = value.data!["profile_picture"].stringValue
                    user.address = value.data!["address"].stringValue
                    user.profile_picture_url = value.data!["profile_picture_url"].stringValue
                    
                    var res = [Int]()
                    var res2 = [String]()
                    
                    for im in value.data.dictionaryValue["prefered_games"]!.arrayValue{
                        let m = Game(JSON: im.dictionaryObject!)
                        
                        res.append(m!.game_type_id!)
                        res2.append(m!.name!)
                    }
                    
                    user.prefered_games = res
                    user.prefered_gamesNames = res2
                    
                    User.saveMe(me: user)
                    
                    
                    //TO DO
                    callback(value.data, nil)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    func deactive_player(callback : @escaping ServiceResponse){
        
        let url = URL(string: "\(baseURL)\(deactiveURL)")!
        let auth = getHeaders()
        
        Alamofire.request(url, method: .post, parameters: nil, encoding: encodingBody, headers: auth).responseObject { (response : DataResponse<CustomResponse>) in
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    //TO DO
                    callback(value.data, nil)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    func refresh_token(callback : @escaping ServiceResponse){
        
        let params = ["token" : Provider.token]
        print("\(baseURL)\(refTokenURL)")
        let url = URL(string: "\(baseURL)\(refTokenURL)")!
        let auth = getHeaders()
        
        Alamofire.request(url, method: .post, parameters: params, encoding: encodingBody, headers: auth).responseObject { (response : DataResponse<CustomResponse>) in
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    //TO DO
                    callback(value.data, nil)
                    
                }else{
                    //                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                //                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    func get_all_companies(){
        let url = URL(string: "\(baseURL)\(get_all_companyURL)")!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            var res = [Company]()
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    for i in value.data.arrayValue{
                        let c = Company(JSON: i.dictionaryObject!)
                        res.append(c!)
                    }
                    
                    Provider.shared.all_companies = res
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print("ERROR")
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    
    func show_company(company_id : Int, callback : @escaping ServiceResponse) {
        
        let params = ["company_id" : company_id]
        
        let url = URL(string: "\(baseURL)\(show_companyURL)")!
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    callback(value.data, nil)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print("ERROR")
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    func get_nearby_companies() {
        
        var long = ""
        var lat = ""
        
        if let region = Provider.region {
            long = String(region.center.longitude)
            lat = String(region.center.latitude)
        }
        
        var params : [String : Any] = ["longitude" : long , "latitude" : lat]
        
        print(params)
        
        if let location = Provider.shared.currentLocation{
            params = ["longitude" : location.coordinate.longitude , "latitude" : location.coordinate.longitude]
        }
        
        let url = URL(string: "\(baseURL)\(get_nearby_companiesURL)")!
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            var res = [Company]()
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    for i in value.data.arrayValue{
                        let c = Company(JSON: i.dictionaryObject!)
                        res.append(c!)
                    }
                    
                    Provider.shared.nearby_companies = res
                    notic.post(name: _update_nearby_companies.not, object: value.message)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print("ERROR")
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    func show_field(field_id : Int, callback : @escaping ServiceResponse){
        
        let params = ["field_id" : field_id]
        
        let url = URL(string: "\(baseURL)\(show_field_URL)")!
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    callback(value.data, nil)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print("ERROR")
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    
    func get_fields_by_company(company_id : Int){
        
        var long = ""
        var lat = ""
        
        if let region = Provider.region {
            long = String(region.center.longitude)
            lat = String(region.center.latitude)
        }
        
        let params : [String : Any] = ["company_id" : company_id, "longitude" : long , "latitude" : lat]
        
        let url = URL(string: "\(baseURL)\(get_fields_by_companyURL)")!
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            let tt = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue)
            print(tt!)
            
            var res = [Field]()
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    for i in value.data.arrayValue{
                        
                        let c = Field(JSON: i.dictionaryObject!)
                        
                        res.append(c!)
                    }
                    
                    Provider.shared.fields_by_company = res
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print("ERROR")
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    func get_by_company_with_timing(company_id: Int){
        
        var long = ""
        var lat = ""
        
        if let region = Provider.region {
            long = String(region.center.longitude)
            lat = String(region.center.latitude)
        }
        
        
        var timing : Int = 0
        var date = ""
        var start = ""
        var duration = ""
        
        print("TT : \(Time.selected)")
        
        if let time = Time.selected{
            
            if time.duration == nil{
                
                timing = 2
                date = time.date
                
            }else if time.duration != nil{
                
                timing = 1
                date = time.date
                duration = time.duration
                start = time.start
            }
            
        }else{
            timing = 0
        }
        
        let params : [String : Any] = ["company_id" : company_id, "start" : start, "duration" : duration, "date" : date , "timing" : timing,"longitude" : long , "latitude" : lat, "game_type" : Provider.game_type]
        
        print(params)
        
        let url = URL(string: "\(baseURL)\(get_by_company_with_timingURL)")!
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            
            let tt = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue)
            print(tt!)
            
            var res = [Field]()
            
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    for i in value.data.arrayValue{
                        
                        let c = Field(JSON: i.dictionaryObject!)
                        
                        res.append(c!)
                    }
                    print("CCC: \(res.count)")
                    Provider.shared.fields_by_company = res
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print("ERROR")
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    func check_availability_field(field_id : Int, date : String,callback : @escaping ([(String, String)],[(String, String)]) -> Void ){
        
        let params : [String : Any] = ["field_id" : field_id , "date" : date, "game_type" : SearchCell.selectedSoccer!.game_type_id!]
        
        print(params)
        
        let url = URL(string: "\(baseURL)\(check_availabilityURL)")!
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            let tt = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue)
            print("KJH")
            print(tt!)
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    //TO DO
                    print(value.data)
                    
                    var res = [(String, String)]()
                    var res2 = [(String, String)]()
                    
                    
                    for i in value.data["available"].arrayValue{
                        let start = i["start"].stringValue
                        let end = i["end"].stringValue
                        
                        res.append((start, end))
                    }
                    
                    for i in value.data["busy"].arrayValue{
                        let start = i["start"].stringValue
                        let end = i["end"].stringValue
                        
                        res2.append((start, end))
                    }
                    
                    
                    callback(res2,res)
                    
                    
                }else{
                    notic.post(name: _RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print("ERROR")
                print(error.localizedDescription)
                
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    

    
    func creak_book(field_id : Int, start : String, duration : String, date : String, notes : String,varchar : String, callback : @escaping ServiceResponse){
        
        let params : [String : Any] = ["field_id" : field_id , "start" : start , "duration" : duration , "date" : date , "notes" : notes, "game_type" : SearchCell.selectedSoccer!.game_type_id!,"voucher" : varchar]
        
        print(params)
        
        let url = URL(string: "\(baseURL)\(create_bookURL)")!
        
        let auth = getHeaders()
        
        Alamofire.request(url, method: .post, parameters: params, encoding: encodingBody, headers: auth).responseObject { (response : DataResponse<CustomResponse>) in
            
            //            print(response.result.value!)
            
            let rr = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue)
            print("\(rr!)")
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    callback(value.data, nil)
                    
                }else{
                    notic.post(name: _RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    func get_my_booking(){
        
        let url = URL(string: "\(baseURL)\(my_bookingsURL)")!
        
        let auth = getHeaders()
        
        Alamofire.request(url, method: .get, parameters: nil, encoding: encodingBody, headers: auth).responseObject { (response : DataResponse<CustomResponse>) in
            
            var res = [Book]()
            
            let rr = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue)
            print("\(rr!)")
            
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    for i in value.data.arrayValue{
                        let c = Book(JSON: i.dictionaryObject!)
                        res.append(c!)
                    }
                    
                    Provider.shared.my_booking = res
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    func get_upcoming_and_last_bookings(callback : @escaping ServiceResponse){
        
        let url = URL(string: "\(baseURL)\(upcoming_and_last_bookingsURL)")!
        
        let auth = getHeaders()
        
        Alamofire.request(url, method: .get, parameters: nil, encoding: encodingBody, headers: auth).responseObject { (response : DataResponse<CustomResponse>) in
            
            var games = [Game]()
            var areas = [Area]()
            
            let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue)
            
            print("RESS: :\(ss!)")
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    for i in value.data["areas"].arrayValue{
                        let c = Area(JSON: i.dictionaryObject!)
                        areas.append(c!)
                    }
                    
                    for i in value.data["games"].arrayValue{
                        let c = Game(JSON: i.dictionaryObject!)
                        games.append(c!)
                    }
                    
                    var res2 = [Area]()
                    
                    for i in areas{
                        if i.active > 0{
                            res2.append(i)
                        }
                    }
                    
                    
                    Provider.shared.all_areas = res2
                    Provider.shared.all_games = games
                    
                    callback(value.data, nil)
                    
                    
                }else{
                    print("ERROR:\(value.message)")
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    func site_home(callback : @escaping ServiceResponse){
        let url = URL(string: "\(baseURL)\(site_homeURL)")!
        
        let auth = getHeaders()
        
        Alamofire.request(url, method: .get, parameters: nil, encoding: encodingBody, headers: auth).responseObject { (response : DataResponse<CustomResponse>) in
            
            print("MARK SITE HOME")
            let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue)
            print(ss!)
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    var games = [Game]()
                    var areas = [Area]()
                    
                    for i in value.data["areas"].arrayValue{
                        let c = Area(JSON: i.dictionaryObject!)
                        areas.append(c!)
                    }
                    
                    for i in value.data["games"].arrayValue{
                        let c = Game(JSON: i.dictionaryObject!)
                        games.append(c!)
                    }
                    
                    var res2 = [Area]()
                    
                    for i in areas{
                        if i.active > 0{
                            res2.append(i)
                        }
                    }
                    
                    
                    Provider.shared.all_areas = res2
                    Provider.shared.all_games = games

                    callback(value.data, nil)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    func get_up_coming_booking(callback : @escaping ServiceResponse){
        
        let url = URL(string: "\(baseURL)\(get_upcoming_bookingURL)")!
        
        let auth = getHeaders()
        
        Alamofire.request(url, method: .get, parameters: nil, encoding: encodingBody, headers: auth).responseObject { (response : DataResponse<CustomResponse>) in
            
            print(response.result.value!.data)
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    callback(value.data, nil)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    
    
    func show_book(booking_id : Int, callback : @escaping ServiceResponse){
        
        let params = ["booking_id" : booking_id]
        let url = URL(string: "\(baseURL)\(show_bookURL)")!
        
        let auth = getHeaders()
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: auth).responseObject { (response : DataResponse<CustomResponse>) in
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    callback(value.data, nil)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    func get_notifications(){
        
        let url = URL(string: "\(baseURL)\(get_notificationsURL)")!
        
        let auth = getHeaders()
        
        Alamofire.request(url, method: .get, parameters: nil, encoding: encodingQuery, headers: auth).responseObject { (response : DataResponse<CustomResponse>) in
            
            var res = [Notificat]()
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    for i in value.data.arrayValue{
                        let c = Notificat(JSON: i.dictionaryObject!)
                        res.append(c!)
                    }
                    
                    Provider.shared.my_notifications = res
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    func get_searches(){
        
        let params = ["token" : Provider.token]
        
        let url = URL(string: "\(baseURL)\(get_searchesURL)")!
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            var res = [Search]()
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    for i in value.data.arrayValue{
                        let c = Search(JSON: i.dictionaryObject!)
                        res.append(c!)
                    }
                    
                    Provider.shared.my_searches = res
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    func search(area_id : Int, name : String, game_type : Int,start : String,duration : String,date : String,timing : Int){
        
        Provider.game_type = game_type
        
        let params : [String : Any] = ["area_id" : area_id, "name" : name,"game_type" : game_type, "start" : start, "duration" : duration, "date" : date , "timing" : timing.toString, "token" : Provider.token  ]
        
        print(params)
        
        let url = URL(string: "\(baseURL)\(searchURL)")!
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            
            var res_c = [Company]()
            //            var res_f = [Field]()
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    
                    for i in value.data.arrayValue{
                        let c = Company(JSON: i.dictionaryObject!)
                        res_c.append(c!)
                    }
                    
                    Provider.shared.companies_search = res_c
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    func get_all_games(callback : @escaping ServiceResponse){
        
        let url = URL(string: "\(baseURL)\(get_all_gamesURL)")!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            var res = [Game]()
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    for i in value.data.arrayValue{
                        let c = Game(JSON: i.dictionaryObject!)
                        res.append(c!)
                    }
                    
                    Provider.shared.all_games = res
                    
                    callback(JSON(true), nil)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    
    func get_all_areas(callback : @escaping ServiceResponse){
        
        let url = URL(string: "\(baseURL)\(get_all_areasURL)")!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            var res = [Area]()
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    for i in value.data.arrayValue{
                        let c = Area(JSON: i.dictionaryObject!)
                        res.append(c!)
                    }
                    
                    var res2 = [Area]()
                    
                    for i in res{
                        if i.active > 0{
                            res2.append(i)
                        }
                    }
                    
                    Provider.shared.all_areas = res2
                    
                    callback(true, nil)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    func get_all_amenities(){
        
        let url = URL(string: "\(baseURL)\(get_all_amenitiesURL)")!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            var res = [Amenity]()
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    for i in value.data.arrayValue{
                        let c = Amenity(JSON: i.dictionaryObject!)
                        res.append(c!)
                    }
                    
                    Provider.shared.all_amenities = res
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    func contactUs(phone : String,email: String, message: String, callback : @escaping (String) -> Void){
        
        let params = ["phone" : phone, "email" : email, "message" : message, "os" : "ios"]
        let url = URL(string: "\(baseURL)\(contactUsURL)")!
        
        Alamofire.request(url, method: .post, parameters: params, encoding: encodingBody, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            if let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue){
                print(ss)
            }
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    callback(value.message)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                break
            }
        }
    }
    
    
    func about(callback : @escaping ((CustomResponse) -> Void) ){
        
        let url = URL(string: "\(baseURL)\(aboutURL)")!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            if let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue){
                print(ss)
            }
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    
                    callback(value)
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    // VOUCHERS
    

    func vouchers_check_validity(voucher : String, field_id : Int, date : String, duration : String, start: String ,callback : @escaping (Voucher) -> Void ){
        
        let url = URL(string: "\(baseURL)\(vouchers_check_validityURL)")!
        
        let params : [String : Any] = ["voucher" : voucher, "field_id" :field_id , "date" : date, "duration" : duration, "start" : start,"game_type" : SearchCell.selectedSoccer!.game_type_id! ]
        
        print(params)
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            if let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue){
                print(ss)
            }
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    if let v = Voucher(JSON : value.data.dictionaryObject!){
                        callback(v)
                    }
                    
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    
    func vouchers_show(voucher : String,callback : @escaping (Voucher) -> Void ){
        
        let url = URL(string: "\(baseURL)\(vouchers_showURL)")!
        
        let params : [String : Any] = ["voucher" : voucher]
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            if let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue){
                print(ss)
            }
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    let v = Voucher(JSON : value.data.dictionaryObject!)
                    callback(v!)
                    
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    
    func vouchers_my_vouchers(_ field_id : Int? = nil, callback : @escaping ([Voucher]) -> Void ){
        
        let url = URL(string: "\(baseURL)\(vouchers_my_vouchersURL)")!
        
        var params : [String : Any] = [:]
        
        if field_id != nil{
            params["field_id"] = field_id
        }
        
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            if let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue){
                print(ss)
            }
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    var res = [Voucher]()
                    
                    for i in value.data.arrayValue{
                        let v = Voucher(JSON: i.dictionaryObject!)
                        res.append(v!)
                    }
                    
                    callback(res)
                    
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    
    func get_all_countries(callback : @escaping ([Country]) -> Void ){
        
        let url = URL(string: "\(baseURL)\(get_all_countriesURL)")!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            if let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue){
                print(ss)
            }
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    var res = [Country]()
                    
                    for i in value.data.arrayValue{
                        let v = Country(JSON: i.dictionaryObject!)
                        res.append(v!)
                    }
                    
                    callback(res)
                    
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }
    
    func get_Country(_ country_id : String ,callback : @escaping (Country) -> Void ){
        
        let url = URL(string: "\(baseURL)\(get_countryURL)")!
        let params = ["country_id" : country_id]
        
        Alamofire.request(url, method: .get, parameters: params, encoding: encodingQuery, headers: getHeaders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            if let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue){
                print(ss)
            }
            
            switch response.result{
            case .success(let value):
                if value.status{
                    
                    let v = Country(JSON: value.data.dictionaryObject!)!
                    callback(v)
                    
                    
                }else{
                    notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                }
                break
            case .failure(let error):
                print(error.localizedDescription)
                notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                
                break
            }
        }
    }

    
    //MARK: Api headers
    /*func getAuthorizationHeaders() -> [String : String]
    {
        
        
        //API required headers
        var headers: [String: String] = [String: String]()//getHeaders()
        
        let me = User.getCurrentUser()
        let username = me.phone == nil ? "994729458" : me.phone!
        let password = me.server_id == nil ? "994729458" : me.server_id!
        
        let plainString = "\(username):\(password)" as NSString
        let plainData = plainString.data(using: String.Encoding.utf8.rawValue)
        let base64String = plainData?.base64EncodedString(options: NSData.Base64EncodingOptions(rawValue: 0))
        
        
        
        headers["Authorization"] = "Basic " + base64String!
        
        
        print(headers)
        
        return headers
    }*/
    
    func getHeaders() -> [String : String]
    {
        //        let version = Bundle.main.object(forInfoDictionaryKey: "CFBundleShortVersionString") as! String
        
        //        let me = User.getCurrentUser()
        
        var headers: [String: String] = ["Api_call" : "true"]
        headers["lang"] = Provider.lang
        
        
        let me = User.getCurrentUser()
        
        headers["country"] = me.country_id
        
        if me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue{
//            let username = me.phone == nil ? "994729458" : me.phone!
//            let password = me.server_id == nil ? "994729458" : me.server_id!
            
            if let username = me.phone, let password = me.server_id{
                print("USERNAME: \(username)")
                print("PASSWORD: \(password)")

            let plainString = "\(username):\(password)" as NSString
            let plainData = plainString.data(using: String.Encoding.utf8.rawValue)
            let base64String = plainData?.base64EncodedString(options: NSData.Base64EncodingOptions(rawValue: 0))
            
            headers["Authorization"] = "Basic " + base64String!

            }
        }
        
//        headers["Authorization"] = "Basic OTk0NzI5NDU4OmM1ZTY3MmY1MWQ5MjM3OWFkOTk1MDU3Y2RkZWIxZGM0"
        
        print("HEADERS : \(headers)")
        
        return headers
    }

    
}
