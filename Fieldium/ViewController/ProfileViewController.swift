//
//  ProfileViewController2.swift
//  Fieldium
//
//  Created by Yahya Tabba on 6/1/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import SwiftyJSON
import Alamofire
import MBProgressHUD
import SDWebImage


class ProfileViewController: BaseViewController {
    
    @IBOutlet weak var imgProfileHeight: NSLayoutConstraint!
    @IBOutlet weak var imgProfile: UIImageView!
    
    @IBOutlet weak var playerNameLbl: UILabel!
    @IBOutlet weak var phoneLbl: UILabel!
    
    @IBOutlet weak var tableView: UITableView!
    
    var image_updated : Bool = false
    
    var GamesArray = [Game]()
    
    var GamesInt = [Int]()
    var GamesString = [String]()
    
    var isUploading : Bool = false
    
    var cells  = [ProfileCell]()
    var indexes  = [IndexPath]()
    var indexesTemp  = [IndexPath]()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
        Provider.shared.refresh_token { (res, err) in
            print("RES TOKEN: \(String(describing: res?.rawString()))")
        }
        
        if self.view.bounds.height <= 568 {
//            let _ = self.imgProfileHeight.setMultiplier(multiplier: 0.25)
//            self.imgProfileHeight.constant = 200
            self.playerNameLbl.font.withSize(18)
            self.phoneLbl.font.withSize(17)
        }
        
