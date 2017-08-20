//
//  CompaniesViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/21/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit


class CompaniesViewController: BaseViewController {
    
    @IBOutlet weak var tableView: UITableView!
    
    var id = 0 // if 0 all companies else companies by seaarch else all companies
    
    var companies = [Company]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initView()
        
    }
    
    func initView(){
        
        if id == 0 {
            self.title = "Search Results".localized
            self.tableView.isHidden = true
            self.showLoading()
            Provider.shared.all_companies.removeAll()
            Provider.shared.get_all_companies()
        }else{
            self.title = "Search Results".localized // TO DO Fields
            self.companies = Provider.shared.companies_search
            self.tableView.reloadData()
        }
        
        
        tableView.delegate = self
        tableView.dataSource = self
        self.tableView.register(UINib.init(nibName: "CompanyCell", bundle: nil), forCellReuseIdentifier: "cell")
    }
    
    override func viewWillAppear(_ animated: Bool) {
        notic.addObserver(self, selector: #selector(self.refreshData), name: _update_all_companies.not, object: nil)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        notic.removeObserver(self, name: _update_all_companies.not, object: nil)
    }
    
    
    func refreshData(){
        self.hideLoading()
        self.companies = Provider.shared.all_companies
        self.tableView.reloadData()
        self.tableView.isHidden = false
    }
    
}

extension CompaniesViewController : UITableViewDataSource, UITableViewDelegate, CompanyProtocol{
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.companies.count
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if self.companies.count == 0{
            let label = UILabel()
            label.backgroundColor = UIColor.clear
            
            label.font = ThemeManager.shared.font
            label.font.withSize(22)
            
            label.text = "No Companies!".localized
            label.textAlignment = .center;
            label.textColor = ThemeManager.shared.customBlue
            label.numberOfLines = 0
            label.sizeToFit()
            
            return label
        }
        
        return UIView.init(frame: CGRect.zero)
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if self.companies.count == 0 {
            return 50
        }
        return 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! CompanyCell
        
        
        cell.delegate = self
        cell.selectionStyle = .none
        cell.company = self.companies[indexPath.row]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        goToFields(company: self.companies[indexPath.row] )
    }
    
    func goToFields(company : Company){
        self.performSegue(withIdentifier: _goToFields, sender: company)
    }
    
    func goToMap(company : Company){

        let vc = self.storyboard?.instantiateViewController(withIdentifier: "MapFieldViewController") as! MapFieldViewController
        let nv = UINavigationController(rootViewController: vc)
        
        vc.company = company
        vc.type = 1
        
        self.present(nv, animated: true, completion: nil)
        
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return tableView.bounds.height / 2
    }
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == _goToFields {
            let des = segue.destination as! FieldsViewController
            des.id = 0
            des.company = sender as! Company
        }else if segue.identifier == _goToMap{
            let des = segue.destination as! MapFieldViewController
            des.company = sender as! Company
        }
    }
    
    
    
}
