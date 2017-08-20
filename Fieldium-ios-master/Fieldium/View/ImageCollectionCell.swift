//
//  ImageCollectionCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/22/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class ImageCollectionCell: UICollectionViewCell {
    
    @IBOutlet weak var img: UIImageView!
    @IBOutlet weak var nameLbl: UILabel!
    
    
    var imgUrl : String? {
        didSet{
            if let im = imgUrl{
                img.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
            }
        }
    }
    
    
    var amenity : Amenity? {
        didSet{
            if let im = amenity?.image_url{
                img.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
            }
            nameLbl.text = amenity?.name
        }
    }
    
    
    var game : Game? {
        didSet{
            if let im = game?.image_url{
                img.sd_setImage(with: URL.init(string: im),placeholderImage: UIImage(named: logoPlaceholder))
            }
            nameLbl.text = game?.name
        }
    }
    
    
}
