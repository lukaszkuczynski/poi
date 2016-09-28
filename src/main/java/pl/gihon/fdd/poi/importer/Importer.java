package pl.gihon.fdd.poi.importer;

import java.util.List;

import pl.gihon.fdd.poi.model.Place;

public interface Importer {

	public List<Place> importPlaces(String filePath);
}
