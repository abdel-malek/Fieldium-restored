//
//  ProfileCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/1/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class ProfileCell: UITableViewCell {
    
    var index : IndexPath!
    
    @IBOutlet weak var tf : UITextField!
    
    @IBOutlet weak var lbl : UILabel!
    
    
    let me = User.getCurrentUser()
    
    var type : Int = 9{
        didSet{
            switch type {
            case 0:
                lbl.text = "Name".localized
                tf.placeholder = "Name".localized
                tf.text = me.name
                break
            case 1:
                lbl.text = "Email".localized
                tf.placeholder = "Email".localized
                tf.text = me.email
                tf.keyboardType = .emailAddress
                break
            case 2:
                lbl.text = "Address".localized
                tf.placeholder = "Address".localized
                tf.text = me.address
                break
            default:
                tf.text = ""
                break
            }
        }
    }
    
    
    func setupGame(_ game : String){
        self.lbl.text = game
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
