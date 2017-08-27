//
//  FieldDetailsViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/22/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import SwiftyJSON
import MBProgressHUD
import CalendarKit



class FieldDetailsViewController: UITableViewController {
    
    //cover
    @IBOutlet weak var collectionCover: UICollectionView!
    
    @IBOutlet weak var costLbl: UILabel!
    @IBOutlet weak var nameLbl: UILabel!
    @IBOutlet weak var addressLbl: UILabel!
    @IBOutlet weak var descriptionLbl: UILabel!
    @IBOutlet weak var logoImg: UIImageView!
    
    @IBOutlet weak var hourRateLbl: UILabel!
    @IBOutlet weak var phoneLbl: UILabel!
    @IBOutlet weak var phoneLbl2: UILabel!
    
    @IBOutlet weak var open_hours_lbl: UILabel!
    @IBOutlet weak var maxCapacity: UILabel!
    @IBOutlet weak var fieldArea: UILabel!
    
    @IBOutlet weak var bookBtn: UIButton!
    
    @IBOutlet weak var pageControl: UIPageControl!
    
    var loadingView = MBProgressHUD.init()
    
    
    @IBOutlet weak var collectionAmenities: UICollectionView!
    @IBOutlet weak var collectionGames: UICollectionView!
    
    
    var field = Field()
    var company = Company()
    var id = 0
    var fromBook = false
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        configureNavigationBar()
        
        registerReceivers()
        
        initView()
        
