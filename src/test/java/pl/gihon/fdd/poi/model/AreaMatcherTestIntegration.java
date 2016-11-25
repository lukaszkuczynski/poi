package pl.gihon.fdd.poi.model;

import org.junit.Test;
import pl.gihon.fdd.poi.importer.KmlAreasReader;
import pl.gihon.fdd.poi.importer.KmlPolygonImporter;
import pl.gihon.fdd.poi.importer.LocatedPlacesReaderJson;
import pl.gihon.fdd.poi.importer.PyAreaReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by luk on 2016-11-25.
 */
public class AreaMatcherTestIntegration {

    @Test
    public void matchFromPy() throws IOException {

        LocatedPlacesReaderJson readerJson = new LocatedPlacesReaderJson();
        List<LocatedPlace> locatedPlaces = readerJson.readPlaces(new File("d:\\temp\\pyplaces-last.json"));
        KmlAreasReader areasReader = new KmlAreasReader(new KmlPolygonImporter());
        List<Area> areas = areasReader.read(new FileInputStream(new File("d:\\temp\\poi_test_polygons.kml")));

        assertThat(areas).isNotEmpty();
        assertThat(locatedPlaces).isNotEmpty();

        AreaMatcher matcher = new AreaMatcher();
        matcher.match(locatedPlaces, areas);

    }
}
