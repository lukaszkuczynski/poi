package pl.gihon.fdd.poi.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "id", "city", "streetAndFlat", "otherHost", "status", "lastMet", "lang1", "lang2", "other_rl",
		"key", "latitude", "longitude", "test" })
public class Place {
	protected long id;
	protected String city;
	protected String streetAndFlat;
	private String otherHost;
	private String status = "";
	private Date lastMet;
	private String lang1 = "";
	private String lang2 = "";
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

	public String getFullAddress() {
		return city + ',' + streetAndFlat;
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

	public void setStatus(String status) {
		this.status = status;
	}

	public void setLang1(String lang1) {
		this.lang1 = lang1;
	}

	public void setLang2(String lang2) {
		this.lang2 = lang2;
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

	@Override
	public String toString() {
		return "Place [id=" + id + ", city=" + city + ", streetAndFlat=" + streetAndFlat + ", status=" + status
				+ ", lang1=" + lang1 + ", lang2=" + lang2 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((streetAndFlat == null) ? 0 : streetAndFlat.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Place other = (Place) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (streetAndFlat == null) {
			if (other.streetAndFlat != null)
				return false;
		} else if (!streetAndFlat.equals(other.streetAndFlat))
			return false;
		return true;
	}

}
