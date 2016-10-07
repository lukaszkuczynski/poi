package pl.gihon.fdd.poi.printer;

import java.io.IOException;
import java.util.List;

import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;

public interface Printer {

	void print(List<Area> areas) throws IOException;

	void printWithUnassigned(List<Area> areas, List<LocatedPlace> unassignedPlaces) throws IOException;

}
