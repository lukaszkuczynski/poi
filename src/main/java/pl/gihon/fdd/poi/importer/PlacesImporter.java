package pl.gihon.fdd.poi.importer;

import java.io.File;
import java.util.List;

import pl.gihon.fdd.poi.model.Place;

public interface PlacesImporter {

	public List<Place> importPlaces(File file);
}
