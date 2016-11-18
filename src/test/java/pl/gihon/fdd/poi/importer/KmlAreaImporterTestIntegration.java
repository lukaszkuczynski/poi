package pl.gihon.fdd.poi.importer;

import de.micromata.opengis.kml.v_2_2_0.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by luk on 2016-11-18.
 */
public class KmlAreaImporterTestIntegration {

    @Test
    public void importer_readsKmlFile() throws Exception {
        // given
        KmlAreaImporter importer = new KmlAreaImporter();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("easy.kml");

        // when
        Kml kml = importer.importAreas(is);

        // then
        String name = kml.getFeature().getName();
        assertThat(name).isEqualTo("London, UK");
    }

}