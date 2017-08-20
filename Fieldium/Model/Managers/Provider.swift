//
//  DataProvider.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/19/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import CoreLocation
import MapKit
import FirebaseInstanceID
import FirebaseMessaging
import SwiftyJSON

let _goToVerify = "goToVerify"
let _goToHome = "goToHome"
let _goToFullMap = "goToFullMap"
let _goToFieldDetails = "goToFieldDetails"
let _goToCompanies = "goToCompanies"
let _goToFields = "goToFields"
let _goToBooking = "goToBooking"
let _goToMap = "goToMap"
let _goToMapAvailability = "goToMapAvailability"
let _goToProfile = "goToProfile"
let _goToBookings = "goToBookings"
let _goToNotifications = "goToNotifications"
let _goToLogin = "goToLogin"
let _goToAbout = "goToAbout"
let _goToContact = "goToContact"
let _goToSettings = "goToSettings"
let _goToVouchers = "goToVouchers"


let _update_up_bookings = "update_up_bookings"
let _update_all_fields = "update_all_fields"
let _update_search_companies = "update_search_companies"
let _update_search_fields = "update_search_fields"
let _update_all_companies = "update_all_companies"
let _update_my_bookings = "update_my_bookings"
let _update_nearby_companies = "update_nearby_companies"
let _update_my_notifications = "update_my_notifications"


let _youMustLogin = "Please login"

class Provider : BaseManager {
    
    static let shared = Provider()
    
    static var token : String = String(){
        didSet{
            if let tok = FIRInstanceID.instanceID().token(){
                token = tok
            }else{
                token = ""
            }
        }
    }
    
    static var game_type : Int!
    
    static var region : MKCoordinateRegion!
    
    var currentLocation : CLLocation!
    
    func register(name: String, phone : String,country : Country){
        Communication.shared.register(name: name, phone: phone, country: country)
    }
    
    func verify(code : String){
        Communication.shared.verify(code: code)
    }
    
    func request_verification_code(callback : @escaping (String) -> Void){
        Communication.shared.request_verification_code(callback : callback)
    }
    
    func update_player(email: String, name: String, profile_picture: String, address: String,prefered_games : [Int], callback: @escaping ServiceResponse){
        Communication.shared.update_player(email: email, name: name, profile_picture: profile_picture, address: address, prefered_games : prefered_games, callback: callback)
    }
    
    func deactive_player(callback : @escaping ServiceResponse) {
        Communication.shared.deactive_player(callback: callback)
    }
    
    func refresh_token(callback : @escaping ServiceResponse){
        Communication.shared.refresh_token(callback: callback)
    }
    
    func get_all_companies(){
        Communication.shared.get_all_companies()
    }
    
    func show_company(company_id : Int, callback : @escaping ServiceResponse){
        Communication.shared.show_company(company_id: company_id, callback: callback)
    }
    
    func get_nearby_companies(){
        Communication.shared.get_nearby_companies()
    }
    
    func show_field(field_id : Int , callback : @escaping ServiceResponse){
        Communication.shared.show_field(field_id: field_id, callback: callback)
    }
    
    func get_fields_by_company(company_id : Int){
        Communication.shared.get_fields_by_company(company_id: company_id)
    }
    
    func get_by_company_with_timing(company_id : Int){
        Communication.shared.get_by_company_with_timing(company_id: company_id)
    }
    
    func check_availability_field(field_id : Int, date: String, callback : @escaping ([(String, String)],[(String, String)]) -> Void){
        Communication.shared.check_availability_field(field_id: field_id ,date : date, callback : callback)
    }
    
    func creak_book(field_id : Int, start : String, duration : String, date : String, notes : String,varchar : String, callback : @escaping ServiceResponse){
        
        Communication.shared.creak_book(field_id: field_id, start: start, duration: duration, date: date, notes: notes,varchar : varchar, callback: callback)
    }
    
    func get_my_booking(){
        Communication.shared.get_my_booking()
    }
    
    func show_book(booking_id : Int, callback : @escaping ServiceResponse){
        Communication.shared.show_book(booking_id: booking_id, callback: callback)
    }
    
    func get_up_coming_booking (callback : @escaping ServiceResponse){
        Communication.shared.get_up_coming_booking(callback: callback)
    }
    
    func get_upcoming_and_last_bookings(callback : @escaping ServiceResponse){
        Communication.shared.get_upcoming_and_last_bookings(callback: callback)
    }
    
    func get_notifications(){
        Communication.shared.get_notifications()
    }
    
    func get_searches(){
        Communication.shared.get_searches()
    }
    
    func search(area_id : Int, name : String, game_type : Int,start : String,duration : String,date : String,timing : Int){
        Communication.shared.search(area_id: area_id, name: name, game_type: game_type, start: start, duration: duration, date: date, timing: timing)
    }
    
    func get_all_games(callback : @escaping ServiceResponse){
        Communication.shared.get_all_games(callback : callback)
    }
    
    func get_all_areas(callback : @escaping ServiceResponse){
        Communication.shared.get_all_areas(callback : callback)
    }
    
    func get_all_amenities(){
        Communication.shared.get_all_amenities()
    }
    
