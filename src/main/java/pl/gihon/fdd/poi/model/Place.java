package pl.gihon.fdd.poi.model;

import java.util.Date;
import java.util.List;

public class Place {
	private long id;
	private String streetAndFlat;
	private String city;
	private List<String> languages;
	private Date lastMet;

	public long getId() {
		return id;
	}

	public String getStreetAndFlat() {
		return streetAndFlat;
	}

	public String getCity() {
		return city;
	}

	public List<String> getLanguages() {
		return languages;
	}

	public Date getLastMet() {
		return lastMet;
	}

	public Place(long id, String streetAndFlat, String city) {
		super();
		this.id = id;
		this.streetAndFlat = streetAndFlat;
		this.city = city;
	}

}
