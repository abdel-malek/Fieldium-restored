//
//  imageLastBookingCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/12/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class imageLastBookingCell: UICollectionViewCell {
    
    @IBOutlet weak var vv: UIView!
    @IBOutlet weak var imgLogo: UIImageView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
    }
    
    var logo : String? {
        didSet{
            if let im = self.logo{
                self.imgLogo.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
            }
            
            self.vv.layer.borderColor = ThemeManager.shared.customRed.cgColor
            self.vv.layer.borderWidth = 1
            self.vv.backgroundColor = UIColor.clear
            
            DispatchQueue.main.async {
                self.vv.layer.cornerRadius = self.vv.bounds.size.width / 2.0
                self.vv.clipsToBounds = true
            }

        }
    }
    
    
    var urlString : String?{
        didSet{
            self.backgroundColor = .clear
            self.vv.backgroundColor = .clear
            if let u = urlString{
                print("URLSTRING: \(u)")
                self.imgLogo.sd_setImage(with: URL.init(string: u)!,placeholderImage: #imageLiteral(resourceName: "logoPlaceholder2.png"))
            }
        }
    }
    
    
}
