//
//  VerifyViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/18/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class VerifyViewController: BaseViewController {
    
    @IBOutlet weak var enterLbl: UILabel!
    
    @IBOutlet weak var codeField: UITextField!
    
    @IBOutlet weak var skipBtn: UIButton!
    @IBOutlet weak var orBtn: UIButton!
    
    // TO DO 1.1
    var timer = Timer()
    
    var type = 0
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initView()
    }
    
    func initView(){
        
        let me = User.getCurrentUser()
        
        if let p = me.phone{
            enterLbl.text = "\("Enter the verification code sent to".localized)\n\(me.dialing_code!)\(p)"
        }
        
        if type != 2{
            self.navigationItem.hidesBackButton = true
        }
        
        if type == 2{
            let closeBtn = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.stop, target: self, action: #selector(self.closeView))
            self.navigationItem.leftBarButtonItem = closeBtn
            
        }
        
        
        // TO DO 1.1
        setupTimer()
        
    }
    
    func setupTimer(){
        timerVerify()
        
        if Time.getTimer() != 0{
            timer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(self.timerVerify), userInfo: nil, repeats: true)
        }
    }
    
    func timerVerify(){
        if Time.getTimer() != 0{
            self.orBtn.isEnabled = false
            UIView.performWithoutAnimation {
                let s = Time.getTimer()
                self.orBtn.setTitle("\(s / 60)\("min".localized) \(s % 60)\("sec".localized)", for: .normal)
                self.orBtn.layoutIfNeeded()
            }
            
        }else{
            self.orBtn.isEnabled = true
            self.orBtn.setTitle("GET A NEW ONE".localized, for: .normal)
            self.timer.invalidate()
        }
    }
    

    
    func closeView(){
        self.dismiss(animated: true, completion: nil)
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        notic.addObserver(self, selector: #selector(self.goToHome), name: _goToHome.not, object: nil)
//        notic.addObserver(self, selector: #selector(self.timerVerify), name: _timerVerify.not, object: nil)
    }
    
    
    override func viewWillDisappear(_ animated: Bool) {
        notic.removeObserver(self, name: _goToHome.not, object: nil)
//        notic.removeObserver(self, name: _timerVerify.not, object: nil)
    }
    
    
    
    func goToHome(){
        self.hideLoading()
        
        if type != 2 {
            self.performSegue(withIdentifier: _goToHome, sender: nil)
        }
            
        else{
            self.dismiss(animated: true, completion: nil)
        }
    }
    
    
    @IBAction func skipAction(_ sender: Any) {
        if type != 2{
            self.performSegue(withIdentifier: _goToHome, sender: nil)
        }else{
            self.dismiss(animated: true, completion: nil)
        }
    }
    
    @IBAction func getNew(_ sender: Any) {
        
            self.showLoading()
            
            Provider.shared.request_verification_code { (message) in
                self.hideLoading()
                self.setupTimer()
                
                self.showErrorMessage(text: message)
            }
    }
    
    @IBAction func verifyAction(_ sender: Any) {
        let code = codeField.text!
        
        if code.characters.count != 6{
            self.showErrorMessage(text: "Invalid Code".localized)
        }
        else{
            self.showLoading()
            Provider.shared.verify(code: code)
        }
    }
    
    @IBAction func goToLogin(_ sender: Any) {
        performSegue(withIdentifier: _goToLogin, sender: nil)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == _goToLogin{
            let vc = segue.destination as! SignupViewController
            vc.new = true
        }
    }
    
    
    @IBAction func handleGesture(_ sender: UIPanGestureRecognizer) {
        
        if type == 2 {
            if sender.state == .began || sender.state == .changed {
                
                let translation = sender.translation(in: self.view)
                
                if translation.y >= 100{
                    self.dismiss(animated: true, completion: nil)
                }
                
            }
        }
    }
    
}
