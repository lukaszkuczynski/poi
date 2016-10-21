package pl.gihon.fdd.poi.printer.oldpythonprinter;

import java.text.SimpleDateFormat;

import pl.gihon.fdd.poi.model.LocatedPlace;

public class PyPlace {
	private String city;
	private String street;
	private String status;
	private String lastmet;
	private String lang1;
	private String lang2;
	private long id;
	private String district = "";
	private String latitude;
	private String longitude;
	private String latlng;

	public PyPlace() {
	}

	public PyPlace(LocatedPlace place) {
		this.city = place.getCity();
		this.street = place.getStreetAndFlat();
		this.status = place.getStatus();
		this.lastmet = "";
		if (place.getLastMet() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			this.lastmet = sdf.format(place.getLastMet());
		}
		this.lang1 = place.getLang1();
		this.lang2 = place.getLang2();
		this.id = place.getId();
		this.district = "";
		this.latitude = place.getLatitude();
		this.longitude = place.getLongitude();
		this.latlng = String.format("%s,%s", latitude, longitude);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastmet() {
		return lastmet;
	}

	public void setLastmet(String lastmet) {
		this.lastmet = lastmet;
	}

	public String getLang1() {
		return lang1;
	}

	public void setLang1(String lang1) {
		this.lang1 = lang1;
	}

	public String getLang2() {
		return lang2;
	}

	public void setLang2(String lang2) {
		this.lang2 = lang2;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatlng() {
		return latlng;
	}

	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}

}
