package pl.gihon.fdd.poi.importer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.printer.oldpythonprinter.PyArea;

/**
 * reads areas from json file
 * 
 * @author luk
 *
 */
@Component
public class AreasImporter {

	private PyAreaReader reader;

	@Autowired
	public AreasImporter(PyAreaReader reader) {
		super();
		this.reader = reader;
	}

	public List<Area> importAreas(File file, List<LocatedPlace> locatedPlaces)
			throws JsonProcessingException, IOException {
		List<PyArea> pyAreas = reader.readAll(file);
		List<Area> areas = pyAreas.stream().map(p -> toArea(p, locatedPlaces)).collect(Collectors.toList());
		return areas;
	}

	private Area toArea(PyArea pyArea, List<LocatedPlace> places) {
		Area area = new Area(pyArea.getNr(), pyArea.getName());
		List<LocatedPlace> placesFound = Lists.newArrayList();
		for (final long idd : pyArea.getPlace_ids()) {
			placesFound.add(places.stream().filter(lp -> lp.getId() == idd).findFirst().get());
		}
		area.setPlaces(placesFound);
		return area;
	}

}
