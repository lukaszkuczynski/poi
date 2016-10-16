package pl.gihon.fdd.poi.printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;
import pl.gihon.fdd.poi.printer.googlemymaps.CsvPlacePrinter;

public class CsvPlacePrinterTestIntegration {

	@Test
	public void test_print() throws IOException {
		CsvPlacePrinter csvPlacePrinter = new CsvPlacePrinter("d:\\temp\\aa.csv");

		csvPlacePrinter.print(AreasHelper.areas());

	}

	@Test
	public void test_print_withUnassigned() throws IOException {
		CsvPlacePrinter csvPlacePrinter = new CsvPlacePrinter("d:\\temp\\aaunas.csv");

		List<LocatedPlace> sadUnassignedLonelyPlaces = new ArrayList<>();
		sadUnassignedLonelyPlaces.add(new LocatedPlace("", "", new Place(5L, "Grójecka 1", "Warszawa")));
		sadUnassignedLonelyPlaces.add(new LocatedPlace("", "", new Place(6L, "Kowalska 1", "Warszawa")));
		sadUnassignedLonelyPlaces.add(new LocatedPlace("", "", new Place(7L, "Podmiejska 1", "Warszawa")));

		csvPlacePrinter.printWithUnassigned(AreasHelper.areas(), sadUnassignedLonelyPlaces);

	}

}
