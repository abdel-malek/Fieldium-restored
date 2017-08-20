package com.bluebrand.fieldium.core.model;

import java.io.Serializable;

public class Address implements Serializable {

	private static final long serialVersionUID = 600630611212706673L;

	private int id ;
	private City city;
	private String details;
	private String street;
	private String name;
	private double longitude;
	private double latitude;

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return getName();
	}


	public String getAddressLine () {
		return city.getName() + (street.equals("")?"": " - " + street) + (details.equals("")?"": " - " + details) ;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
