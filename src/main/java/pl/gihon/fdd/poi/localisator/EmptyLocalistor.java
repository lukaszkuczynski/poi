package pl.gihon.fdd.poi.localisator;

import java.util.List;
import java.util.stream.Collectors;

import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

public class EmptyLocalistor implements Localisator {

	@Override
	public List<LocatedPlace> locate(List<Place> places) {
		List<LocatedPlace> locatedPlaces = places.stream().map((p) -> new LocatedPlace("", "", p))
				.collect(Collectors.toList());
		return locatedPlaces;
	}

}
