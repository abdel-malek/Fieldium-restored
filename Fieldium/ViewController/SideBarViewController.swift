//
//  SideBarViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/11/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class SideBarViewController: UIViewController {
    
    
    var list = [(UIImage,String,String)]()
    
    @IBOutlet weak var tableView: UITableView!
    
    @IBOutlet weak var nameLbl: UILabel!
    @IBOutlet weak var location: UIButton!
    
    @IBOutlet weak var imgProfile: UIImageView!
    @IBOutlet weak var locationBottom: NSLayoutConstraint!
    
    @IBOutlet var btns: [UIButton]!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.refreshList()
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        
        self.refreshList()
        
    }
    
    func refreshList(){
        
        
        let me = User.getCurrentUser()
        var l = [(UIImage,String,String)]()
        
        let isRegistered = (me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue)
        
        if isRegistered{
            if let im = me.profile_picture_url, im != "" {
                self.imgProfile.sd_setImage(with: URL.init(string: im))
            }else{
                self.imgProfile.image = #imageLiteral(resourceName: "pic.png")
            }
            
            self.nameLbl.text = me.name
            self.location.setTitle(me.address, for: .normal)
            
            self.nameLbl.isHidden = false
            self.location.isHidden = false
            
            
            if me.address == nil || me.address == "" {
                self.locationBottom.constant = 0
            }else{
                self.locationBottom.constant = 40
            }
            
        }else{
            self.nameLbl.isHidden = true
            self.location.isHidden = true
            
            self.nameLbl.text = nil
            self.locationBottom.constant = 0
            
            self.imgProfile.image = #imageLiteral(resourceName: "pic.png")
            self.imgProfile.contentMode = .scaleAspectFit
        }
        
        DispatchQueue.main.async {
            self.imgProfile.layer.cornerRadius = self.imgProfile.bounds.width / 2
        }
        
        
        l.append((#imageLiteral(resourceName: "ic_home"), "Home".localized, _goToHome))
        if !isRegistered{l.append((#imageLiteral(resourceName: "ic_log_in"), "Login".localized, _goToLogin));}
        if isRegistered{
            l.append((#imageLiteral(resourceName: "ic_profile"), "My Profile".localized, _goToProfile))
            l.append((#imageLiteral(resourceName: "ic_notifications"), "My Notifications".localized, _goToNotifications))
            l.append((#imageLiteral(resourceName: "ic_bookings"), "My Bookings".localized, _goToBookings))
            l.append((#imageLiteral(resourceName: "vouchers"), "My Vouchers".localized, _goToVouchers))
        }
        l.append((#imageLiteral(resourceName: "settings"), "Settings".localized, _goToSettings))
        l.append((#imageLiteral(resourceName: "ic_location"), "Country".localized, _goToSettings))
        l.append((#imageLiteral(resourceName: "ic_contact"), "Contact Us".localized, _goToContact))
        l.append((#imageLiteral(resourceName: "ic_about"), "About".localized, _goToAbout))
        self.list = l
        self.tableView.reloadData()
    }
    
    
    
}


extension SideBarViewController : UITableViewDelegate, UITableViewDataSource{
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return list.count
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! SideBarCell
        cell.selectionStyle = .none
        
        cell.item = list[indexPath.row]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        if list[indexPath.row].1 == "Login".localized{
            let me = User.getCurrentUser()
            if me.statues_key == User.USER_STATUES.NEW_USER.rawValue{
                self.performSegue(withIdentifier: _goToLogin, sender: nil)
            }else{
                self.performSegue(withIdentifier: _goToVerify, sender: nil)
            }
            return
        }
        
        if list[indexPath.row].1 == "Country".localized{
            
            changeCountry()
            return
        }
        
        self.performSegue(withIdentifier: list[indexPath.row].2, sender: nil)
    }
    
    
    func changeCountry(){
        
        let me = User.getCurrentUser()
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        let w = appDelegate.window!.rootViewController?.childViewControllers.filter({ (vv) -> Bool in
            vv.isKind(of: HomeViewController.self)
        }).first as! HomeViewController
        
        let alert = UIAlertController(title: "Change Country".localized , message: "", preferredStyle: .alert)
        
        
        
        
        self.dismiss(animated: true) {
            
//            
//        }
//        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2) {
            
            w.showLoading()

            Communication.shared.get_all_countries { (res) in
                            w.hideLoading()
                
                for i in res{
                    
                    var buttonStyle = UIAlertActionStyle.default
                    
                    if let meID = me.country_id, meID == i.country_id.stringValue{
                        buttonStyle = UIAlertActionStyle.destructive
                    }
                    
                    
                    alert.addAction(UIAlertAction(title: i.name.stringValue, style: buttonStyle, handler: { (ac) in
                        
                        Provider.shared.changeCurrentCountry(i)
                        w.initView()
                        
                        
                    }))
                }
                
                alert.addAction(UIAlertAction(title: "Cancel".localized, style: .cancel, handler: nil))
                
                w.present(alert, animated: true, completion: nil)

//                self.dismiss(animated: true) {
//                    w.present(alert, animated: true, completion: nil)
//                }
            }
        
        }
        
    }
    
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 55
    }
    
}
