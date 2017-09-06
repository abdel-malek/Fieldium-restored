//
//  VoucherCircleCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/14/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class VoucherCircleCell: UICollectionViewCell {

    @IBOutlet weak var vv: UIViewX!
    @IBOutlet weak var lbl: UILabel!
    @IBOutlet weak var lbl2: UILabel!

    
    @IBOutlet weak var img: UIImageView!

    
    var v : Voucher! = nil {
        didSet{
            self.lbl.isHidden = false
            self.img.isHidden = true
            
            self.lbl2.text = "\(v.voucher!)\n\(v.expiry_date!)"
            
            
            if v.type.intValue == 1{
                self.lbl.text = v.value.stringValue + "%"
            }else{
//                let imageAttachment =  NSTextAttachment()
//                imageAttachment.image = #imageLiteral(resourceName: "ic_about")
//                
//                let imageOffsetY:CGFloat = -5.0;
//                imageAttachment.bounds = CGRect(x: 0, y: imageOffsetY, width: imageAttachment.image!.size.width, height: imageAttachment.image!.size.height)
//
//                let attachmentString = NSAttributedString(attachment: imageAttachment)
//
//                let completeText = NSMutableAttributedString(string: "")
//                let fullString = NSMutableAttributedString(string: "\(Int(v.value.doubleValue / 60))")
//                completeText.append(fullString)
//
//                completeText.append(attachmentString)
//
//                self.lbl.textAlignment = .center;
//                self.lbl.attributedText = completeText;
                
                
                let value = "\(Double(v.value.intValue / 60) + Double(Double(v.value.intValue % 60) / 60.0)) \("free hours".localized)"
                
                
                self.lbl.text = value
                //"\(Int(v.value.doubleValue / 60)) \("free hours".localized)"
                
            }
        }
    }
    
    var new : Bool = false{
        didSet{
            if new{
                self.lbl.isHidden = true
                self.img.isHidden = false

                self.img.image = #imageLiteral(resourceName: "plus")
                self.lbl2.text = "Type code"

            }
        }
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()

        DispatchQueue.main.async {
            self.vv.clipsToBounds = true
//            self.vv.cornerRadius = self.vv.frame.height / 2
        }

    }

}
