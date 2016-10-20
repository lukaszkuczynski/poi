package pl.gihon.fdd.poi.importer;

import java.io.File;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import pl.gihon.fdd.poi.exception.ImportException;
import pl.gihon.fdd.poi.model.Place;

/**
 * internal settings as - separator can be matter of bean configuration later
 * 
 * @author luk
 *
 */
public class CsvImporter implements PlacesImporter {

	private static final char DEFAULT_COLUMN_SEPARATOR = '\t';
	private char columnSeparator = DEFAULT_COLUMN_SEPARATOR;

	@Override
	public List<Place> importPlaces(File file) {
		try {
			CsvMapper mapper = new CsvMapper();

			CsvSchema schema = mapper.schemaFor(Place.class)
					.withColumnSeparator(columnSeparator)
					.withHeader();
			MappingIterator<Place> places = mapper.readerFor(Place.class).with(schema).readValues(file);
			return places.readAll();
		} catch (Exception e) {
			throw new ImportException(e);
		}
	}

}
