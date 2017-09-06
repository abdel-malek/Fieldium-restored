//
//  AddVoucherViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/12/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class AddVoucherViewController: BaseViewController {
    
    
    @IBOutlet weak var vv : UIView!
    @IBOutlet weak var errorLbl : UILabel!
    @IBOutlet weak var tfCode : UITextField!
    @IBOutlet weak var okBtn : UIButton!
    
    
    var field : Field!
    var date : String!
    var duration : String!
    var start : String!
    var selectedVoucher : Voucher!
    var parentBook : BookViewController!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        vv.clipsToBounds = true
        vv.layer.cornerRadius = 20
        
        
        errorLbl.text = nil
    }
    
    
    
    @IBAction func cancelAction(_ sender : UIButton){
        self.dissmiss()
    }
    
    @IBAction func okAction(_ sender : UIButton){
        
        if selectedVoucher != nil{
            applyAction()
            return
        }
        
        let code  = tfCode.text!
        self.errorLbl.isHidden = true
        
        if code == ""{
            self.showErrorMessage(text: "Please enter a valid code")
            return
        }
        
        
        self.showLoading()
        print("\(code) - \(field.field_id)- \(date)- \(duration)- \(start)")
        
        Communication.shared.vouchers_check_validity(voucher: code, field_id: field.field_id, date: date, duration: duration, start: start) { (res) in
            
            self.hideLoading()
            
            if res.valid.intValue != 0{
                self.selectedVoucher = res
                    self.tfCode.isEnabled = false
                    self.okBtn.setTitle("Apply".localized, for: .normal)
            }else{
                self.errorLbl.text = res.message
                self.errorLbl.isHidden = false
            }
        }
    }
    
    
    func applyAction(){
        if let res = self.selectedVoucher{
            self.parentBook.selectedVoucher = res
            self.parentBook.refreshData()
            self.dissmiss()
        }
    }
    
}
