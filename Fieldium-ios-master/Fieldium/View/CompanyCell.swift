//
//  CompanyCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/21/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import SDWebImage


protocol CompanyProtocol {
    func goToFields(company : Company)
    func goToMap(company : Company)
}

class CompanyCell: UITableViewCell {

    @IBOutlet weak var img: UIImageView!
    @IBOutlet weak var nameLbl: UILabel!
    @IBOutlet weak var addressLbl: UILabel!
    @IBOutlet weak var nrField: UILabel!
    
    @IBOutlet weak var imgLogo: UIImageView!
    
    @IBOutlet weak var startsFromLbl: UILabel!
    
    var delegate : CompanyProtocol?
    
    var company : Company = Company(){
        didSet{
            
            nameLbl.text = (company.name != nil) ? company.name! : ""
            let areaNm = (company.area_name != nil) ? company.area_name! : ""
            let addressNm = (company.address != nil) ? company.address! : ""
            
            addressLbl.text = "\(areaNm) - \(addressNm)"
            
            nrField.text =  "\(company.fields_number!) \("Fields".localized)"
            startsFromLbl.text = (company.starts_from != nil) ? "\("Starts from".localized)\n\(company.starts_from!) \(Provider.currancy())" : ""
            
            
            if let im = company.logo_url{
                imgLogo.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
            }

            if let im = company.image_url{
                img.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
            }
        }
    }
    
    @IBAction func mapAction(_ sender: Any) {
        delegate?.goToMap(company: self.company)
    }
    
    
    
    func updateInterface(){
                let layer = CAShapeLayer()
                layer.path = UIBezierPath(roundedRect: CGRect(x: 0, y: self.bounds.height, width: self.bounds.width, height: 3), cornerRadius: 0).cgPath
                layer.fillColor = ThemeManager.shared.customRed.cgColor
                self.contentView.layer.insertSublayer(layer, at: 0)
    }
    
    
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
    }
    
    
    
    override func select(_ sender: Any?) {
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
    
    }
    
}
