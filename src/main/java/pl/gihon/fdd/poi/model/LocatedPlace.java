package pl.gihon.fdd.poi.model;

public class LocatedPlace extends Place {
	private String latitude;
	private String longitude;
	private Place place;

	// TODO: remove Place, this should be the only Place! with flag isLocated()
	public LocatedPlace(String latitude, String longitude, Place place) {
		super(place.getId(), place.getStreetAndFlat(), place.getCity());
		this.id = place.getId();
		this.city = place.getCity();
		this.streetAndFlat = place.getStreetAndFlat();
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

	// public long getId() {
	// return place.getId();
	// }
	//
	// public String getStreetAndFlat() {
	// return place.getStreetAndFlat();
	// }
	//
	// public String getCity() {
	// return place.getCity();
	// }

	@Override
	public String toString() {
		return "LocatedPlace [latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