        initView()
    }
    
    func initView(){
        
        let tap = UITapGestureRecognizer.init(target: self, action: #selector(self.selectPhotoAction))
        tap.numberOfTapsRequired = 1
        
        self.imgProfile.isUserInteractionEnabled = true
        self.imgProfile.addGestureRecognizer(tap)
        
        let tap2 = UILongPressGestureRecognizer.init(target: self, action: #selector(self.deactivate))
        tap2.minimumPressDuration = 3
        
        self.imgProfile.isUserInteractionEnabled = true
        //        self.imgProfile.addGestureRecognizer(tap2) // TO DO
        
        DispatchQueue.main.async {
            self.imgProfile.layer.cornerRadius = self.imgProfile.bounds.width / 2
        }
        
        let me = User.getCurrentUser()
        print(me.printUser)
        
        self.playerNameLbl.text = me.name
        self.phoneLbl.text = me.phone
        //        self.tfName.text = me.name
        //        self.tfEmail.text = me.email
        //        self.tfAddress.text = me.address
        
        self.GamesInt = me.prefered_games
        self.GamesString = me.prefered_gamesNames
        
        if let im = me.profile_picture_url, im != ""{
            self.imgProfile.sd_setImage(with: URL.init(string: im))
            
            
        }else{
            self.imgProfile.image = #imageLiteral(resourceName: "pic.png")
        }
        
        
        Provider.shared.get_all_games { (dat, nil) in
            if let d = dat{
                if d.boolValue{
                    self.setupSoccer()
                }
            }
        }
        
    }
    
    
    override var prefersStatusBarHidden: Bool {
        return false
    }
    
    func deactivate(){
        print("deactivate")
        
        self.showLoading()
        
        Provider.shared.deactive_player { (res, err) in
            self.hideLoading()
            
            let me = User.getCurrentUser()
            var new = User()
            
            new.country_id = me.country_id
            
            new.en_name = me.en_name
            new.ar_name = me.ar_name
            new.code = me.code
            new.currency = me.currency
            new.en_currency_symbol = me.en_currency_symbol
            new.ar_currency_symbol = me.ar_currency_symbol
            new.ar_currency_symbol = me.ar_currency_symbol
            new.dialing_code = me.dialing_code
            new.timezone = me.timezone

            
            User.saveMe(me: new)
            
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "SignupViewController") as! SignupViewController
            let nv = UINavigationController.init(rootViewController: vc)
            let del = UIApplication.shared.delegate as! AppDelegate
            
            del.window?.rootViewController = nv
        }
    }
    
    @IBAction func deactiveAction(_ sender: Any) {
        let alert = UIAlertController(title: "Alert!".localized, message: "deactive this user".localized, preferredStyle: .alert)
        
        alert.addAction(UIAlertAction(title: "OK".localized, style: .destructive, handler: { (ac) in
            self.deactivate()
        }))
        
        alert.addAction(UIAlertAction(title: "Cancel".localized, style: .cancel, handler: nil))
        
        self.present(alert, animated: true, completion: nil)
        
    }
    
    
    
    func setupSoccer(){
        self.GamesArray = Provider.shared.all_games
        self.tableView.reloadSections(IndexSet.init(integer: 1), with: .fade)
    }
    
    override func configureNavigationBar() {
        super.configureNavigationBar()
        
        let saveButton = UIBarButtonItem.init(title: "Save".localized, style: .done, target: self, action: #selector(self.save))
        
        saveButton.tintColor = ThemeManager.shared.customBlue
        self.navigationItem.rightBarButtonItem = saveButton
        
        
        self.navigationItem.title = "My Profile".localized
    }
    
    func isValidEmail(testStr:String) -> Bool {
        
        if testStr == "" {
            return true
        }else{
            
            let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}"
            
            let emailTest = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
            return emailTest.evaluate(with: testStr)
        }
    }
    
    
    func save(){
        
        if !self.isUploading{
            
            var name = ""
            var email = ""
            var address = ""
            
            
            for j in cells{
                if j.type == 0 {name = j.tf.text!;}
                else if j.type == 1 {email = j.tf.text!;}
                else if j.type == 2 {address = j.tf.text!;}
            }
            
            
            //            for i in tableView.visibleCells{
            //                if let j = i as? ProfileCell{
            //                    if j.type == 0 {name = j.tf.text!;}
            //                    else if j.type == 1 {email = j.tf.text!;}
            //                    else if j.type == 2 {address = j.tf.text!;}
            //                }
            //            }
            
            
            
            //            for i in 0...2{
            //                if let cell = self.tableView.cellForRow(at: IndexPath(row: i, section: 0)) as? ProfileCell{
            //                    print(i);
            //                    if cell.type == 0 {name = cell.tf.text!;}
            //                    else if cell.type == 1 {email = cell.tf.text!;}
            //                    else if cell.type == 2 {address = cell.tf.text!;}
            //                }
            //            }
            
            
            print("NAME : \(name)")
            
            let url = URL(string: Communication.shared.baseURL + Communication.shared.updateURL)!
            
            
            if !isValidEmail(testStr: email){
                self.showErrorMessage(text: "This email address is invalid".localized)
            }
            else{
                
                let auth = Communication.shared.getHeaders()
                
                self.showLoading()
                
                print(GamesString)
                
                let jsonData = try! JSONSerialization.data(withJSONObject: self.GamesInt, options: JSONSerialization.WritingOptions.prettyPrinted)
                
                //Convert back to string. Usually only do this for debugging
                let JSONString = String(data: jsonData, encoding: String.Encoding.utf8)!
                
                
                Alamofire.upload(
                    multipartFormData: { multipartFormData in
                        
                        multipartFormData.append(name.data(using: String.Encoding.utf8)!, withName: "name")
                        multipartFormData.append(email.data(using: String.Encoding.utf8)!, withName: "email")
                        multipartFormData.append(address.data(using: String.Encoding.utf8)!, withName: "address")
                        multipartFormData.append(JSONString.data(using: String.Encoding.utf8)!, withName: "prefered_games")
                },
                    to: url,headers : auth,
                    encodingCompletion: { encodingResult in
                        switch encodingResult {
                        case .success(let upload, _, _):
                            
                            upload.uploadProgress(closure: { (Progress) in
                                print("Upload Progress: \(Progress.fractionCompleted)")
                                
                                //self.loadingView.progress = Float(Progress.fractionCompleted)
                            })
                            
                            upload.responseObject { (response : DataResponse<CustomResponse>) in
                                debugPrint(response)
                                
                                self.hideLoading()
                                
                                
                                switch response.result{
                                case .success(let value):
                                    
                                    if value.status{
                                        
                                        let user = User.getCurrentUser()
                                        user.phone = value.data!["phone"].stringValue
                                        user.email = value.data!["email"].stringValue
                                        user.address = value.data!["address"].stringValue
                                        user.name = value.data!["name"].stringValue
                                        user.player_id = value.data!["player_id"].stringValue
                                        user.profile_picture = value.data!["profile_picture"].stringValue
                                        user.address = value.data!["address"].stringValue
                                        user.profile_picture_url = value.data!["profile_picture_url"].stringValue
                                        user.profile_picture = value.data!["profile_picture"].stringValue
                                        
                                        var res = [Int]()
                                        var res2 = [String]()
                                        
                                        for im in value.data.dictionaryValue["prefered_games"]!.arrayValue{
                                            let m = Game(JSON: im.dictionaryObject!)
                                            
                                            res.append(m!.game_type_id!)
                                            res2.append(m!.name!)
                                        }
                                        
                                        user.prefered_games = res
                                        user.prefered_gamesNames = res2
                                        
                                        User.saveMe(me: user)
                                        
                                        self.navigateBack()
                                        
                                        self.showErrorMessage(text: value.message)
                                        
                                    }else{
                                        self.hideLoading()
                                        
                                        notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                                    }
                                    
                                    break
                                case .failure(let error):
                                    print(error.localizedDescription)
                                    notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                                    break
                                }
                                
                            }
                        case .failure(let encodingError):
                            print(encodingError)
                            
                            self.showErrorMessage(text: encodingError.localizedDescription)
                            
                        }
                }
                )
            }
        }
    }
    
    //MARK: Api headers
    private func getAuthorizationHeaders() -> [String : String]
    {
        //API required headers
        var headers: [String: String] = [String: String]()//getHeaders()
        
        let me = User.getCurrentUser()
        let username = me.phone == nil ? "994729458" : me.phone!
        let password = me.server_id == nil ? "994729458" : me.server_id!
        
        let plainString = "\(username):\(password)" as NSString
        let plainData = plainString.data(using: String.Encoding.utf8.rawValue)
        let base64String = plainData?.base64EncodedString(options: NSData.Base64EncodingOptions(rawValue: 0))
        
        
        
        headers["Authorization"] = "Basic " + base64String!
        
        print(headers)
        
        return headers
    }
    
    func SelectPhoto(){
        // UIImagePickerController is a view controller that lets a user pick media from their photo library.
        let imagePickerController = UIImagePickerController()
        
        // Only allow photos to be picked, not taken.
        imagePickerController.sourceType = .photoLibrary
        
        // Make sure ViewController is notified when the user picks an image.
        imagePickerController.delegate = self
        
        present(imagePickerController, animated: true, completion: nil)
        
        
    }
    
    func SelectCamera(){
        if UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceType.camera){
            let imagePicker = UIImagePickerController()
            imagePicker.delegate = self
            imagePicker.sourceType = UIImagePickerControllerSourceType.camera;
            imagePicker.allowsEditing = false
            self.present(imagePicker, animated: true, completion: nil)
        }
    }
    
    
    
    
    func selectPhotoAction() {
        
        if !self.isUploading{
            
            let alert = UIAlertController.init(title: nil, message: nil, preferredStyle: .actionSheet)
            
            alert.addAction(UIAlertAction.init(title: "Take a picture".localized, style: .default, handler: { (ac) in
                self.SelectCamera()
            }))
            
            alert.addAction(UIAlertAction.init(title: "Choose an Image".localized, style: .default, handler: { (ac) in
                self.SelectPhoto()
            }))
            
            alert.addAction(UIAlertAction.init(title: "Delete Photo".localized, style: .destructive, handler: { (ac) in
                self.imgProfile.image = #imageLiteral(resourceName: "pic.png")
                self.image_updated = false
                self.uploadPhoto(img: self.imgProfile.image!, deleted: true)
            }))
            
            alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
            
            self.present(alert, animated: true, completion: nil)
        }
    }
    
    
    
    
}



