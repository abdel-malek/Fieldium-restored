    //
    //  MapFieldViewController.swift
    //  Fieldium
    //
    //  Created by Yahya Tabba on 12/29/16.
    //  Copyright © 2017 Tradinos UG. All rights reserved.
    //
    
    import UIKit
    import MapKit
    
    
    class MapFieldViewController: BaseViewController, MKMapViewDelegate {
        
        @IBOutlet weak var mapView: MKMapView!
        
        var long : Double!
        var lat : Double!
        var name : String!
        var field : Field!
        var company : Company!
        
        var type : Int = 0
        
        override func viewDidLoad() {
            super.viewDidLoad()
            
            
            mapView.delegate = self
            
            
            if let f = field{
                
                let location = CLLocationCoordinate2DMake(f.latitude.doubleValue, f.longitude.doubleValue)
                
                let span = MKCoordinateSpanMake(0.01, 0.01)
                let region = MKCoordinateRegionMake(location, span)
                
                mapView.setRegion(region, animated: true)
                
                
                let annotation = MKPointAnnotation()
                annotation.coordinate = location
                annotation.title = self.field.name
                
                
                mapView.addAnnotation(annotation)
                
                let n = mapView.annotations[0]
                
                mapView.selectAnnotation(n, animated: true)
            }
                
            else if let f = company{
                
                let location = CLLocationCoordinate2DMake(f.latitude!, f.longitude!)
                
                let span = MKCoordinateSpanMake(0.01, 0.01)
                let region = MKCoordinateRegionMake(location, span)
                
                mapView.setRegion(region, animated: true)
                
                
                let annotation = MKPointAnnotation()
                annotation.coordinate = location
                annotation.title = f.name
                
                
                mapView.addAnnotation(annotation)
                
                let n = mapView.annotations[0]
                
                mapView.selectAnnotation(n, animated: true)
            }
            
            if type == 1 {
                let closeBtn = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.stop, target: self, action: #selector(self.closeView))
                self.navigationItem.leftBarButtonItem = closeBtn
                
            }
        }
        
        func closeView(){
            self.dismiss(animated: true, completion: nil)
        }
        
        
        func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
            // Don't want to show a custom image if the annotation is the user's location.
            guard !(annotation is MKUserLocation) else {
                return nil
            }
            
            // Better to make this class property
            let annotationIdentifier = "AnnotationIdentifier"
            
            var annotationView: MKAnnotationView?
            if let dequeuedAnnotationView = mapView.dequeueReusableAnnotationView(withIdentifier: annotationIdentifier) {
                annotationView = dequeuedAnnotationView
                annotationView?.annotation = annotation
            }
            else {
                annotationView = MKAnnotationView(annotation: annotation, reuseIdentifier: annotationIdentifier)
            }
            
            if let annotationView = annotationView {
                // Configure your annotation view here
                annotationView.canShowCallout = true
                annotationView.image = UIImage(named: "ic_location")
            }
            
            return annotationView
        }
        
        
        
        
    }
