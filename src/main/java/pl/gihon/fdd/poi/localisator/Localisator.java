package pl.gihon.fdd.poi.localisator;

import java.util.List;

import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

public interface Localisator {

	List<LocatedPlace> locate(List<Place> places);
}