extension ProfileViewController : UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        // Dismiss the picker if the user canceled.
        
        dismiss(animated: true, completion: nil)
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        
        let selectedImage = info[UIImagePickerControllerOriginalImage] as! UIImage
        
        // Set photoImageView to display the selected image.
        imgProfile.image = selectedImage
        
        self.image_updated = true
        
        // Dismiss the picker.
        dismiss(animated: true, completion: nil)
        
        self.uploadPhoto(img: selectedImage, deleted: false)
        
    }
    
    
    func uploadPhoto(img : UIImage, deleted : Bool){
        var path : URL!
        let me = User.getCurrentUser()
        
        let url = URL(string: Communication.shared.baseURL + Communication.shared.upload_image)!
        
        let auth = Communication.shared.getHeaders()
        
        
        self.loadingView = MBProgressHUD.showAdded(to: self.imgProfile, animated: true)
        
        self.loadingView.bezelView.style = .solidColor
        self.loadingView.bezelView.backgroundColor = .clear
        self.loadingView.label.textColor = .clear
        self.loadingView.backgroundColor = .clear
        
        
        let tt = img.resized(toWidth: 512)
        
        var imageData = UIImagePNGRepresentation(tt!)
        
        let imageSize: Int = imageData!.count
        print("size of image in KB: %f ", imageSize / 1024)
        
        if (imageSize / 1024) > 2000 {
            
            self.showErrorMessage(text: "The size of image is too large".localized)
            return
        }
        
        if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
            
            let path = dir.appendingPathComponent("\(me.name!).png")
            
            //writing
            do {
                try imageData!.write(to: path, options: Data.WritingOptions.atomic)
            }
            catch {}
        }
        
        let rrr = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
        
        path = rrr.appendingPathComponent("\(me.name!).png")
        
        
        Alamofire.upload(
            multipartFormData: { multipartFormData in
                if deleted{
                    multipartFormData.append(Data(), withName: "image")
                    multipartFormData.append("1".data(using: String.Encoding.utf8)!, withName: "delete")
                }else{
                    multipartFormData.append(path!, withName: "image")
                    multipartFormData.append("0".data(using: String.Encoding.utf8)!, withName: "delete")
                }
                
                self.isUploading = true
                
        },
            to: url,headers : auth,
            encodingCompletion: { encodingResult in
                switch encodingResult {
                case .success(let upload, _, _):
                    
                    upload.uploadProgress(closure: { (Progress) in
                        print("Upload Progress: \(Progress.fractionCompleted)")
                        
                        self.loadingView.progress = Float(Progress.fractionCompleted)
                        
                    })
                    
                    upload.responseObject { (response : DataResponse<CustomResponse>) in
                        debugPrint(response)
                        
                        self.hideLoading()
                        
                        //                        let ss = NSString.init(data: response.data!, encoding: String.Encoding.utf8.rawValue)
                        
                        
                        switch response.result{
                        case .success(let value):
                            
                            if value.status{
                                let user = User.getCurrentUser()
                                user.phone = value.data!["phone"].stringValue
                                user.email = value.data!["email"].stringValue
                                user.address = value.data!["address"].stringValue
                                user.name = value.data!["name"].stringValue
                                user.player_id = value.data!["player_id"].stringValue
                                user.profile_picture = value.data!["profile_picture"].stringValue
                                user.address = value.data!["address"].stringValue
                                user.profile_picture_url = value.data!["profile_picture_url"].stringValue
                                user.profile_picture = value.data!["profile_picture"].stringValue
                                
                                var res = [Int]()
                                var res2 = [String]()
                                
                                for im in value.data.dictionaryValue["prefered_games"]!.arrayValue{
                                    let m = Game(JSON: im.dictionaryObject!)
                                    
                                    res.append(m!.game_type_id!)
                                    res2.append(m!.name!)
                                }
                                
                                user.prefered_games = res
                                user.prefered_gamesNames = res2
                                
                                User.saveMe(me: user)
                                
                                
                                let uu = value.data!["profile_picture_url"].stringValue
                                print(uu)
                                
                                SDWebImageManager.shared().saveImage(toCache: self.imgProfile.image, for: URL.init(string: uu))
                                
                                self.isUploading = false
                                
                                print("MMSS : \(value.message)")
                                self.showErrorMessage(text: value.message)
                                
                            }else{
                                self.isUploading = false
                                self.hideLoading()
                                notic.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                            }
                            
                            break
                        case .failure(let error):
                            self.isUploading = false
                            print(error.localizedDescription)
                            notic.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                            break
                        }
                        
                    }
                case .failure(let encodingError):
                    print(encodingError)
                    self.isUploading = false
                    self.showErrorMessage(text: encodingError.localizedDescription)
                    
                }
        })}
}


