//
//  SignupViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/18/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import DropDown

class SignupViewController: BaseViewController {
    
//    @IBOutlet weak var nameField: UITextField!
//    @IBOutlet weak var numberField: UITextField!
    
    @IBOutlet weak var tfNameEn: UITextField!

    @IBOutlet weak var tfNumberEn: UITextField!

    
    //    @IBOutlet weak var uaeField: UITextField!
    @IBOutlet weak var codeArWidth: NSLayoutConstraint!
    
    @IBOutlet weak var codeEnWidth: NSLayoutConstraint!
    @IBOutlet weak var uaeView: UIView!
    
    @IBOutlet weak var skipBtn: UIButton!
    
    var type : Int = 0
    
    var new : Bool = false
    
    var dropDown = DropDown()
    @IBOutlet weak var countryBtn: UIButton!
    @IBOutlet weak var countryLbl: UILabel!
    @IBOutlet weak var countryLbl2: UILabel!

    
    var countries = [Country]()
    
    var selectedCountry : Country!{
        didSet{
            if let s = self.countryLbl{
                s.text = selectedCountry.dialing_code.string
            }
            if let s = self.countryLbl2{
                s.text = selectedCountry.dialing_code.string
            }
            if let s = self.countryBtn{
                if let img = selectedCountry.image_url, img != ""{
                    s.sd_setImage(with: URL(string : img), for: .normal)
                }
            }
        }
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initView()
    }
    
    func initView(){
        self.title = "Login".localized
        self.tfNumberEn.minimumFontSize = 12
        
        self.tfNumberEn.placeholder = ""//"Name".localized
        self.tfNumberEn.placeholder = ""//"Phone number".localized
        
        if Provider.isArabic{
//            self.nameField.textAlignment = .center
            self.tfNumberEn.textAlignment = .left
            self.tfNameEn.textAlignment = .right
            
            self.codeEnWidth.constant = 0
            self.codeArWidth.constant = 40
            
            self.countryLbl2.isHidden = false
        }else{
            self.tfNumberEn.textAlignment = .left
            self.tfNameEn.textAlignment = .left

        }
        
        let tap = UITapGestureRecognizer.init(target: self, action: #selector(self.showtfNumber))
        tap.numberOfTapsRequired = 1
        self.uaeView.isUserInteractionEnabled = true
        self.uaeView.addGestureRecognizer(tap)
        
        if type == 1 || type == 2{
            let closeBtn = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.stop, target: self, action: #selector(self.closeView))
            self.navigationItem.leftBarButtonItem = closeBtn
        }
        
        if new{
            self.navigationItem.leftBarButtonItem = nil
        }
        
        self.setupDropdown()

        
        if let s = self.selectedCountry{
            self.countryLbl.text = s.dialing_code.string
            self.countryLbl2.text = s.dialing_code.string
            
            if let img = s.image_url, img != ""{
                self.countryBtn.sd_setImage(with: URL(string : img), for: .normal)
            }

        }else{
            self.countryLbl.text = nil
            self.countryLbl2.text = nil
            
            self.showLoading()
            Communication.shared.get_all_countries { (res) in
                self.hideLoading()
                
                self.countries = res
                self.setupDropdown()
                
                self.selectedCountry = res.first
            }
        }
    }
    
    func closeView(){
        self.dismiss(animated: true, completion: nil)
    }
    
    func showtfNumber(){
        self.tfNumberEn.becomeFirstResponder()
    }
    
    
    override func viewWillAppear(_ animated: Bool) {
        notic.addObserver(self, selector: #selector(self.goToVerify), name: _goToVerify.not, object: nil)
        
    }
    
    
    override func viewWillDisappear(_ animated: Bool) {
        notic.removeObserver(self, name: _goToVerify.not, object: nil)
    }
    
    
    
    @IBAction func skipAction(_ sender: Any) {
        
        if type == 0{
            self.performSegue(withIdentifier: _goToHome, sender: nil)
        }else{
            self.dismiss(animated: true, completion: nil)
        }
        
    }
    
    @IBAction func nextAction(_ sender: Any) {
        let nm = tfNumberEn.text!
        let nr = tfNumberEn.text!
        
        if  nr.characters.count != 9 {
            self.showErrorMessage(text: "Please enter a valid phone")
        }
        else{
            self.showLoading()
            Provider.shared.register(name: nm, phone: nr,country: self.selectedCountry)
        }
    }
    
    func goToVerify(){
        self.hideLoading()
        self.performSegue(withIdentifier: _goToVerify, sender: nil)
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == _goToVerify{
            let vc = segue.destination as! VerifyViewController
            vc.type = type
        }
    }
    
    
    func setupDropdown(){
        dropDown.anchorView = uaeView // UIView or UIBarButtonItem
        dropDown.direction = .bottom
        dropDown.bottomOffset = CGPoint(x: 0, y: uaeView.bounds.height)
        dropDown.width = self.view.frame.width * 5 / 6
        
        var list = [String]()
        
        for i in self.countries{
            list.append((i.name.stringValue))
        }
        
        
        dropDown.dataSource = list
        
        dropDown.cellNib = UINib(nibName: "CountryCell", bundle: nil)
        
        dropDown.customCellConfiguration = { (index: Index, item: String, cell: DropDownCell) -> Void in
            guard let cell = cell as? CountryCell else { return }
            
            let i = self.countries[index]
            
            cell.nameLbl.text = i.name.stringValue
            cell.numberLbl.text = i.dialing_code.stringValue
            cell.img.image = #imageLiteral(resourceName: "c1.png")
            
            if let img = i.image_url, img != ""{
                cell.img.sd_setImage(with: URL(string: img)!)
            }

        }
        
        
        dropDown.selectionAction = { (index: Index, item: String) -> Void in
            
            self.selectedCountry = self.countries[index]
            
            //            self.countryBtn.setImage(list[index].2, for: .normal)
            //            self.countryLbl.text = i.dialing_code.stringValue
        }
    }
    
    
    @IBAction func showDropdown(_ sender : UIButton){
        self.dropDown.show()
    }
    
    
    @IBAction func handleGesture(_ sender: UIPanGestureRecognizer) {
        
        if type != 0 {
            if sender.state == .began || sender.state == .changed {
                
                let translation = sender.translation(in: self.view)
                
                if translation.y >= 100{
                    self.dismiss(animated: true, completion: nil)
                }
            }
        }
    }
    
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        print("textFieldShouldBeginEditing")
        
        return true
    }
    
    
    
}
