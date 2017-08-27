//
//  Search2ViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 2/7/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit

class Search2ViewController: BaseViewController {

    @IBOutlet weak var tableView: UITableView!
    
    var companies = [Company]()
    
    let tfSearch = UITextField()
    
    override func viewWillAppear(_ animated: Bool) {
        registerReceivers()
        notic.addObserver(self, selector: #selector(self.refreshData), name: _update_search_companies.not, object: nil)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        unregisterReceivers()
        notic.removeObserver(self, name: _update_search_companies.not, object: nil)
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        
    }

    
    override func viewDidLoad() {
        super.viewDidLoad()

        
        initView()
        
    }
    
    
    func initView(){
        
        self.tableView.isHidden = true
        
        self.tableView.delegate = self
        self.tableView.dataSource = self
        
        self.tableView.register(UINib.init(nibName: "CompanyCell", bundle: nil), forCellReuseIdentifier: "cell")

        
        tfSearch.delegate = self
        tfSearch.frame = CGRect(x: 0, y: 0, width: 400, height: 30)
        tfSearch.backgroundColor = UIColor.white
        
        let imm = UIImageView(frame: CGRect(x: 0, y: 0, width: 30, height: tfSearch.frame.height))
        imm.contentMode = .scaleAspectFit
        imm.image = #imageLiteral(resourceName: "search")
        
        if Provider.isArabic{
            imm.image = imm.image?.imageFlippedForRightToLeftLayoutDirection()
        }

        tfSearch.leftViewMode = .always
        tfSearch.leftView = imm
        
        tfSearch.placeholder = "Search by name or address".localized
        tfSearch.font = ThemeManager.shared.font?.withSize(18)
        
        if !(UIScreen.main.bounds.height > 568){
            tfSearch.font = ThemeManager.shared.font?.withSize(16)
        }

        tfSearch.delegate = self
        tfSearch.borderStyle = .roundedRect
//        tfSearch.textAlignment = .left
        tfSearch.returnKeyType = .search
//        tfSearch.semanticContentAttribute = .forceLeftToRight

        tfSearch.clearButtonMode = .whileEditing
        tfSearch.spellCheckingType = .no
        tfSearch.autocapitalizationType = .none
        tfSearch.autocorrectionType = .no
        
        self.navigationItem.titleView = tfSearch

        self.tfSearch.becomeFirstResponder()
        
//        let image = !Provider.isArabic ? #imageLiteral(resourceName: "back") : #imageLiteral(resourceName: "back").imageFlippedForRightToLeftLayoutDirection()
//        let rr = UIBarButtonItem(image: image, style: .plain, target: self, action: #selector(self.navigateBack))
//        self.navigationItem.leftBarButtonItem = rr
        
        
    }
    
    override func navigateBack() {
        
        self.tfSearch.resignFirstResponder()
        
        super.navigateBack()
    }
    
    
    func refreshData(){
        self.hideLoading()
        self.companies = Provider.shared.companies_search
        self.tableView.reloadData()
        self.tableView.isHidden = false
    }
    
    
    override func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        tfSearch.resignFirstResponder()
        
        if let ss = textField.text, ss != ""{
            self.showLoading()
            
            Provider.shared.search(area_id: 0, name: ss, game_type: 0, start: "", duration: "", date: "", timing: 0)
        }
        
        return true
    }
    
    
    
    override func onTouch() {
        super.onTouch()
        
        self.tfSearch.resignFirstResponder()
    }

}

extension Search2ViewController : UITableViewDataSource, UITableViewDelegate, CompanyProtocol{
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.companies.count
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if self.companies.count == 0{
            let label = UILabel()
            label.backgroundColor = UIColor.clear
            
            label.font = ThemeManager.shared.font
            label.font.withSize(25)
            
            label.text = "No Results!".localized
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
            des.fromSearch = true
            
        }else if segue.identifier == _goToMap{
            let des = segue.destination as! MapFieldViewController
            des.company = sender as! Company
        }
    }
    
    
    
}

protocol refreshTime {
    func refreshTime(time : Time)
}
