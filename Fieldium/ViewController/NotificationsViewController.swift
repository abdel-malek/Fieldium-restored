//
//  NotificationsViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/8/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit


class NotificationsViewController: BaseViewController {
    
    @IBOutlet weak var tableView: UITableView!
    
    var notifications = [Notificat]()
    var ref = UIRefreshControl()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initView()
    }
    
    func initView(){
        self.tableView.delegate = self
        self.tableView.dataSource = self
        
        self.tableView.register(UINib.init(nibName: "BookCell", bundle: nil), forCellReuseIdentifier: "cell")
        
        ref.addTarget(self, action: #selector(self.refreshNotifications), for: UIControlEvents.valueChanged)
        
        self.tableView.addSubview(self.ref)
        self.tableView.isHidden = true
        
        self.showLoading()
        Provider.shared.get_notifications()
        
        self.title = "My Notifications".localized
    }
    
    func refreshNotifications(){
        Provider.shared.get_notifications()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        notic.addObserver(self, selector: #selector(self.refreshData), name: _update_my_notifications.not, object: nil)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        notic.removeObserver(self, name: _update_my_notifications.not, object: nil)
    }
    
    
    
    func refreshData(){
        self.hideLoading()
        self.notifications = Provider.shared.my_notifications
        
        self.ref.endRefreshing()
        self.tableView.isHidden = false
        self.tableView.reloadData()
    }
    
    
    override func RequestErrorNotificationRecived(_ notification: NSNotification) {
        super.RequestErrorNotificationRecived(notification)
        
        self.ref.endRefreshing()
    }
    
    override func connectionErrorNotificationReceived(_ notification: NSNotification) {
        super.connectionErrorNotificationReceived(notification)
        
        self.ref.endRefreshing()
    }
    
    
}

extension NotificationsViewController : UITableViewDelegate, UITableViewDataSource {
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return self.notifications.count
    }
    
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        if self.notifications.count == 0{
            let label = UILabel()
            label.backgroundColor = UIColor.clear
            
            label.font = ThemeManager.shared.font
            label.font.withSize(22)
            
            label.text = "No Notifications!".localized
            label.textAlignment = .center;
            label.textColor = ThemeManager.shared.customBlue
            label.numberOfLines = 0
            label.sizeToFit()
            
            return label
        }
        
        return UIView.init(frame: CGRect.zero)
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if self.notifications.count == 0 {
            return 50
        }else{
            return 0
        }
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell", for: indexPath) as! BookCell
        
        cell.selectionStyle = .none
        
        cell.not = self.notifications[indexPath.row]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if (UIScreen.main.bounds.height > 568){
            return tableView.bounds.height / 4
        }else{
            return tableView.bounds.height / 3
        }
    }
    
    /*func numberOfSections(in tableView: UITableView) -> Int
    {
        var numOfSections: Int = 0
        if self.notifications.count > 0
        {
            tableView.separatorStyle = .singleLine
            numOfSections            = 1
            tableView.backgroundView = nil
        }
        else
        {
            let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
            noDataLabel.text          = "No data available"
            noDataLabel.textColor     = UIColor.black
            noDataLabel.textAlignment = .center
            tableView.backgroundView  = noDataLabel
            tableView.separatorStyle  = .none
        }
        return numOfSections
    }*/

    
    
}
