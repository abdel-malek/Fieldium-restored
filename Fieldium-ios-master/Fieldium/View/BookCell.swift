//
//  BookCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/3/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class BookCell: UITableViewCell {
    
    @IBOutlet weak var nameLbl: UILabel!
    @IBOutlet weak var detailsLbl: UILabel!
    @IBOutlet weak var dateLbl: UILabel!
    @IBOutlet weak var timeLbl: UILabel!
    @IBOutlet weak var durationLbl: UILabel!
    @IBOutlet weak var refLbl: UILabel!
    @IBOutlet weak var imgLogo: UIImageView!
    @IBOutlet weak var vv: UIView!
    
    @IBOutlet weak var stateLbl: UILabel!
    
    
    var delegate : BookProtocol?
    
    var book : Book?{
        didSet{
            
            if let b = book{
                if let im = b.logo_url{
                    imgLogo.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
                }
                
                if let cc = b.company_name{
                    nameLbl.text = cc

                }else{
                    nameLbl.text = b.field_name
                }
                
                detailsLbl.text = b.address
                dateLbl.text = b.date
                timeLbl.text = Time.convert24To12(time: b.start)
                durationLbl.text = "\(b.duration / 60)h \(b.duration % 60)m"
                refLbl.text = "\("Ref".localized).# " + b.booking_id.toString
                                
                let t2 = Time.DateToString(date: Date())
                                
                var d1 = Date()
                if let dd1 = Time.StringToDate(dateString: b.date) {
                    d1 = dd1
                }else{
                    var y = b.date.components(separatedBy: "-")
                    y[2] = (Int(y[2])! - 1).toString
                    print(y)
                    d1 = Time.StringToDate(dateString: "\(y[0])-\(y[1])-\(y[2])")!.add(minutes: 120)
                }

                
                
                let d2 = Time.StringToDate(dateString: t2)!
                
                if d1 >= d2 {
                    if d1 == d2{
                        let dateFormatter = DateFormatter()
                        dateFormatter.locale =  NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
                        dateFormatter.dateFormat = "HH:mm:ss"
                        let u = dateFormatter.string(from: Date())
                        let arr = u.components(separatedBy: ":")
                        let thisHour = arr[0]
                        
                        if b.start.components(separatedBy: ":").first! > thisHour{
                            vv.backgroundColor = ThemeManager.shared.customBlue
                        }else{
                            vv.backgroundColor = UIColor.lightGray
                        }
                    }

                    else{
                    vv.backgroundColor = ThemeManager.shared.customBlue
                    }
                    
                    }else{
                    vv.backgroundColor = UIColor.lightGray
                }

                
                if let state = b.state_id{
                    
                    if Provider.isArabic{
                        stateLbl.textAlignment = .left
                    }
                    
                    switch state {
                    case 1:
                        self.stateLbl.text = "Pending".localized
                        break
                    case 2:
                        self.stateLbl.text = "Approved".localized
                        break
                    case 3:
                        self.stateLbl.text = "Declined".localized
                        break
                    case 4:
                        self.stateLbl.text = "Canceled".localized
                        break
                    default:
                        break
                    }
                }
                
            }
        }
    }
    
    
    var not : Notificat?{
        didSet{
            
            if let b = not{
                if let im = b.logo_url{
                    imgLogo.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
                }
                

                    nameLbl.text = b.field_name
                    detailsLbl.text = b.address
                    dateLbl.text = b.date
                    timeLbl.text = Time.convert24To12(time: b.start)
                    durationLbl.text = "\(Int(b.duration)) \("Hours".localized)"
                    refLbl.text =  b.notification_time
                
                    vv.backgroundColor = ThemeManager.shared.customBlue
                    
                    self.stateLbl.text = ""
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
