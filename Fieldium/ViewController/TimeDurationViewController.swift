import UIKit
//import DateTools
import CalendarKit

class TimeDurationViewController: BaseViewController {
    
    
    @IBOutlet weak var vv1: UIView!
    @IBOutlet weak var vv2: UIView!
    @IBOutlet weak var vv3: UIView!
    
    @IBOutlet weak var vv: UIView!
    
    @IBOutlet weak var startLbl: UILabel!
    
    @IBOutlet weak var availableLbl: UILabel!
    
    @IBOutlet weak var btn: UIButton!
    
    
    @IBOutlet weak var picker1: UIPickerView!
    @IBOutlet weak var picker2: UIPickerView!
    
    var field : Field!
    
    var range : (String,String)!
    var firstTime : String!
    
    var hours = [String]()
    var minutes = ["00","15","30","45"]
    
    var hoursDuration = [String]()
    var minutesDuration = ["0","15","30","45"]
    
    var minimum = 45//Int()
    var increament = 15//Int()
    
    var ok = false
    
    var timeAM = ["AM","PM"]
    
    var selectedTime : String!
    var selectedDuration : Int = 0
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        self.btn.addTarget(self, action: #selector(self.goToBook), for: UIControlEvents.touchUpInside)
        
        picker1.delegate = self
        picker1.dataSource = self
        
        picker2.delegate = self
        picker2.dataSource = self
        
        
        picker1.semanticContentAttribute = .forceLeftToRight
        picker2.semanticContentAttribute = .forceLeftToRight
        
        
        setupData()
        setupViews()
        
        self.title = "Timing".localized
        
        self.availableLbl.text = "\("Available range is from:".localized) \(range!.0) - \("to:".localized) \(range!.1)"
        
    }
    
    func setupViews(){
        
        vv.layer.borderColor = ThemeManager.shared.customBlue.cgColor
        vv.layer.borderWidth = 1

        setupVV(vv3)
        
        let close = UIBarButtonItem.init(barButtonSystemItem: .stop, target: self, action: #selector(self.closeAction))
        self.navigationItem.leftBarButtonItem = close
        
        
    }
    
    func setupVV(_ vv : UIView){
        vv.clipsToBounds = true
        vv.layer.cornerRadius = 20
        vv.layer.borderColor = ThemeManager.shared.customBlue.cgColor
        vv.layer.borderWidth = 1
    }
    
    func setupData(){
        
        let d = 60 / increament
        
        self.minutesDuration.removeAll()
        self.minutes.removeAll()
        
        for i in 0..<d{
            self.minutes.append((i * increament).toString)
            self.minutesDuration.append((i * increament).toString)
        }
        
        if let durations = range{
            print("DURAT : \(durations)")
            picker1.selectRow(0, inComponent: 0, animated: true)
            
            picker2.selectRow(0, inComponent: 0, animated: true)
            picker2.selectRow(3, inComponent: 1, animated: true)
            
            let a1 = Time.convert12To24(time: durations.0)
            let a2 = Time.convert12To24(time: durations.1)
            
            let s = Int(a1.components(separatedBy: ":")[0])!
            let e = Int(a2.components(separatedBy: ":")[0])!
            
            self.hours.removeAll()
            self.hoursDuration.removeAll()
            
            for i in s...e {
                if (i == e && Int(a2.components(separatedBy: ":")[1])! > 0) || i != e{
                    self.hours.append(Time.convert24To12_2(time: i.toString))
                }
            }
            
            let d = e - s
            
            for i in 0...d{
                self.hoursDuration.append(i.toString)
            }
            
            self.picker1.reloadAllComponents()
            self.picker2.reloadAllComponents()
            
            for i in 0..<minutes.count{
                if minutes[i] == a1.components(separatedBy: ":")[1]{
                    self.picker1.selectRow(i, inComponent: 1, animated: true)
                }
            }
            
            
            
            if let t = firstTime{
                for i in 0..<self.hours.count{
                    print("HOURS : \(self.hours[i])")
                    if self.hours[i] == t.components(separatedBy: ":")[0] {
                        self.picker1.selectRow(i, inComponent: 0, animated: true)
                        break
                    }
                }
                
                let minute = t.components(separatedBy: ":")[1].components(separatedBy: " ")[0]
                for i in 0..<self.minutes.count{
                    if Int(self.minutes[i]) == Int(minute) {
                        self.picker1.selectRow(i, inComponent: 1, animated: true)
                        break
                    }
                }
                
                if t.components(separatedBy: " ")[1] == "AM" {
                    self.picker1.selectRow(0, inComponent: 2, animated: true)
                }else{
                    self.picker1.selectRow(1, inComponent: 2, animated: true)
                }

            }
            
            
            
            let hh = minimum / 60
            let ss = minimum % 60
            
            
            self.picker2.selectRow(hh, inComponent: 0, animated: true)
            for i in 0..<self.hoursDuration.count{
                if self.hoursDuration[i] == hh.toString {
                    self.picker2.selectRow(i, inComponent: 0, animated: true)
                    break
                }
            }

            for i in 0..<self.minutesDuration.count{
                if self.minutesDuration[i] == ss.toString {
                    self.picker2.selectRow(i, inComponent: 1, animated: true)
                    break
                }
            }

            
            
            
            refresh()
        }
    }
    
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
//        self.refresh()
    }
    
