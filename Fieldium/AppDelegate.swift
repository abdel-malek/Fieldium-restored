//
//  AppDelegate.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/15/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import SwiftyJSON
import IQKeyboardManagerSwift
import Firebase
import UserNotifications
import FirebaseInstanceID
import FirebaseMessaging
import LNNotificationsUI
import Alamofire
import SDWebImage


@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    var storyBoard = UIStoryboard.init(name: "Main", bundle: nil)
    let gcmMessageIDKey = "gcm.message_id"
    
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        
        
        
        
        // TO DO
        Thread.sleep(forTimeInterval: 3)
        
        //IQKeyboardManager
        IQKeyboardManager.sharedManager().enable = true
        
        // setup navigation bar
        var titleAttributes = UINavigationBar.appearance().titleTextAttributes
        if titleAttributes == nil {
            titleAttributes = [String : AnyObject]()
        }
        titleAttributes![NSFontAttributeName] = ThemeManager.shared.boldFont
        titleAttributes![NSForegroundColorAttributeName] = UIColor.black
        
        UINavigationBar.appearance().titleTextAttributes = titleAttributes
        UINavigationBar.appearance().barStyle = UIBarStyle.default
        UINavigationBar.appearance().tintColor = ThemeManager.shared.customBlue
        
        
        //setup View on launch
        self.setupView()
        
        //setup push notifications
        setupNotifications(application)
        
        
        Provider.shared.gg()
        
        
        return true
    }
    
    func setupNotifications(_ application : UIApplication){
        //push notification
        if #available(iOS 10.0, *) {
            let authOptions: UNAuthorizationOptions = [.alert, .badge, .sound]
            UNUserNotificationCenter.current().requestAuthorization(
                options: authOptions,
                completionHandler:  {_, _ in })
            
            // For iOS 10 display notification (sent via APNS)
            UNUserNotificationCenter.current().delegate = self
            // For iOS 10 data message (sent via FCM)
            FIRMessaging.messaging().remoteMessageDelegate = self
            
        } else {
            let settings: UIUserNotificationSettings =
                UIUserNotificationSettings(types: [.alert, .badge, .sound], categories: nil)
            application.registerUserNotificationSettings(settings)
        }
        
        LNNotificationCenter.default().registerApplication(withIdentifier: PushManager.LN_KEY, name: "Fieldium", icon: PushManager.icon, defaultSettings: LNNotificationAppSettings.default())
        
        application.registerForRemoteNotifications()
        
        FIRApp.configure()
        
        NotificationCenter.default.addObserver(self,
                                               selector: #selector(self.tokenRefreshNotification),
                                               name: .firInstanceIDTokenRefresh,
                                               object: nil)
        
    }
    
    func setupView(){
        
        Provider.lang = NSLocale.preferredLanguages.first!
        
//        UserDefaults.standard.set("en", forKey: "lang")
//        UserDefaults.standard.setValue(["en","ar"], forKey: "AppleLanguages")
//        Provider.shared.lang = "en"
        
        if UserDefaults.standard.value(forKey: "lang") == nil{
            UserDefaults.standard.set("en", forKey: "lang")
            UserDefaults.standard.setValue(["en","ar"], forKey: "AppleLanguages")
            Provider.lang = "en"
        }
        
        
        let me = User.getCurrentUser()
        
        if me.statues_key == nil {
            me.statues_key = User.USER_STATUES.NEW_USER.rawValue
            User.saveMe(me: me)
        }
        
        if me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue{
            
            //FirstViewController
            // TO DO homeviewcontroller
            let vc = self.storyBoard.instantiateViewController(withIdentifier: "HomeViewController") as! HomeViewController
            let nv = UINavigationController.init(rootViewController: vc)
            
            self.window?.rootViewController = nv
        }
        
        if me.statues_key == User.USER_STATUES.USER_PENDING_VERIFICATION.rawValue{
            let vc = self.storyBoard.instantiateViewController(withIdentifier: "HomeViewController") as! HomeViewController
            let nv = UINavigationController.init(rootViewController: vc)
            
            self.window?.rootViewController = nv
            
            if let navigationController = self.window?.rootViewController as?  UINavigationController
            {
                let storyboard: UIStoryboard = UIStoryboard(name: "Main", bundle: nil)
                let initViewController: UIViewController = storyboard.instantiateViewController(withIdentifier: "VerifyViewController") as! VerifyViewController
                navigationController.pushViewController(initViewController, animated: true)
            }
        }
        
        if #available(iOS 9.0, *) {
            
            //            UILabel.appearance().semanticContentAttribute = .forceLeftToRight
            //            UIButton.appearance().semanticContentAttribute = .forceLeftToRight
            //
            //            UIStackView.appearance().semanticContentAttribute = .forceLeftToRight
            //
            //            UICollectionViewCell.appearance().semanticContentAttribute = .forceLeftToRight
            //            UICollectionView.appearance().semanticContentAttribute = .forceLeftToRight
            //
            //            UITableViewCell.appearance().semanticContentAttribute = .forceLeftToRight
            //            UITableView.appearance().semanticContentAttribute = .forceLeftToRight
            //
            //            UITextField.appearance().semanticContentAttribute = .forceLeftToRight
        }
        
        
        //clear cashe again
        Provider.shared.all_amenities.removeAll()
        Provider.shared.all_areas.removeAll()
        Provider.shared.all_companies.removeAll()
        Provider.shared.all_games.removeAll()
        Provider.shared.companies_search.removeAll()
        Provider.shared.fields_by_company.removeAll()
        Provider.shared.fields_by_search.removeAll()
        Provider.shared.my_booking.removeAll()
        Provider.shared.my_searches.removeAll()
        Provider.shared.my_notifications.removeAll()
        
    }
    
    
    
    func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable: Any], fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {
        
        if let messageID = userInfo[gcmMessageIDKey] {
            print("Message ID: \(messageID)")
        }
        
        
        print(userInfo)
        
        if #available(iOS 10, *){
            
        }
        else{
            
            if application.applicationState == .active{
                PushManager.handleForegroundNotification(data: userInfo)
                completionHandler(.newData)
            }else if application.applicationState != .background{
                PushManager.handleNotificationTapping(data: userInfo)
                completionHandler(.newData)
            }else{
                completionHandler(.noData)
            }
        }
        
    }
    
    
    
    func application(_ application: UIApplication, didReceive notification: UILocalNotification) {
        
        if #available(iOS 10, *) {
            
        }else
        {
            if application.applicationState != UIApplicationState.active{
                PushManager.handleNotificationTapping(data: notification.userInfo)
            }
        }
        
    }
    
    
    // [START refresh_token]
    func tokenRefreshNotification(_ notification: Notification) {
        print("TOKENTOKEN")
        
        if let refreshedToken = FIRInstanceID.instanceID().token() {
            print("InstanceID token: \(refreshedToken)")
            
            Provider.token = refreshedToken
            
            Provider.shared.refresh_token(callback: { (res, err) in
                //print("REFRESH : \(res)")
            })
        }
        
        connectToFcm()
    }
    
    
    func connectToFcm() {
        // Won't connect since there is no token
        guard FIRInstanceID.instanceID().token() != nil else {
            return;
        }
        
        // Disconnect previous FCM connection if it exists.
        FIRMessaging.messaging().disconnect()
        
        FIRMessaging.messaging().connect { (error) in
            if error != nil {
                print("Unable to connect with FCM. \(error!.localizedDescription)")
            } else {
                print("Connected to FCM.")
            }
        }
    }
    
    
    // [END connect_to_fcm]
    func application(_ application: UIApplication, didFailToRegisterForRemoteNotificationsWithError error: Error) {
        print("Unable to register for remote notifications: \(error.localizedDescription)")
    }
    
    
    // the InstanceID token.
    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
        print("APNs token retrieved: \(deviceToken)")
        
        // With swizzling disabled you must set the APNs token here.
        FIRInstanceID.instanceID().setAPNSToken(deviceToken, type: FIRInstanceIDAPNSTokenType.prod)
        
        if let refreshedToken = FIRInstanceID.instanceID().token() {
            print("InstanceID token: \(refreshedToken)")
            
            Provider.token = refreshedToken
            
            Provider.shared.refresh_token(callback: { (res, err) in
                //print("REFRESH : \(res)")
            })
        }
    }
    
}


