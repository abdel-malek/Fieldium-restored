//
//  ListViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/7/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class ListViewController: UIViewController {

    var list : [(String,UIImage)] = {
        var l = [(String,UIImage)]()
        
        l.append(("Lebanon",#imageLiteral(resourceName: "c2.png")))
        l.append(("United Arab Emirates",#imageLiteral(resourceName: "c1.png")))
        
        return l
    }()

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

}

extension ListViewController : UITableViewDataSource, UITableViewDelegate{
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
            return list.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath)
        cell.selectionStyle = .none
        
        
        if indexPath.row == 0{
            cell.accessoryType = .checkmark
        }
            cell.textLabel?.text = list[indexPath.row].0
            cell.imageView?.image = list[indexPath.row].1
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "HomeViewController") as! HomeViewController
        
        self.navigationController?.pushViewController(vc, animated: true)
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 50
    }
    
    
    
}
