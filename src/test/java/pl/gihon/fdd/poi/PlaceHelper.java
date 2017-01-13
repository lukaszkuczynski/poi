package pl.gihon.fdd.poi;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomStringUtils;
import pl.gihon.fdd.poi.model.LocatedPlace;
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

	public static LocatedPlace simpleLocated(double latitude, double longitude) {
		Place simple = simple(new Random().nextLong(), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5));
		return new LocatedPlace(String.valueOf(latitude), String.valueOf(longitude), simple);
	}

    public static List<LocatedPlace> placesWithinPolygons_20552253_24552653() {
        return Lists.newArrayList(simpleLocated(54,21), simpleLocated(54,25));
    }

}
