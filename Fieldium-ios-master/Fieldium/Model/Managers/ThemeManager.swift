//
//  ThemeManager.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/18/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation
import UIKit

let logoPlaceholder = "logoPlaceholder.jpg"

class ThemeManager{

let colors = ["Red": UIColor.red, "Green": UIColor(red: 98/255, green: 148/255, blue: 0, alpha: 1.0), "Orange":UIColor(red: 244/255, green: 93/255, blue: 4/255, alpha: 1.0), "Metal": UIColor(red: 54/255, green: 70/255, blue: 88/255, alpha: 1.0), "Blue": UIColor(red: 33/255, green: 150/255, blue: 243/255, alpha: 1.0)]

    let customRed  = UIColor.from(hex: "F26539")
    let customBlue = UIColor.from(hex: "09BED2")
    let customGrey = UIColor.from(hex: "464A4B")


    let font = UIFont.init(name: "SourceSansPro-Regular", size: 14)
    let boldFont = UIFont.init(name: "SourceSansPro-Bold", size: 18)

    static let shared = ThemeManager()
}
