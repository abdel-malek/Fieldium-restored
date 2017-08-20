//
//  PointCell2.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/7/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class PointCell2: UICollectionViewCell {
    
    @IBOutlet weak var vvBack: UIView!
    @IBOutlet weak var vvLine: UIView!
    @IBOutlet weak var wid: NSLayoutConstraint!
    @IBOutlet weak var lbl: UILabel!

    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        setup()
    }
    
    func setup(){
        DispatchQueue.main.async {
            self.vvBack.clipsToBounds = true
            self.vvBack.layer.masksToBounds = true
            self.vvBack.layer.cornerRadius = self.vvBack.frame.size.width / 2
        }
        
        self.vvBack.layer.borderColor = ThemeManager.shared.customBlue.cgColor
        self.vvBack.layer.borderWidth = 4
        self.lbl.textColor = ThemeManager.shared.customGrey
    }
    
    
    
    var txt : String! {
        didSet{
            self.lbl.text = txt
            vvBack.isHidden = false
            vvLine.isHidden = false
            
            wid.constant = self.frame.width / 2
            self.layoutSubviews()
            setup()
        }
    }
    
    
    
    var c : Bool!{
        didSet{
            wid.constant = self.frame.width
            self.layoutSubviews()
            setup()
        }
    }
    
    
}
