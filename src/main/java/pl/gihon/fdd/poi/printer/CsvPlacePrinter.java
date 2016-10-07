package pl.gihon.fdd.poi.printer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

public class CsvPlacePrinter implements Printer {

	private static final char DEFAULT_COLUMN_SEPARATOR = '\t';
	private char columnSeparator = DEFAULT_COLUMN_SEPARATOR;

	class AssignedPlace extends LocatedPlace {
		long areaNr;

		public long getAreaNr() {
			return areaNr;
		}

		public void setAreaNr(long areaNr) {
			this.areaNr = areaNr;
		}

		AssignedPlace(LocatedPlace p, long areaNr) {
			super(p.getLatitude(), p.getLongitude(), new Place(p.getId(), p.getStreetAndFlat(), p.getCity()));
			this.areaNr = areaNr;
		}
	}

	@Override
	public void print(List<Area> areas) throws IOException {
		CsvMapper mapper = new CsvMapper();
		File file = new File(fileName);

		List<AssignedPlace> assignedPlaces = areasToList(areas);

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

}
