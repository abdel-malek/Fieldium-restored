//
//  MapComapniesViewController.swift
//  Fieldium
//
//  Created by Yahya Tabba on 1/5/17.
//  Copyright © 2017 Tradinos UG. All rights reserved.
//

import UIKit
import MapKit
import CoreLocation
import SDWebImage


class MapComapniesViewController : BaseViewController , MKMapViewDelegate, CLLocationManagerDelegate {
    
    @IBOutlet weak var mapView: MKMapView!
    
    var long : Double!
    var lat : Double!
    var name : String!
    var field : Field!
    
    var ii = 0
    
    let manager = CLLocationManager()
    
    
    override func viewWillAppear(_ animated: Bool) {
        notic.addObserver(self, selector: #selector(self.reloadCompanies), name: _update_nearby_companies.not, object: nil)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        notic.removeObserver(self, name: _update_nearby_companies.not, object: nil)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        mapView.delegate = self
        
        manager.delegate = self
        manager.desiredAccuracy = kCLLocationAccuracyBest
        
        manager.requestWhenInUseAuthorization()
        manager.startUpdatingLocation()
        
        print("AAAAA")
        
        let rrr = UIBarButtonItem(image: #imageLiteral(resourceName: "ic_nearby places"), style: .plain, target: self, action: #selector(self.gg))
        
        self.navigationItem.rightBarButtonItem = rrr
        
    }
    
    
    func gg(){
        
        if let reg = Provider.region{
            mapView.setRegion(reg, animated: true)
        }
        
    }
    
    
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        // Don't want to show a custom image if the annotation is the user's location.
        
        
        if let ann = annotation as? CompanyAnnotation {
            
            /*guard !(ann is MKUserLocation) else {
             return nil
             }*/
            
            // Better to make this class property
            let annotationIdentifier = "AnnotationIdentifier"
            
            var annotationView: MKAnnotationView?
            if let dequeuedAnnotationView = mapView.dequeueReusableAnnotationView(withIdentifier: annotationIdentifier) {
                annotationView = dequeuedAnnotationView
                annotationView?.annotation = ann
            }
            else {
                annotationView = MKAnnotationView(annotation: ann, reuseIdentifier: annotationIdentifier)
                //                annotationView?.rightCalloutAccessoryView = UIButton(type: .detailDisclosure)
            }
            
            if let annotationView = annotationView {
                // Configure your annotation view here
                annotationView.canShowCallout = true
                annotationView.image = UIImage(named: "ic_location")
                
            }
            
            annotationView?.rightCalloutAccessoryView = UIButton(type: UIButtonType.detailDisclosure)
            annotationView?.tag = ann.id!
            
            
            return annotationView
        }else{
            return nil
        }
    }
    
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        
        let location = locations[0]
        
        Provider.shared.currentLocation = location
        
        let span = MKCoordinateSpan.init(latitudeDelta: 0.1, longitudeDelta: 0.1)
        let myLocation = CLLocationCoordinate2D.init(latitude: location.coordinate.latitude, longitude: location.coordinate.longitude)
        
        let region = MKCoordinateRegion.init(center: myLocation, span: span)
        
        Provider.region = region
        
        //        mapView.setRegion(region, animated: true)
        
        mapView.showsUserLocation = true
        
    }
    
    
    
    
    @IBAction func NearbyAction(_ sender: Any) {
        
        
        if Provider.shared.nearby_companies.count == 0 {
            self.showLoading()
            
            if Provider.region != nil {
                Provider.shared.get_nearby_companies()
            }else{
                self.alertError(message: nil)
            }
        }else{
            self.reloadCompanies()
        }
        
    }
    
    
    func reloadCompanies(){
        self.hideLoading()
        
        var regions = [MKCoordinateRegion]()
        
        print("EEE: \(Provider.shared.nearby_companies.count)")
        
        for i in Provider.shared.nearby_companies{
            
            let location = CLLocationCoordinate2D.init(latitude: i.latitude, longitude: i.longitude)
            
            //let location = CLLocation.init(latitude: i.latitude, longitude: i.longitude)
            
            let span = MKCoordinateSpanMake(0.005, 0.005)
            let region = MKCoordinateRegionMake(location, span)
            
            regions.append(region)
            
            let annotation = CompanyAnnotation(coordinate: location)
            //                annotation.coordinate = location
            annotation.title = i.name
            annotation.imageURL = i.logo_url
            annotation.id = i.company_id
            
            
            mapView.addAnnotation(annotation)
            
            //let n = mapView.annotations[0]
            
            //mapView.selectAnnotation(n, animated: true)
        }
        
        if regions.count > 0 {
            
            mapView.setRegion(regions[ii], animated: true)
            
            for aa in self.mapView.annotations{
                if aa.coordinate.latitude == regions[ii].center.latitude{
                    self.mapView.selectAnnotation(aa, animated: true)
                }
            }
            
            
            if ii == regions.count - 1 {
                self.ii = 0
            }else{
                self.ii += 1
            }
        }
    }
    
    
    func mapView(_ mapView: MKMapView, annotationView view: MKAnnotationView, calloutAccessoryControlTapped control: UIControl) {
        
        if view.tag != 0 {
            self.performSegue(withIdentifier: _goToFields, sender: view.tag)
        }
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == _goToFields{
            let des = segue.destination as! FieldsViewController
            let company = Company()
            company.company_id = sender as! Int
            
            for i in Provider.shared.nearby_companies{
                if i.company_id == sender as! Int{
                    company.name = i.name
                }
            }
            
            des.company = company
            des.fromBook = true
            
        }
    }
    
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("ERROR")
        print(error.localizedDescription)
        
        alertError(message: error.localizedDescription)
    }
    
    
    func alertError(message: String?){
        self.hideLoading()
        
        if let message = title{
            self.showAlert(title: "Alert!".localized, message: message)
        }else{
            self.showAlert(title: "Alert!".localized, message: "enable location".localized)
            
        }
        
    }
    
}
