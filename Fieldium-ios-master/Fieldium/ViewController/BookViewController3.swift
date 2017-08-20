////
////  BookViewController.swift
////  Fieldium
////
////  Created by Yahya Tabba on 12/27/16.
////  Copyright © 2017 Tradinos UG. All rights reserved.
////
//
//import UIKit
//import DropDown
//
//class BookViewController: BaseViewController,refreshTime {
//    
//    var field = Field()
//    
//    var book = Book()
//    var id = 0
//    @IBOutlet weak var constraintTop: NSLayoutConstraint!
//    
//    @IBOutlet weak var constraintBottom: NSLayoutConstraint!
//    
//    @IBOutlet weak var stackFull: UIStackView!
//    @IBOutlet weak var lineNote: UIView!
//    @IBOutlet weak var imgLogo: UIImageView!
//    @IBOutlet weak var nameLbl: UILabel!
//    @IBOutlet weak var detailsLbl: UILabel!
//    
//    @IBOutlet weak var stateLbl: UIButton!
//    @IBOutlet weak var stateStack: UIStackView!
//    
//    @IBOutlet weak var gameBtn: UIButton!
//    @IBOutlet weak var dateBtn: UIButton!
//    @IBOutlet weak var startBtn: UIButton!
//    @IBOutlet weak var durationBtn: UIButton!
//    @IBOutlet weak var costHourBtn: UIButton!
//    
//    @IBOutlet weak var dateStack: UIStackView!
//    @IBOutlet weak var startStack: UIStackView!
//    @IBOutlet weak var durationStack: UIStackView!
//    @IBOutlet weak var costStack: UIStackView!
//    
//    
//    @IBOutlet weak var tfNote: UITextField!
//    @IBOutlet weak var costLbl: UILabel!
//    
//    @IBOutlet weak var successLbl: UILabel!
//    @IBOutlet weak var vSuccess: UIView!
//    
//    @IBOutlet weak var bookBtnView: UIView!
//    @IBOutlet weak var noteView: UIStackView!
//    
//    var created : Bool = false
//    var isRequest = false
//    
//    //    var selectedTime : Time?
//    
//    let dateTimeDropDown = DropDown()
//    let durationTimeDropDown = DropDown()    
//    
//    var selectedDateTime : String?
//    var selectedDurationTime : String?
//    
//    var editDate : Bool = false
//    var fromAvailabilitly : Bool = false
//    
//    var ress = [(String,String)]()
//    
//    var array3 : [String] = {
//        var rr = [String]()
//        
//        for i in 1...12{
//            rr.append("\(i) Hours")
//        }
//        
//        return rr
//    }()
//    
//    
//    var array2 : [String] = {
//        var aa = [String]()
//        
//        aa.append("12:00 AM")
//        for i in 1...11{
//            aa.append("\(i.toString):00 AM")
//        }
//        
//        aa.append("12:00 PM")
//        for i in 1...11{
//            aa.append("\(i.toString):00 PM")
//        }
//        
//        return aa
//    }()
//    
//    override func viewDidLoad() {
//        super.viewDidLoad()
//        
//        
//        vSuccess.isHidden = true
//        
//        updateData()
//        
//        
//        if id == 1{
//            self.showBook()
//        }else{
//            let image = !Provider.isArabic ? #imageLiteral(resourceName: "back") : #imageLiteral(resourceName: "back").imageFlippedForRightToLeftLayoutDirection()
//            let rr = UIBarButtonItem(image: image, style: .plain, target: self, action: #selector(self.navigateBack))
//            self.navigationItem.leftBarButtonItem = rr
//        }
//        
//        self.navigationController?.interactivePopGestureRecognizer?.isEnabled = false
//    }
//    
//    func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
//        return false
//    }
//    
//    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldBeRequiredToFailBy otherGestureRecognizer: UIGestureRecognizer) -> Bool {
//        return false
//    }
//    
//    
//    @IBAction func dateTimeAction(_ sender: Any) {
//        // openStart()
//    }
//    
//    @IBAction func durationTimeAction(_ sender: Any) {
//        // openDuration()
//    }
//    
//    
//    @IBAction func timingAction(_ sender: Any) {
//        //openDate()
//    }
//    
//    func openDate(){
//        let vc = self.storyboard?.instantiateViewController(withIdentifier: "TimingViewController2") as! TimingViewController
//        
//        vc.modalPresentationStyle = .overCurrentContext
//        vc.modalTransitionStyle = .crossDissolve
//        vc.ref = self
//        vc.type = 4
//        
//        
//        self.present(vc, animated: true, completion: nil)
//    }
//    
//    func openStart(){
//        dateTimeDropDown.show()
//    }
//    
//    func openDuration(){
//        durationTimeDropDown.show()
//    }
//    
//    func showSuccessOverlay() {
//        self.vSuccess.isHidden = false
//        self.created = true
//        
//        let tap : UITapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.showBookings))
//        self.vSuccess.addGestureRecognizer(tap)
//        
//        let bb = UIBarButtonItem.init(barButtonSystemItem: .stop , target: self, action: #selector(self.close))
//        
//        self.navigationItem.leftBarButtonItem = bb
//    }
//    
//    func close(){
//        
//        let vc = self.storyboard?.instantiateViewController(withIdentifier: "HomeViewController") as! HomeViewController
//        let nv = UINavigationController.init(rootViewController: vc)
//        let del = UIApplication.shared.delegate as! AppDelegate
//        
//        del.window?.rootViewController = nv
//    }
//    
//    
//    func showBookings()
//    {
//        self.vSuccess.isHidden = true
//        self.performSegue(withIdentifier: _goToBookings, sender: nil)
//    }
//    
//    
//    
//    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
//        if segue.identifier == _goToBookings {
//            let vc = segue.destination as! BookingsViewController
//            vc.type = 2
//            
//        }
//    }
//    
//    func refreshTime(time: Time) {
//        Time.selected = time
//        
//        reloadAvailability()
//        
//    }
//    
//    
//    func updateData(){
//        
//        if id == 0 {
//            
//            
//            self.title = "Booking".localized
//            
//            if let im = self.field.logo_url {
//                self.imgLogo.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
//            }
//            
//            if let hr = self.field.hour_rate{
//                costHourBtn.setTitle("\(hr) \(Provider.shared.currancy)", for: .normal)
//                
//                
//                if self.durationBtn.titleLabel?.text != nil && self.durationBtn.titleLabel?.text != ""{
//                    
//                    if let time = Time.selected {
//                        if let dd = time.duration{
//                            self.costLbl.text = "\(Double(hr) / 60 * Double(dd)!) \(Provider.shared.currancy)"
//                        }
//                    }
//                    
//                }else{
//                    self.costLbl.text = "\(Int(self.field.hour_rate!)) \(Provider.shared.currancy)"
//                }
//                
//            }else{
//                self.costLbl.text = ""
//            }
//            
//            
//            self.nameLbl.text = field.name
//            
//            if let time = Time.selected{
//                
//                gameBtn.setTitle(SearchCell.selectedSoccer!.name, for: .normal)
//                dateBtn.setTitle(time.date, for: .normal)
//                //                startBtn.setTitle(time.start, for: .normal) // TO DO
//                startBtn.setTitle(Time.convert24To12(time: time.start!), for: .normal)
//                //                durationBtn.setTitle("\(Int(time.duration)!)", for: .normal) // TO DO
//                let dd = Int(time.duration!)!
//                durationBtn.setTitle("\(dd / 60)h \(dd % 60)m", for : .normal)
//            }else{
//                dateBtn.setTitle("-", for: .normal)
//                startBtn.setTitle("-", for: .normal)
//                durationBtn.setTitle("-", for: .normal)
//            }
//            
//            
//            if let time = Time.selected{
//                dateBtn.setTitle(time.date, for: .normal)
//                selectedDateTime = time.start
//                selectedDurationTime = time.duration
//                
//            }else{
//                dateBtn.setTitle("-", for: .normal)
//            }
//            
//            
//            // TO DO when book
//            gameBtn.isEnabled = false
//            dateBtn.isEnabled = false
//            startBtn.isEnabled = false
//            durationBtn.isEnabled = false
//            
//            
//        }else {
//            
//            gameBtn.setTitle(self.book.game_type_name, for: .normal)
//            
//            gameBtn.isEnabled = false
//            dateBtn.isEnabled = false
//            startBtn.isEnabled = false
//            durationBtn.isEnabled = false
//            
//            tfNote.isEnabled = false
//            lineNote.isHidden = true
//            tfNote.placeholder = ""
//            
//            bookBtnView.isHidden = true
//            
//            stateStack.isHidden = false
//            costStack.isHidden = true
//            
//            gameBtn.setTitleColor(.black, for: .normal)
//            dateBtn.setTitleColor(UIColor.black, for: .normal)
//            startBtn.setTitleColor(UIColor.black, for: .normal)
//            durationBtn.setTitleColor(UIColor.black, for: .normal)
//            
//            if (UIScreen.main.bounds.height > 568){
//                self.constraintTop.constant = 50
//            }else{
//                self.constraintTop.constant = 40
//            }
//            
//            
//            if let state = self.book.state_id{
//                
//                switch state {
//                case 1:
//                    self.stateLbl.setTitle("Pending".localized, for: .normal)
//                    break
//                case 2:
//                    self.stateLbl.setTitle("Approved".localized, for: .normal)
//                    break
//                case 3:
//                    self.stateLbl.setTitle("Declined".localized, for: .normal)
//                    break
//                case 4:
//                    self.stateLbl.setTitle("Canceled".localized, for: .normal)
//
//                    if !(UIScreen.main.bounds.height > 568){
//                        self.constraintTop.constant = 20
//                        self.constraintBottom.constant = -10
//                    }
//                    let lbl = UILabel()
//                    lbl.font = ThemeManager.shared.boldFont
//                    lbl.textAlignment = .left
//                    lbl.text = "Cancel reason:".localized
//                    lbl.tag = 55
//                    
//                    let reasonLbl = UILabel()
//                    reasonLbl.font = ThemeManager.shared.font?.withSize(16)
//                    reasonLbl.numberOfLines = 0
//                    reasonLbl.textAlignment = .left
//                    reasonLbl.text = self.book.cancellation_reason
//                    reasonLbl.tag = 55
//                    
//                    let emptyLbl = UILabel()
//                    emptyLbl.text = " "
//                    emptyLbl.numberOfLines = 0
//                    emptyLbl.tag = 55
//                    
//                    
//                    for i in stackFull.arrangedSubviews{
//                        if i.tag == 55{
//                            i.removeFromSuperview()
//                        }
//                    }
//                    
//                    stackFull.addArrangedSubview(emptyLbl)
//                    stackFull.addArrangedSubview(lbl)
//                    stackFull.addArrangedSubview(reasonLbl)
//                    break
//                default:
//                    break
//                }
//            }
//            
//            
//            if let im = self.book.logo_url {
//                self.imgLogo.sd_setImage(with: URL.init(string: im), placeholderImage: UIImage(named: logoPlaceholder))
//            }
//            
//            if let cc = self.book.company_name{
//                nameLbl.text = self.book.field_name + " - " + cc
//                
//            }else{
//                nameLbl.text = self.book.field_name
//            }
//            
//            
//            detailsLbl.text = self.book.address
//            costLbl.text = (self.book.total != nil) ? "\(Int(self.book.total!))" + " \(Provider.shared.currancy)" : nil
//            dateBtn.setTitle(self.book.date, for: .normal)
//            
//            if let s = self.book.start{
//                startBtn.setTitle(Time.convert24To12(time: s), for: .normal)
//                durationBtn.setTitle("\(book.duration / 60)h \(book.duration % 60)m", for: .normal)
//                
//                self.title = "\("Ref".localized).# " + self.book.booking_id.toString
//            }
//            
//        }
//        
//    }
//    
//    func showBook(){
//        
//        if let ii = self.book.booking_id{
//            self.showLoading()
//            
//            Provider.shared.show_book(booking_id: ii) { (res, err) in
//                
//                self.hideLoading()
//                
//                if let i = res{
//                    print(i)
//                    
//                    let ff = Book(JSON: i.dictionaryObject!)
//                    self.book = ff!
//                    
//                    self.updateData()
//                    
//                }
//            }
//        }
//    }
//    
//    func setupDateTime(){
//        
//        var array = [String]()
//        
//        array = self.array2
//        
//        for o in array{
//            print(o)
//        }
//        
//        if self.editDate && fromAvailabilitly && array.count > 0{
//
//            if let time = Time.selected{
//                self.selectedDateTime = array[0]
//                self.startBtn.setTitle(Time.convert24To12(time: array[0]), for: .normal)
//                time.start = array[0]
//                
//                var tt3 = [String]()
//                
//                let start = array[0]
//                
//                for j in 0..<self.ress.count {
//                    
//                    let s1 = Int(self.ress[j].0.components(separatedBy: ":")[0])!
//                    var s2 = Int(self.ress[j].1.components(separatedBy: ":")[0])!
//                    
//                    if Int(self.ress[j].1.components(separatedBy: ":")[1])! > 0 {
//                        if Int(self.ress[j].1.components(separatedBy: ":")[0])! == 12{
//                            s2 = 12
//                        }else{
//                            s2 = 24
//                        }
//                    }
//                    
//                    let uu = Time.convert12To24(time: start)
//                    let s0 = Int(uu.components(separatedBy: ":")[0])!
//                    
//                    if s0 >= s1 && s0 < s2{
//                        
//                        
//                        let y = self.ress[j]
//                        var ee = [String]()
//                        
//                        var en2 = Int(y.1.components(separatedBy: ":")[0])!
//                        
//                        if Int(y.1.components(separatedBy: ":")[1])! > 0 {
//                            if Int(y.1.components(separatedBy: ":")[0])! == 12{
//                                en2 = 12
//                            }else{
//                                en2 = 24
//                            }
//                        }
//                        
//                        
//                        for u in s0...en2{
//                            ee.append(Time.convert24To12(time: "\(u):00:00"))
//                        }
//                        
//                        let tmm = Time.convert12To24(time: ee[0])
//                        
//                        if ee.count < 24{
//                            var ttt = 0
//                            let tt2 = tmm.components(separatedBy: ":")[0]
//                            var cnt = ee.count - 1
//                            
//                            while cnt > 0 {
//                                let z = Time.convert12To24(time: ee[cnt])
//                                let q = z.components(separatedBy: ":")[0]
//                                print("Q: \(q) -- TT2: \(tt2)")
//                                
//                                if Int(q)! >= Int(tt2)!{
//                                    ttt  = ttt + 1
//                                }
//                                
//                                cnt = cnt - 1
//                            }
//                            
//                            tt3 = [String]()
//                            
//                            for i in 1...ttt{
//                                tt3.append("\(i) Hours")
//                            }
//                            
//                            self.array3 = tt3
//                        }
//                    }
//                }
//            }
//        }
//        
//        
//        if let selected = self.selectedDateTime{
//            Time.selected.start = selected
//            startBtn.setTitle(Time.convert24To12(time: selected), for: .normal)
//        }
//        else if array.count > 0{
//            
//            self.selectedDateTime = array[0]
//            self.startBtn.setTitle(Time.convert24To12(time: array[0]), for: .normal)
//        }
//        
//        if array.count == 0{
//            self.array3.removeAll()
//            startBtn.setTitle("-", for: .normal)
//            Time.selected.start = nil
//            self.selectedDateTime = nil
//        }
//        
//        dateTimeDropDown.dataSource = array
//        dateTimeDropDown.anchorView = startBtn
//        dateTimeDropDown.direction = .any
//        dateTimeDropDown.bottomOffset = CGPoint(x: 0, y: startBtn.bounds.height * 2 / 3)
//        
//        dateTimeDropDown.selectionAction = { [unowned self] (index, item) in
//            self.startBtn.setTitle(item, for: .normal)
//            let tm = Time.convert12To24(time: item)
//            self.selectedDateTime = tm
//            Time.selected.start = tm
//            
//            var tt3 = [String]()
//            
//            let start = tm
//            
//            for j in 0..<self.ress.count {
//                
//                let s1 = Int(self.ress[j].0.components(separatedBy: ":")[0])!
//                var s2 = Int(self.ress[j].1.components(separatedBy: ":")[0])!
//                
//                if Int(self.ress[j].1.components(separatedBy: ":")[1])! > 0 {
//                    if Int(self.ress[j].1.components(separatedBy: ":")[0])! == 12{
//                        s2 = 12
//                    }else{
//                        s2 = 24
//                    }
//                }
//                
//                let s0 = Int(start.components(separatedBy: ":")[0])!
//                
//                
//                if s0 >= s1 && s0 < s2{
//                    
//                    
//                    let y = self.ress[j]
//                    var ee = [String]()
//                    
//                    var en2 = Int(y.1.components(separatedBy: ":")[0])!
//                    
//                    if Int(y.1.components(separatedBy: ":")[1])! > 0 {
//                        if Int(y.1.components(separatedBy: ":")[0])! == 12{
//                            en2 = 12
//                        }else{
//                            en2 = 24
//                        }
//                    }
//                    
//                    
//                    for u in s0...en2{
//                        ee.append(Time.convert24To12(time: "\(u):00:00"))
//                    }
//                    
//                    let tmm = Time.convert12To24(time: ee[0])
//                    
//                    
//                    
//                    if ee.count < 24{
//                        var ttt = 0
//                        let tt2 = tmm.components(separatedBy: ":")[0]
//                        var cnt = ee.count - 1
//                        
//                        while cnt > 0 {
//
//                            let z = Time.convert12To24(time: ee[cnt])
//                            let q = z.components(separatedBy: ":")[0]
//                            
//                            if Int(q)! >= Int(tt2)!{
//                                ttt  = ttt + 1
//                            }
//                            
//                            cnt = cnt - 1
//                        }
//                        
//                        tt3 = [String]()
//                        
//                        for i in 1...ttt{
//                            tt3.append("\(i) Hours")
//                        }
//                        
//                        self.array3 = tt3
//                    }
//                }
//            }
//            
//            self.setupDurationTime()
//            
//        }
//    }
//    
//    
//    
//    func setupDurationTime(){
//        
//        var array = [String]()
//        
//        array = self.array3
//        
//        if array.count > 0{
//            self.selectedDurationTime = "1"
//            Time.selected.duration = "1"
//        }
//        
//        if array.count == 0 {
//            self.durationBtn.setTitle("-", for: .normal)
//            self.selectedDurationTime = nil
//            Time.selected.duration = nil
//        }
//        
//        
//        if let selected = self.selectedDurationTime{
//            let arr = selected.characters.split { $0 == " " }.map(String.init)
//            durationBtn.setTitle("\(Int(arr.first!)!) Hours", for: .normal)
//        }
//        else if array.count > 0 {
//            durationBtn.setTitle("-", for: .normal)
//            selectedDurationTime = "1"
//        }
//        
//        durationTimeDropDown.dataSource = array
//        durationTimeDropDown.anchorView = durationBtn
//        durationTimeDropDown.direction = .any
//        durationTimeDropDown.bottomOffset = CGPoint(x: 0, y: durationBtn.bounds.height * 2 / 3)
//        
//        durationTimeDropDown.selectionAction = { [unowned self] (index, item) in
//            self.durationBtn.setTitle("\(item)", for: .normal)
//            self.selectedDurationTime = (index + 1).toString
//            Time.selected.duration = (index + 1).toString
//            
//            self.costLbl.text = "\(Int(self.field.hour_rate!) * (index + 1)) \(Provider.shared.currancy)"
//        }
//    }
//    
//    func reloadAvailability(){
//        self.showLoading()
//        
//        let dat = (Time.selected == nil) ? Time.DateToString(date: Date()) : Time.selected.date!
//        
//        Provider.shared.check_availability_field(field_id: self.field.field_id, date : dat) { (res) in
//            self.array2 = [String]()
//            self.ress = res.0
//            
//            for i in res.1{
//                let st = Int(i.0.components(separatedBy: ":")[0])!
//                var en = Int(i.1.components(separatedBy: ":")[0])!
//                
//                if Int(i.1.components(separatedBy: ":")[1])! > 0 {
//                    if Int(i.1.components(separatedBy: ":")[0])! == 12{
//                        en = 12
//                    }else{
//                        en = 24
//                    }
//                }
//                
//                for i in st...en - 1{
//                    self.array2.append(Time.convert24To12(time: "\(i):00:00"))
//                }
//            }
//            
//            self.editDate = true
//            self.updateData()
//            self.hideLoading()
//        }
//    }
//    
//    
//    
//    @IBAction func confirmAction(_ sender: Any) {
//        
//        if !(self.isRequest){
//            var message = ""
//            
//            message += "\n\("Date".localized): \(self.dateBtn.titleLabel!.text!)"
//            message += "\n\("Start".localized): \(self.startBtn.titleLabel!.text!)"
//            message += "\n\("Duration".localized): \(self.durationBtn.titleLabel!.text!) "
//            message += "\n\("Total".localized): \(self.costLbl.text!)"
//            
//            if let time = Time.selected{
//                
//                if let start = time.start, let duration = time.duration{
//                    
//                    let alert = UIAlertController.init(title: "Confirmation".localized, message: message, preferredStyle: .alert)
//                    
//                    alert.addAction(UIAlertAction(title: "Cancel".localized, style: .cancel, handler: nil))
//                    
//                    alert.addAction(UIAlertAction(title: "Ok".localized, style: .default, handler: { (ac) in
//                        self.showLoading()
//                        self.isRequest = true
//                        
//                        Provider.shared.creak_book(field_id: self.field.field_id, start: start, duration: duration, date: time.date!, notes: self.tfNote.text!) { (dat, err) in
//                            self.isRequest = false
//                            
//                            self.hideLoading()
//                            print("DATA: \(dat) -- \(err?.localizedDescription)")
//                            
//                            if let dd = dat, dd["state_id"].intValue == 2{
//                                self.successLbl.text = "Approved".localized
//                            }
//                            else{
//                                self.successLbl.text = "You’ll get notified".localized
//                            }
//                            
//                            self.showSuccessOverlay()
//                            Time.selected = nil
//                        }
//                    }))
//                    
//                    self.present(alert, animated: true, completion: nil)
//                    
//                }else{
//                    self.showErrorMessage(text: "Please select another date".localized)
//                }
//            }else{
//                self.showErrorMessage(text: "Pick date".localized)
//            }
//        }
//    }
//    
//    override func RequestErrorNotificationRecived(_ notification: NSNotification) {
//        super.RequestErrorNotificationRecived(notification)
//        
//        self.isRequest = false
//    }
//    
//    
//    override func connectionErrorNotificationReceived(_ notification: NSNotification) {
//        super.connectionErrorNotificationReceived(notification)
//        
//        self.isRequest = false
//    }
//    
//    override func navigateBack() {
//        
//        
//        if !(self.isRequest){
//            
//            var poped = false
//            
//            if(vSuccess.isHidden == false)
//            {
//                //pop the back stack until the menu view controller
//                for controller in self.navigationController!.viewControllers as Array {
//                    if controller.isKind(of : HomeViewController.self) {
//                        let home = controller as! HomeViewController
//                        poped = true
//                        let _ = self.navigationController?.popToViewController(home, animated: true)
//                        break
//                    }
//                }
//            }
//            if(!poped)
//            {
//                super.navigateBack()
//            }
//        }
//    }
//    
//    func setupAR(btn : UIButton){
//        btn.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
//        btn.titleLabel!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
//        btn.imageView!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
//        btn.contentHorizontalAlignment = .left
//        btn.titleLabel!.textAlignment = .left
//        btn.setImage(#imageLiteral(resourceName: "down"), for: .normal)
//    }
//    
//    func setupAR2(btn : UIButton){
//        btn.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
//        btn.titleLabel!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
//        btn.imageView!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
//        btn.contentHorizontalAlignment = .left
//        btn.titleLabel!.textAlignment = .left
//        btn.tintColor = UIColor.white
//        btn.setImage(#imageLiteral(resourceName: "downLight"), for: .normal)
//    }
//    
//    
//    
//    
//}
