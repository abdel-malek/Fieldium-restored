////
////  SideBarViewController.swift
////  Fieldium
////
////  Created by Yahya Tabba on 12/27/16.
////  Copyright © 2017 Tradinos UG. All rights reserved.
////
//
//import UIKit
//
//
//
//class SideBarViewController: UIViewController {
//    
//    @IBOutlet weak var nameLbl: UILabel!
//    @IBOutlet weak var location: UIButton!
//    
//    @IBOutlet weak var imgProfile: UIImageView!
//    @IBOutlet weak var locationBottom: NSLayoutConstraint!
//    
//    @IBOutlet weak var loginStack: UIStackView!
//    @IBOutlet weak var profileStack: UIStackView!
//    @IBOutlet weak var notificationsStack: UIStackView!
//    @IBOutlet weak var bookingsStack: UIStackView!
//    
//    @IBOutlet var btns: [UIButton]!
//    
//    
//    override func viewDidLoad() {
//        super.viewDidLoad()
//        
//        if Provider.isArabic{
//            for i in btns{
//                i.contentHorizontalAlignment = .right
//            }
//        }
//    }
//    
//    override func viewWillAppear(_ animated: Bool) {
//        let me = User.getCurrentUser()
//        
//        if let im = me.profile_picture_url, im != "" {
//            self.imgProfile.sd_setImage(with: URL.init(string: im))
//        }else{
//            self.imgProfile.image = #imageLiteral(resourceName: "pic.png")
//        }
//        
//        DispatchQueue.main.async {
//            self.imgProfile.layer.cornerRadius = self.imgProfile.bounds.width / 2
//        }
//        
//        if  me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue{
//            self.nameLbl.text = me.name
//            self.location.setTitle(me.address, for: .normal)
//            self.loginStack.isHidden = true
//            
//            self.nameLbl.isHidden = false
//            self.location.isHidden = false
//            
//            self.profileStack.isHidden = false
//            self.notificationsStack.isHidden = false
//            self.bookingsStack.isHidden = false
//            
//            if me.address == nil || me.address == "" {
//                self.locationBottom.constant = 0
//            }else{
//                self.locationBottom.constant = 40
//            }
//        }else{
//            self.nameLbl.isHidden = true
//            self.location.isHidden = true
//            
//            self.nameLbl.text = nil
//            self.locationBottom.constant = 0
//            
//            self.profileStack.isHidden = true
//            self.notificationsStack.isHidden = true
//            self.bookingsStack.isHidden = true
//            
//            
//            self.imgProfile.image = #imageLiteral(resourceName: "logo.png")
//            self.imgProfile.contentMode = .scaleAspectFit
//        }
//    }
//    
//    @IBAction func Home(_ sender: Any) {
//        self.performSegue(withIdentifier: _goToHome, sender: nil)
//    }
//    
//    @IBAction func Login(_ sender: Any) {
//        
//        let me = User.getCurrentUser()
//        
//        if me.statues_key == nil {
//            me.statues_key = User.USER_STATUES.NEW_USER.rawValue
//            User.saveMe(me: me)
//        }
//        
//        
//        if me.statues_key == User.USER_STATUES.NEW_USER.rawValue{
//            self.performSegue(withIdentifier: _goToLogin, sender: nil)
//        }
//        
//        if me.statues_key == User.USER_STATUES.USER_PENDING_VERIFICATION.rawValue{
//            self.performSegue(withIdentifier: _goToVerify, sender: nil)
//        }
//    }
//    
//    
//    @IBAction func myProfile(_ sender: Any) {
//        self.performSegue(withIdentifier: _goToProfile, sender: nil)
//    }
//    
//    @IBAction func MyNotifications(_ sender: Any) {
//        self.performSegue(withIdentifier: _goToNotifications, sender: nil)
//    }
//    
//    @IBAction func MyBooking(_ sender: Any) {
//        self.performSegue(withIdentifier: _goToBookings, sender: nil)
//    }
//    
//    @IBAction func AboutUs(_ sender: Any) {
//        self.performSegue(withIdentifier: _goToAbout, sender: nil)
//    }
//    
//    @IBAction func ContactUs(_ sender: Any) {
//        self.performSegue(withIdentifier: _goToContact, sender: nil)
//    }
//    
//    @IBAction func Settings(_ sender: Any) {
//        self.performSegue(withIdentifier: _goToSettings, sender: nil)
//    }
//
//    
//    @IBAction func Vouchers(_ sender: Any) {
//        self.performSegue(withIdentifier: _goToVouchers, sender: nil)
//    }
//    
//
//    
//}
