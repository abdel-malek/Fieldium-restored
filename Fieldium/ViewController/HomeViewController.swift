//
//  HomeViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/18/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import SideMenu

class HomeViewController: BaseViewController,FieldProtocol {
    
    @IBOutlet weak var tableView: UITableView!
    var ref = UIRefreshControl.init()
    
    let searchBar = UISearchBar()
    let tfSearch = UITextField()
    
    
    var up_book : Book!
    var lastBooking = [Book]()
    var myVouchers = [Voucher]()
    var myOffers = [Offer]()
    
    
    var count : Int = 1
    var isRefreshed : Bool = false
    var goToSearch : Bool = false
    
    override func viewWillAppear(_ animated: Bool) {
        
        self.goToSearch = false
        Provider.game_type = nil
        
        registerReceivers()
        notic.addObserver(self, selector: #selector(self.goToCompanies), name: _update_search_companies.not, object: nil)
        notic.addObserver(self, selector: #selector(self.goToFields), name: _update_search_fields.not, object: nil)
        
        
        let me = User.getCurrentUser()
        
        if me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue{
            
            if count == 1{
                self.initView()
            }else{
                self.tableView.reloadData()
            }
        }else{
            self.tableView.reloadData()
        }
        
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        
        unregisterReceivers()
        notic.removeObserver(self, name: _update_search_companies.not, object: nil)
        notic.removeObserver(self, name: _update_search_fields.not, object: nil)
        
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        
    }
    
    func refreshData(){
        isRefreshed = true
        
        let me = User.getCurrentUser()
        
        if me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue{
            
            if self.tableView.numberOfRows(inSection: 0) == 1{
                self.tableView.reloadData()
            }
        }
        
        if let cell0 = self.tableView.cellForRow(at: IndexPath.init(row: 0, section: 0)) as? SearchCell{
            cell0.setup()
        }
        
        
        self.tableView.reloadRows(at: [IndexPath.init(row: 1, section: 0)], with: .fade)
        self.tableView.reloadRows(at: [IndexPath.init(row: 2, section: 0)], with: .fade)
        self.tableView.reloadRows(at: [IndexPath.init(row: 3, section: 0)], with: .fade)
        self.tableView.reloadRows(at: [IndexPath.init(row: 4, section: 0)], with: .fade)
        
    }
    
    func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        return false
    }
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldBeRequiredToFailBy otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return false
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.tableView.register(UINib(nibName: "SearchCell", bundle: nil), forCellReuseIdentifier: "SearchCell")
        
        self.tableView.register(UINib(nibName: "UncomingCell", bundle: nil), forCellReuseIdentifier: "UncomingCell")
        
        self.tableView.register(UINib(nibName: "LastBookingCell", bundle: nil), forCellReuseIdentifier: "LastBookingCell")
        
        self.tableView.register(UINib(nibName: "OfferProgressCell2", bundle: nil), forCellReuseIdentifier: "OfferProgressCell2")
        
        
        self.tableView.addSubview(ref)
        
        self.ref.addTarget(self, action: #selector(self.initView), for: .valueChanged)
        self.ref.tintColor = UIColor.white
        
        initView()
        
        setupSideMenuNavController()
        
        self.tableView.backgroundColor = UIColor.clear
        
        self.tableView.rowHeight = UITableViewAutomaticDimension;
        self.tableView.estimatedRowHeight = 500; // set to whatever your "average" cell height is
    
    }
    
    let bb = UIButton()
    let lll = UILabel()
    
    
    func initView(){
        
        let me = User.getCurrentUser()
        
        bb.removeFromSuperview()
        lll.removeFromSuperview()
        
        if me.statues_key == User.USER_STATUES.NEW_USER.rawValue || me.statues_key == User.USER_STATUES.USER_PENDING_VERIFICATION.rawValue{
            
            self.count = 1
            
            bb.setTitle("Register".localized, for: .normal)
            bb.backgroundColor = ThemeManager.shared.customBlue
            bb.tintColor = UIColor.white
            bb.frame.size.width = self.view.bounds.width * 3 / 5
            bb.frame.size.height = 40
            bb.frame.origin.y = self.view.bounds.height - 70 //- (self.navigationController?.navigationBar.frame.size.height)!
            bb.center.x = self.view.center.x
            bb.addTarget(self, action: #selector(self.register), for: .touchUpInside)
            
            lll.font = UIFont.init(name: "Futura-Medium", size: 21)
            lll.textColor = UIColor.white
            lll.text = "not booking yet".localized
            lll.textAlignment = .center
            lll.numberOfLines = 0
            
            lll.frame.size.width = self.view.bounds.width * 4 / 5
            lll.frame.size.height = 100
            
            if (UIScreen.main.bounds.height > 568){
                lll.center.y = self.view.center.y
            }else{
                lll.center.y = self.view.center.y + 30
            }
            lll.center.x = self.view.center.x
            
            
            
            self.tableView.addSubview(lll)
            self.view.addSubview(self.bb)
            
            self.tableView.rowHeight = UITableViewAutomaticDimension;
            self.tableView.estimatedRowHeight = 300;
            
            
            Provider.shared.get_all_games { (dat, nil) in
                if let d = dat{
                    if d.boolValue{
                        if let cell0 = self.tableView.cellForRow(at: IndexPath.init(row: 0, section: 0)) as? SearchCell{
                            cell0.setup()
                            self.ref.endRefreshing()
                        }
                    }
                }
            }
            
            Provider.shared.get_all_areas { (dat, nil) in
                if let d = dat{
                    if d.boolValue{
                        if let cell0 = self.tableView.cellForRow(at: IndexPath.init(row: 0, section: 0)) as? SearchCell{
                            cell0.setup()
                            self.ref.endRefreshing()
                        }
                    }
                }
            }
            
        }else if me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue{
            self.count = 5
            
            lastBooking.removeAll()
            myVouchers.removeAll()
            myOffers.removeAll()
            var upcoming : Book!
            
            /*Provider.shared.get_upcoming_and_last_bookings { (res, err) in
             
             self.ref.endRefreshing()
             
             if let value = res{
             
             print(value["last_bookings"])
             
             for i in value["last_bookings"].arrayValue{
             let c = Book(JSON: i.dictionaryObject!)
             self.lastBooking.append(c!)
             }
             
             if let ar = value["upcoming_booking"].array{
             if let f = ar.first{
             if let i = f.dictionaryObject{
             let c = Book(JSON: i)
             upcoming = c!
             
             if let j = f["images"].array {
             if let first = j.first{
             upcoming.imgBack = first["image_url"].stringValue
             }
             }
             }
             }
             }
             
             self.up_book = upcoming
             
             print(value)
             
             self.refreshData()
             }
             }*/
            
            
            Communication.shared.site_home(callback: { (res, err) in
                self.ref.endRefreshing()
                
                if let value = res{
                    
                    print(value["last_bookings"])
                    
                    for i in value["last_bookings"].arrayValue{
                        let c = Book(JSON: i.dictionaryObject!)
                        self.lastBooking.append(c!)
                    }
                    
                    for i in value["vouchers"].arrayValue{
                        let c = Voucher(JSON: i.dictionaryObject!)
                        self.myVouchers.append(c!)
                    }
                    
                    for i in value["offers"].arrayValue{
                        let c = Offer(JSON: i.dictionaryObject!)
                        self.myOffers.append(c!)
                    }
                    
                    if let ar = value["upcoming_booking"].array{
                        if let f = ar.first{
                            if let i = f.dictionaryObject{
                                let c = Book(JSON: i)
                                upcoming = c!
                                
                                if let j = f["images"].array {
                                    if let first = j.first{
                                        upcoming.imgBack = first["image_url"].stringValue
                                    }
                                }
                            }
                        }
                    }
                    
                    self.up_book = upcoming
                    
                    print(value)
                    
                    self.refreshData()
                }
                
            })
        }
        
    }
    
    func register(){
        let me = User.getCurrentUser()
        
        if me.statues_key == nil {
            me.statues_key = User.USER_STATUES.NEW_USER.rawValue
            User.saveMe(me: me)
        }
        
        
        if me.statues_key == User.USER_STATUES.NEW_USER.rawValue{
            self.performSegue(withIdentifier: _goToLogin, sender: nil)
        }
        
        if me.statues_key == User.USER_STATUES.USER_PENDING_VERIFICATION.rawValue{
            self.performSegue(withIdentifier: _goToVerify, sender: nil)
        }
    }
    
    override func connectionErrorNotificationReceived(_ notification: NSNotification) {
        super.connectionErrorNotificationReceived(notification)
        
        self.ref.endRefreshing()
    }
    
    
    override func RequestErrorNotificationRecived(_ notification: NSNotification) {
        super.RequestErrorNotificationRecived(notification)
        self.ref.endRefreshing()
        
        self.count = 1
        self.tableView.reloadData()
        
        Provider.shared.get_all_games { (dat, nil) in
            if let d = dat{
                if d.boolValue{
                    if let cell0 = self.tableView.cellForRow(at: IndexPath.init(row: 0, section: 0)) as? SearchCell{
                        cell0.setup()
                    }
                }
            }
        }
        
        Provider.shared.get_all_areas { (dat, nil) in
            if let d = dat{
                if d.boolValue{
                    if let cell0 = self.tableView.cellForRow(at: IndexPath.init(row: 0, section: 0)) as? SearchCell{
                        cell0.setup()
                    }
                }
            }
        }
        
    }
    
    func search(){
        self.performSegue(withIdentifier: "goToSearch", sender: nil)
    }
    
    @IBAction func searchAction(_ sender: Any) {
        search()
    }
    
    
    func setupSideMenuNavController(){
        //Menu controller
        let mainStoryboard: UIStoryboard = UIStoryboard(name: "Main",bundle: nil)
        let menuControllerId = "SideBarViewController"
        let menuController = mainStoryboard.instantiateViewController(withIdentifier: menuControllerId) as! SideBarViewController
        
        // left Nav controller
        let menuLeftNavigationController = UISideMenuNavigationController()
        
        
        //SideBarController
        menuLeftNavigationController.viewControllers = [menuController]
        menuLeftNavigationController.isNavigationBarHidden = true
        
        if Provider.isArabic{
            menuLeftNavigationController.leftSide = false
            SideMenuManager.menuRightNavigationController = menuLeftNavigationController
        }else{
            menuLeftNavigationController.leftSide = true
            SideMenuManager.menuLeftNavigationController = menuLeftNavigationController
        }
        
        
        SideMenuManager.menuAllowPushOfSameClassTwice = false
        SideMenuManager.menuPresentMode = .menuSlideIn // YAHYA
        SideMenuManager.menuAnimationPresentDuration = 0.25 // YAHYA
        
        //        SideMenuManager.menuAddPanGestureToPresent(toView: self.navigationController!.navigationBar)
        SideMenuManager.menuAddScreenEdgePanGesturesToPresent(toView: self.view)
        SideMenuManager.menuAnimationBackgroundColor = UIColor.clear
    }
    
    func showMap(){
        /*let transition = CATransition()
         transition.duration = 0.5
         transition.timingFunction = CAMediaTimingFunction.init(name: kCAMediaTimingFunctionEaseIn)
         transition.type = kCATransitionPush;
         transition.subtype = kCATransitionFade;
         navigationController?.view.layer.add(transition, forKey: kCATransition)*/
        
        self.performSegue(withIdentifier: _goToFullMap, sender: nil)
        
    }
    
    func showSideMenu(_ sender:UIBarButtonItem)
    {
        addSideMenuNavController()
    }
    
    func addSideMenuNavController()
    {
        if Provider.isArabic{
            present(SideMenuManager.menuRightNavigationController!, animated: true, completion: nil)
        }else{
            present(SideMenuManager.menuLeftNavigationController!, animated: true, completion: nil)
        }
    }
    
    
    override func configureNavigationBar() {
        super.configureNavigationBar()
        
        
        let btnName = UIButton(type: UIButtonType.custom)
        let img = UIImage(named: "ic_side_menu")?.withRenderingMode(.alwaysOriginal)
        btnName.setImage(img, for: .normal)
        btnName.frame = CGRect(x: 0,y: 0,width: 30,height: 30)
        btnName.addTarget(self, action: #selector(self.showSideMenu(_:)), for: .touchUpInside)
        btnName.tintColor = ThemeManager.shared.customGrey
        let menuBtn = UIBarButtonItem(customView: btnName)
        self.navigationItem.leftBarButtonItem = menuBtn
        
        let btnName2 = UIButton(type: UIButtonType.custom)
        let img2 = UIImage(named: "ic_location")?.withRenderingMode(.alwaysOriginal)
        btnName2.setImage(img2, for: .normal)
        btnName2.frame = CGRect(x: 0,y: 0,width: 30,height: 30)
        btnName2.addTarget(self, action: #selector(self.showMap), for: .touchUpInside)
        btnName2.tintColor = ThemeManager.shared.customGrey
        let menuBtn2 = UIBarButtonItem(customView: btnName2)
        self.navigationItem.rightBarButtonItem = menuBtn2
        
        if Provider.shared.getLang() {
//            self.navigationItem.rightBarButtonItem = menuBtn
//            self.navigationItem.leftBarButtonItem = menuBtn2
        }
        
        
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
            tfSearch.font = ThemeManager.shared.font?.withSize(15)
        }
        
        tfSearch.delegate = self
        tfSearch.borderStyle = .roundedRect
        //        tfSearch.textAlignment = .left
        tfSearch.returnKeyType = .search
        //        tfSearch.semanticContentAttribute = .forceLeftToRight
        tfSearch.clearButtonMode = .whileEditing
        tfSearch.spellCheckingType = .no
        tfSearch.autocapitalizationType = .none
        
        self.navigationItem.titleView = tfSearch
    }
    
    override func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        
        if let cell = self.tableView.cellForRow(at: IndexPath.init(row: 0, section: 0)) as? SearchCell{
            cell.searchText = textField.text
        }
        
        tfSearch.resignFirstResponder()
        
        if let ss = textField.text, ss != ""{
            self.showLoading()
            
            Provider.shared.search(area_id: 0, name: ss, game_type: 1, start: "", duration: "", date: "", timing: 0)
        }
        
        return true
    }
    
    func textFieldShouldClear(_ textField: UITextField) -> Bool {
        
        textField.text = nil
        
        return false
    }
    
    
    override func onTouch() {
        super.onTouch()
        
        
        if let cell = self.tableView.cellForRow(at: IndexPath.init(row: 0, section: 0)) as? SearchCell{
            cell.searchText = self.tfSearch.text
        }
        
        self.tfSearch.resignFirstResponder()
        self.searchBar.resignFirstResponder()
    }

    
}

extension HomeViewController: UITableViewDelegate,UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        print("COUNTCOUNT : \(self.count)")
        return self.count//3
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if indexPath.row == 0 {
            let cell = tableView.dequeueReusableCell(withIdentifier: "SearchCell", for: indexPath) as! SearchCell
            
            cell.selectionStyle = .none
            cell.delegate = self
            cell.setup()
            
            return cell}
        else if indexPath.row == 1{
            let cell = tableView.dequeueReusableCell(withIdentifier: "UncomingCell", for: indexPath) as! UncomingCell
            cell.selectionStyle = .none
            
            if let t = up_book{
                cell.book = t
            }
            
            cell.isRefreshed = self.isRefreshed
            
            
            return cell
        }else if indexPath.row == 2{
            let cell = tableView.dequeueReusableCell(withIdentifier: "LastBookingCell", for: indexPath) as! LastBookingCell
            cell.delegate = self
            cell.type = 0
            cell.bookings = self.lastBooking
            cell.isRefreshed = self.isRefreshed
            cell.selectionStyle = .none
            return cell
        }else if indexPath.row == 3{
            let cell = tableView.dequeueReusableCell(withIdentifier: "LastBookingCell", for: indexPath) as! LastBookingCell
            cell.delegate = self
            cell.type = 1
            cell.vouchers = self.myVouchers
            cell.isRefreshed2 = self.isRefreshed
            cell.homeVC = self
            cell.selectionStyle = .none
            return cell
        }else{
            let cell = tableView.dequeueReusableCell(withIdentifier: "OfferProgressCell2", for: indexPath) as! OfferProgressCell2
            
            if let o = self.myOffers.first{
                cell.offer = o
            }
            
            //            let cell = tableView.dequeueReusableCell(withIdentifier: "LastBookingCell", for: indexPath) as! LastBookingCell
            
            cell.selectionStyle = .none
            //            cell.delegate = self
            //
            //            cell.bookings = self.lastBooking
            //
            //            cell.isRefreshed = self.isRefreshed
            
            
            return cell
        }
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        if (UIScreen.main.bounds.height > 568){
            if indexPath.row == 0 {
                return tableView.frame.height * 40 / 100
            }
            if indexPath.row == 1 {
                return tableView.frame.height * 30 / 100
            }
            if indexPath.row == 2 {
                if self.lastBooking.count > 0 {
                    return tableView.frame.height * 30 / 100
                }else{
                    return 0
                }
            }
            if indexPath.row == 3 {
                if self.myVouchers.count > 0{
                    return tableView.frame.height * 30 / 100
                }else{
                    return 0
                }
            }
            if indexPath.row == 4{
                if self.myOffers.count > 0{
                    return UITableViewAutomaticDimension
                }else{
                    return 0
                }
            }
            
            return UITableViewAutomaticDimension
        }else{
            
            if indexPath.row == 0 {
                return tableView.frame.height * 2.5 / 5
            }
            
            if indexPath.row == 1 {
                if self.lastBooking.count > 0 {
                    return tableView.frame.height / 3 //+ 60
                }else{
                    return 0
                }
            }
            
            if indexPath.row == 2 || indexPath.row == 3{
                if self.myVouchers.count > 0 {
                    return tableView.frame.height / 3
                }else{
                    return 0
                }
            }
            
            if indexPath.row == 4{
                if self.myOffers.count > 0{
                    return UITableViewAutomaticDimension
                }else{
                    return 0
                }
            }
            
            return UITableViewAutomaticDimension
            
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("DIDSELECT : \(indexPath.row)")
        if indexPath.row == 1{
            if let g = self.up_book{
                self.performSegue(withIdentifier: _goToBooking, sender: g)
            }
        }
    }
    
    
    func goToFieldDetails(field: Field) {
        self.performSegue(withIdentifier: _goToFieldDetails, sender: field)
    }
    
    
    func goToCompanies(){
        self.hideLoading()
        self.performSegue(withIdentifier: _goToCompanies, sender: nil)
    }
    
    func goToFields(){
        self.hideLoading()
        self.performSegue(withIdentifier: _goToFields, sender: nil)
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == _goToCompanies{
            let vc = segue.destination as! CompaniesViewController
            vc.id = 1
        }else if segue.identifier == _goToFields {
            let vc = segue.destination as! FieldsViewController
            vc.id = 1
        }else if segue.identifier == _goToFieldDetails {
            let vc = segue.destination as! FieldDetailsViewController
            vc.field = sender as! Field
        }else if segue.identifier == _goToBooking{
            let vc = segue.destination as! BookViewController
            vc.id = 1
            vc.book = sender as! Book
        }else if segue.identifier == "goToFields2"{
            let des = segue.destination as! FieldsViewController
            let company = Company()
            let bb = sender as! Book
            company.company_id = bb.company_id
            company.name = bb.company_name
            des.company = company
            des.fromBook = true
            Time.selected = nil // TO DO
        }
        
    }
    
    
}


extension HomeViewController :  HomeProtocol,refreshTime {
    
    
    func sell() -> UIViewController {
        return self
    }
    
    func refreshTime(time: Time) {
        Time.selected = time
        if let cell = self.tableView.cellForRow(at: IndexPath.init(row: 0, section: 0)) as? SearchCell{
            cell.setupTime()
        }
    }
    
    func goToBook(book : Book){
        self.performSegue(withIdentifier: _goToBooking, sender: book)
    }
    
    func goToFields2(book : Book){
        self.performSegue(withIdentifier: "goToFields2", sender: book)
    }
    
}


extension HomeViewController : UISearchBarDelegate{
    
    
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        
        if !goToSearch{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "Search2ViewController") as! Search2ViewController
            
            self.navigationController?.pushViewController(vc, animated: true)
            
            self.goToSearch = true
        }
        
        return false
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
        
        if let cell = self.tableView.cellForRow(at: IndexPath.init(row: 0, section: 0)) as? SearchCell{
            cell.searchText = self.searchBar.text
        }
    }
    
    
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        searchBar.resignFirstResponder()
    }
    
}
