package pl.gihon.fdd.poi.localisator.google;

import pl.gihon.fdd.poi.exception.PoiException;
import pl.gihon.fdd.poi.model.Place;

public class GoogleLocalisatorException extends PoiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6959115424263445086L;

	public GoogleLocalisatorException(Place place, String string) {
		super("for place " + place.getCity() + "," + place.getStreetAndFlat() + " error is " + string);
	}

}
