package pl.gihon.fdd.poi.importer;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;

public class CsvImporterTest {

	Importer importer = new CsvImporter();

	@Test
	public void importer_givenFile_importsAllPlaces() {

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("poi_csv.csv").getFile());

		assertThat(file.exists()).isTrue();

		importer.importPlaces(file);
	}

}
