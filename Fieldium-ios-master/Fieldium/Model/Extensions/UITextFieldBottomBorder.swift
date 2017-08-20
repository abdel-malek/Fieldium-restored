//
//  UITextFieldBottomBorder.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/18/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

extension UITextField {
    func setBottomBorder(color : UIColor)
    {
        let border = CALayer()
        let width = CGFloat(1.0)
        border.borderColor = color.cgColor
        
        border.frame = CGRect(x: 0, y: self.bounds.size.height - width, width:  self.bounds.size.width, height: self.frame.size.height)
        
        border.borderWidth = width
//        self.layer.addSublayer(border)
        
        self.layer.insertSublayer(border, below:self.layer)
        self.layer.masksToBounds = true
    }
    
    func setBottomBorder2(borderColor: UIColor)
    {
        
        self.borderStyle = UITextBorderStyle.none
        self.backgroundColor = UIColor.clear
        let width = 1.0
        
        let borderLine = UIView()
        borderLine.frame = CGRect(x: 0, y: Double(self.frame.height) - width, width: Double(self.frame.width), height: width)
        
        borderLine.backgroundColor = borderColor
        self.addSubview(borderLine)
    }
}
