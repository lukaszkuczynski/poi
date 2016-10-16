package pl.gihon.fdd.poi.model;

public class LocatedPlace extends Place {
	private String latitude;
	private String longitude;

	// TODO: remove Place, this should be the only Place! with flag isLocated()
	public LocatedPlace(String latitude, String longitude, Place place) {
		// long id, String streetAndFlat, String city, String lang1, String
		// lang2, Date lastMet, String status
		super(place.getId(), place.getStreetAndFlat(), place.getCity(), place.getLang1(), place.getLang2(),
				place.getLastMet(), place.getStatus());
		this.latitude = latitude;
		this.longitude = longitude;
	}

	// Jackson likes it
	public LocatedPlace() {
		super();
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "LocatedPlace [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
