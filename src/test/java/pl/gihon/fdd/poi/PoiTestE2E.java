package pl.gihon.fdd.poi;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import pl.gihon.fdd.poi.importer.Importer;
import pl.gihon.fdd.poi.localisator.Localisator;
import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;
import pl.gihon.fdd.poi.printer.Printer;
import pl.gihon.fdd.poi.validator.Validator;

public class PoiTestE2E {

	Importer importer;
	Validator validator;
	Localisator localisator;
	TestClusterer clusterer;
	Printer printer;

	@Test
	@Ignore
	public void app_givenAllInputs_printsAreas() {

		String filePath = "";
		List<Place> places = importer.importPlaces(filePath);
		validator.validate(places);
		List<LocatedPlace> locatedPlaces = localisator.locate(places);

		// this is job done by user
		List<Area> areas = clusterer.cluster(locatedPlaces);
		printer.print(areas);
		// assert areas
	}

}
