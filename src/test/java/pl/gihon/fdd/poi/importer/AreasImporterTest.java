package pl.gihon.fdd.poi.importer;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

public class AreasImporterTest {

	@Test
	public void importer_readsAreas() throws JsonProcessingException, IOException {
		AreasImporter importer = new AreasImporter(new PyAreaReader());
		File file = new File(this.getClass().getClassLoader().getResource("areas.json").getFile());

		LocatedPlace lp1 = new LocatedPlace("", "", new Place(1L, "", "", "", "", new Date(), "status1"));
		LocatedPlace lp2 = new LocatedPlace("", "", new Place(2L, "", "", "", "", new Date(), "status2"));
		LocatedPlace lp3 = new LocatedPlace("", "", new Place(3L, "", "", "", "", new Date(), "status3"));
		LocatedPlace lp4 = new LocatedPlace("", "", new Place(4L, "", "", "", "", new Date(), "status4"));
		List<LocatedPlace> locatedPlaces = Lists.newArrayList(lp1, lp2, lp3, lp4);
		List<Area> areas = importer.importAreas(file, locatedPlaces);

		assertThat(areas).isNotEmpty();
		Area firstArea = areas.get(0);
		assertThat(firstArea.getNr()).isEqualTo(1);
		assertThat(firstArea.getName()).isEqualTo("area1");
		assertThat(firstArea.getPlaceCount()).isGreaterThan(0);
		LocatedPlace firstPlaceOfFirstArea = firstArea.getPlaces().get(0);
		assertThat(firstPlaceOfFirstArea.getStatus()).isEqualTo("status1");
	}
}
