package pl.gihon.fdd.poi.importer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import pl.gihon.fdd.poi.exception.PoiException;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;
import pl.gihon.fdd.poi.printer.googlemymaps.AssignedPlace;

@Component
public class LocatedPlacesImporter {

	private static final char DEFAULT_COLUMN_SEPARATOR = ',';
	private char columnSeparator = DEFAULT_COLUMN_SEPARATOR;

	public List<LocatedPlace> importUnassignedPlaces(File file) {
		try {
			List<AssignedPlace> assignedAndNotAssigned = loadAssignedPlaces(file);
			List<AssignedPlace> reallyAssigned = assignedAndNotAssigned.stream().filter(ap -> ap.getAreaNr() == null)
					.collect(Collectors.toList());

			List<LocatedPlace> located = reallyAssigned.stream().map(ap -> assignedToLocated(ap))
					.collect(Collectors.toList());
			return located;
		} catch (IOException e) {
			throw new PoiException(e);
		}
	}

	private List<AssignedPlace> loadAssignedPlaces(File file) throws JsonProcessingException, IOException {
		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(AssignedPlace.class).withColumnSeparator(columnSeparator).withHeader();
		MappingIterator<AssignedPlace> readValues = mapper.readerFor(AssignedPlace.class).with(schema).readValues(file);
		return readValues.readAll();
	}

	private LocatedPlace assignedToLocated(AssignedPlace ap) {
		return new LocatedPlace(ap.getLatitude(), ap.getLongitude(), new Place(ap.getId(), ap.getStreetAndFlat(),
				ap.getCity(), ap.getLang1(), ap.getLang2(), ap.getLastMet(), ap.getStatus()));
	}
}