extension ProfileViewController : UITableViewDelegate , UITableViewDataSource{
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if section == 0{
            return 3
        }else{
            return self.GamesArray.count
        }
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 2
    }
    
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell\(indexPath.section)", for: indexPath) as! ProfileCell
        
        cell.selectionStyle = .none
        cell.index = indexPath
        
        if indexPath.section == 0{
            
            cell.type = indexPath.row
            
        }else if indexPath.section == 1{
            cell.lbl.text = self.GamesArray[indexPath.row].name!
            
            
            if !indexesTemp.contains(indexPath){
                for i in self.GamesInt{
                    if self.GamesArray[indexPath.row].game_type_id == i{
                        //tableView.selectRow(at: indexPath, animated: false, scrollPosition: .middle)
                        //cell.accessoryType = .checkmark
                        if !indexes.contains(indexPath){
                            indexes.append(indexPath)
                        }
                    }else{
                        //tableView.deselectRow(at: indexPath, animated: true)
                        //cell.accessoryType = .none
                        
                    }
                }
                
                self.indexesTemp.append(indexPath)
            }
        }
        
        if self.cells.filter({$0.index == indexPath}).count == 0{
            cells.append(cell)
        }
        
        
        return cell
    }
    
    
    
    func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        
        if indexes.contains(indexPath){
            cell.accessoryType = .checkmark
        }else{
            cell.accessoryType = .none
        }
    }
    
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: false)
        
        
        if let i = indexes.index(of: indexPath){
            self.indexes.remove(at: i)
            
            let game = self.GamesArray[indexPath.row]
            if let y = self.GamesInt.index(of: game.game_type_id!){
                print("DELETE")
                self.GamesInt.remove(at: y)
            }
            if let y = self.GamesString.index(of: game.name!){
                print("DELETE")
                self.GamesString.remove(at: y)
            }

            if let c = tableView.cellForRow(at: indexPath){
                c.accessoryType = .none
            }
        }else{
            self.indexes.append(indexPath)
            
            let game = self.GamesArray[indexPath.row]
            
            self.GamesInt.append(game.game_type_id)
            self.GamesString.append(game.name)

            if let c = tableView.cellForRow(at: indexPath){
                c.accessoryType = .checkmark
            }
        }
        
    }
    
    /*func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
     if indexPath.section == 1{
     let game = self.GamesArray[indexPath.row]
     
     self.GamesInt.append(game.game_type_id)
     self.GamesString.append(game.name)
     
     tableView.cellForRow(at: indexPath)?.accessoryType = .checkmark
     }
     }
     
     
     
     func tableView(_ tableView: UITableView, didDeselectRowAt indexPath: IndexPath) {
     if indexPath.section == 1{
     let game = self.GamesArray[indexPath.row]
     
     if let y = self.GamesInt.index(of: game.game_type_id!){
     self.GamesInt.remove(at: y.hashValue)
     }
     
     if let y = self.GamesString.index(of: game.name!){
     self.GamesString.remove(at: y.hashValue)
     }
     
     
     tableView.cellForRow(at: indexPath)?.accessoryType = .none
     }
     }*/
    
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        
        let vvv = UIView(frame: CGRect(x: 0, y: 20, width: tableView.frame.width, height: 40))
        let lab = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.frame.width, height: 40))
        
        vvv.backgroundColor = UIColor.white
        lab.textColor = UIColor.lightGray
        
        lab.center.y = vvv.center.y
        lab.font = ThemeManager.shared.font
        lab.font.withSize(17)
        lab.text = "Select Games".localized
        
        vvv.addSubview(lab)
        
        return vvv
    }
    
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return section == 0 ? 0 : 48
    }
    
}


