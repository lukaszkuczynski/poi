package pl.gihon.fdd.poi.printer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

/**
 * prints csv readable by google my maps import CSV
 * 
 * @author luk
 *
 */
public class CsvPlacePrinter implements Printer {

	private static final char DEFAULT_COLUMN_SEPARATOR = ',';
	private char columnSeparator = DEFAULT_COLUMN_SEPARATOR;

	class AssignedPlace extends LocatedPlace {
		Long areaNr;

		public Long getAreaNr() {
			return areaNr;
		}

		public void setAreaNr(Long areaNr) {
			this.areaNr = areaNr;
		}

		AssignedPlace(LocatedPlace p, Long areaNr) {
			super(p.getLatitude(), p.getLongitude(), new Place(p.getId(), p.getStreetAndFlat(), p.getCity()));
			this.areaNr = areaNr;
		}
	}

	@Override
	public void print(List<Area> areas) throws IOException {
		List<AssignedPlace> assignedPlaces = areasToList(areas);
		printPlaces(assignedPlaces);
	}

	private void printPlaces(List<AssignedPlace> assignedPlaces) throws IOException {
		CsvMapper mapper = new CsvMapper();
		File file = new File(fileName);
		CsvSchema schema = mapper.schemaFor(AssignedPlace.class).withColumnSeparator(columnSeparator).withHeader();
		SequenceWriter writeValues = mapper.writerFor(AssignedPlace.class).with(schema).writeValues(file);
		writeValues.writeAll(assignedPlaces);
	}

	private List<AssignedPlace> areasToList(List<Area> areas) {
		List<AssignedPlace> places = new ArrayList<>();
		for (Area area : areas) {
			for (LocatedPlace place : area.getPlaces()) {
				places.add(new AssignedPlace(place, area.getNr()));
			}
		}
		return places;
	}

	private String fileName;

	public CsvPlacePrinter(String file) {
		super();
		this.fileName = file;
	}

	@Override
	public void printWithUnassigned(List<Area> areas, List<LocatedPlace> unassignedPlaces) throws IOException {
		List<AssignedPlace> assignedPlaces = areasToList(areas);
		List<AssignedPlace> placesAssignedToNullArea = unassignedToAreaNull(unassignedPlaces);
		// TODO better joining lists?
		List<AssignedPlace> allPlaces = new ArrayList<>();
		allPlaces.addAll(assignedPlaces);
		allPlaces.addAll(placesAssignedToNullArea);
		printPlaces(allPlaces);
	}

	private List<AssignedPlace> unassignedToAreaNull(List<LocatedPlace> unassignedPlaces) {
		return unassignedPlaces.stream().map(lp -> new AssignedPlace(lp, null)).collect(Collectors.toList());
	}

}
