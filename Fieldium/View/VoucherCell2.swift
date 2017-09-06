//
//  VoucherCell2.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/13/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import FoldingCell

class VoucherCell2: FoldingCell {
    
    //    @IBOutlet weak var closeNumberLabel: UILabel!
    //    @IBOutlet weak var openNumberLabel: UILabel!
    
    var VouchersVC : VouchersViewController!

    @IBOutlet weak var valueCircle: UIView!
//    @IBOutlet weak var valueCircle2: UIView!
    
    @IBOutlet weak var collectionViewCompanies: UICollectionView!
    @IBOutlet weak var collectionViewGames: UICollectionView!
    
    @IBOutlet weak var daysLbl1: UILabel!
//    @IBOutlet weak var daysLbl2: UILabel!
    @IBOutlet weak var daysLbl3: UILabel!
    
    
    @IBOutlet weak var Code1: UILabel!
//    @IBOutlet weak var Code2: UILabel!
    @IBOutlet weak var Code3: UILabel!
    
    @IBOutlet weak var value1: UILabel!
//    @IBOutlet weak var value2: UILabel!
    @IBOutlet weak var value3: UILabel!
    
    @IBOutlet weak var fromHeight: NSLayoutConstraint!
    @IBOutlet weak var fromTopHeight: NSLayoutConstraint!
    
    @IBOutlet weak var toHeight: NSLayoutConstraint!
    @IBOutlet weak var toTopHeight: NSLayoutConstraint!
    
    @IBOutlet weak var from_hour: UILabel!
    @IBOutlet weak var to_hour: UILabel!
    @IBOutlet weak var start_date: UILabel!
    @IBOutlet weak var expiry_date: UILabel!
    
    @IBOutlet weak var descriptionLbl: UILabel!
    
    @IBOutlet weak var logo1: UIImageView!
    @IBOutlet weak var logo2: UIImageView!
    
    @IBOutlet weak var heightCompanies: NSLayoutConstraint!
    var timer : Timer = Timer()
    var x1 = -1
    var x2 = 0
    
    
    
    var number: Int = 0 {
        didSet {
            //            closeNumberLabel.text = String(number)
            //            openNumberLabel.text = String(number)
            
        }
    }
    
