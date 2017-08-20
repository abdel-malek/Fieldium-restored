//
//  BaseViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/18/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import MBProgressHUD

class BaseViewController: UIViewController, UITextFieldDelegate, UITextViewDelegate, UIGestureRecognizerDelegate {
    
    var loadingView = MBProgressHUD.init()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        configureNavigationBar()
        
        registerReceivers()
        
        self.view.endEditing(true)
        
        let tap = UITapGestureRecognizer(target: self, action: #selector(BaseViewController.onTouch))
        tap.cancelsTouchesInView = false
        self.view.addGestureRecognizer(tap)
        
        
    }
    
    override var prefersStatusBarHidden: Bool {
         let y = UIApplication.shared.statusBarFrame.size.height
        
        if y == 20{
            return false
        }else{
            return true
        }
    }
    
    
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        unregisterReceivers()
    }
    
    func registerReceivers()
    {
        
        notic.addObserver(self, selector: #selector(self.connectionErrorNotificationReceived(_:)), name: _ConnectionErrorNotification.not, object: nil)
        
        notic.addObserver(self, selector: #selector(self.RequestErrorNotificationRecived(_:)), name: _RequestErrorNotificationReceived.not, object: nil)
        
        notic.addObserver(self, selector: #selector(self.dissmiss), name: "dis".not, object: nil)
    }
    
    func unregisterReceivers()
    {
        notic.removeObserver(self, name: _ConnectionErrorNotification.not, object: nil)
        
        notic.removeObserver(self, name: _RequestErrorNotificationReceived.not, object: nil)
        
        notic.removeObserver(self, name: "dis".not, object: nil)
        
    }
    
    
    func dissmiss(){
        self.dismiss(animated: true, completion: nil)
    }
    
    func connectionErrorNotificationReceived(_ notification: NSNotification) {
        self.hideLoading()
        
        print("connectionErrorNotificationReceived")
        if let msg = notification.object as? String{
            self.showErrorMessage(text : msg)
        }
    }
    
    func RequestErrorNotificationRecived(_ notification : NSNotification)
    {
        print("RequestErrorNotificationRecived")
        self.hideLoading()
        
        if let code = notification.object as? Int
        {
            if(code == 401)
            {
                return
            }
        }
        
        if let msg = notification.object as? String{
            self.showErrorMessage(text: msg)
            
            if msg == "Not authorized" {
                User.saveMe(me: User())
                
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "SignupViewController") as! SignupViewController
                let nv = UINavigationController.init(rootViewController: vc)
                let del = UIApplication.shared.delegate as! AppDelegate
                
                del.window?.rootViewController = nv
            }
        }
        
        if let msg = notification.object as? [String]
        {
            self.showErrorMessage(text: msg.joined(separator: "\n"))
        }
        else if let msg = notification.object as? String
        {
            self.showErrorMessage(text: msg)
        }
        else
        {
            self.showErrorMessage(text: NSLocalizedString("Request.Error", comment: ""))
        }
    }
    
    func showErrorMessage(text: String) {
        KSToastView.ks_showToast(text.html2String, duration: 3)
    }
    
    
    func configureNavigationBar() {
        if let nav = self.navigationController{
            nav.isNavigationBarHidden = false

            nav.navigationBar.shadowImage = UIImage()

            //setup back button
            self.navigationItem.hidesBackButton = false
            let rr = UIBarButtonItem(title: "", style: UIBarButtonItemStyle.plain, target: nil, action:nil)
            self.navigationItem.backBarButtonItem = rr
            
            
            //configure back button
            if self != nav.viewControllers[0] {

            }
            
        }
    }
    
    func navigateBack() {
        let _ = self.navigationController?.popViewController(animated: true)
    }
    
    
    func showLoading(){
        self.loadingView = MBProgressHUD.showAdded(to: self.view, animated: true)
    }
    
    func hideLoading(){
        self.loadingView.hide(animated: true)
    }

    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()
        return true
    }
    
    
    func onTouch() {
        self.view.endEditing(true)
    }
    
    
    func showAlert(title : String, message : String){
        let alert = UIAlertController.init(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "OK".localized, style: .default, handler: nil))
        
        self.present(alert, animated: true, completion: nil)
    }
    
    
}
