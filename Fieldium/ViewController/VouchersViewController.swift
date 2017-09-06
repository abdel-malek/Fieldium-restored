//
//  VouchersViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/11/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import FoldingCell

class VouchersViewController: BaseViewController {
    
    
    @IBOutlet weak var tableView: UITableView!
    var ref = UIRefreshControl()
    
    var vouchers = [Voucher]()
    let kCloseCellHeight: CGFloat = 100
    let kOpenCellHeight: CGFloat = 450
    var cellHeights: [CGFloat] = []
    
    var selected : Voucher!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        initView()
        
    }
    
    private func setup() {
        cellHeights = Array(repeating: kCloseCellHeight, count: vouchers.count)
        tableView.estimatedRowHeight = kCloseCellHeight
        tableView.rowHeight = UITableViewAutomaticDimension
    }
    
    
    func initView(){
        self.tableView.delegate = self
        self.tableView.dataSource = self
        
//        self.tableView.register(UINib.init(nibName: "VoucherCell", bundle: nil), forCellReuseIdentifier: "VoucherCell")
        
        self.tableView.register(UINib.init(nibName: "VoucherCell2", bundle: nil), forCellReuseIdentifier: "VoucherCell2")

        
        ref.addTarget(self, action: #selector(self.refreshVouchers), for: .valueChanged)
        self.tableView.addSubview(ref)
        
        self.tableView.addSubview(self.ref)
        
        self.tableView.isHidden = true

        
        self.showLoading()
        refreshVouchers()
        
        self.title = "My Vouchers".localized
        
    }
    
    func refreshVouchers(){
        
        Communication.shared.vouchers_my_vouchers { (res) in
            self.hideLoading()
            self.ref.endRefreshing()
            self.vouchers = res
            self.setup()
            self.tableView.reloadData()
            
            self.tableView.isHidden = false

            
//            if res.count == 0{
//                self.tableView.reloadEmptyDataSet()
//            }
        }
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

extension VouchersViewController : UITableViewDelegate, UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.vouchers.count
    }
    
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        guard case let cell as VoucherCell = cell else {
            return
        }
        
        cell.backgroundColor = .clear
        
        if cellHeights[indexPath.row] > kCloseCellHeight {
            cell.selectedAnimation(false, animated: false, completion:nil)
        } else {
            cell.selectedAnimation(true, animated: false, completion: nil)
        }
        
        cell.number = indexPath.row
    }
    
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        //        if indexPath.row == 0{
        //            let cell = tableView.dequeueReusableCell(withIdentifier: "VoucherCell", for: indexPath) as! VoucherCell
        //
        //            //cell.textLabel?.text = self.vouchers[indexPath.row].voucher
        //            let durations: [TimeInterval] = [0.26, 0.2, 0.2]
        //            cell.durationsForExpandedState = durations
        //            cell.durationsForCollapsedState = durations
        //            cell.voucher = self.vouchers[indexPath.row]
        //            cell.VouchersVC = self
        //            return cell
        //
        //        }else{
        let cell = tableView.dequeueReusableCell(withIdentifier: "VoucherCell2", for: indexPath) as! VoucherCell2
        
        //cell.textLabel?.text = self.vouchers[indexPath.row].voucher
        let durations: [TimeInterval] = [0.26, 0.2, 0.2]
        cell.durationsForExpandedState = durations
        cell.durationsForCollapsedState = durations
        cell.voucher = self.vouchers[indexPath.row]
        cell.VouchersVC = self
        
        
        if let s = selected{
            if s.voucher_id == self.vouchers[indexPath.row].voucher_id{
                print("OKK")
                cellHeights[indexPath.row] = HeightOfCell(self.vouchers[indexPath.row])
                cell.selectedAnimation(true, animated: false, completion: nil)
            }
        }
        
        return cell
        
        //        }
        
    }
    
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return cellHeights[indexPath.row]
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        
        let cell = tableView.cellForRow(at: indexPath) as! VoucherCell2
        
        if cell.isAnimating() {
            return
        }
        
        var duration = 0.0
        let cellIsCollapsed = cellHeights[indexPath.row] == kCloseCellHeight
        if cellIsCollapsed {
            cellHeights[indexPath.row] = HeightOfCell(self.vouchers[indexPath.row])
            cell.selectedAnimation(true, animated: true, completion: nil)
            duration = 0.5
        } else {
            
            cellHeights = Array.init(repeating: kCloseCellHeight, count: vouchers.count)
            
            for i in tableView.visibleCells{
                if let y = i as? FoldingCell{
                    y.selectedAnimation(false, animated: false, completion: nil)
                }
            }
            
            cellHeights[indexPath.row] = kCloseCellHeight
            cell.selectedAnimation(false, animated: true, completion: nil)
            duration = 0.4
        }
        
        UIView.animate(withDuration: duration, delay: 0, options: .curveEaseOut, animations: { () -> Void in
            tableView.beginUpdates()
            tableView.endUpdates()
        }, completion: nil)
    }
    
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        if self.vouchers.count == 0{
            let label = UILabel()
            label.backgroundColor = UIColor.clear
            
            label.font = ThemeManager.shared.boldFont
            label.font.withSize(30)
            
            label.text = "No Vouchers!".localized
            label.textAlignment = .center;
            label.textColor = .white
            label.numberOfLines = 0
            label.sizeToFit()
            return label
        }
        
        return nil
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        if self.vouchers.count == 0{
            return 60
        }
        
        return 0
    }
    
    
    func HeightOfCell(_ v : Voucher) -> CGFloat{
        
        var height = kOpenCellHeight
        
        if v.companies.count == 0{
            height  -= 100
        }
        
        if (v.from_hour == nil || v.from_hour == "") && (v.to_hour == nil || v.to_hour == ""){
            height  -= 50
        }
        
        return height
    }
    
}


