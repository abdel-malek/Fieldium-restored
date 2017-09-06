//
//  OfferProgressCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/7/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class OfferProgressCell2: UITableViewCell {
    
    var cnt = 0
    @IBOutlet weak var collectionView : UICollectionView!
    @IBOutlet weak var vv : UIView!
    
    @IBOutlet weak var progress: YLProgressBar!
    @IBOutlet weak var aspect: NSLayoutConstraint!
    @IBOutlet weak var lbl: UILabel!

    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
        self.collectionView.register(UINib.init(nibName: "PointCell2", bundle: nil), forCellWithReuseIdentifier: "cell")
        
        
        setupProgress()
        
        self.vv.layer.cornerRadius = 10

    }
    
    
    var offer : Offer! = nil{
        didSet{
            
            if aspect != nil{
                
                // design
                let c = (offer.set_of_minutes.intValue / 60) + 1
               let _ = self.aspect.setMultiplier(multiplier: CGFloat(c))
                self.layoutSubviews()
                self.cnt = c
                
                
                //offer value
                var val = ""
                if offer.voucher_type.intValue == 1{
                    val =  offer.voucher_value.stringValue + "%"
                }else{
                    let h = Int(offer.voucher_value.doubleValue / 60)
                    val = (h == 1) ? "\(h) \("Hour".localized)" : "\(h) \("Hours".localized)"
                }
                
                
                /*let days = (offer.valid_days.intValue == 1) ? "\(offer.valid_days.intValue) \("day".localized)" : "\(offer.valid_days.intValue) \("days".localized)"
                
                var tt = "book \(c - 1) hours to get \(val) for free!\nHurry up it only for \(days)"
                
                if Provider.isArabic{
                tt = "احجز \(c - 1) ساعات لتحصل على \(val) مجانية!\nأسرع بالحجز فالعرض متوفر لمدة \(days)"

                }*/
                
                var tt = self.offer.description_en

                if Provider.isArabic{
                    tt = self.offer.description_ar
                }
                
                self.lbl.text = tt
            }

            self.collectionView.reloadData()
        }
    }
    
    
    func setupProgress(){
        self.progress.setProgress(0.6, animated: false)
        
        progress.type = .flat;
        progress.progressTintColors = [UIColor.red];
        progress.hideStripes = true
        progress.hideTrack  = true
        progress.behavior  = .default
        progress.indicatorTextDisplayMode = .progress
        progress.indicatorTextLabel.text = "3 / 6 h"
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}

extension OfferProgressCell2 : UICollectionViewDelegate , UICollectionViewDataSource{
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return cnt
    }
    
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! PointCell2
        
        cell.txt = "\(indexPath.row + 1)"
        
        if indexPath.row == self.cnt - 1{
        
            if offer.voucher_type.intValue == 1{
                cell.txt =  offer.voucher_value.stringValue + "%"
            }else{
                let h = Int(offer.voucher_value.doubleValue / 60)
                cell.txt = (h == 1) ? "\(h) \("free hour".localized)" : "\(h) \("free hours".localized)"
            }

            
            cell.c = true
        }

        if (indexPath.row + 1) <= (self.offer.booked_hours.intValue / 60){
            cell.vvBack.backgroundColor = ThemeManager.shared.customBlue
        }else{
            cell.vvBack.backgroundColor = .white
        }
        
        return cell
    }
    
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {
        
        return CGSize(width: collectionView.frame.width / CGFloat(cnt), height: collectionView.frame.width / CGFloat(cnt))
    }
    
    
}
