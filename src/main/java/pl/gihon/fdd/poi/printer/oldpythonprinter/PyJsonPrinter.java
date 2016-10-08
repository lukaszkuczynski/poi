package pl.gihon.fdd.poi.printer.oldpythonprinter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.printer.Printer;

public class PyJsonPrinter implements Printer {

	private File areasFile;
	private File placesFile;

	public PyJsonPrinter(File areasFilePath, File placesFilePath) {
		super();
		this.areasFile = areasFilePath;
		this.placesFile = placesFilePath;
	}

	@Override
	public void print(List<Area> areas) throws IOException {
		List<LocatedPlace> places = new ArrayList<>();
		List<PyArea> pyAreas = new ArrayList<>();
		for (Area area : areas) {
			pyAreas.add(new PyArea(area));
			places.addAll(area.getPlaces());
		}
		List<PyPlace> pyPlaces = places.stream().map(p -> new PyPlace(p)).collect(Collectors.toList());

		printPlacesToJson(pyPlaces);
		printAreasToJson(pyAreas);

	}

	private void printAreasToJson(List<PyArea> pyAreas) throws IOException {
		ObjectWriter objectWriter = getWriter();
		objectWriter.writeValue(areasFile, pyAreas);
	}

	private ObjectWriter getWriter() {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
		return objectWriter;
	}

	private void printPlacesToJson(List<PyPlace> pyPlaces) throws IOException {
		ObjectWriter objectWriter = getWriter();
		objectWriter.writeValue(placesFile, pyPlaces);

	}

	@Override
	public void printWithUnassigned(List<Area> areas, List<LocatedPlace> unassignedPlaces) throws IOException {

	}

}
