//
//  BookingsViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/3/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit


class BookingsViewController: BaseViewController {
    
    var bookings = [Book]()
    
    var bookings1 = [Book]()
    var bookings2 = [Book]()
    
    var type : Int = 0
    
    @IBOutlet weak var tableView: UITableView!
    var ref = UIRefreshControl()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initView()
    }
    
    func initView(){
        self.tableView.delegate = self
        self.tableView.dataSource = self
        
        self.tableView.register(UINib.init(nibName: "BookCell", bundle: nil), forCellReuseIdentifier: "cell")
        
        ref.addTarget(self, action: #selector(self.refreshBookings), for: .valueChanged)
        
        self.tableView.addSubview(self.ref)
        
        self.tableView.isHidden = true
        
        
        self.showLoading()
        Provider.shared.get_my_booking()
        
        self.title = "My Bookings".localized
        
        if type == 2 {
            
            let bb = UIBarButtonItem.init(barButtonSystemItem: .stop , target: self, action: #selector(self.close))
            
            self.navigationItem.leftBarButtonItem = bb
        }
    }
    
    func close(){
        
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "HomeViewController") as! HomeViewController
        let nv = UINavigationController.init(rootViewController: vc)
        let del = UIApplication.shared.delegate as! AppDelegate

        del.window?.rootViewController = nv

        
    }
    
    func refreshBookings(){
        Provider.shared.get_my_booking()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        notic.addObserver(self, selector: #selector(self.refreshData), name: _update_my_bookings.not, object: nil)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        notic.removeObserver(self, name: _update_my_bookings.not, object: nil)
    }
    
    func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        if type == 2{
            return false
        }else{
            return true
        }
    }
    
    func gestureRecognizer(_ gestureRecognizer: UIGestureRecognizer, shouldBeRequiredToFailBy otherGestureRecognizer: UIGestureRecognizer) -> Bool {
        if type == 2{
            return false
        }else{
            return true
        }
    }
    
    
    func refreshData(){
        self.hideLoading()
        self.bookings = Provider.shared.my_booking
        
        self.bookings1.removeAll()
        self.bookings2.removeAll()
        
        for i in self.bookings{
            
            let t2 = Time.DateToString(date: Date())
            
            var d1 = Date()
            
            if let dd1 = Time.StringToDate(dateString: i.date) {
                d1 = dd1
            }else{
                var y = i.date.components(separatedBy: "-")
                y[2] = (Int(y[2])! - 1).toString

                d1 = Time.StringToDate(dateString: "\(y[0])-\(y[1])-\(y[2])")!.add(minutes: 120)
            }
            
            
            let d2 = Time.StringToDate(dateString: t2)!
            
            
            
            if d1 >= d2 {
                
                if d1 == d2{
                    let dateFormatter = DateFormatter()
                    dateFormatter.locale =  NSLocale(localeIdentifier: "en_US_POSIX") as Locale!
                    dateFormatter.dateFormat = "HH:mm:ss"
                    let u = dateFormatter.string(from: Date())
                    let arr = u.components(separatedBy: ":")
                    let thisHour = arr[0]
                    
                    if i.start.components(separatedBy: ":").first! > thisHour{
                        self.bookings1.append(i)
                    }else{
                        self.bookings2.append(i)
                    }
                }
                
                else{
                self.bookings1.append(i)
                }
                
            }else{
                self.bookings2.append(i)
            }
        }
        
        
        
        
        self.ref.endRefreshing()
        self.tableView.isHidden = false
        
        self.tableView.reloadData()
    }
    
    
    override func connectionErrorNotificationReceived(_ notification: NSNotification) {
        super.connectionErrorNotificationReceived(notification)
        
        self.ref.endRefreshing()
    }
    
    override func RequestErrorNotificationRecived(_ notification: NSNotification) {
        super.RequestErrorNotificationRecived(notification)
        
        self.ref.endRefreshing()
    }
    
}


extension BookingsViewController : UITableViewDelegate, UITableViewDataSource, BookProtocol {
    
    
    func numberOfSections(in tableView: UITableView) -> Int {
        if self.bookings.count == 0 {
            return 1
        }
        return 2
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0 {
            return self.bookings1.count
        }else{
            return self.bookings2.count
        }
        
        //return bookings.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! BookCell
        
        //        cell.delegate = self
        cell.selectionStyle = .none
        
        if indexPath.section == 0{
            cell.book = self.bookings1[indexPath.row]
        }else{
            cell.book = self.bookings2[indexPath.row]
        }
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        if self.bookings.count == 0{
            let label = UILabel()
            label.backgroundColor = UIColor.clear
            
            label.font = ThemeManager.shared.font
            label.font.withSize(22)
            
            label.text = "No Bookings!".localized
            label.textAlignment = .center;
            label.textColor = ThemeManager.shared.customBlue
            label.numberOfLines = 0
            label.sizeToFit()
            return label
        }
        else if self.bookings.count > 0{
            let vvv = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 40))
            let lab = UILabel(frame: CGRect(x: 8, y: 0, width: tableView.frame.width - 16, height: 40))
            let vv = UIView(frame: CGRect(x: 0, y: 39, width: tableView.frame.width, height: 1))
            let vv2 = UIView(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 1))
            
            vvv.backgroundColor = UIColor.white
            lab.textColor = UIColor.black
            vv.backgroundColor = ThemeManager.shared.customRed
            vv2.backgroundColor = ThemeManager.shared.customRed
            
            lab.font = ThemeManager.shared.boldFont
            lab.font.withSize(20)
            lab.text = section == 0 ? "Upcoming Bookings".localized : "Previous Bookings".localized
            
            if self.bookings1.count == 0 && section == 0 {
                return UIView(frame: CGRect.zero)
            }else if self.bookings2.count == 0 && section == 1{
                return UIView(frame: CGRect.zero)
            }
            
            vvv.backgroundColor = .groupTableViewBackground
            
            vvv.addSubview(lab)
            vvv.addSubview(vv)
            vvv.addSubview(vv2)

            return vvv
        }
        
        
        return UIView(frame: CGRect.zero)
        
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if self.bookings.count > 0 {
            if self.bookings1.count == 0 && section == 0 {
                return 0
            }else if self.bookings2.count == 0 && section == 1{
                return 0
            }
        }
        
        return 40
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if (UIScreen.main.bounds.height > 568){
            return tableView.bounds.height / 4
        }else{
            return tableView.bounds.height / 3
        }
    
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.section == 0{
            goToBookDetails(book: self.bookings1[indexPath.row])
            
        }else{
            goToBookDetails(book: self.bookings2[indexPath.row])
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == _goToBooking{
            let vc = segue.destination as! BookViewController
            vc.book = sender as! Book
            vc.id = 1
        }
    }
    
    func goToBookDetails(book: Book) {
        self.performSegue(withIdentifier: _goToBooking, sender: book)
    }
    
    
    
}

protocol BookProtocol {
    func goToBookDetails(book : Book)
}

