//
//  AvailabilityViewController2.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/4/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import CalendarKit
import DateTools
import MBProgressHUD

class AvailabilityViewController2: DayViewController {
    
    @IBOutlet weak var dateBtn: UIButton!
    
    @IBOutlet var vvs: [UIView]!
    
    var busy = [(String,String)]()
    var available = [(String,String)]()
    
    var array = [(String,String)]()
    var field = Field()
    
    var game = Game()
    
    var range = ("10:00:00","14:00:00")
    
    @IBOutlet weak var dateVV: UIView!
    
    var loadingView = MBProgressHUD.init()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        registerReceivers()
        
        initView()
        
        configureNavigationBar()

        
        if let time = Time.selected, time.date != nil{
            dateBtn.setTitle(time.date!, for: .normal)
        }else{
            dateBtn.setTitle(Time.DateToString(date: Date()), for: .normal)
        }
        
        self.reloadAvailability()
        
        
        print("\(field.open_time!) - \(field.close_time!)")
        self.setTimes(open : field.open_time!, close : field.close_time!)
        

        //        self.reloadData()
    }
    
    
    func reloadTimeView(_ date2 : Date) -> [EventView]{
        var events = [EventView]()
        
        let open = Int(self.field.open_time.components(separatedBy: ":")[0])!
        let close = Int(self.field.close_time.components(separatedBy: ":")[0])!
        
        var hh = 0
        
        if close > open{
            hh = -1 * open
        }
        
        let date = Date.init(year: date2.year, month: date2.month, day: date2.day, hour: hh, minute: 0, second: 0)
        
        
        // create for closed
        if close < open{
            let date = date.add(TimeChunk(seconds: 0,
                                          minutes: 0,
                                          hours: close,
                                          days: 0,
                                          weeks: 0,
                                          months: 0,
                                          years: 0))
            
            let durHoures = open - close
            
            let event = EventView()
            let datePeriod = TimePeriod(beginning: date,
                                        chunk: TimeChunk(seconds: 0,
                                                         minutes: 0,
                                                         hours: durHoures,
                                                         days: 0,
                                                         weeks: 0,
                                                         months: 0,
                                                         years: 0))
            
            
            event.datePeriod = datePeriod
            var info = ["Closed".localized]
            info.append("\(datePeriod.beginning!.add(minutes: hh * 60 * -1).format(with: "HH:mm:ss")!)")
            info.append("\(datePeriod.beginning!.format(with: "HH:mm")!) - \(datePeriod.end!.format(with: "HH:mm")!)")
            event.data = info
            event.tit = "Closed".localized
            event.fff = ThemeManager.shared.font!.withSize(18)
            event.color = .gray
            event.isEmpty = false
            events.append(event)
        }
        
        
        for i in busy{
            let s = i.0.components(separatedBy: ":")
            let e = i.1.components(separatedBy: ":")
            
            let date = date.add(TimeChunk(seconds: 0,
                                          minutes: Int(s[1])!,
                                          hours: Int(s[0])!,
                                          days: 0,
                                          weeks: 0,
                                          months: 0,
                                          years: 0))
            
            
            var durMinutes = Int(e[1])! - Int(s[1])!
            var durHoures = Int(e[0])! - Int(s[0])!
            
            if (e[0].toInt == 0 && e[1].toInt == 0) || (e[0].toInt == 23 && e[1].toInt == 59){
                durMinutes = 59
                durHoures = 24 - Int(s[0])! - 1
            }
            
            
            let event = EventView()
            let datePeriod = TimePeriod(beginning: date,
                                        chunk: TimeChunk(seconds: 0,
                                                         minutes: durMinutes,
                                                         hours: durHoures,
                                                         days: 0,
                                                         weeks: 0,
                                                         months: 0,
                                                         years: 0))
            
            
            event.datePeriod = datePeriod
            var info = ["Busy".localized]
            info.append("\(datePeriod.beginning!.add(minutes: hh * 60 * -1).format(with: "HH:mm:ss")!)")
            info.append("\(datePeriod.beginning!.format(with: "HH:mm")!) - \(datePeriod.end!.format(with: "HH:mm")!)")
            event.data = info
            event.tit = "Busy".localized
            event.fff = ThemeManager.shared.font!.withSize(18)
            event.color = ThemeManager.shared.customRed//.red
            event.isEmpty = false
            events.append(event)
        }
        
        for j in available{
            
            let s = j.0.components(separatedBy: ":")
            let e = j.1.components(separatedBy: ":")
            
            for i in Int(s[0])!...Int(e[0])! {
                
                if (i == Int(e[0])! && Int(e[1])! == 59) || i != Int(e[0])!{
                    
                    var arr = [0,1]
                    
                    if i == Int(s[0])! {
                        if Int(s[1])! > 0 {
                            if Int(s[1])! > 30{
                                arr = []
                            }else{
                                arr = [1]
                            }
                        }
                    }
                    
                    for j in arr{
                        
                        let cnt = j * 30
                        
                        let date = date.add(TimeChunk(seconds: 0,
                                                      minutes: cnt,
                                                      hours: i,
                                                      days: 0,
                                                      weeks: 0,
                                                      months: 0,
                                                      years: 0))
                        
                        let event = EventView()
                        var duration = 30
                        
                        if i == 23 && cnt == 30{
                            duration = 29
                        }
                        
                        let datePeriod = TimePeriod(beginning: date,
                                                    chunk: TimeChunk(seconds: 0,
                                                                     minutes: duration,
                                                                     hours: 0,
                                                                     days: 0,
                                                                     weeks: 0,
                                                                     months: 0,
                                                                     years: 0))
                        
                        event.datePeriod = datePeriod
                        var info = ["TEST \(i)"]
                        info.append("\(datePeriod.beginning!.add(minutes: hh * 60 * -1).format(with: "HH:mm:ss")!)")
                        info.append("\(datePeriod.beginning!.format(with: "HH:mm")!) - \(datePeriod.end!.format(with: "HH:mm")!)")
                        event.data = info
                        event.color = .white
                        event.isEmpty = true
                        events.append(event)
                    }
                }
            }
        }
        
        
        if let time = Time.selected{
            if time.date == Time.DateToString(date: Date()){
                print("TODAY")
                
                let eventsBefore = events.filter({ (ee) -> Bool in
                    ee.datePeriod.beginning!.hour * 60 + ee.datePeriod.beginning!.minute < Date().hour * 60 + Date().minute
                })
                
                var eventsArr = [(Int,Int)]()
                
                for ee in eventsBefore {
                    let b = ee.datePeriod.beginning!
                    let e  = ee.datePeriod.end!
                    
                    print("EE : \(b)-\(e)")
                    print("FM : \(b.hour) - \(e.hour)")
                    print("F : \(b.hour * 60 + b.minute) - To : \(e.hour * 60 + e.minute)")
                    eventsArr.append((b.hour * 60 + b.minute, e.hour * 60 + e.minute))
                }
                
//                var f1 = (hh * 60)
                let f2 = Date().hour * 60 + Date().minute
                var temp =  0//(hh * 60)
                
                let date = Date.init(year: date2.year, month: date2.month, day: date2.day, hour: 0, minute: 0, second: 0)

                
                eventsArr = eventsArr.sorted(by: { (a1, a2) -> Bool in
                    a1.0 < a2.0
                })
                
                if eventsArr.count > 0 {
                    for i in eventsArr{
                        print("NN : \(temp) - \(i.0) - \(i.1)")
                        
                        if temp >= i.0 && temp <= i.1{
                            //break
                            temp = i.1
                        }
                            
                        else if temp < i.0 {
                            let date = date.add(TimeChunk(seconds: 0,
                                                          minutes: temp,
                                                          hours: 0,
                                                          days: 0,
                                                          weeks: 0,
                                                          months: 0,
                                                          years: 0))
                            
                            let durMinutes = i.0 - temp
                            
                            if i.1 >= f2{
                                // durMinutes = f2 - temp
                            }
                            
                            print("durMinutes : \(durMinutes)")
                            
                            let event = EventView()
                            let datePeriod = TimePeriod(beginning: date,
                                                        chunk: TimeChunk(seconds: 0,
                                                                         minutes: durMinutes,
                                                                         hours: 0,
                                                                         days: 0,
                                                                         weeks: 0,
                                                                         months: 0,
                                                                         years: 0))
                            
                            
                            event.datePeriod = datePeriod
                            var info = ["Previous"]
                            info.append("\(datePeriod.beginning!.add(minutes: hh * 60 * -1).format(with: "HH:mm:ss")!)")
                            info.append("\(datePeriod.beginning!.format(with: "HH:mm")!) - \(datePeriod.end!.format(with: "HH:mm")!)")
                            event.data = info
//                            event.tit = "Previous"
                            event.color = .white
                            event.isEmpty = false
                            events.append(event)
                            temp = i.1
                        }
                    }
                }else{
                    let date = date.add(TimeChunk(seconds: 0,
                                                  minutes: temp,
                                                  hours: 0,
                                                  days: 0,
                                                  weeks: 0,
                                                  months: 0,
                                                  years: 0))
                    
                    let durMinutes = f2 - temp
                    
                    
                    print("durMinutes : \(durMinutes)")
                    
                    let event = EventView()
                    let datePeriod = TimePeriod(beginning: date,
                                                chunk: TimeChunk(seconds: 0,
                                                                 minutes: durMinutes,
                                                                 hours: 0,
                                                                 days: 0,
                                                                 weeks: 0,
                                                                 months: 0,
                                                                 years: 0))
                    
                    
                    event.datePeriod = datePeriod
                    var info = ["Previous"]
                    info.append("\(datePeriod.beginning!.add(minutes: hh * 60 * -1).format(with: "HH:mm:ss")!)")
                    info.append("\(datePeriod.beginning!.format(with: "HH:mm")!) - \(datePeriod.end!.format(with: "HH:mm")!)")
                    event.data = info
//                    event.tit = "Previous"
                    event.color = .white
                    event.isEmpty = false
                    events.append(event)
                }
                
                
            }
        }
        
        
        for e in events{
            print("EWQ \(e.data)")
        }
        
        return events
    }
    
    override func eventViewsForDate(_ date: Date) -> [EventView] {
        return reloadTimeView(date)
    }
    
    
    override func dayViewDidSelectEventView(_ eventview: EventView) {
        
        print("Event has been selected: \(eventview.data) - \(eventview.isEmpty)")
        
        
        if eventview.data[0] == "Previous"{
            self.showErrorMessage(text: "You can't book before now".localized)
        }
        
        if eventview.isEmpty{
            let storyBoard = UIStoryboard.init(name: "Main", bundle: nil)
            let uu =  storyBoard.instantiateViewController(withIdentifier: "TimeDurationViewController") as! TimeDurationViewController
            
            //            let s = Time.convert24To12(time: self.range.0)
            //            let e = Time.convert24To12(time: self.range.1)
            
            let selected = eventview.data[1].components(separatedBy: ":")
            
            for i in self.available{
                let s = i.0.components(separatedBy: ":")
                let e = i.1.components(separatedBy: ":")
                
                if Int(selected[0])! >= Int(s[0])! && Int(selected[0])! <= Int(e[0])!{
                    let r1 = Time.convert24To12(time: i.0)
                    let r2 = Time.convert24To12(time: i.1)
                    uu.range = (r1,r2)
                    print("DUW : \(r1,r2)")
                    uu.field = self.field
                    uu.firstTime = Time.convert24To12(time: eventview.data[1])
                    if let g = SearchCell.selectedSoccer{
                        uu.minimum = g.minimum_duration
                        uu.increament = g.increament_factor
                    }
                    break
                }
            }
            
            
            let nv = UINavigationController.init(rootViewController: uu)
            self.present(nv, animated: true, completion: nil)
        }
    }
    
    override func dayViewDidLongPressEventView(_ eventView: EventView) {
        print("Event has been longPressed: \(eventView.data)")
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if Time.selected == nil{
            if let t = self.dateBtn.titleLabel?.text{
                let y = Time()
                y.date = t
                Time.selected = y
            }
        }
    }
    
    func initView(){
        
        self.title = "Check availability".localized
        
        dateBtn.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        dateBtn.titleLabel!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        dateBtn.imageView!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        
        
//        let vvv = UIView(frame: CGRect(x: 0, y: 0, width: self.view.frame.width / 2, height: 50))
//        vvv.backgroundColor = .yellow
//        vvv.isUserInteractionEnabled = true
//        vvv.addGestureRecognizer(tap)
//        
//        self.view.addSubview(vvv)

        
        for i in vvs{
//            var tap = UITapGestureRecognizer.init(target: self, action: #selector(self.openDate))
//            tap.numberOfTapsRequired = 1
//            tap.cancelsTouchesInView = false
//            
//            i.addGestureRecognizer(tap)
            i.isHidden = true
        }
        
        self.view.sendSubview(toBack: dayView)

        self.dateBtn.addTarget(self, action: #selector(self.openDate), for: .touchUpInside)
        
        initNavigation()
    }
    
    func initNavigation(){
        let tap = myTapGuester.init(target: self, action: #selector(self.openDate))
        tap.numberOfTapsRequired = 1
        //        tap.cancelsTouchesInView = false
        tap.tag = 2017
        
        
        self.navigationController?.navigationBar.addGestureRecognizer(tap)
    }
    
    
    func removeGuester(){
        
        
        for i in self.navigationController!.navigationBar.gestureRecognizers!{
            if let t = i as? myTapGuester{
                print("MYTAP")
                if t.tag == 2017{
                    print("REMOVED")
                    self.navigationController!.navigationBar.removeGestureRecognizer(i)
                    return
                }
            }
            
        }
        
    }
    
    func closeAction(){
        Time.selected = nil
        self.dismiss(animated: true, completion: nil)
        let _ = self.navigationController?.popViewController(animated: true)
    }
    
    func configureNavigationBar() {
        
        
        self.navigationController?.isNavigationBarHidden = false
//        self.navigationController?.navigationBar.shadowImage = UIImage()
        self.navigationController?.navigationBar.backgroundColor = UIColor.groupTableViewBackground
        
        
//        self.navigationItem.hidesBackButton = false
//        let rr = UIBarButtonItem(title: "", style: UIBarButtonItemStyle.plain, target: nil, action:nil)
//        
//        self.navigationItem.backBarButtonItem = rr
        
        let image = !Provider.isArabic ? #imageLiteral(resourceName: "back") : #imageLiteral(resourceName: "back").imageFlippedForRightToLeftLayoutDirection()
        let rr2 = UIBarButtonItem(image: image, style: .plain, target: self, action: #selector(self.closeAction))
        self.navigationItem.leftBarButtonItem = rr2
    }
    
    
    func reloadAvailability(){
        self.showLoading()
        //
        
        let dat = (Time.selected == nil) ? Time.DateToString(date: Date()) : Time.selected.date!
        
        if Time.selected == nil{
            let t = Time()
            t.date = dat
            Time.selected = t
        }
        
        //
        Provider.shared.check_availability_field(field_id: self.field.field_id, date : dat) { (res) in
            self.array = res.1
            
            self.busy = res.0
            self.available = res.1
            
            self.reloadData()
            self.hideLoading()
            
            self.view.bringSubview(toFront: self.dateBtn)
            // TO DO
        }
    }
    
    func hideLoading(){
        self.loadingView.hide(animated: true)
    }
    
    func showLoading(){
        self.loadingView = MBProgressHUD.showAdded(to: self.view, animated: true)
    }
    
    func showErrorMessage(text: String) {
        KSToastView.ks_showToast(text.html2String, duration: 3)
    }
    
    
    func connectionErrorNotificationReceived(_ notification: NSNotification) {
        self.hideLoading()
        
        print("connectionErrorNotificationReceived")
        if let msg = notification.object as? String{
            self.showErrorMessage(text : msg)
        }
    }
    
    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        unregisterReceivers()
        
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        removeGuester()
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
    
    
    func RequestErrorNotificationRecived(_ notification : NSNotification)
    {
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
                User.saveMe(me: User.init())
                
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
    }
    
    
    @IBAction func dateAction(_ sender: Any) {
        openDate()
    }
    
    
    func openDate(){
        print("OPEN DATE")
        
    let vc = self.storyboard?.instantiateViewController(withIdentifier: "TimingViewController2") as! TimingViewController
    
    vc.modalPresentationStyle = .overCurrentContext
    vc.modalTransitionStyle = .crossDissolve
    vc.ref = self
    vc.type = 1
    vc.fromAvaib = true
    
        
    self.present(vc, animated: false, completion: nil)
}
    
}


extension AvailabilityViewController2 : refreshTime{
    
    
    func goToBooking(){
        
        let me = User.getCurrentUser()
        if me.statues_key == User.USER_STATUES.USER_REGISTERED.rawValue{
            self.performSegue(withIdentifier: _goToBooking, sender: nil)
        }
        else{
            //            self.showErrorMessage(text : msg)
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == _goToBooking{
            let des = segue.destination as! BookViewController
            des.field = self.field
            
            var tt = [String]()
            var tt3 = [String]()
            
            for i in 0..<self.array.count{
                let st = Int(self.array[i].0.components(separatedBy: ":")[0])!
                var s2 = Int(self.array[i].1.components(separatedBy: ":")[0])!
                
                print("PPPP \(self.array[i].1)")
                
                if Int(self.array[i].1.components(separatedBy: ":")[1])! > 0 {
                    if Int(self.array[i].1.components(separatedBy: ":")[0])! == 12{
                        s2 = 12
                    }else{
                        s2 = 24
                    }
                }
                
                for j in st...s2 - 1{
                    tt.append(Time.convert24To12(time: "\(j):00:00"))
                }
            }
            
//            des.array2 = tt
            
            
            let start = Time.selected.start
            
            for j in 0..<self.array.count {
                if start == self.array[j].0{
                    
                    let y = self.array[j]
                    
                    var ee = [String]()
                    
                    print("RR TT YY: \(j)")
                    
                    let st2 = Int(y.0.components(separatedBy: ":")[0])!
                    var en2 = Int(y.1.components(separatedBy: ":")[0])!
                    
                    
                    if Int(y.1.components(separatedBy: ":")[1])! > 0 {
                        if Int(y.1.components(separatedBy: ":")[0])! == 12{
                            en2 = 12
                        }else{
                            en2 = 24
                        }
                    }
                    
                    
                    for u in st2...en2{
                        ee.append(Time.convert24To12(time: "\(u):00:00"))
                    }
                    
                    let tmm = Time.convert12To24(time: ee[0])
                    
                    print("EE COUNT: \(ee.count)")
                    for o in ee{
                        print("O: \(o) -- ")
                    }
                    
                    if ee.count < 24{
                        var ttt = 0
                        let tt2 = tmm.components(separatedBy: ":")[0]
                        var cnt = ee.count - 1
                        
                        while cnt > 0 {
                            let z = Time.convert12To24(time: ee[cnt])
                            let q = z.components(separatedBy: ":")[0]
                            
                            print("Q:\(q)--TT2:\(tt2)")
                            
                            
                            if Int(q)! >= Int(tt2)!{
                                print(ttt)
                                ttt  = ttt + 1
                            }
                            
                            cnt = cnt - 1
                        }
                        
                        tt3 = [String]()
                        
                        print("TTT: \(ttt)")
                        
                        for i in 1...ttt{
                            tt3.append("\(i) Hours")
                        }
                        
                    }
                    
//                    des.array3 = tt3
                    
//                    des.ress = self.array
//                    des.fromAvailabilitly = true
                }
            }
            
            
        }
    }
    
    
    func refreshTime(time: Time) {
        Time.selected = time
        dateBtn.setTitle(time.date, for: .normal)
        
        self.reloadAvailability()
    }
    
    
}


