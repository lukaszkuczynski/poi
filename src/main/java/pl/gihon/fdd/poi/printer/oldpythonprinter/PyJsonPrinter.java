package pl.gihon.fdd.poi.printer.oldpythonprinter;

import java.io.IOException;
import java.util.List;

import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.printer.Printer;

public class PyJsonPrinter implements Printer {

	private String areasFilePath;
	private String placesFilePath;

	public PyJsonPrinter(String areasFilePath, String placesFilePath) {
		super();
		this.areasFilePath = areasFilePath;
		this.placesFilePath = placesFilePath;
	}

	@Override
	public void print(List<Area> areas) throws IOException {

	}

	@Override
	public void printWithUnassigned(List<Area> areas, List<LocatedPlace> unassignedPlaces) throws IOException {

	}

}
