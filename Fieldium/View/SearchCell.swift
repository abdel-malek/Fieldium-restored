//
//  SearchCell.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/10/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import DropDown
import SwiftGifOrigin

protocol HomeProtocol {
    func goToBook(book : Book)
    func showLoading()
    func sell() -> UIViewController
    func goToFields2(book : Book)
}

class SearchCell: UITableViewCell, refreshTime {
    
    @IBOutlet weak var vv: UIView!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
        
        vv.layer.cornerRadius = 10
        
        let tap1 = UITapGestureRecognizer.init(target: self, action: #selector(self.soccenAction(_:)))
        tap1.numberOfTapsRequired = 1
        stackGame.addGestureRecognizer(tap1)
        
        let tap2 = UITapGestureRecognizer.init(target: self, action: #selector(self.areaAction(_:)))
        tap2.numberOfTapsRequired = 1
        stackArea.addGestureRecognizer(tap2)
        
        let tap3 = UITapGestureRecognizer.init(target: self, action: #selector(self.timingAction(_:)))
        tap3.numberOfTapsRequired = 1
        stackDate.addGestureRecognizer(tap3)
        
        let tap4 = UITapGestureRecognizer.init(target: self, action: #selector(self.timiniig2Action(_:)))
        tap4.numberOfTapsRequired = 1
        stackTime.addGestureRecognizer(tap4)
        
        
    }
    
    let soccerDropDown = DropDown()
    let areaDropDown = DropDown()
    let timeDropDown = DropDown()
    let time2DropDown = DropDown()
    
    
    static var selectedSoccer : Game?
    
    static var selectedArea : Area?
    
    @IBOutlet weak var soccerBtn: UIButton!
    @IBOutlet weak var areaBtn: UIButton!
    
    @IBOutlet weak var soccerBtn2: UIButton!
    @IBOutlet weak var areaBtn2: UIButton!
    
    @IBOutlet weak var timeBtn: UIButton!
    @IBOutlet weak var timeBtn2: UIButton!
    
    @IBOutlet weak var time2Btn: UIButton!
    @IBOutlet weak var time2Btn2: UIButton!
    
    @IBOutlet weak var stackGame: UIStackView!
    @IBOutlet weak var stackArea: UIStackView!
    @IBOutlet weak var stackDate: UIStackView!
    @IBOutlet weak var stackTime: UIStackView!
    
    
    let tex = UITextField()
    
    var searchText : String! = ""
    
    var delegate : HomeProtocol?
    
    
    func setup(){
        soccerBtn.setImage(UIImage.gif(name: "loading"), for: .normal)
        areaBtn.setImage(UIImage.gif(name: "loading"), for: .normal)
        
        if Provider.isArabic{
            self.setupArabic()
        }
        
        if Provider.shared.all_areas.count > 0 || Provider.shared.all_games.count > 0 {
            self.setupSoccer()
            self.setupArea()
        }
        
        time2Btn2.titleLabel?.adjustsFontSizeToFitWidth = true
        
        setupTime()
        setupTime2()
        
    }
    
    func setupArabic(){
        
        self.setupImageDropDownOnLeft(soccerBtn)
        self.setupImageDropDownOnLeft(areaBtn)
        self.setupImageDropDownOnLeft(timeBtn)
        self.setupImageDropDownOnLeft(time2Btn)
        
    }
    
    func setupImageDropDownOnLeft(_ b : UIButton){
        b.imageEdgeInsets = UIEdgeInsets.init(top: 0, left: self.soccerBtn.center.x/2, bottom: 0, right: 0)
        b.contentHorizontalAlignment = UIControlContentHorizontalAlignment.left
    }
    
    func setupSoccer(){
        
        var array = [String]()
        
        for i in Provider.shared.all_games{
            array.append(i.name == nil ? "" : i.name)
        }
        
        soccerBtn.setImage(UIImage(named: "down"), for: .normal)
        
        if array.count > 0 {
            
            if SearchCell.selectedSoccer == nil {
                soccerBtn2.setTitle(array[0], for: .normal)
                SearchCell.selectedSoccer = Provider.shared.all_games[0]
            }else{
                soccerBtn2.setTitle(SearchCell.selectedSoccer?.name, for: .normal)
            }
        }
        
        
        soccerDropDown.dataSource = array
        soccerDropDown.anchorView = soccerBtn2
        soccerDropDown.direction = .any
        soccerDropDown.bottomOffset = CGPoint(x: 0, y: soccerBtn.bounds.height * 2 / 3)
        
        soccerDropDown.selectionAction = { [unowned self] (index, item) in
            self.soccerBtn2.setTitle(item, for: .normal)
            SearchCell.selectedSoccer = Provider.shared.all_games[index]
        }
    }
    
    func setupArea(){
        
        SearchCell.selectedArea = nil
        
        var array = [String]()
        
        for i in Provider.shared.all_areas{
            if let n = i.name{
                array.append(n)
            }
        }
        
        areaBtn.setImage(UIImage(named: "down"), for: .normal)
        
        if array.count > 0 {
            
            // 1.0.2
            if let selected_area = UserDefaults.standard.value(forKey: "selectedArea") as? Int{
                if let index = Provider.shared.all_areas.index(where: { (a) -> Bool in
                    a.area_id == selected_area
                }){
                    print("SELECTED ")
                    SearchCell.selectedArea = Provider.shared.all_areas[index]
                }
            }
            
            if SearchCell.selectedArea == nil {
                areaBtn2.setTitle(array[0], for: .normal)
                SearchCell.selectedArea = Provider.shared.all_areas[0]
            }else{
                areaBtn2.setTitle(SearchCell.selectedArea?.name, for: .normal)
            }
        }else{
            areaBtn2.setTitle("", for: .normal)
        }
        
        areaDropDown.dataSource = array
        areaDropDown.anchorView = areaBtn2
        areaDropDown.direction = .any
        areaDropDown.bottomOffset = CGPoint(x: 0, y: areaBtn.bounds.height * 2 / 3)
        
        areaDropDown.selectionAction = { [unowned self] (index, item) in
            self.areaBtn2.setTitle(item, for: .normal)
            SearchCell.selectedArea = Provider.shared.all_areas[index]
        }
    }
    
    
    
    func setupTime(){
        var array = [String]()
        
        if let time = Time.selected{
            timeBtn2.setTitle(time.date!, for: .normal)
            array.append(time.date)
        }else{
            array.append("Pick date".localized)
            timeBtn2.setTitle("Any Date".localized, for: .normal)
        }
        
        array.append("Any Date".localized)
        
        timeBtn.setImage(UIImage(named: "down"), for: .normal)
        
        timeDropDown.dataSource = array
        timeDropDown.anchorView = timeBtn2
        timeDropDown.direction = .any
        timeDropDown.bottomOffset = CGPoint(x: 0, y: areaBtn.bounds.height * 2 / 3)
        
        timeDropDown.selectionAction = { [unowned self] (index, item) in
            if index == 0{
                let main = UIStoryboard.init(name: "Main", bundle: nil)
                let vc = main.instantiateViewController(withIdentifier: "TimingViewController2") as! TimingViewController
                
                vc.modalPresentationStyle = .overCurrentContext
                vc.modalTransitionStyle = .crossDissolve
                vc.ref = self
                vc.type = 1
                
                self.delegate?.sell().present(vc, animated: true, completion: nil)
            }else{
                Time.selected = nil
                self.timeBtn2.setTitle("Any Date".localized, for: .normal)
                self.setupTime2()
            }
        }
    }
    
    func setupTime2(){
        if Time.selected != nil{
            self.stackTime.isHidden = false
            
            var array = [String]()
            
            if let time = Time.selected, time.duration != nil{
                let dd = "\(Int(time.duration)! / 60)h \(Int(time.duration)! % 60)m"
                
                time2Btn2.setTitle("\(Time.convert24To12(time: time.start!)) - \(dd)", for: .normal)
                array.append("\(Time.convert24To12(time: time.start!)) - \(dd)")
            }else{
                array.append("Pick time".localized)
                time2Btn2.setTitle("Any Time".localized, for: .normal)
            }
            
            array.append("Any Time".localized)
            
            time2Btn.setImage(UIImage(named: "down"), for: .normal)
            
            time2DropDown.dataSource = array
            time2DropDown.anchorView = time2Btn2
            time2DropDown.direction = .any
            time2DropDown.bottomOffset = CGPoint(x: 0, y: areaBtn.bounds.height * 2 / 3)
            
            time2DropDown.selectionAction = { [unowned self] (index, item) in
                if index == 0{
                    let main = UIStoryboard.init(name: "Main", bundle: nil)
                    let vc = main.instantiateViewController(withIdentifier: "TimingViewController3") as! TimingViewController
                    
                    vc.modalPresentationStyle = .overCurrentContext
                    vc.modalTransitionStyle = .crossDissolve
                    vc.ref = self
                    vc.type = 2
                    
                    self.delegate?.sell().present(vc, animated: true, completion: nil)
                }else{
                    //                Time.selected = nil
                    if let time = Time.selected{
                        time.duration = nil
                        time.start = nil
                    }else{
                        Time.selected = nil
                    }
                    
                    self.time2Btn2.setTitle("Any Time".localized, for: .normal)
                }
            }
        }
        else{
            self.stackTime.isHidden = true
        }
    }
    
    
    
    @IBAction func soccenAction(_ sender: Any) {
        soccerDropDown.show()
    }
    
    @IBAction func areaAction(_ sender: Any) {
        areaDropDown.show()
    }
    
    @IBAction func timingAction(_ sender: Any) {
        timeDropDown.show()
    }
    
    @IBAction func timiniig2Action(_ sender: Any) {
        time2DropDown.show()
    }
    
    
    @IBAction func searchAction(_ sender: Any) {
        
        if timeDropDown.indexForSelectedRow == 1 {
            Time.selected = nil
        }
        
        
        if let area = SearchCell.selectedArea , let game = SearchCell.selectedSoccer{
            delegate?.showLoading()
            
            // 1.0.2
            UserDefaults.standard.set(area.area_id, forKey: "selectedArea")
            
            if let time = Time.selected{
                
                if time.duration == nil{
                    Provider.shared.search(area_id: area.area_id!, name: "", game_type: game.game_type_id!, start: "", duration: "", date: time.date, timing: 2)
                    
                }else if time.duration != nil{
                    Provider.shared.search(area_id: area.area_id!, name: "", game_type: game.game_type_id!, start: time.start, duration: time.duration, date: time.date, timing: 1)
                }
                
            }else{
                Provider.shared.search(area_id: area.area_id!, name: "", game_type: game.game_type_id!, start: "", duration: "", date: "", timing: 0)
            }
        }
    }
    
    func refreshTime(time: Time) {
        Time.selected = time
        
        self.setupTime()
        self.setupTime2()
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        
        // Configure the view for the selected state
    }
    
}
