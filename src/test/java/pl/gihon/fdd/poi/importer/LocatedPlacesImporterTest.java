package pl.gihon.fdd.poi.importer;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;

import org.junit.Test;

import pl.gihon.fdd.poi.model.LocatedPlace;

public class LocatedPlacesImporterTest {

	@Test
	public void importer_imports() {

		LocatedPlacesImporter importer = new LocatedPlacesImporter();

		File file = new File(this.getClass().getClassLoader().getResource("konin_assigned.csv").getFile());

		List<LocatedPlace> located = importer.importFromMyMapsExportFile(file);

		assertThat(located).isNotEmpty();
		assertThat(located.get(0).getLatitude()).isEqualTo("52.240507");
	}

}