    func refresh() {
        
        var mm = minutes[picker1.selectedRow(inComponent: 1)]
        
        if mm == "0"{
            mm = "00"
        }
        
        self.selectedDuration = (Int(hoursDuration[picker2.selectedRow(inComponent: 0)])! * 60) + Int(minutesDuration[picker2.selectedRow(inComponent: 1)])!

        
        if selectedDuration < minimum {
            print("\(selectedDuration) - \(minimum)")
            KSToastView.ks_showToast("\("Minimum duration is".localized) \(minimum) min")
            
            self.selectedDuration = minimum
            
            let hh = minimum / 60
            let mm = minimum % 60
            
            self.picker2.selectRow(hh, inComponent: 0, animated: true)
            for i in 0..<self.hoursDuration.count{
                if self.hoursDuration[i] == hh.toString {
                    self.picker2.selectRow(i, inComponent: 0, animated: true)
                    break
                }
            }
            
            for i in 0..<self.minutesDuration.count{
                if self.minutesDuration[i] == mm.toString {
                    self.picker2.selectRow(i, inComponent: 1, animated: true)
                    break
                }
            }
        }
        
        self.selectedTime = hours[picker1.selectedRow(inComponent: 0)] + ":" + mm + " " + timeAM[picker1.selectedRow(inComponent: 2)]
        
        self.selectedDuration = (Int(hoursDuration[picker2.selectedRow(inComponent: 0)])! * 60) + Int(minutesDuration[picker2.selectedRow(inComponent: 1)])!
        
        print("FIRST: \(self.selectedTime) - \(self.selectedDuration)")
        
        let dd = Time.StringToDate3(time: selectedTime!)
        let ddNew = dd.add(minutes: self.selectedDuration - 1)
        
        let dd1 = Time.StringToDate3(time: self.range!.0)
        let dd2 = Time.StringToDate3(time: self.range!.1)
        
        print("d: \(dd)\nd1: \(dd1)\n\(dd2)")
        
        
        if ddNew >= dd1 && ddNew <= dd2 && dd >= dd1{
            self.ok = true
            print("TRUE")
        }else{
            self.ok = false
            print("FALSE")
        }

        
        var txt = ""
        txt += "\("Your booking is on:".localized) \(Time.selected.date!)\n"
        txt += "\("From:".localized) " + selectedTime + " - \("to:".localized) \(Time.DateToString2(date: ddNew.add(minutes: -119)))"
        startLbl.text = txt
        startLbl.adjustsFontSizeToFitWidth = true
        
        if !self.ok {
            self.btn.setTitle("Out of available range".localized, for: .normal)
            self.btn.backgroundColor = UIColor.init(red: 242/255, green: 101/255, blue: 57/255, alpha: 1)
        }else{
            self.btn.setTitle("Continue".localized, for: .normal)
            self.btn.backgroundColor = .clear
        }
        
    }
    
