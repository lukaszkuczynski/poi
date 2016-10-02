package pl.gihon.fdd.poi.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "city", "streetAndFlat", "otherHost", "status", "lastMet", "lang1", "lang2", "other_rl",
		"key", "latitude", "longitude", "test" })
public class Place {
	private long id;
	private String city;
	private String streetAndFlat;
	private String otherHost;
	private String status;
	private Date lastMet;
	private String lang1;
	private String lang2;
	private String other_rl;
	private String key;
	private String latitude;
	private String longitude;
	private String test;

	public long getId() {
		return id;
	}

	public String getStreetAndFlat() {
		return streetAndFlat;
	}

	public String getCity() {
		return city;
	}

	public String getOtherHost() {
		return otherHost;
	}

	public String getStatus() {
		return status;
	}

	public String getLang1() {
		return lang1;
	}

	public String getLang2() {
		return lang2;
	}

	public String getOther_rl() {
		return other_rl;
	}

	public String getKey() {
		return key;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getTest() {
		return test;
	}

	public Date getLastMet() {
		return lastMet;
	}

	// for Jackson
	public Place() {
		super();
	}

	public Place(long id, String streetAndFlat, String city) {
		super();
		this.id = id;
		this.streetAndFlat = streetAndFlat;
		this.city = city;
	}

}
