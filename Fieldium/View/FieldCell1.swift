//
//  FieldCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/20/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

protocol FieldProtocol {
    func goToFieldDetails(field : Field)
}

class FieldCell1: UITableViewCell, UICollectionViewDelegate,UICollectionViewDataSource {

//    @IBOutlet weak var companyLbl: UILabel!
    @IBOutlet weak var nameLbl: UILabel!

    @IBOutlet weak var costLbl: UILabel!
    @IBOutlet weak var imgFace: UIImageView!
    
    @IBOutlet weak var playersCount: UILabel!
    @IBOutlet weak var collectionView: UICollectionView!
    
    
    var delegate : FieldProtocol?
    
    
    var field : Field = Field() {
        didSet{
            let tap = UITapGestureRecognizer(target: self, action: #selector(self.goToFieldDetails))
            tap.numberOfTapsRequired = 1
            self.contentView.addGestureRecognizer(tap)
            
            nameLbl.text = field.name
//            companyLbl.text = field.company_name
            costLbl.text = "\(field.hour_rate.intValue) \(Provider.currancy())"
            playersCount.text = "\(field.max_capacity!) \("Players".localized)"
            
            collectionView.reloadData()
        }
    }
    
    
    func goToFieldDetails(){
        delegate?.goToFieldDetails(field: field)
    }
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.field.imgs.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! ImageCollectionCell
        
        cell.imgUrl = self.field.imgs[indexPath.row].image_url
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {
        
        return CGSize(width: collectionView.frame.width, height: collectionView.frame.height)
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()

        collectionView.dataSource = self
        collectionView.delegate = self
        
        collectionView.isPagingEnabled = true
        
        collectionView.register(UINib(nibName: "ImageCollectionCell", bundle: nil), forCellWithReuseIdentifier: "cell")
        
        collectionView.reloadData()

    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
