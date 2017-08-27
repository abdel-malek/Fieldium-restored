//
//  AboutViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/19/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class AboutViewController: BaseViewController {
    
    @IBOutlet weak var fieldiumVersion: UILabel!
    
    @IBOutlet weak var phoneLbl: UILabel!
    @IBOutlet weak var emailLbl: UILabel!
    @IBOutlet weak var mobileLbl: UILabel!
    @IBOutlet weak var reservedLbl: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "About".localized
        
        
        reservedLbl.isHidden = true
        
        if let version = Bundle.main.infoDictionary?["CFBundleShortVersionString"] as? String {
            self.fieldiumVersion.text = "Fieldium Player V\(version)"
        }
        
        
        Provider.shared.about { (cc) in
            print(cc.data)
            print("SDF")
            if cc.status{
                let email = cc.data["email"].stringValue
                let phone = cc.data["phone"].stringValue
                let mobile = cc.data["mobile"].stringValue
                
                self.phoneLbl.text = "Phone: " + phone
                self.emailLbl.text = "Email: " + email
                self.mobileLbl.text = "Mobile: " + mobile
                
                self.phoneLbl.isHidden = (phone == "") ? true : false
                self.emailLbl.isHidden = (email == "") ? true : false
                self.mobileLbl.isHidden = (mobile == "") ? true : false
                self.reservedLbl.isHidden = false
            }
            
        }
    }
}
