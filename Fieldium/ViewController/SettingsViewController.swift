//
//  SettingsViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/7/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class SettingsViewController: UIViewController {
    
    var alert = UIAlertController()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.title = "Settings".localized
    }
    
}

extension SettingsViewController : UITableViewDelegate,UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        
        cell.detailTextLabel?.textColor = UIColor.lightGray
        
        if indexPath.row == 0{
            cell.textLabel?.text = "Language".localized
            if let l = UserDefaults.standard.value(forKey: "lang") as? String{
                cell.detailTextLabel?.text = (l == "en") ? "English".localized : "Arabic".localized
            }else{
                cell.detailTextLabel?.text = "English".localized
            }
            
        }else if indexPath.row == 1{
            cell.textLabel?.text = "Country".localized
            cell.detailTextLabel?.text = "Dubai".localized

        }
        
        return cell
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        if indexPath.row == 0{
            
            alert = UIAlertController.init(title: "Select Languages".localized, message: "changeLang".localized, preferredStyle: .alert)
            
            alert.addAction(UIAlertAction.init(title: "English", style: .default, handler: { (ac) in
                    UserDefaults.standard.set("en", forKey: "lang")
                    UserDefaults.standard.setValue(["en","ar"], forKey: "AppleLanguages")

                self.goToHome()
            }))
            alert.addAction(UIAlertAction.init(title: "العربية", style: .default, handler: { (ac) in
                    UserDefaults.standard.set("ar", forKey: "lang")
                    UserDefaults.standard.setValue(["ar","en"], forKey: "AppleLanguages")

                self.goToHome()
            }))
            
            self.present(alert, animated: true, completion: {
                self.alert.view.superview?.isUserInteractionEnabled = true
                self.alert.view.superview?.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(self.alertControllerBackgroundTapped)))
            })
                    
        }else{
            self.performSegue(withIdentifier: "goToList", sender: nil)
        }
    }
    
    func alertControllerBackgroundTapped()
    {
        alert.dismiss(animated: true, completion: nil)
    }

    
    func goToHome(){
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "HomeViewController") as! HomeViewController
        
        self.navigationController?.pushViewController(vc, animated: true)

    }
    
}