        self.showLoading()
        Provider.shared.show_field(field_id: self.field.field_id, callback: {
            (data : JSON?, error : NSError?) -> Void in
            
            if let i = data{
                let ff = Field(JSON: i.dictionaryObject!)
                
//                var imgs = [IMG]()
//                var amenities = [Amenity]()
//                var games = [Game]()
                
                self.field = ff!
                
                self.collectionCover.reloadData()
                self.updateDetails()
                self.tableView.reloadData()
            }
            
            self.hideLoading()
            
        })
    }
    
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.updateDetails()
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        unregisterReceivers()
    }
    
    func initView(){
        //tableview
        self.tableView.estimatedRowHeight = 300
        self.tableView.rowHeight = UITableViewAutomaticDimension
        
        //collections
        collectionCover.delegate = self
        collectionCover.dataSource = self
        collectionCover.isPagingEnabled = true
        
        collectionAmenities.delegate = self
        collectionAmenities.dataSource = self
        
        collectionGames.delegate = self
        collectionGames.dataSource = self
        
        collectionCover.register(UINib(nibName: "ImageCollectionCell", bundle: nil), forCellWithReuseIdentifier: "cell")
        
        if let time = Time.selected, time.start == nil{
            self.id = 0
        }
        
        updateDetails()
    }
    
    func updateDetails(){
        
        self.title = self.field.name
        costLbl.text = "\(field.hour_rate.intValue) \(Provider.currancy())"
        hourRateLbl.text = "\(field.hour_rate.intValue) \(Provider.currancy())"
        nameLbl.text = field.name
        addressLbl.text = field.address
        descriptionLbl.text = company.description
        
        maxCapacity.text = field.max_capacity.stringValue
        
        let hours = Time.convert24To12(time: field.open_time!) + " - " + Time.convert24To12(time: field.close_time!)
        open_hours_lbl.text = hours
        
        let me = User.getCurrentUser()
        if me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue{
            phoneLbl2.text = "Phone number".localized
            phoneLbl.text = field.phone
            phoneLbl2.isHidden = true
        }else{
            phoneLbl2.isHidden = false
        }
        
        
        if let xx = field.area_x.double ,let yy = field.area_y.double{
        
            let space = xx * yy
            let t = "\(Int(space).toString)m&sup2;".html2String
        
            fieldArea.text = t
        }
        
        if let im = self.field.logo_url{
            logoImg.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
        }
        
        if self.id == 1 {
            self.bookBtn.setTitle("Book".localized, for: .normal)
        }else{
            self.bookBtn.setTitle("Check availability".localized, for: .normal)
        }
        
        self.setupPageControl()
    }
    
    
    //Setup PageControl
    func setupPageControl() {
        self.pageControl.numberOfPages = self.field.imgs.count
        self.pageControl.currentPage = 0
        self.pageControl.tintColor = ThemeManager.shared.customBlue
        self.pageControl.pageIndicatorTintColor = UIColor.white
        self.pageControl.currentPageIndicatorTintColor = ThemeManager.shared.customBlue
        
        if self.field.imgs.count < 2 {
            self.pageControl.isHidden = true
        }
    }
    
    
    @IBAction func bookAction(_ sender: Any) {
        
        let me = User.getCurrentUser()
        if me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue{
            
            if self.id == 1 {
                
                self.performSegue(withIdentifier: _goToBooking, sender: self.field)
                
            }else{
                
                
                if self.fromBook{
                    let alert = UIAlertController.init(title: "Select Game".localized, message: nil, preferredStyle: .actionSheet)
                    
                    alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
                    
                    for i in self.field.games{
                        alert.addAction(UIAlertAction.init(title: i.name!, style: .default, handler: { (ac) in
                            SearchCell.selectedSoccer = i
                            self.goToAvailabilty()
                        }))
                    }
                    
                    self.present(alert, animated: true, completion: nil)
                    
                }
                else{
                    
                    goToAvailabilty()
                }
            }
        }
        else{
            self.showErrorMessage(text: _youMustLogin)
            
            
            let me = User.getCurrentUser()
            
            if me.statues_key == nil {
                me.statues_key = User.USER_STATUES.NEW_USER.rawValue
                User.saveMe(me: me)
            }
            
            
            if me.statues_key == User.USER_STATUES.NEW_USER.rawValue{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "SignupViewController") as! SignupViewController
                let nv = UINavigationController.init(rootViewController: vc)
                vc.type = 2
                
                self.present(nv, animated: true, completion: nil)
            }
            
            
            if me.statues_key == User.USER_STATUES.USER_PENDING_VERIFICATION.rawValue{
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "VerifyViewController") as! VerifyViewController
                let nv = UINavigationController.init(rootViewController: vc)
                vc.type = 2
                
                self.present(nv, animated: true, completion: nil)
            }
            
            
        }
    }
    
    func goToAvailabilty(){
        self.performSegue(withIdentifier: "goToAvailability", sender: nil)
    }
    
    
    
    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 10
    }
    
    
    override func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        cell.selectionStyle = .none
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch indexPath.row {
        case 0:
            break
        case 8:
            let me = User.getCurrentUser()
            if me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue{
                if let pp = self.field.phone{
                    if UIApplication.shared.canOpenURL(URL(string: "telprompt://\(pp)")!){
                        UIApplication.shared.openURL(URL(string: "telprompt://\(pp)")!)
                    }
                }
            }else{
                self.showErrorMessage(text: _youMustLogin)
                
                let me = User.getCurrentUser()
                
                if me.statues_key == nil {
                    me.statues_key = User.USER_STATUES.NEW_USER.rawValue
                    User.saveMe(me: me)
                }
                
                
                if me.statues_key == User.USER_STATUES.NEW_USER.rawValue{
                    let vc = self.storyboard?.instantiateViewController(withIdentifier: "SignupViewController") as! SignupViewController
                    let nv = UINavigationController.init(rootViewController: vc)
                    vc.type = 2
                    
                    self.present(nv, animated: true, completion: nil)
                }
                
                
                if me.statues_key == User.USER_STATUES.USER_PENDING_VERIFICATION.rawValue{
                    let vc = self.storyboard?.instantiateViewController(withIdentifier: "VerifyViewController") as! VerifyViewController
                    let nv = UINavigationController.init(rootViewController: vc)
                    vc.type = 2
                    
                    self.present(nv, animated: true, completion: nil)
                }
                
            }
            break
        case 9:
            self.performSegue(withIdentifier: _goToMap, sender: company)
            break
        default:
            break
        }
        
    }
    
    @IBAction func goToMapAction(_ sender: Any) {
        self.performSegue(withIdentifier: _goToMap, sender: company)
        
    }
    
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == _goToMap{
            let des = segue.destination as! MapFieldViewController
            des.field = self.field
            
        }else if segue.identifier == _goToBooking{
            let des = segue.destination as! BookViewController
            des.field = sender as! Field
        }else if segue.identifier == "goToAvailability"{
            let vc = segue.destination as! AvailabilityViewController2
            
            vc.field = self.field
            
            
            TimelineView.countRows = BaseManager2.shared.times(open: self.field.open_time!, close: self.field.close_time!).count - 1
        }
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        let h = self.view.bounds.height
        let w = tableView.bounds.width
        
        switch indexPath.row {
        case 0:
            return h / 2
        case 1:
            return (self.descriptionLbl.text == "" || self.descriptionLbl.text == nil) ? 0 : UITableViewAutomaticDimension
        case 2:
            let hh = w / 4
            
            if self.field.amenities.count == 0 {
                return 0
            }else{
                return hh + 30
            }
            
            
        case 3:
            
            let hh = w / 4
            if self.field.games.count == 0 {
                return 0
            }else{
                return hh + 30
            }
            
        case 5,9:
            return 0
        case 6,7:
            return h / 12
        case 8,10:
            return h / 10
        default:
            return UITableViewAutomaticDimension
        }
    }
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return nil
    }
    
    
}


