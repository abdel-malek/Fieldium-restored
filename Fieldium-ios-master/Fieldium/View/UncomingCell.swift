//
//  UncomingCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/10/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class UncomingCell: UITableViewCell {
    
    @IBOutlet weak var stack: UIStackView!
    @IBOutlet weak var stackV: UIView!
    @IBOutlet weak var img: UIImageView!
    @IBOutlet weak var nameLbl: UILabel!
    @IBOutlet weak var dateLbl: UILabel!
    @IBOutlet weak var timeLbl: UILabel!
    
    @IBOutlet weak var vv: UIView!
    
    @IBOutlet weak var noDataView: UIView!
    var delegate : HomeProtocol?
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        vv.layer.cornerRadius = 10

    }
    
    
    
    var book : Book?{
        didSet{
                        
            if let b = book{
                if let im = b.imgBack{
                    self.img.sd_setImage(with: URL.init(string: im), placeholderImage: #imageLiteral(resourceName: "logoPlaceholder.jpg"))
                }
                
                if let _ = b.start {
                    self.nameLbl.text = b.field_name
                    self.dateLbl.text = b.date
                    
                    self.timeLbl.text = Time.convert24To12(time: b.start!)
                    
                    self.stackV.isHidden = false

                    self.img.isHidden = false
                    self.stack.isHidden = false
                    
                }
            }
        }
    }
    
    var isRefreshed : Bool = false{
        didSet{
            if isRefreshed{
                if let _ = book {
                    noDataView.isHidden = true
                    vv.isHidden = false
                }else{
                    noDataView.isHidden = false
                    vv.isHidden = true
                }
            }else{
                noDataView.isHidden = true
                vv.isHidden = true
            }
        }
    }
    
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
                
        // Configure the view for the selected state
    }
    
}
