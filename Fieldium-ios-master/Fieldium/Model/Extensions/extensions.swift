//
//  extensions.swift
//  Fieldium
//
//  Created by Yahya Tabba on 3/5/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation



extension UINib{
    
    func instanceFromNib() -> UIView {
        return self.instantiate(withOwner: nil, options: nil)[0] as! UIView
    }
    
}

extension UIImage {
    func resized(withPercentage percentage: CGFloat) -> UIImage? {
        let canvasSize = CGSize(width: size.width * percentage, height: size.height * percentage)
        UIGraphicsBeginImageContextWithOptions(canvasSize, false, scale)
        defer { UIGraphicsEndImageContext() }
        draw(in: CGRect(origin: .zero, size: canvasSize))
        return UIGraphicsGetImageFromCurrentImageContext()
    }
    func resized(toWidth width: CGFloat) -> UIImage? {
        let canvasSize = CGSize(width: width, height: CGFloat(ceil(width/size.width * size.height)))
        UIGraphicsBeginImageContextWithOptions(canvasSize, false, scale)
        defer { UIGraphicsEndImageContext() }
        draw(in: CGRect(origin: .zero, size: canvasSize))
        return UIGraphicsGetImageFromCurrentImageContext()
    }
}



extension String{
    
    var not : NSNotification.Name {
        return NSNotification.Name.init(rawValue: self)
    }
    
    
//    var localized : String{
//        return NSLocalizedString(self, comment: "")
//    }
    
    var localized : String{
        //return NSLocalizedString(self, comment: "")
        
        let language = Provider.lang// ? "ar" : "en"
        
        //if let lang : String = UserDefaults.standard.value(forKey: "lang") as? String {
        //   language = lang
        //}
        
        let path = Bundle.main.path(forResource: language, ofType: "lproj")
        let bundle = Bundle.init(path: path!)
        let s = bundle!.localizedString(forKey: self, value: nil, table: nil)
        return s
        
    }

    
    var toInt : Int{
        return Int(self)!
    }

    var html2AttributedString: NSAttributedString? {
        guard let data = data(using: .utf8) else { return nil }
        do {
            return try NSAttributedString(data: data, options: [NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType, NSCharacterEncodingDocumentAttribute: String.Encoding.utf8.rawValue], documentAttributes: nil)
        } catch let error as NSError {
            print(error.localizedDescription)
            return  nil
        }
    }
    var html2String: String {
        return html2AttributedString?.string ?? ""
    }
 
}



extension Int{
    var toString : String{
        return String(self)
    }
}


class myTapGuester : UITapGestureRecognizer{
    
    var tag = Int()
    
}

