//
//  BookViewController2.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/12/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import MBProgressHUD
import DropDown
import SideMenu

class BookViewController: UITableViewController {
    
    var loadingView = MBProgressHUD.init()
    
    var field = Field()
    
    var book = Book()
    var id = 0 // show book or book now, 0 for book
    
    @IBOutlet weak var imgLogo: UIImageView!
    @IBOutlet weak var nameLbl: UILabel!
    @IBOutlet weak var addressLbl: UILabel!
    
    @IBOutlet weak var stateLbl: UIButton!
    @IBOutlet weak var gameBtn: UIButton!
    @IBOutlet weak var dateBtn: UIButton!
    @IBOutlet weak var startBtn: UIButton!
    @IBOutlet weak var durationBtn: UIButton!
    @IBOutlet weak var costHourBtn: UIButton!
    @IBOutlet weak var voucherBtn: UIButton!
    @IBOutlet weak var useVoucherBtn: UIButton!
    
    @IBOutlet weak var tfNote: UITextField!
    
    @IBOutlet weak var lineViewTopTotal: UIView!
    
    @IBOutlet weak var costLbl: UILabel!
    @IBOutlet weak var discountLbl: UILabel!
    @IBOutlet weak var subtotalLbl: UILabel!
    
    @IBOutlet weak var subtotalStack: UIStackView!
    @IBOutlet weak var discountStack: UIStackView!
    
    @IBOutlet weak var footerView: UIView!
    
    var successLbl = UILabel()
    var vSuccess: UIView!
    
    @IBOutlet weak var bookBtnView: UIView!
    
    var created : Bool = false
    var isRequest = false
    
    
    var selectedDateTime : String?
    var selectedDurationTime : String?
    
    //    @IBOutlet weak var selectVoucherBtn: UIButton!
    @IBOutlet weak var collectionView: UICollectionView!
    
    var isUseVoucher : Bool = false
    var selectedVoucher : Voucher! = nil{
        didSet{
            if let v = selectedVoucher{
                print(v.type)
                if v.type.intValue == 1{
                    self.voucherBtn.setTitle(v.value.stringValue + "%", for: .normal)
                }else{
                    self.voucherBtn.setTitle("\(v.value.intValue / 60) free hours", for: .normal)
                }
            }
        }
    }
    var myVouchers = [Voucher]()
    var dropdownVouchers = DropDown()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        vSuccess = UIView()
        vSuccess.frame = CGRect.init(x: 0, y: 0, width: self.view.frame.width, height: self.view.frame.height)
        vSuccess.isHidden = true
        successLbl.sizeToFit()
        self.vSuccess.addSubview(successLbl)
        self.view.addSubview(vSuccess)
        
        
        self.useVoucherBtn.setTitle("Use Voucher".localized, for: .normal)
        
        self.useVoucherBtn.contentHorizontalAlignment = (Provider.isArabic) ? .right : .left
        
        //collectionview
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
        self.collectionView.register(UINib.init(nibName: "VoucherCircleCell", bundle: nil), forCellWithReuseIdentifier: "VoucherCircleCell")
        
        
        if id == 1 {self.footerView.isHidden = true}
        
        updateData()
        