    func contactUs(phone: String, email: String, message: String, callback: @escaping (String) -> Void){
        Communication.shared.contactUs(phone: phone, email: email, message: message, callback: callback)
    }
    
    func about(callback : @escaping ((CustomResponse) -> Void) ){
        Communication.shared.about(callback: callback)
    }
    
    
    var fields_by_company : [Field] = [Field]() {
        didSet{
            notic.post(name: _update_all_fields.not, object: nil)
        }
    }
    
    var all_companies : [Company] = [Company]() {
        didSet{
            print("COUNT")
            print(all_companies.count)
            notic.post(name: _update_all_companies.not, object: nil)
        }
    }
    
    var nearby_companies : [Company] = [Company]() {
        didSet{
            print("COUNT")
            print(nearby_companies.count)
        }
    }
    
    var my_booking : [Book] = [Book](){
        didSet{
            print("COUNT")
            print(my_booking.count)
            notic.post(name: _update_my_bookings.not, object: nil)
        }
    }
    
    var my_notifications : [Notificat] = [Notificat](){
        didSet{
            print("COUNT")
            print(my_notifications.count)
            notic.post(name: _update_my_notifications.not, object: nil)
        }
    }
    
    var my_searches : [Search] = [Search](){
        didSet{
            print("COUNT")
            print(my_searches.count)
        }
    }
    
    var fields_by_search : [Field] = [Field](){
        didSet{
            print("COUNT")
            print(fields_by_search.count)
            notic.post(name: _update_search_fields.not, object: nil)
        }
    }
    
    var companies_search : [Company] = [Company](){
        didSet{
            print("COUNT")
            print(companies_search.count)
            notic.post(name: _update_search_companies.not, object: nil)
        }
    }
    
    var all_games : [Game] = [Game](){
        didSet{
            print("COUNT")
            print(all_games.count)
        }
    }
    
    var all_areas : [Area] = [Area](){
        didSet{
            print("COUNT")
            print(all_areas.count)
        }
    }
    
    var all_amenities : [Amenity] = [Amenity](){
        didSet{
            print("COUNT")
            print(all_amenities.count)
        }
    }
    
    
    func getLang() -> Bool {
        
        if let langStr = Locale.current.languageCode{
            print("CODECODE: \(langStr)")
            if langStr.contains("ar"){
                return true
            }
        }
        
        return false
    }
    
    
    func setArabic(_ i : Bool){
        if i {
            UserDefaults.standard.set("ar", forKey: "lang")
            UserDefaults.standard.setValue(["ar","en"], forKey: "AppleLanguages")
        }else{
            UserDefaults.standard.set("en", forKey: "lang")
            UserDefaults.standard.setValue(["en","ar"], forKey: "AppleLanguages")
        }
    }
    
    static var isArabic : Bool {
        
//        let langStr = Provider.shared.lang//NSLocale.preferredLanguages.first!
        
        if lang.contains("en".localized){
            return false
        }
        
        return true
        
        /*if let lang = UserDefaults.standard.value(forKey: "lang") as? String {
         if lang.contains("en"){
         return false
         }else{
         return true
         }
         
         }else{
         UserDefaults.standard.set("ar", forKey: "lang")
         UserDefaults.standard.setValue(["ar","en"], forKey: "AppleLanguages")
         return false
         }*/
        
    }
    
    static var lang : String = ""
    
    
    static func currancy() -> String {
        let me = User.getCurrentUser()
        
        if let en_symbol = me.en_currency_symbol, let ar_symbol = me.ar_currency_symbol{
            return (Provider.isArabic) ? ar_symbol : en_symbol
        }else{
            return "AED"
        }
    }
    
    
    func changeCurrentCountry(_ country : Country){
        let user  = User.getCurrentUser()
        
        UserDefaults.standard.removeObject(forKey: "selectedArea")
        
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
    }
    
    func gg(){
        
        print("PROVIDER PROVIDER")

        let formatter = NumberFormatter()
        let arLocale = Locale(identifier: "ar")
        formatter.locale = arLocale
        if let g = Int.init("1234098765"){
            print("\(formatter.string(for: NSNumber(value: g))!)")
        }
        
        
        var tt = [Character]()
        for (i, c) in "The Price is 198371239 AED".characters.enumerated() {
            print("\(i): \(c)")
            
            if let g = Int.init(String(c)) {
                let y = formatter.string(for: NSNumber.init(value: g))
                tt.append(Character.init(y!))
            }
        }
        
        
        
        print(String(tt))
    }
    
    
    
}

extension NSLayoutConstraint {
    /**
     Change multiplier constraint
     
     - parameter multiplier: CGFloat
     - returns: NSLayoutConstraint
     */
    func setMultiplier(multiplier:CGFloat) -> NSLayoutConstraint {
        
        NSLayoutConstraint.deactivate([self])
        
        let newConstraint = NSLayoutConstraint(
            item: firstItem,
            attribute: firstAttribute,
            relatedBy: relation,
            toItem: secondItem,
            attribute: secondAttribute,
            multiplier: multiplier,
            constant: constant)
        
        newConstraint.priority = priority
        newConstraint.shouldBeArchived = self.shouldBeArchived
        newConstraint.identifier = self.identifier
        
        NSLayoutConstraint.activate([newConstraint])
        return newConstraint
    }
}

