//
//  SuccessViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/13/17.
//  Copyright © 2017 Yahya Tabba. All rights reserved.
//

import UIKit

class SuccessViewController: BaseViewController {

    @IBOutlet weak var lbl : UILabel!
    
    var text : String!
    var parentBook : BookViewController!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        self.lbl.text = text
    
        let tap : UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.showBookings))
        self.view.addGestureRecognizer(tap)
        
        let bb = UIBarButtonItem.init(barButtonSystemItem: .stop , target: self, action: #selector(self.close))
        
        self.navigationItem.leftBarButtonItem = bb
    }
    
    func close(){
        
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "HomeViewController") as! HomeViewController
        let nv = UINavigationController.init(rootViewController: vc)
        let del = UIApplication.shared.delegate as! AppDelegate
        
        del.window?.rootViewController = nv
    }
    
    
    func showBookings()
    {
        self.dismiss(animated: false, completion: nil)
        self.parentBook.showBookings()
    }


}