        self.tableView.rowHeight = UITableViewAutomaticDimension;
        self.tableView.estimatedRowHeight = 100; // set to whatever your "average" cell height is
        
        
        if id == 1{
            self.showBook()
        }else{
            let image = !Provider.isArabic ? #imageLiteral(resourceName: "back") : #imageLiteral(resourceName: "back").imageFlippedForRightToLeftLayoutDirection()
            let rr = UIBarButtonItem(image: image, style: .plain, target: self, action: #selector(self.navigateBack))
            self.navigationItem.leftBarButtonItem = rr
        }
        
        self.navigationController?.interactivePopGestureRecognizer?.isEnabled = false
    }
    
    
    func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        return false
    }
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldBeRequiredToFailBy otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        return false
    }
    
    
    
    func showSuccessOverlay() {
        self.vSuccess.isHidden = false
        self.created = true
        
        let tap : UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.showBookings))
        self.vSuccess.addGestureRecognizer(tap)
        
        let bb = UIBarButtonItem.init(barButtonSystemItem: .stop , target: self, action: #selector(self.close))
        
        self.navigationItem.leftBarButtonItem = bb
    }
    
    func close(){
        
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "HomeViewController") as! HomeViewController
        let nv = UINavigationController.init(rootViewController: vc)
        let del = UIApplication.shared.delegate as! AppDelegate
        
        del.window?.rootViewController = nv
    }
    
    
    func showBookings()
    {
        self.vSuccess.isHidden = true
        self.performSegue(withIdentifier: _goToBookings, sender: nil)
    }
    
    
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == _goToBookings {
            let vc = segue.destination as! BookingsViewController
            vc.type = 2
        }
    }
    
    func updateData(){
        print(id)
        if id == 0 {
            
            self.title = "Booking".localized
            
            
            if let im = self.field.logo_url {
                self.imgLogo.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
            }
            
            if let hr = self.field.hour_rate{
                costHourBtn.setTitle("\(hr) \(Provider.currancy())", for: .normal)
                
                
                if self.durationBtn.titleLabel?.text != nil && self.durationBtn.titleLabel?.text != ""{
                    
                    if let time = Time.selected {
                        if let dd = time.duration{
                            self.costLbl.text = "\(hr.doubleValue / 60 * Double(dd)!) \(Provider.currancy())"
                        }
                    }
                    
                }else{
                    self.costLbl.text = "\(hr.intValue) \(Provider.currancy())"
                }
                
            }else{
                self.costLbl.text = ""
            }
            
            
            self.nameLbl.text = field.name
            
            if let time = Time.selected{
                
                gameBtn.setTitle(SearchCell.selectedSoccer!.name, for: .normal)
                dateBtn.setTitle(time.date, for: .normal)
                //                startBtn.setTitle(time.start, for: .normal) // TO DO
                startBtn.setTitle(Time.convert24To12(time: time.start!), for: .normal)
                //                durationBtn.setTitle("\(Int(time.duration)!)", for: .normal) // TO DO
                let dd = Int(time.duration!)!
                durationBtn.setTitle("\(dd / 60)h \(dd % 60)m", for : .normal)
            }else{
                dateBtn.setTitle("-", for: .normal)
                startBtn.setTitle("-", for: .normal)
                durationBtn.setTitle("-", for: .normal)
            }
            
            
            if let time = Time.selected{
                dateBtn.setTitle(time.date, for: .normal)
                selectedDateTime = time.start
                selectedDurationTime = time.duration
                
            }else{
                dateBtn.setTitle("-", for: .normal)
            }
            
            
            // TO DO when book
            gameBtn.isEnabled = false
            dateBtn.isEnabled = false
            startBtn.isEnabled = false
            durationBtn.isEnabled = false
            self.lineViewTopTotal.isHidden = true
            
        }else {
            
            self.lineViewTopTotal.isHidden = false
            
            gameBtn.setTitle(self.book.game_type_name, for: .normal)
            
            gameBtn.isEnabled = false
            dateBtn.isEnabled = false
            startBtn.isEnabled = false
            durationBtn.isEnabled = false
            
            tfNote.isEnabled = false
            tfNote.placeholder = ""
            
            bookBtnView.isHidden = true
            
            gameBtn.setTitleColor(.black, for: .normal)
            dateBtn.setTitleColor(UIColor.black, for: .normal)
            startBtn.setTitleColor(UIColor.black, for: .normal)
            durationBtn.setTitleColor(UIColor.black, for: .normal)
            
            
            print("TRUE")
            
            if let state = self.book.state_id{
                
                switch state {
                case 1:
                    self.stateLbl.setTitle("Pending".localized, for: .normal)
                    break
                case 2:
                    self.stateLbl.setTitle("Approved".localized, for: .normal)
                    break
                case 3:
                    self.stateLbl.setTitle("Declined".localized, for: .normal)
                    break
                case 4:
                    self.stateLbl.setTitle("Canceled".localized, for: .normal)
                    
                    let lbl = UILabel()
                    lbl.font = ThemeManager.shared.boldFont
                    lbl.textAlignment = .left
                    lbl.text = "Cancel reason:".localized
                    lbl.tag = 55
                    
                    let reasonLbl = UILabel()
                    reasonLbl.font = ThemeManager.shared.font?.withSize(16)
                    reasonLbl.numberOfLines = 0
                    reasonLbl.textAlignment = .left
                    reasonLbl.text = self.book.cancellation_reason
                    reasonLbl.tag = 55
                    
                    let emptyLbl = UILabel()
                    emptyLbl.text = " "
                    emptyLbl.numberOfLines = 0
                    emptyLbl.tag = 55
                    
                    break
                default:
                    break
                }
            }
            
            
            if let im = self.book.logo_url {
                self.imgLogo.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
            }
            
            if let cc = self.book.company_name{
                nameLbl.text = self.book.field_name + " - " + cc
                
            }else{
                nameLbl.text = self.book.field_name
            }
            
            let currn = (Provider.isArabic) ? self.book.ar_currency_symbol : self.book.en_currency_symbol
            
            let currancy : String = (currn != nil) ? currn! : ""
            
            addressLbl.text = self.book.address
            costLbl.text = (self.book.total != nil) ? "\(Int(self.book.total!))" + " \(currancy)" : nil
            dateBtn.setTitle(self.book.date, for: .normal)
            
            if let s = self.book.start{
                startBtn.setTitle(Time.convert24To12(time: s), for: .normal)
                durationBtn.setTitle("\(book.duration / 60)h \(book.duration % 60)m", for: .normal)
                
                self.title = "\("Ref".localized).# " + self.book.booking_id.toString
            }
            
            
            let hour_rate = (self.book.hour_rate != nil) ? "\(self.book.hour_rate!)" : ""
            self.costHourBtn.setTitle("\(hour_rate) \(currancy)", for: .normal)
            
            
            if self.book.voucher != "" && self.book.voucher_type != nil {
                
                let vv = Voucher()
                vv.type = self.book.voucher_type
                vv.value = self.book.voucher_value
                print("TYPE = \(self.book.voucher_type) - \(self.book.voucher_value)")
                self.selectedVoucher = vv
                
                let subtotal = self.book.subtotal.doubleValue
                
                var discount : Double = 0.0
                
                if let v = self.selectedVoucher{
                    if v.type.intValue == 1{
                        discount = v.value.doubleValue * subtotal / 100
                        //                        self.voucherBtn.setTitle(v.value.stringValue + "%", for: .normal)
                    }else if v.type.intValue == 2{
                        //                        self.voucherBtn.setTitle("\(v.value.intValue / 60) free hours", for: .normal)
                        discount = (Double(self.book.duration!) / 60 * (v.value.doubleValue))
                    }
                }
                
                print(self.selectedVoucher.value.doubleValue)
                print("DISCOUNT : \(discount)")
                
                
                var dis = "-\(discount) \(Provider.currancy())"
                if Provider.isArabic{
                    dis = "\(discount)- \(Provider.currancy())"
                }
                
                let paragraph = NSMutableParagraphStyle()
                paragraph.alignment = Provider.isArabic ? .left : .right
                
                let textRange = NSMakeRange(0, dis.characters.count)
                let attributedText = NSMutableAttributedString(string: dis)
                attributedText.addAttribute(NSStrikethroughStyleAttributeName , value: NSUnderlineStyle.styleSingle.rawValue, range: textRange)
                attributedText.addAttribute(NSParagraphStyleAttributeName, value: paragraph, range: textRange)
                
                self.subtotalLbl.text = "\(subtotal) \(Provider.currancy())"
                self.discountLbl.attributedText = attributedText
                
                
                if discount != 0{
                    self.subtotalStack.isHidden = false
                    self.discountStack.isHidden = false
                }
                
                self.refreshData()

            }
        }
        
    }
    
    func showBook(){
        
        if let ii = self.book.booking_id{
            self.showLoading()
            
            Provider.shared.show_book(booking_id: ii) { (res, err) in
                
                self.hideLoading()
                
                if let i = res{
                    print(i)
                    
                    let ff = Book(JSON: i.dictionaryObject!)
                    self.book = ff!
                    
                    print("THATS BOOK")
                    
//                    if self.book.voucher != "" && self.book.voucher_type != nil {
//                        let vv = Voucher()
//                        vv.type = self.book.voucher_type
//                        vv.value = self.book.voucher_value
//                        
//                        self.selectedVoucher = vv
//                    }
                    
                    
                    self.updateData()
                    self.tableView.reloadData()
                }
            }
        }
    }
    
    
    
    
    
    @IBAction func confirmAction(_ sender: Any) {
        
        if !(self.isRequest){
            var message = ""
            
            message += "\n\("Date".localized): \(self.dateBtn.titleLabel!.text!)"
            message += "\n\("Start".localized): \(self.startBtn.titleLabel!.text!)"
            message += "\n\("Duration".localized): \(self.durationBtn.titleLabel!.text!) "
            message += "\n\("Total".localized): \(self.costLbl.text!)"
            
            if let time = Time.selected{
                
                if let start = time.start, let duration = time.duration{
                    
                    let alert = UIAlertController.init(title: "Confirmation".localized, message: message, preferredStyle: .alert)
                    
                    alert.addAction(UIAlertAction(title: "Cancel".localized, style: .cancel, handler: nil))
                    
                    alert.addAction(UIAlertAction(title: "Ok".localized, style: .default, handler: { (ac) in
                        self.showLoading()
                        self.isRequest = true
                        
                        var v = ""
                        if self.selectedVoucher != nil {v = self.selectedVoucher!.voucher}
                        
                        Provider.shared.creak_book(field_id: self.field.field_id, start: start, duration: duration, date: time.date!, notes: self.tfNote.text!,varchar : v) { (dat, err) in
                            self.isRequest = false
                            
                            self.hideLoading()
                            
                            let vc = self.storyboard?.instantiateViewController(withIdentifier: "SuccessViewController") as! SuccessViewController
                            
                            vc.parentBook = self
                            vc.modalPresentationStyle = .overCurrentContext
                            vc.modalTransitionStyle = .crossDissolve
                            
                            if let dd = dat, dd["state_id"].intValue == 2{
                                self.successLbl.text = "Approved".localized
                                vc.text = "Approved".localized
                            }
                            else{
                                self.successLbl.text = "You’ll get notified".localized
                                vc.text = "You’ll get notified".localized
                            }
                            
                            //                            self.showSuccessOverlay()
                            
                            self.present(vc, animated: false, completion: nil)
                            
                            Time.selected = nil
                        }
                    }))
                    
                    self.present(alert, animated: true, completion: nil)
                    
                }else{
                    self.showErrorMessage(text: "Please select another date".localized)
                }
            }else{
                self.showErrorMessage(text: "Pick date".localized)
            }
        }
    }
    
    
    func setupAR(btn : UIButton){
        btn.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        btn.titleLabel!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        btn.imageView!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        btn.contentHorizontalAlignment = .left
        btn.titleLabel!.textAlignment = .left
        btn.setImage(#imageLiteral(resourceName: "down"), for: .normal)
    }
    
    func setupAR2(btn : UIButton){
        btn.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        btn.titleLabel!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        btn.imageView!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        btn.contentHorizontalAlignment = .left
        btn.titleLabel!.textAlignment = .left
        btn.tintColor = UIColor.white
        btn.setImage(#imageLiteral(resourceName: "downLight"), for: .normal)
    }
    
    
    
    func connectionErrorNotificationReceived(_ notification: NSNotification) {
        self.hideLoading()
        
        print("connectionErrorNotificationReceived")
        if let msg = notification.object as? String{
            self.showErrorMessage(text : msg)
        }
        
        self.isRequest = false
        
    }
    
    func RequestErrorNotificationRecived(_ notification : NSNotification)
    {
        print("RequestErrorNotificationRecived")
        self.hideLoading()
        
        if let code = notification.object as? Int
        {
            if(code == 401)
            {
                return
            }
        }
        
        if let msg = notification.object as? String{
            self.showErrorMessage(text: msg)
            
            if msg == "Not authorized" {
                User.saveMe(me: User())
                
                let vc = self.storyboard?.instantiateViewController(withIdentifier: "SignupViewController") as! SignupViewController
                let nv = UINavigationController.init(rootViewController: vc)
                let del = UIApplication.shared.delegate as! AppDelegate
                
                del.window?.rootViewController = nv
            }
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
        
        self.isRequest = false
    }
    
    func navigateBack() {
        
        if !(self.isRequest){
            
            var poped = false
            
            if(vSuccess.isHidden == false)
            {
                //pop the back stack until the menu view controller
                for controller in self.navigationController!.viewControllers as Array {
                    if controller.isKind(of : HomeViewController.self) {
                        let home = controller as! HomeViewController
                        poped = true
                        let _ = self.navigationController?.popToViewController(home, animated: true)
                        break
                    }
                }
            }
            if(!poped)
            {
                self.navigationController?.popViewController(animated: true)
            }
        }
    }
    
    
    func showErrorMessage(text: String) {
        KSToastView.ks_showToast(text.html2String, duration: 3)
    }
    
    func showLoading(){
        self.loadingView = MBProgressHUD.showAdded(to: self.view, animated: true)
    }
    
    func hideLoading(){
        self.loadingView.hide(animated: true)
    }
    
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
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(true)
        
        //        self.tableView.tableFooterView = nil
        //        self.footerView.frame = CGRect(x : 0,y : 0, width : self.view.frame.size.width, height :self.tableView.frame.size.height - self.tableView.contentSize.height - self.footerView.frame.size.height)
        //        self.tableView.tableFooterView = self.footerView
        
        
        registerReceivers()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(true)
        
        unregisterReceivers()
    }
    
    
}

// TABLEVIEW DELEGATE
extension BookViewController {
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        let heightCell = CGFloat(50)
        
        if indexPath.section == 0{
            switch indexPath.row {
            case 0:
                return UITableViewAutomaticDimension
            case 1:
                return (id != 1) ? 0 : heightCell
            case 7:
                return (selectedVoucher != nil) ? heightCell : 0
            case 8:
                
                //                if self.view.bounds.height <= 568 && id == 0 {
                //                    return self.view.frame.height / 4
                //                }
                
                return (id == 1) ? 0 : UITableViewAutomaticDimension
            default:
                return heightCell
            }
        }else{
            if indexPath.row == 2 {return UITableViewAutomaticDimension}
            
            if id == 1{
                return 0
            }else{
                if self.selectedVoucher == nil{
                    if indexPath.row == 1 {
                        let h =  (self.view.bounds.height <= 568) ? 5 : 6
                        let collectionHeight = tableView.frame.height / CGFloat(h)
                        return (self.isUseVoucher) ? collectionHeight : 0
                    }
                    return heightCell
                }else{
                    return 0
                }
            }
            
        }
    }
    
    
    @IBAction func  useVoucherAction(_ sender : UIButton){
        self.isUseVoucher = true
        //        self.tableView.reloadSections(IndexSet(integer: 1), with: .fade)
        
        
        self.showLoading()
        Communication.shared.vouchers_my_vouchers(self.field.field_id) { (res) in
            self.hideLoading()
            
            self.myVouchers = res
            
            if res.count == 0 {
                self.openAddVoucher()
                return
            }
            
            //self.setupDropdown()
            self.collectionView.isHidden = false
            self.collectionView.reloadData()
            self.refreshData()
            
        }
    }
    
    
    /*func setupDropdown(){
     
     var array = [String]()
     
     for i in self.myVouchers{
     array.append("\(i.voucher) - \(i.value.stringValue)")
     }
     
     array.append("type another one")
     
     print(array)
     
     dropdownVouchers.dataSource = array
     dropdownVouchers.anchorView = selectVoucherBtn
     dropdownVouchers.direction = .any
     dropdownVouchers.bottomOffset = CGPoint(x: 0, y: selectVoucherBtn.bounds.height * 2 / 3)
     
     dropdownVouchers.selectionAction = { [unowned self] (index, item) in
     
     if index < self.myVouchers.count{
     self.selectedVoucher = self.myVouchers[index]
     }else{
     
     let vc = self.storyboard?.instantiateViewController(withIdentifier: "AddVoucherViewController") as! AddVoucherViewController
     
     vc.field = self.field
     vc.date = self.dateBtn.titleLabel!.text!
     vc.duration = Time.selected.duration
     vc.start = Time.selected.start
     vc.parentBook = self
     
     vc.modalPresentationStyle = .overCurrentContext
     vc.modalTransitionStyle = .crossDissolve
     
     self.present(vc, animated: true, completion: nil)
     }
     
     self.refreshData()
     }
     
     }
     
     @IBAction func  selectOneVoucher(_ sender : UIButton){
     self.dropdownVouchers.show()
     }*/
    
    func refreshData(){
        var subtotal = 0.0
        var discount = 0.0
        var total = 0.0
        
        if id == 0{
        if let hr = self.field.hour_rate{
            costHourBtn.setTitle("\(hr) \(Provider.currancy())", for: .normal)
            
            
            if self.durationBtn.titleLabel?.text != nil && self.durationBtn.titleLabel?.text != ""{
                
                if let time = Time.selected {
                    if let dd = time.duration{
                        subtotal = hr.doubleValue / 60 * Double(dd)!
                        total = subtotal
                        
                            if let v = self.selectedVoucher{
                                if v.type.intValue == 1{
                                    discount = v.value.doubleValue * subtotal / 100
                                }else if v.type.intValue == 2{
                                    discount = (hr.doubleValue / 60 * (v.value.doubleValue))
                                    print(v.value.doubleValue)
                                    print("DISCOUNT : \(discount)")
                                }
                            }
                        
                        
                        
                        total -= discount
                        
                        if total < 0 {
                            total = 0
                        }
                        
                        self.subtotalLbl.text = "\(subtotal) \(Provider.currancy())"
                        self.costLbl.text = "\(total) \(Provider.currancy())"
                        
                        var dis = "-\(discount) \(Provider.currancy())"
                        if Provider.isArabic{
                            dis = "\(discount)- \(Provider.currancy())"
                        }
                        
                        let paragraph = NSMutableParagraphStyle()
                        paragraph.alignment = Provider.isArabic ? .left : .right
                        
                        let textRange = NSMakeRange(0, dis.characters.count)
                        let attributedText = NSMutableAttributedString(string: dis)
                        attributedText.addAttribute(NSStrikethroughStyleAttributeName , value: NSUnderlineStyle.styleSingle.rawValue, range: textRange)
                        attributedText.addAttribute(NSParagraphStyleAttributeName, value: paragraph, range: textRange)
                        
                        self.discountLbl.attributedText = attributedText
                        
                        if discount != 0{
                            self.subtotalStack.isHidden = false
                            self.discountStack.isHidden = false
                        }
                        
                    }
                }
            }
        }
        
        self.tableView.reloadData()
        }else{
            
            if let hr = self.book.hour_rate{

                let dd = self.book.duration
                subtotal = hr / 60 * Double(dd!)
                total = subtotal

                
                
            print("TEST1 : \(self.book.voucher_type)")
            if let type = self.book.voucher_type, type.stringValue == "1"{
                discount = self.book.voucher_value.doubleValue * subtotal / 100
                print("TEST2 - \(discount)")
            }else if let type = self.book.voucher_type, type.stringValue == "2"{
                discount = (hr / 60 * (self.book.voucher_value.doubleValue))
                print("TEST3 - \(discount)")
                
            }
            
            total -= discount
            
            if total < 0 {
                total = 0
            }
            
            self.subtotalLbl.text = "\(subtotal) \(Provider.currancy())"
            self.costLbl.text = "\(total) \(Provider.currancy())"
            
            var dis = "-\(discount) \(Provider.currancy())"
            if Provider.isArabic{
                dis = "\(discount)- \(Provider.currancy())"
            }
                
            
            let paragraph = NSMutableParagraphStyle()
            paragraph.alignment = Provider.isArabic ? .left : .right
            
            let textRange = NSMakeRange(0, dis.characters.count)
            let attributedText = NSMutableAttributedString(string: dis)
            attributedText.addAttribute(NSStrikethroughStyleAttributeName , value: NSUnderlineStyle.styleSingle.rawValue, range: textRange)
            attributedText.addAttribute(NSParagraphStyleAttributeName, value: paragraph, range: textRange)
            
            self.discountLbl.attributedText = attributedText
            
            if discount != 0{
                self.subtotalStack.isHidden = false
                self.discountStack.isHidden = false
            }
        
            self.tableView.reloadData()
            }
        
        }
    }

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("\(indexPath.section)-\(indexPath.row)")
        if indexPath.section == 1 && indexPath.row == 0{
            self.useVoucherAction(UIButton())
            print("OK")
        }
    }
    
    override func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        cell.selectionStyle = .none
    }
    
}


extension BookViewController : UICollectionViewDelegate, UICollectionViewDataSource{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.myVouchers.count + 1
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "VoucherCircleCell", for: indexPath) as! VoucherCircleCell
        
        if indexPath.row < self.myVouchers.count{
            cell.v = self.myVouchers[indexPath.row]
        }else{
            cell.new = true
        }
        
        
        return cell
        
        
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {
        return CGSize(width: collectionView.frame.height, height: collectionView.frame.height)
    }
    
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if indexPath.row < self.myVouchers.count{
            self.selectedVoucher = self.myVouchers[indexPath.row]
        }else{
            self.openAddVoucher()
        }
        
        self.refreshData()
        
    }
    
    
    func openAddVoucher(){
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "AddVoucherViewController") as! AddVoucherViewController
        
        vc.field = self.field
        vc.date = self.dateBtn.titleLabel!.text!
        vc.duration = Time.selected.duration
        vc.start = Time.selected.start
        vc.parentBook = self
        
        vc.modalPresentationStyle = .overCurrentContext
        vc.modalTransitionStyle = .crossDissolve
        
        self.present(vc, animated: true, completion: nil)
    }
    
}
