//
//  FirstViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/6/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import SDWebImage

class FirstViewController: BaseViewController {
    
    @IBOutlet weak var logoImg: UIImageView!
    
    @IBOutlet weak var tableView : UITableView!
    @IBOutlet weak var tableViewHeight: NSLayoutConstraint!
    
    @IBOutlet weak var vv: UIView!
    var isSelected : Bool = false
    
    @IBOutlet weak var goBtn: UIButton!
    var countries = [Country]()
    
    var country : Country!{
        didSet{
            Provider.shared.changeCurrentCountry(country)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        goBtn.clipsToBounds = true
        goBtn.layer.cornerRadius = 10
        goBtn.layer.borderColor = UIColor.white.cgColor
        goBtn.layer.borderWidth = 1
        
        self.vv.clipsToBounds = true
        self.vv.layer.cornerRadius = 10
        
        self.tableView.register(UINib.init(nibName: "CountryCell2", bundle: nil), forCellReuseIdentifier: "CountryCell2")
        self.tableView.indicatorStyle = .white
        self.tableView.separatorColor = .white
        self.vv.isHidden = true
        
        
        self.showLoading()
        
        Communication.shared.get_all_countries { (res) in
            self.hideLoading()
            self.vv.isHidden = false
            
            self.countries = res
            
            self.tableView.reloadData()
            self.tableViewHeight.constant = self.tableView.contentSize.height
        }
        
    }
    
    
}

extension FirstViewController : UITableViewDelegate , UITableViewDataSource{
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0{
            return self.countries.count
        }else{
            return 0//(isSelected) ? 1 : 0
        }
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "CountryCell2", for: indexPath) as! CountryCell2
        cell.selectionStyle = .none
        
        if indexPath.section == 0{
            
            cell.country = self.countries[indexPath.row]
            /*cell.textLabel?.text = self.countries[indexPath.row].name.stringValue
            cell.textLabel?.adjustsFontSizeToFitWidth = true
            if let img = self.countries[indexPath.row].image_url, img != ""{

                cell.imageView?.setShowActivityIndicator(true)
                cell.imageView?.setIndicatorStyle(.gray)
                
                cell.imageView?.sd_setImage(with: URL(string : img)!, placeholderImage: nil, options: SDWebImageOptions.refreshCached)
            }*/
            
        }else if indexPath.section == 1{
            cell.imageView?.image = nil
            cell.textLabel?.text = "GO"
            cell.textLabel?.textAlignment = .center
            cell.textLabel?.font = UIFont.boldSystemFont(ofSize: 24)
        }
        
        return cell
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        if indexPath.section == 0{
            
            self.country = self.countries[indexPath.row]
            
            for i in tableView.visibleCells{
                i.accessoryType = .none
            }
            
            if let cell = tableView.cellForRow(at: indexPath){
                cell.tintColor = .white
                cell.accessoryType = .checkmark
                self.isSelected = true
                
                UIView.transition(with: tableView,
                                  duration: 0.35,
                                  options: .transitionCrossDissolve,
                                  animations: {
                                    self.goBtn.isHidden = false
                                    self.tableView.reloadData()
                                    self.tableViewHeight.constant = self.tableView.contentSize.height
                })
                
            }
        }else{
            
            let appDelegate = UIApplication.shared.delegate as! AppDelegate
            
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "SignupViewController") as! SignupViewController
            let nv = UINavigationController(rootViewController: vc)
            
            vc.selectedCountry = self.country
            vc.countries = self.countries
            
            appDelegate.window?.rootViewController = nv
        }
    }
    
    
    
    @IBAction func goToRegister(_ sender : UIButton){
        let appDelegate = UIApplication.shared.delegate as! AppDelegate
        
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "SignupViewController") as! SignupViewController
        let nv = UINavigationController(rootViewController: vc)
        
        vc.selectedCountry = self.country
        vc.countries = self.countries
        
        appDelegate.window?.rootViewController = nv
    }
    
    func imageWithImage(image:UIImage,scaledToSize newSize:CGSize) -> UIImage{
        UIGraphicsBeginImageContext(newSize)
        image.draw(in: CGRect(x: 0,y: 0,width: newSize.width,height: newSize.height))
        let newImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        return newImage!.withRenderingMode(.alwaysTemplate)
    }
    
    
    
    
}
