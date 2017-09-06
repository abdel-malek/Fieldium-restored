//
//  BookBaseViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 9/6/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class BookBaseViewController: BaseViewController {

    @IBOutlet weak var vvContent : UIView!
    @IBOutlet weak var footerView: UIView!

    
//    var field = Field()
//    
//    var book = Book()
//    var id = 0 // show book or book now, 0 for book
    
    let vcBook : BookViewController = {
        let vc = UIStoryboard(name: "Main", bundle: nil).instantiateViewController(withIdentifier: "BookViewController") as! BookViewController
        
        return vc
    }()

    override func viewDidLoad() {
        super.viewDidLoad()


        self.vcBook.view.frame = self.vvContent.bounds
        self.vvContent.addSubview(vcBook.view)
        self.addChildViewController(vcBook)

        self.title = self.vcBook.title
        
        if vcBook.id == 1 {self.footerView.isHidden = true}
    
    }
    
    @IBAction func confirmAction(_ sender: Any) {
        self.vcBook.confirmAction(sender)
    }

    
}
