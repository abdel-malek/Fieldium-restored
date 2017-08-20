//
//  TimingViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/2/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import DropDown
import FSCalendar
import CalendarKit

class TimingViewController: UIViewController, FSCalendarDataSource, FSCalendarDelegate,UIGestureRecognizerDelegate {
    
    
    @IBOutlet weak var stackView: UIStackView!
    @IBOutlet weak var vv: UIView!
    @IBOutlet weak var calendar: FSCalendar!
    
    var ref : refreshTime?
    
    let dateTimeDropDown = DropDown()
    let durationTimeDropDown = DropDown()
    
    @IBOutlet weak var dateTimeBtn: UIButton!
    @IBOutlet weak var durationTimeBtn: UIButton!
    
    var selectedDateTime : String?
    var selectedDurationTime : String?
    
    var type = 0
    var fromAvaib = false
    
    @IBOutlet weak var picker1: UIDatePicker!
    @IBOutlet weak var picker2: UIDatePicker!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initView()
        
    }
    
    func initView(){
        
        self.setupAR(btn: dateTimeBtn)
        self.setupAR(btn: durationTimeBtn)
        
        if let time = Time.selected{
            if let date = Time.StringToDate(dateString: time.date){
                self.calendar.select(date)
            }
            
            selectedDateTime = time.start
            selectedDurationTime = time.duration
        }else{
            self.calendar.select(Date())
        }
        
        self.calendar.scope = .month
        self.calendar.delegate = self
        
        self.vv.layer.cornerRadius = 10
        
        
        if type == 2{
            
            picker1.locale = NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
            picker2.locale = NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
            
            picker1.semanticContentAttribute = .forceLeftToRight
            picker2.semanticContentAttribute = .forceLeftToRight

            
            if let time = Time.selected , time.duration != nil {
                picker2.countDownDuration = Double(time.duration!)! * 60
                let y = Time.StringToDate3(time: Time.convert24To12(time: time.start!))
                self.picker1.setDate(y.add(minutes: -120), animated: true)
            }else{
                picker2.countDownDuration = 60 * 60
                var dd = Date()
                dd = dd.add(minutes: dd.minute * -1)
                
                self.picker1.date = dd
            }
            
            
            let start24 = Time.DateToString2(date: self.picker1.date)
            self.selectedDateTime = Time.convert12To24(time: start24)
            self.selectedDurationTime = String(Int(picker2.countDownDuration) / 60)
            
        }
        
    }
    
    // TO DO 1.1
    func minimumDate(for calendar: FSCalendar) -> Date {
        return Date()
    }
    
    
    func calendar(_ calendar: FSCalendar, didSelect date: Date, at monthPosition: FSCalendarMonthPosition) {
        
        print("did select date \(Time.dateFormatter.string(from: date))")
        let selectedDates = calendar.selectedDates.map({Time.dateFormatter.string(from: $0)})
        print("selected dates is \(selectedDates)")
        
        if monthPosition == .next || monthPosition == .previous {
            calendar.setCurrentPage(date, animated: true)
        }
    }
    
    
    
    
    func setupDateTime(){
        
        var array = [String]()
        
        array.append("12:00 AM")
        for i in 1...11{
            array.append("\(i.toString):00 AM")
        }
        
        array.append("12:00 PM")
        for i in 1...11{
            array.append("\(i.toString):00 PM")
        }
        
        if let selected = self.selectedDateTime{
            dateTimeBtn.setTitle(Time.convert24To12(time: selected), for: .normal)
        }
        else if array.count > 0 {
            dateTimeBtn.setTitle(array[0], for: .normal)
            self.selectedDateTime = Time.convert12To24(time: array[0])
        }
        
        dateTimeDropDown.dataSource = array
        dateTimeDropDown.anchorView = dateTimeBtn
        dateTimeDropDown.direction = .any
        dateTimeDropDown.bottomOffset = CGPoint(x: 0, y: dateTimeBtn.bounds.height * 2 / 3)
        
        dateTimeDropDown.selectionAction = { [unowned self] (index, item) in
            self.dateTimeBtn.setTitle(item, for: .normal)
            
            self.selectedDateTime = Time.convert12To24(time: item)
        }
    }
    
    
    
    func setupDurationTime(){
        
        var array = [String]()
        
        
        for i in 1...12{
            array.append("\(i) \("Hours".localized)")
        }
        
        if let selected = self.selectedDurationTime{
            let arr = selected.characters.split { $0 == " " }.map(String.init)
            durationTimeBtn.setTitle(arr.first! + " Hours".localized, for: .normal)
        }
        else if array.count > 0 {
            durationTimeBtn.setTitle(array[0], for: .normal)
            selectedDurationTime = "1"
        }
        
        durationTimeDropDown.dataSource = array
        durationTimeDropDown.anchorView = durationTimeBtn
        durationTimeDropDown.direction = .any
        durationTimeDropDown.bottomOffset = CGPoint(x: 0, y: durationTimeBtn.bounds.height * 2 / 3)
        
        durationTimeDropDown.selectionAction = { [unowned self] (index, item) in
            self.durationTimeBtn.setTitle(item, for: .normal)
            self.selectedDurationTime = (index + 1).toString
        }
    }
    
    @IBAction func dateTimeAction(_ sender: Any) {
        //dateTimeDropDown.show()
    }
    
    @IBAction func durationTimeAction(_ sender: Any) {
        //durationTimeDropDown.show()
    }
    
    @IBAction func okAction(_ sender: Any) {
        
        if type == 2{
            let start24 = Time.DateToString2(date: self.picker1.date)

            self.selectedDateTime = Time.convert12To24(time: start24)
            self.selectedDurationTime = String(Int(picker2.countDownDuration) / 60)
        }
        
                
        let selectedDates = calendar.selectedDates.map({Time.dateFormatter.string(from: $0)})
        
        if let select = calendar.selectedDates.first{
            let one = select.addingTimeInterval(7200)
            
            let t1 = Time.DateToString(date: one)
            let t2 = Time.DateToString(date: Date())
            
            print("T1T1 : \(t1)")
            
            var d1 = Date()
            
            if let dd1 = Time.StringToDate(dateString: t1){
                d1 = dd1
            }else{
                d1 = Time.StringToDate(dateString: Time.DateToString(date: select.add(minutes: -120)))!
            }
            
            
            let d2 = Time.StringToDate(dateString: t2)!
            
            if d1 >= d2 {
                let time = Time()
                time.date = selectedDates.first!
                print("ff : \(selectedDates.first!)")
                
                if type == 2{
                    
                    if let g = SearchCell.selectedSoccer{
                        if selectedDurationTime!.toInt < g.minimum_duration{
                            KSToastView.ks_showToast("the duration must be minimum \(g.minimum_duration!) mins")
                            return
                        }
                    }
                    
                    time.duration = selectedDurationTime!
                    time.start = selectedDateTime!
                }
                    
                if type == 1 {
                    Time.selected = time
                    Time.selected.duration = nil
                    Time.selected.start = nil
                }
                
                if type == 4{
                    Time.selected.date = time.date
                }
                
                if type == 2 {
                    Time.selected = time
                }
                
                
                self.ref?.refreshTime(time: time)
                
                self.dismiss(animated: !fromAvaib, completion: nil)
                
            }else{
                KSToastView.ks_showToast("invalid date".localized, duration: 3)
            }
        }
    }
    
    @IBAction func cancelAction(_ sender: Any) {
        self.dismiss(animated: !fromAvaib, completion: nil)
    }
    
    func onTouch() {
        self.dismiss(animated: true, completion: nil)
    }
    
    func setupAR(btn : UIButton){
        btn.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        btn.titleLabel!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        btn.imageView!.transform = CGAffineTransform(scaleX: -1.0, y: 1.0);
        btn.contentHorizontalAlignment = .left
        btn.titleLabel!.textAlignment = .left
        btn.setImage(#imageLiteral(resourceName: "down"), for: .normal)
        btn.tintColor = ThemeManager.shared.customBlue
    }
    
    @IBOutlet weak var datePic: UIDatePicker!
    
    @IBOutlet weak var datePic2: UIDatePicker!
    
    var y = Date()
    var r = 0
    
}
extension Date {
    func add(minutes: Int) -> Date {
        return Calendar(identifier: .gregorian).date(byAdding: .minute, value: minutes, to: self)!
    }
    
        func interval(ofComponent comp: Calendar.Component, fromDate date: Date) -> Int {
            
            let currentCalendar = Calendar.current
            
            guard let start = currentCalendar.ordinality(of: comp, in: .era, for: date) else { return 0 }
            guard let end = currentCalendar.ordinality(of: comp, in: .era, for: self) else { return 0 }
            
            return end - start
        }

}

