package pl.gihon.fdd.poi;

import java.util.List;

import org.assertj.core.util.Lists;

import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;

public class AreasHelper {

	public static List<Area> areas() {

		Area area1 = new Area(1, "area1");
		area1.addPlace(new LocatedPlace("52.225440", "21.228237", PlaceHelper.simple(1L, "Mazowiecka 1", "Warszawa")));
		area1.addPlace(new LocatedPlace("52.226794", "21.233705", PlaceHelper.simple(2L, "Brzozowa 23", "Warszawa")));
		Area area2 = new Area(2, "area2");
		area2.addPlace(new LocatedPlace("52.195041", "20.885705", PlaceHelper.simple(3L, "Kościuszki 23", "Warszawa")));
		area2.addPlace(
				new LocatedPlace("52.184926", "20.881196", PlaceHelper.simple(4L, "Michałowicza 22", "Warszawa")));
		List<Area> areas = Lists.newArrayList(area1, area2);
		return areas;

	}
}
