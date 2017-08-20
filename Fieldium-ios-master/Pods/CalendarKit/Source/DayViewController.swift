import UIKit

//import DateToolsSwift

open class DayViewController: UIViewController, DayViewDelegate {
    
    public lazy var dayView: DayView = DayView()
    
    override open func viewDidLoad() {
        super.viewDidLoad()
        self.edgesForExtendedLayout = UIRectEdge()
        view.addSubview(dayView)
        //    view.tintColor = UIColor.red // TO DO
        view.tintColor = UIColor.blue
        
        dayView.dataSource = self
        dayView.delegate = self
        dayView.reloadData()
    }
    
    public func setTimes(open : String , close : String){
        let t  = self.dayView.timelinePager.reusableViews[0].timeline
        
        let tms = times(open : open, close: close)
        t?.times = tms
        
        //        t?.frame.size.height = (t?.setHeight(tms.count - 1))!
        
        //        t?.countRows = tms.count - 1
        
        print("COUNT TIMES : \(tms.count) - \(t?.frame.size.height)")
    }
    
    func times(open : String , close : String) -> [String]{
        
        let o = open.components(separatedBy: ":")
        let c = close.components(separatedBy: ":")
        
        var arr = [Int]()
        
        if Int(c[0])! < Int(o[0])! {
            
            for i in 0..<24-Int(o[0])! {
                arr.append((24 - i))
            }
            
            for i in 0...Int(c[0])!{
                arr.append(i)
            }
            
            
            for i in 0...23{
                if !arr.contains(i) {
                    arr.append(i)
                }
            }
            
            
            arr.sort()
            
            
            var t = [String]()
            for i in arr{
                
                if i < 12{
                    t.append(String(i) + " AM")
                }else{
                    t.append(String(i) + " PM")
                }
            }
            
            for i in 0..<t.count{
                let h = t[i].components(separatedBy: " ")
                
                if  (h[1] == "PM" && h[0] == "24") ||  (h[1] == "AM" && h[0] == "0"){
                    t[i] = "12 AM"
                }
                
                if  h[1] == "PM" && (h[0] != "24" && h[0] != "12"){
                    t[i] = "\(Int(h[0])! - 12) PM"
                }
            }
            
            return t
        }else{
            return times2(open: open, close: close)
        }
    }
    
    func times2(open : String , close : String) -> [String]{
        var numbers = [String]()
        numbers.append("12")
        
        for i in 1...11 {
            let string = String(i)
            numbers.append(string)
        }
        
        var am = numbers.map { $0 + " AM" }
        var pm = numbers.map { $0 + " PM" }
        pm.append("12 AM") // TO DO
        
        //        am.append("Noon")
        //        pm.removeFirst()
        
        let o = open.components(separatedBy: ":")
        let c = close.components(separatedBy: ":")
        
        
        for _ in 0..<Int(o[0])!{
            if am.count > 0 {
            am.removeFirst()
            }else if pm.count > 0 {
                pm.removeFirst()
            }
        }
        
        if !(o[1] == c[1] && Int(o[0])! > Int(c[0])!){
            for i in 0..<(24 - Int(c[0])!){
                print("REMOVE PM \(24 - Int(c[0])!)")
                pm.removeLast()
            }
        }
        
        
        
        return am + pm
    }
    
    open override func viewDidLayoutSubviews() {
        dayView.fillSuperview()
        dayView.frame.origin.y = 51
        dayView.frame.size.height -= 51
    }
    
    open func reloadData() {
        dayView.reloadData()
    }
    
    public func updateStyle(_ newStyle: CalendarStyle) {
        dayView.updateStyle(newStyle)
    }
}

extension DayViewController: DayViewDataSource {
    open func eventViewsForDate(_ date: Date) -> [EventView] {
        return [EventView]()
    }
    
    // MARK: DayViewDelegate
    
    open func dayViewDidSelectEventView(_ eventview: EventView) {
        
    }
    
    open func dayViewDidLongPressEventView(_ eventView: EventView) {
        
    }
}
