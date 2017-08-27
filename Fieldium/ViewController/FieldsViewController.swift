//
//  FieldsViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/20/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit


class FieldsViewController: BaseViewController {
    
    @IBOutlet weak var tableView: UITableView!
    
    var id = 0 // if 0 fields_by_company else fields_by_search
    
    var company = Company()
    var fields = [Field]()
    var txt = "Fields".localized
    
    var fromBook = false
    var fromSearch = false
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initView()
    }
    
    
    func initView(){
        
        
        if id == 0{
            self.tableView.isHidden = true
            self.title = self.company.name

            self.showLoading()
            
            if fromSearch{
                
                Provider.shared.get_fields_by_company(company_id: self.company.company_id!)
                
            }
            else if (Time.selected == nil && Provider.game_type == nil) || fromBook  {
                Provider.shared.get_fields_by_company(company_id: self.company.company_id!)
            }else{
                Provider.shared.get_by_company_with_timing(company_id: self.company.company_id!)
                
                if let timee = Time.selected{
                    if timee.duration != nil {
                        self.id = 1
                    }
                }
                
            }
            
        }else{
            self.title = "Fields".localized
            self.fields = Provider.shared.fields_by_search
            self.tableView.reloadData()
        }
        
        self.tableView.register(UINib.init(nibName: "FieldCell1", bundle: nil), forCellReuseIdentifier: "cell")
        self.tableView.allowsSelection = true
    }
    
    override func viewWillAppear(_ animated: Bool) {
        notic.addObserver(self, selector: #selector(self.update_all_fields), name: _update_all_fields.not, object: nil)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        notic.removeObserver(self, name: _update_all_fields.not, object: nil)
    }
    
    
    func update_all_fields() {
        self.hideLoading()
        self.fields = Provider.shared.fields_by_company
        self.tableView.reloadData()
        self.tableView.isHidden = false
    }
    
    
}


extension FieldsViewController : UITableViewDelegate,UITableViewDataSource, FieldProtocol {
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.fields.count
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        if self.fields.count == 0{
            let label = UILabel()
            label.backgroundColor = UIColor.clear
            
            label.font = ThemeManager.shared.font
            label.font.withSize(22)
            
            label.text = "No Fields!".localized
            label.textAlignment = .center;
            label.textColor = ThemeManager.shared.customBlue
            label.numberOfLines = 0
            label.sizeToFit()
            
            return label
        }
        
        return UIView.init(frame: CGRect.zero)
    }
    
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if self.fields.count == 0 {
            return 50
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! FieldCell1
        
        cell.delegate = self
        cell.selectionStyle = .none
        cell.field = self.fields[indexPath.row]
        
        return cell
    }
    
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return self.view.bounds.height / 3
    }
    
    func goToFieldDetails(field: Field) {
        self.performSegue(withIdentifier: _goToFieldDetails, sender: field)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == _goToFieldDetails{
            let vc = segue.destination as! FieldDetailsViewController
            let f = sender as! Field
//            f.imgs.removeAll()
            vc.field = f
            
            vc.company = self.company
            vc.id = self.id
            
            if self.fromSearch || self.fromBook{
                vc.fromBook = true
            }
        }
        
    }
    
}
