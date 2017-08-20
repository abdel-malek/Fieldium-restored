//
//  CustomView.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/8/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

@IBDesignable class CustomView: UIView {

    @IBInspectable var borderColor : UIColor = UIColor.white{
        didSet{
            self.layer.borderColor = borderColor.cgColor
        }
    }
    
    @IBInspectable var borderWidth : CGFloat = 0.0{
        didSet{
            self.layer.borderWidth = borderWidth
        }
    }

    @IBInspectable var cornerRadius : CGFloat = 0.0{
        didSet{
            self.clipsToBounds = true
            self.layer.cornerRadius =  cornerRadius
        }
    }
    
    
}