    override func awakeFromNib() {
        
        self.foregroundView.layer.cornerRadius = 10
        self.foregroundView.layer.masksToBounds = true
        
        DispatchQueue.main.async {
            self.valueCircle.clipsToBounds = true
            self.valueCircle.layer.cornerRadius = self.valueCircle.frame.size.width / 2
            
//            self.valueCircle2.clipsToBounds = true
//            self.valueCircle2.layer.cornerRadius = self.valueCircle2.frame.size.width / 2
            
            self.daysLbl3.clipsToBounds = true
            self.daysLbl3.layer.cornerRadius = self.daysLbl3.frame.size.width / 2
        }
        
        self.setupCollectionViews()
        
        let tapLong = UILongPressGestureRecognizer.init(target: self, action: #selector(copyCode))
        tapLong.minimumPressDuration = 1
        self.logo2.isUserInteractionEnabled = true
        self.logo2.addGestureRecognizer(tapLong)
        
//        self.timer.invalidate()
//        self.timer = Timer.scheduledTimer(timeInterval: 2, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
//        self.timer.fire()
        
        
        super.awakeFromNib()
    }
    
    
    func copyCode(){
        if let v  = self.voucher{
            print("COPY")
            UIPasteboard.general.string = v.voucher!
            self.VouchersVC.showErrorMessage(text: "Voucher code copy to clipboard")
        }
    }
    
    
    func setupCollectionViews(){
        
        self.collectionViewCompanies.delegate = self
        self.collectionViewCompanies.dataSource = self
        self.collectionViewCompanies.register(UINib.init(nibName: "imageLastBookingCell", bundle: nil), forCellWithReuseIdentifier: "cell")
        
        self.collectionViewGames.delegate = self
        self.collectionViewGames.dataSource = self
        self.collectionViewGames.register(UINib.init(nibName: "imageLastBookingCell", bundle: nil), forCellWithReuseIdentifier: "cell")
        
    }
    
    
    
    var voucher : Voucher?{
        didSet{
            if let v = voucher{
                
                let code  = "\("Voucher Code".localized): \(v.voucher!)"
                self.Code1.text = code
//                self.Code2.text = code
                self.Code3.text = code
                
                
                let daysCnt = Date.init(dateString: "\(v.expiry_date!)", format: "yyyy-MM-dd").interval(ofComponent: Calendar.Component.day, fromDate: Date()) + 1
                let days = "\(daysCnt) \("Days left".localized)"
                self.daysLbl1.text = days
//                self.daysLbl2.text = days
                self.daysLbl3.text = "\(daysCnt)"
                
                
                
                let value = (v.type.intValue == 1) ? "\(v.value.stringValue)%" : "\(Double(v.value.intValue / 60) + Double(Double(v.value.intValue % 60) / 60.0)) \("free hours".localized)"
                self.value1.text = value
//                self.value2.text = value
                self.value3.text = value
                
//                self.from_hour.text = (v.from_hour == nil || v.from_hour == "") ? nil : v.from_hour
//                self.to_hour.text = (v.to_hour == nil || v.to_hour == "") ? nil : v.to_hour
                
                if v.from_hour == nil || v.from_hour == "" {
                    self.fromHeight.constant = 0
                    self.fromTopHeight.constant = 0
                    self.from_hour.text = nil
                }else{
                    self.from_hour.text = Time.convert24To12(time:  v.from_hour!)
                }
                
                if v.to_hour == nil || v.to_hour == "" {
                    self.toHeight.constant = 0
                    self.toTopHeight.constant = 0
                    self.to_hour.text = nil
                }else{
                    self.to_hour.text = Time.convert24To12(time:  v.to_hour!)
                }
                
                self.start_date.text = v.start_date.components(separatedBy: " ")[0]
                self.expiry_date.text = v.expiry_date
                self.descriptionLbl.text = Provider.isArabic ? v.description_ar : v.description_en
                
                if v.all_games.intValue == 1{
                    self.logo1.image = #imageLiteral(resourceName: "logo.png")
                    self.logo2.image = #imageLiteral(resourceName: "logo.png")
                }else{
                    if let im = v.games.first, let ul = im.image_url{
                        self.logo1.sd_setImage(with: URL.init(string: ul)!)
                        self.logo2.sd_setImage(with: URL.init(string: ul)!)
                    }
                }
                
                if v.companies.count == 0 {
                    heightCompanies.constant = 0
                }
                
                self.collectionViewCompanies.reloadData()
            }
        }
    }
    
    
    override func animationDuration(_ itemIndex: NSInteger, type: FoldingCell.AnimationType) -> TimeInterval {
        let durations = [0.26, 0.2, 0.2]
        return durations[itemIndex]
    }
    
}

// MARK: - Actions ⚡️
extension VoucherCell2 {
    
    @IBAction func buttonHandler(_ sender: AnyObject) {
        print("tap")
    }
    
}


extension VoucherCell2 : UICollectionViewDelegate, UICollectionViewDataSource{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        
        if let v = self.voucher{
            print("OKOKOK \(v.companies.count)")
            return v.companies.count
        }else{
            return 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! imageLastBookingCell
        
        if let v = self.voucher{
            cell.urlString = v.companies[indexPath.row].logo_url
        }
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {
        
        if collectionView == collectionViewCompanies{
            return CGSize(width: collectionView.frame.height , height: collectionView.frame.height)
        }else{
            return CGSize(width: collectionView.frame.width , height: collectionView.frame.height)
        }
    }
    
    
    //Delegate With ScrollView (scroll item photos)
//    func scrollViewDidScroll(_ scrollView: UIScrollView) {
//        if scrollView == self.collectionViewGames{
//            let pp = round(self.collectionViewGames.contentOffset.x / self.collectionViewGames.frame.size.width)
//            x2 = Int(pp)
//            print("X2 = \(x2)")
//            
//            self.timer.invalidate()
//            self.timer = Timer.scheduledTimer(timeInterval: 2, target: self, selector: #selector(self.rotate), userInfo: nil, repeats: true)
//        }
//    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if collectionView == collectionViewCompanies{
            let main = UIStoryboard.init(name: "Main", bundle: nil)
            let vc = main.instantiateViewController(withIdentifier: "FieldsViewController") as! FieldsViewController
            
            vc.id = 0
            let c = Company()
            c.company_id = 2
            c.name = "Aktiv Nation"
            vc.company = c
            vc.fromSearch = true
            
            self.VouchersVC.navigationController?.pushViewController(vc, animated: true)
        }
    }
    
}
