//
//  LastBookingCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/10/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class LastBookingCell: UITableViewCell {
    
    @IBOutlet weak var collectionView: UICollectionView!
    
    @IBOutlet weak var lbl: UILabel!
    var delegate : HomeProtocol?
    var homeVC : HomeViewController!
    
    @IBOutlet weak var vv: UIView!
    
    @IBOutlet weak var noDataView: UIView!
    
    var type = 0 // if 0 bookings else vouchers
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        collectionView.delegate = self
        collectionView.dataSource = self
        
        collectionView.register(UINib.init(nibName: "imageLastBookingCell", bundle: nil), forCellWithReuseIdentifier: "cell")
        
        collectionView.register(UINib.init(nibName: "VoucherCircleCell", bundle: nil), forCellWithReuseIdentifier: "cell2")

        
        self.vv.layer.cornerRadius = 10
    }
    
    
    var bookings : [Book] = [Book](){
        didSet{
            self.lbl.text = "Previous Bookings".localized
            collectionView.reloadData()
        }
    }
    
    var vouchers : [Voucher] = [Voucher](){
        didSet{
            self.lbl.text = "My Vouchers".localized
            collectionView.reloadData()
        }
    }
    
    var isRefreshed : Bool = false{
        didSet{
            if isRefreshed{
                if self.bookings.count > 0 {
                    noDataView.isHidden = true
                    vv.isHidden = false
                }else{
                    noDataView.isHidden = false
                    vv.isHidden = true
                }
            }else{
                noDataView.isHidden = false
                vv.isHidden = true
            }
        }
    }
    
    var isRefreshed2 : Bool = false{
        didSet{
            if isRefreshed2{
                if self.vouchers.count > 0 {
                    noDataView.isHidden = true
                    vv.isHidden = false
                }else{
                    noDataView.isHidden = false
                    vv.isHidden = true
                }
            }else{
                noDataView.isHidden = false
                vv.isHidden = true
            }
        }
    }
    
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}

extension LastBookingCell : UICollectionViewDelegate, UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return (self.type == 0) ? self.bookings.count : self.vouchers.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        
        if self.type == 0{

        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! imageLastBookingCell
        
            cell.logo = self.bookings[indexPath.row].logo_url
            
            return cell

        }else{
            let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell2", for: indexPath) as! VoucherCircleCell

            cell.v = self.vouchers[indexPath.row]
            
            return cell

        }
        
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        //        self.delegate?.goToBook(book: self.bookings[indexPath.row])
        
        if type == 0{
            self.delegate?.goToFields2(book: self.bookings[indexPath.row])
        }else{
            let main = UIStoryboard.init(name: "Main", bundle: nil)
            let vc = main.instantiateViewController(withIdentifier: "VouchersViewController") as! VouchersViewController
            vc.selected = self.vouchers[indexPath.row]
            
            self.homeVC.navigationController?.pushViewController(vc, animated: true)
        }
        
    }
    
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {
        
        return CGSize(width: collectionView.frame.width / 3 - 10, height: collectionView.frame.width / 3 - 10)
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, insetForSectionAtIndex section: Int) -> UIEdgeInsets {
        
        if (self.bookings.count == 1 && self.type == 0) || (self.vouchers.count == 1 && self.type == 1){
            if Provider.isArabic{
                return UIEdgeInsets(top: 7, left: 7, bottom: 0, right: (collectionView.frame.width / 2) - (collectionView.frame.width / 3 - 10) / 2)
            }else{
                return UIEdgeInsets(top: 7, left: (collectionView.frame.width / 2) - (collectionView.frame.width / 3 - 10) / 2, bottom: 0, right: 7)
            }
        }
        
        return UIEdgeInsets.init(top: 7, left: 7, bottom: 0, right: 7)
    }
    
    
    
    
    
}
