package pl.gihon.fdd.poi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import pl.gihon.fdd.poi.filter.Filter;
import pl.gihon.fdd.poi.importer.PlacesImporter;
import pl.gihon.fdd.poi.localisator.Localisator;
import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;
import pl.gihon.fdd.poi.printer.Printer;
import pl.gihon.fdd.poi.validator.Validator;

public class PoiTestE2E {

	PlacesImporter importer;
	Filter filter;
	Validator validator;
	Localisator localisator;
	TestClusterer clusterer;
	Printer printer;

	@Test
	@Ignore
	public void app_givenAllInputs_printsAreas() throws IOException {

		String filePath = "";
		File file = new File(filePath);
		List<Place> places = importer.importPlaces(file);
		validator.validate(places);
		List<Place> filteredPlaces = filter.filter(places);
		List<LocatedPlace> locatedPlaces = localisator.locate(filteredPlaces);

		// this is job done by user
		List<Area> areas = clusterer.cluster(locatedPlaces);
		printer.print(areas);
		// assert areas
	}

}
