package com.Team4.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author kgajos
 * Storage class for longitude and latitude data
 */
public class MapPoint implements IsSerializable {
	
	private Long id;
	private String schoolName;
	private double latitude;
	private double longitude;
	
	public MapPoint() {}
	
	public MapPoint( String name, double latit, double longit ) {
		schoolName = name;
		latitude = latit;
		longitude = longit;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}