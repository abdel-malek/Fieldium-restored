//
//  CountryCell2.swift
//  Fieldium
//
//  Created by IOS Developer on 9/6/17.
//  Copyright © 2017 Yahya Tabba. All rights reserved.
//

import UIKit
import SDWebImage

class CountryCell2: UITableViewCell {
    
    @IBOutlet weak var img : UIImageView!
    @IBOutlet weak var lbl : UILabel!
    
    
    var country : Country!{
        didSet{
            self.lbl.text = country.name.stringValue
            
            if let im = country.image_url, im != ""{
                
                self.img.setShowActivityIndicator(true)
                self.img.setIndicatorStyle(.gray)
                
                self.img.sd_setImage(with: URL(string : im)!, placeholderImage: nil, options: SDWebImageOptions.refreshCached)
            }
        }
    }
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}
