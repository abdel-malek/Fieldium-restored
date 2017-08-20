//
//  ContactusViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 2/14/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class ContactusViewController: BaseViewController {
    
    @IBOutlet weak var tfPhone: UITextField!
    @IBOutlet weak var tfEmail: UITextField!
    @IBOutlet weak var tfMessage: UITextView!

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "Contact Us".localized
        
        let me = User.getCurrentUser()
        
        if let phone = me.phone{
            self.tfPhone.text = phone
//            self.tfPhone.isEnabled = false
        }
        
        if let email = me.email {
            self.tfEmail.text = email
        }
    }
    
    
    @IBAction func sentAction(_ sender: Any) {
        let ph = (tfPhone.text != nil) ? tfPhone.text! : ""
        let mail  = (tfEmail.text != nil) ? tfEmail.text! : ""
        let message = (tfMessage.text != nil) ? tfMessage.text! : ""
        
        if ph.characters.count != 9 {
            self.showErrorMessage(text: "Invalid phone number".localized)
        }else if message == "" {
            self.showErrorMessage(text: "Please enter message".localized)
        }else{
            self.showLoading()
            
            Provider.shared.contactUs(phone: ph, email: mail, message: message, callback: { (ss) in
                self.hideLoading()
                self.navigateBack()
                self.showErrorMessage(text: ss)
            })
        }
    }
    
    
    
}
