//
//  PushManager.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/19/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import LNNotificationsUI
import AVFoundation
import SwiftyJSON


class PushManager
{
    static let LN_KEY = "Fieldium"
    static let icon = #imageLiteral(resourceName: "logo.png")
    
    static var token : String?
    
    static func handleForegroundNotification(data: [AnyHashable: Any]?) {
        
        
        if let notification = data {
            // handle Json response
            if let aps = notification["aps"] as? [String: AnyObject]
            {
                if let msg = aps["alert"] as? String
                {
                    
                    let pushTopPresentationsView = LNNotification(message: msg)
                    pushTopPresentationsView?.icon = icon
                    pushTopPresentationsView?.defaultAction = LNNotificationAction(title: "", handler:{ (action) in
                        self.handleNotificationTapping(data: data)
                    })
                    
                    LNNotificationCenter.default().present(pushTopPresentationsView, forApplicationIdentifier: LN_KEY)
                    
                    // YAHYA
                    let notification : UILocalNotification = UILocalNotification()
                    notification.alertBody =  msg
                    notification.userInfo = data;
                    notification.soundName = UILocalNotificationDefaultSoundName
                    notification.fireDate = Date()
                    UIApplication.shared.scheduleLocalNotification(notification)
                    
                    var soundId: SystemSoundID = 0
                    let bundle = Bundle.main
                    guard let soundUrl = bundle.url(forResource: "sms", withExtension: "wav") else {
                        return
                    }
                    AudioServicesCreateSystemSoundID(soundUrl as CFURL, &soundId)
                    AudioServicesPlayAlertSound(soundId)
                }
            }
        }
    }
    
    static func handleNotificationTapping(data: [AnyHashable: Any]?)
    {
        print("handleNotificationTapping")
        
        UIApplication.shared.applicationIconBadgeNumber = 0
        
        if let notification = data {
            
            print("handleNotificationTapping: \(notification)")
            
            let ntf_type = JSON(notification["ntf_type"]).intValue
            
            
            
            if  (notification["ntf_body"] != nil) && (ntf_type == 1 || ntf_type == 2)
            {
                
                let payload = notification["ntf_body"]
                
                print("ntf_body: \(payload)")
                
                let bod = JSON(payload)
                do{
                    
                    let dd = bod.stringValue.data(using: String.Encoding.utf8)
                    
                    let ii = try JSONSerialization.jsonObject(with: dd!, options: JSONSerialization.ReadingOptions.mutableContainers)
                    
                    let tt  = JSON(ii)
                    
                    if ntf_type == 1{
                        let bbb = Book(JSON: tt["booking"].dictionaryObject!)
                        print("booking_id: \(bbb?.booking_id)")
                    
                        OpenBook(book: bbb!)
                    }else{
                        print("OOOKKK \(ntf_type)")
                        print("ttt : \(tt)")
                        
                        let vvv = Voucher(JSON: tt.dictionaryObject!)
                        
                        OpenVoucher(voucher: vvv!)
                        
                    }
                    
                    
                    //                    LNNotificationCenter.default().clearAllPendingNotifications()
                    // important
                    notic.post(name: "dis".not, object: nil)
                    
                    
                }
                catch{}
                
            }
            
        }
    }
    
    
    static func OpenBook(book : Book)
    {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        if let navigationController = appDelegate.window?.rootViewController as?  UINavigationController
        {
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let vc = storyboard.instantiateViewController(withIdentifier: "BookViewController") as! BookViewController
            vc.id = 1
            vc.book = book
            
            navigationController.pushViewController(vc, animated: true)
        }
    }
    
    
    static func OpenVoucher(voucher : Voucher)
    {
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        if let navigationController = appDelegate.window?.rootViewController as?  UINavigationController
        {
            let main = UIStoryboard.init(name: "Main", bundle: nil)
            let vc = main.instantiateViewController(withIdentifier: "VouchersViewController") as! VouchersViewController
            vc.selected = voucher
            
            navigationController.pushViewController(vc, animated: true)
        }
    }
    
    
    
}

