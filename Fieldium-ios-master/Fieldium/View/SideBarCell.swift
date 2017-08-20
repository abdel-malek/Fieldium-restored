//
//  SideBarCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/11/17.
//  Copyright © 2017 Yahya Tabba. All rights reserved.
//

import UIKit

class SideBarCell: UITableViewCell {

    @IBOutlet weak var btn : UIButton!

    
    
    var item : (UIImage,String,String) = (UIImage(),"",""){
        didSet{
            self.btn.setImage(item.0, for: .normal)
            self.btn.setTitle(item.1, for: .normal)
            
            if Provider.isArabic{
                self.btn.contentHorizontalAlignment = .right
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
