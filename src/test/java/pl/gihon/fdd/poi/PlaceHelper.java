package pl.gihon.fdd.poi;

import java.util.Date;
import java.util.Random;

import pl.gihon.fdd.poi.model.Place;

public class PlaceHelper {
	public static Place simple(long id, String street, String city) {
		return new Place(id, street, city, "", "", new Date(), "");
	}

	public static Place withLangs(String lang1, String lang2) {
		return new Place(new Random().nextLong(), "", "", lang1, lang2, new Date(), "");
	}

	public static Place withStatus(String status) {
		return new Place(new Random().nextLong(), "", "", "", "", new Date(), status);
	}

}
