package pl.gihon.fdd.poi.printer;

import java.io.IOException;
import java.util.List;

import pl.gihon.fdd.poi.model.Area;

public interface Printer {

	void print(List<Area> areas) throws IOException;

}
