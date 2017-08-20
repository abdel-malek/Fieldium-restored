//
//  VoucherCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/13/17.
//  Copyright © 2017 Yahya Tabba. All rights reserved.
//

import UIKit
import FoldingCell

class VoucherCell: FoldingCell {

//    @IBOutlet weak var closeNumberLabel: UILabel!
//    @IBOutlet weak var openNumberLabel: UILabel!
    
    var VouchersVC : VouchersViewController!
    
    @IBOutlet weak var valueCircle: UIView!
    @IBOutlet weak var valueCircle2: UIView!

    @IBOutlet weak var tableViewCompanies: UITableView!

    
    @IBOutlet weak var daysLbl1: UILabel!
    @IBOutlet weak var daysLbl2: UILabel!

    
    @IBOutlet weak var Code1: UILabel!
    @IBOutlet weak var Code2: UILabel!

    @IBOutlet weak var value1: UILabel!
    @IBOutlet weak var value2: UILabel!

    @IBOutlet weak var fromHeight: NSLayoutConstraint!
    @IBOutlet weak var fromTopHeight: NSLayoutConstraint!

    @IBOutlet weak var toHeight: NSLayoutConstraint!
    @IBOutlet weak var toTopHeight: NSLayoutConstraint!

    @IBOutlet weak var from_hour: UILabel!
    @IBOutlet weak var to_hour: UILabel!
    @IBOutlet weak var start_date: UILabel!
    @IBOutlet weak var expiry_date: UILabel!

    @IBOutlet weak var descriptionLbl: UILabel!

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
            
            self.valueCircle2.clipsToBounds = true
            self.valueCircle2.layer.cornerRadius = self.valueCircle2.frame.size.width / 2
        }
        
        self.setupTableViews()
        

        
        super.awakeFromNib()
    }
    
    
    func setupTableViews(){
        
        tableViewCompanies.delegate = self
        tableViewCompanies.dataSource = self
//        tableViewCompanies.register(UITableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: "cell").classForCoder, forCellReuseIdentifier: "cell")
        
    }
    
    
    
    var voucher : Voucher?{
        didSet{
            if let v = voucher{
                
                let code  = "Code: \(v.voucher!)"
                self.Code1.text = code
                self.Code2.text = code

                let daysCnt = Date.init(dateString: "\(v.expiry_date!)", format: "yyyy-MM-dd").interval(ofComponent: Calendar.Component.day, fromDate: Date())
                let days = "\(daysCnt) Days left"
                self.daysLbl1.text = days
                self.daysLbl2.text = days
                
                let value = (v.type.intValue == 1) ? "\(v.value.stringValue)%" : "\(v.value.intValue / 60) hours"
                self.value1.text = value
                self.value2.text = value

                self.from_hour.text = (v.from_hour == nil || v.from_hour == "") ? nil : v.from_hour
                self.to_hour.text = (v.to_hour == nil || v.to_hour == "") ? nil : v.to_hour

                if v.from_hour == nil || v.from_hour == "" {
                    self.fromHeight.constant = 0
                    self.fromTopHeight.constant = 0
                }
                if v.to_hour == nil || v.to_hour == nil {
                    self.toHeight.constant = 0
                    self.toTopHeight.constant = 0
                }

                self.start_date.text = v.start_date
                self.expiry_date.text = v.expiry_date
                
                self.descriptionLbl.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cui Tubuli nomen odio non est? Isto modo."
                //v.description_en
                
            }
        }
    }
    
    
    override func animationDuration(_ itemIndex: NSInteger, type: FoldingCell.AnimationType) -> TimeInterval {
        let durations = [0.26, 0.2, 0.2]
        return durations[itemIndex]
    }
    
}

// MARK: - Actions ⚡️
extension VoucherCell {
    
    @IBAction func buttonHandler(_ sender: AnyObject) {
        print("tap")
    }
    
}


extension VoucherCell : UITableViewDelegate, UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 10
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        var cell:UITableViewCell? = tableView.dequeueReusableCell(withIdentifier: "cell")
        
        if (cell == nil)
        {
            cell = UITableViewCell(style: UITableViewCellStyle.subtitle,
                                   reuseIdentifier: reuseIdentifier)
        }
        
        cell!.backgroundColor = .clear
        cell!.contentView.backgroundColor = .clear
        
        cell!.imageView?.image = #imageLiteral(resourceName: "logo.png")
        cell!.imageView?.contentMode = .scaleAspectFit
        
        cell!.textLabel?.text = "Company \(indexPath.row + 1)"
        cell!.textLabel?.textColor = .white
        
        cell!.detailTextLabel?.text = "Dubai"
        cell!.detailTextLabel?.textColor = .white
        
        return cell!
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
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
