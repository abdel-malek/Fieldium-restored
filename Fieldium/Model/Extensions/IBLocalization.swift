//
//  IBLocalization.swift
//  Fieldium
//
//  Created by Yahya Tabba on 12/18/16.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import Foundation

extension UITextField {
    
    @IBInspectable var localizedPlaceholder: String {
        get { return "" }
        set {
            self.placeholder = newValue.localized//NSLocalizedString(newValue, comment: "")
        }
    }
    
    @IBInspectable var localizedText: String {
        get { return "" }
        set {
            self.text = newValue.localized//NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UITextView {
    
    @IBInspectable var localizedText: String {
        get { return "" }
        set {
            self.text = newValue.localized//NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UIBarItem {
    
    @IBInspectable var localizedTitle: String {
        get { return "" }
        set {
            self.title = newValue.localized//NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UILabel {
    
    @IBInspectable var localizedText: String {
        get { return "" }
        set {
            self.text = newValue.localized//NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UINavigationItem {
    
    @IBInspectable var localizedTitle: String {
        get { return "" }
        set {
            self.title = newValue.localized//NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UIButton {
    
    @IBInspectable var localizedTitle: String {
        get { return "" }
        set {
            self.setTitle(newValue.localized, for: UIControlState.normal)
        }
    }
    
}

extension UISearchBar {
    
    @IBInspectable var localizedPrompt: String {
        get { return "" }
        set {
            self.prompt = newValue.localized//NSLocalizedString(newValue, comment: "")
        }
    }
    
    @IBInspectable var localizedPlaceholder: String {
        get { return "" }
        set {
            self.placeholder = newValue.localized//NSLocalizedString(newValue, comment: "")
        }
    }
}

extension UISegmentedControl {
    
    @IBInspectable var localized: Bool {
        get { return true }
        set {
            for index in 0..<numberOfSegments {
                let title = titleForSegment(at: index)!.localized
                setTitle(title, forSegmentAt: index)
            }
        }
    }
}