extension FieldDetailsViewController : UICollectionViewDelegate , UICollectionViewDataSource{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        switch collectionView {
        case collectionCover:
            return self.field.imgs.count
        case collectionAmenities:
            return self.field.amenities.count
        case collectionGames:
            return self.field.games.count
        default:
            return 0
        }
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! ImageCollectionCell
        
        switch collectionView {
        case collectionCover:
            cell.imgUrl = self.field.imgs[indexPath.row].image_url
            break
        case collectionAmenities:
            cell.amenity = self.field.amenities[indexPath.row]
            break
        case collectionGames:
            cell.game = self.field.games[indexPath.row]
            break
        default:
            break
        }
        
        
        return cell
        
        
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {
        let v = self.view.bounds.width
        
        
        if collectionView == collectionCover{
            return CGSize(width: collectionView.frame.width, height: collectionView.frame.height)
        }else{
            return CGSize(width: v / 5, height: v / 5)
        }
    }
}


extension FieldDetailsViewController {
    func registerReceivers()
    {
        
        notic.addObserver(self, selector: #selector(self.connectionErrorNotificationReceived(_:)), name: _ConnectionErrorNotification.not, object: nil)
        
        notic.addObserver(self, selector: #selector(self.RequestErrorNotificationRecived(_:)), name: _RequestErrorNotificationReceived.not, object: nil)
        
    }
    
    func unregisterReceivers()
    {
        notic.removeObserver(self, name: _ConnectionErrorNotification.not, object: nil)
        
        notic.removeObserver(self, name: _RequestErrorNotificationReceived.not, object: nil)
        
    }
    
    func connectionErrorNotificationReceived(_ notification: NSNotification) {
        self.showErrorMessage(text : "")
    }
    
    func RequestErrorNotificationRecived(_ notification : NSNotification)
    {
        if let code = notification.object as? Int
        {
            if(code == 401)
            {
                //                User.logout()
                //                goToLoginScene()
                return
            }
        }
        
        if let msg = notification.object as? String{
            self.showErrorMessage(text: msg)
        }
        
        if let msg = notification.object as? [String]
        {
            self.showErrorMessage(text: msg.joined(separator: "\n"))
        }
        else if let msg = notification.object as? String
        {
            self.showErrorMessage(text: msg)
        }
        else
        {
            self.showErrorMessage(text: NSLocalizedString("Request.Error", comment: ""))
        }
    }
    
    func showErrorMessage(text: String) {
        KSToastView.ks_showToast(text, duration: 1.5)
    }
    
    
    func configureNavigationBar() {
        if let nav = self.navigationController{
            
            
            let rr = UIBarButtonItem(title: "", style: UIBarButtonItemStyle.plain, target: nil, action: nil)
            self.navigationItem.backBarButtonItem = rr
            
            
            //configure back button
            if self != nav.viewControllers[0] {
                
            }
            
        }
    }
    
    func navigateBack() {
        let _ = self.navigationController?.popViewController(animated: true)
    }
    
    
    func showLoading(){
        self.loadingView = MBProgressHUD.showAdded(to: self.view, animated: true)
    }
    
    func hideLoading(){
        self.loadingView.hide(animated: true)
    }
    
    //Delegate With ScrollView (scroll item photos)
    override func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == self.collectionCover{
            if self.field.imgs.count > 0 {
                if self.collectionCover.visibleCells.count > 0{
                    let pp = round(self.collectionCover.contentOffset.x / self.collectionCover.frame.size.width)
                    self.pageControl.currentPage = Int(pp)
                }
            }
        }
    }
    
    
}
