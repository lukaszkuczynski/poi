package pl.gihon.fdd.poi.importer;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Test;

import pl.gihon.fdd.poi.model.Place;

public class CsvImporterTest {

	Importer importer = new CsvImporter();

	@Test
	public void importer_givenFile_importsCorrectNumberOfPlaces() {

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("konin.tsv").getFile());

		assertThat(file.exists()).isTrue();

		List<Place> places = importer.importPlaces(file);
		assertThat(places).hasSize(3);
	}

	@Test
	public void importer_mapsFieldsCorrectly() {

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("konin.tsv").getFile());

		assertThat(file.exists()).isTrue();

		List<Place> places = importer.importPlaces(file);
		Place place = places.get(0);
		assertThat(place.getId()).isEqualTo(1L);
		assertThat(place.getCity()).isEqualTo("Konin");
		assertThat(place.getStreetAndFlat()).isEqualTo("Spółdzielców 1");
		assertThat(place.getOtherHost()).isEmpty();
		assertThat(place.getLastMet()).isEqualToIgnoringHours("2016-01-12");
		assertThat(place.getLang1()).isEqualTo("German");
		assertThat(place.getLang2()).isEqualTo("English");
		assertThat(place.getOther_rl()).isEqualTo("p");
		assertThat(place.getLatitude()).isEqualTo("latitude");
		assertThat(place.getLongitude()).isEqualTo("longitude");
		assertThat(place.getTest()).isEqualTo("n1");
	}

}