@available(iOS 10, *)
extension AppDelegate : UNUserNotificationCenterDelegate {
    
    // Receive displayed notifications for iOS 10 devices.
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                willPresent notification: UNNotification,
                                withCompletionHandler completionHandler: @escaping (UNNotificationPresentationOptions) -> Void) {
        
        let userInfo = notification.request.content.userInfo
        // Print message ID.
        if let messageID = userInfo[gcmMessageIDKey] {
            print("Message ID userNotificationCenter willPresent: \(messageID)")
        }
        
        print(userInfo)
        
        // Change this to your preferred presentation option
        completionHandler([.alert, .sound])
    }
    
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                didReceive response: UNNotificationResponse,
                                withCompletionHandler completionHandler: @escaping () -> Void) {
        let userInfo = response.notification.request.content.userInfo
        // Print message ID.
        if let messageID = userInfo[gcmMessageIDKey] {
            print("Message ID userNotificationCenter didReceive: \(messageID)")
        }
        
        // Print full message.
        print(userInfo)
        
        
        PushManager.handleNotificationTapping(data: userInfo)
        
        completionHandler()
    }
    
    
}
// [END ios_10_message_handling]


// [START ios_10_data_message_handling]
extension AppDelegate : FIRMessagingDelegate {
    // Receive data message on iOS 10 devices while app is in the foreground.
    func applicationReceivedRemoteMessage(_ remoteMessage: FIRMessagingRemoteMessage) {
        print("applicationReceivedRemoteMessage10")
        print(remoteMessage.appData)
    }
    
}
// [END ios_10_data_message_handling]