    func secondsToHoursMinutesSeconds (seconds : Int) -> (Int, Int, Int) {
        return (seconds / 3600, (seconds % 3600) / 60, (seconds % 3600) % 60)
    }
    
}



extension TimeDurationViewController : UIPickerViewDelegate, UIPickerViewDataSource{
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        if pickerView == picker1{
            return 3
        }else{
            return 2
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        if pickerView == picker1{
            if component == 0{
                return self.hours.count
            }else if component == 1{
                return self.minutes.count
            }else{
                return self.timeAM.count
            }
        }else{
            if component == 0 {
                return self.hoursDuration.count
            }else{
                return self.minutes.count
            }
        }
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        
        if pickerView == picker1{
            if component == 0{
                return self.hours[row]
            }else if component == 1{
                return self.minutes[row]
            }else{
                return self.timeAM[row]
            }
        }else if pickerView == picker2{
            if component == 0 {
                return self.hoursDuration[row]
            }else{
                return self.minutes[row]
            }
        }
        
        return ""
    }
    
    func pickerView(_ pickerView: UIPickerView, viewForRow row: Int, forComponent component: Int, reusing view: UIView?) -> UIView {
        
        let ll = UILabel()
        ll.font =  ThemeManager.shared.boldFont!
        ll.textAlignment = .center
        ll.adjustsFontSizeToFitWidth = true
        
        
        if pickerView == picker1{
            if component == 0{
                ll.text = self.hours[row]
                ll.textAlignment = .right
            }else if component == 1{
                ll.text = self.minutes[row]
                ll.textAlignment = .center
            }else{
                ll.text = self.timeAM[row]
                ll.textAlignment = .left
            }
        }else if pickerView == picker2{
            if component == 0 {
                ll.textAlignment = .left
                ll.text = self.hoursDuration[row] + " hours"
            }else{
                ll.textAlignment = .right
                ll.text = self.minutesDuration[row] + " min"
            }
        }
        
        return ll
        
    }
    
    func pickerView(_ pickerView: UIPickerView, widthForComponent component: Int) -> CGFloat {
        return pickerView.frame.width / 4
    }
    
    func pickerView(_ pickerView: UIPickerView, rowHeightForComponent component: Int) -> CGFloat {
        return 40
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        
        self.ok = false
        
        if pickerView == picker1{
            var time = ""
            
            if component == 0 {
                time = time + self.hours[row]
            }else if component == 1{
                time = time + ":" + self.minutes[row]
            }else{
                time = time + " " + self.timeAM[row]
            }
            
            self.selectedTime = time
        }else{
            var dur = 0
            
            if component == 0{
                dur = dur + (Int(self.hoursDuration[row])! * 60)
            }else{
                dur = dur + Int(self.minutesDuration[row])!
            }
            
            self.selectedDuration = dur
        }
        
        refresh()
    }
    
    func closeAction(){
        Time.selected = nil
        self.dismiss(animated: true, completion: nil)
    }
    
    
    func goToBook(){
        refresh()

        
        if self.ok{
            
            print(Time.selected.date)
            Time.selected.start = Time.convert12To24(time: self.selectedTime!)
            Time.selected.duration = selectedDuration.toString
            
            let storyBoard = UIStoryboard.init(name: "Main", bundle: nil)
            let vc = storyBoard.instantiateViewController(withIdentifier: "BookViewController") as! BookViewController
            
            vc.field = self.field
//            vc.fromAvailabilitly = true
            
            self.navigationController?.pushViewController(vc, animated: true)
            
        }
        
    }
    
}


