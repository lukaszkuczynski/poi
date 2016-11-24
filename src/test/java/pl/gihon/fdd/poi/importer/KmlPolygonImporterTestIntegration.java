package pl.gihon.fdd.poi.importer;

import de.micromata.opengis.kml.v_2_2_0.*;
import org.junit.Test;
import pl.gihon.fdd.poi.model.Polygon;

import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by luk on 2016-11-18.
 */
public class KmlPolygonImporterTestIntegration {

    @Test
    public void importer_readsKmlFile() throws Exception {
        // given
        KmlPolygonImporter importer = new KmlPolygonImporter();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("easy.kml");

        // when
        Kml kml = importer.importAreas(is);

        // then
        String name = kml.getFeature().getName();
        assertThat(name).isEqualTo("London, UK");
    }

    @Test
    public void importer_readsPolygons() throws Exception {
        // given
        KmlPolygonImporter importer = new KmlPolygonImporter();
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("poi test.kml");

        // when
        List<pl.gihon.fdd.poi.model.Polygon> polygons = importer.importPolygons(is);

        // then
        assertThat(polygons).hasSize(2);
        Polygon zwierzyniec = polygons.get(0);
        //20.1159668,51.9748355,0.0 20.1073837,51.9489206,0.0 20.1386261,51.9531526,0.0 20.1357079,51.9716631,0.0 20.1159668,51.9748355,0.0
        assertThat(zwierzyniec.getName()).isEqualTo("zwierzyniec");
        assertThat(zwierzyniec.getCoordinates()).contains("20.1159668,51.9748355");


    }

}