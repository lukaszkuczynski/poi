package pl.gihon.fdd.poi.printer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;

import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

public class CsvPlacePrinterTestIntegration {

	@Test
	public void test_print() throws IOException {
		CsvPlacePrinter csvPlacePrinter = new CsvPlacePrinter("C:\\aa.csv");

		Area area1 = new Area(1, "area1");
		area1.addPlace(new LocatedPlace("", "", new Place(1L, "Mazowiecka 1", "Warszawa")));
		area1.addPlace(new LocatedPlace("", "", new Place(2L, "Mazowiecka 24", "Warszawa")));
		Area area2 = new Area(2, "area2");
		area2.addPlace(new LocatedPlace("", "", new Place(3L, "Kościuszki 23", "Warszawa")));
		area2.addPlace(new LocatedPlace("", "", new Place(4L, "Powstańców 24", "Warszawa")));
		List<Area> areas = Lists.newArrayList(area1, area2);

		csvPlacePrinter.print(areas);

	}

	@Test
	public void test_print_withUnassigned() throws IOException {
		CsvPlacePrinter csvPlacePrinter = new CsvPlacePrinter("C:\\aa.csv");

		Area area1 = new Area(1, "area1");
		area1.addPlace(new LocatedPlace("", "", new Place(1L, "Mazowiecka 1", "Warszawa")));
		area1.addPlace(new LocatedPlace("", "", new Place(2L, "Mazowiecka 24", "Warszawa")));
		Area area2 = new Area(2, "area2");
		area2.addPlace(new LocatedPlace("", "", new Place(3L, "Kościuszki 23", "Warszawa")));
		area2.addPlace(new LocatedPlace("", "", new Place(4L, "Powstańców 24", "Warszawa")));

		List<LocatedPlace> sadUnassignedLonelyPlaces = new ArrayList<>();
		sadUnassignedLonelyPlaces.add(new LocatedPlace("", "", new Place(5L, "Grójecka 1", "Warszawa")));
		sadUnassignedLonelyPlaces.add(new LocatedPlace("", "", new Place(6L, "Kowalska 1", "Warszawa")));
		sadUnassignedLonelyPlaces.add(new LocatedPlace("", "", new Place(7L, "Podmiejska 1", "Warszawa")));
		List<Area> areas = Lists.newArrayList(area1, area2);

		csvPlacePrinter.printWithUnassigned(areas, sadUnassignedLonelyPlaces);

	}

}
