package pl.gihon.fdd.poi.importer;

import com.google.common.collect.Lists;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.Polygon;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by luk on 2016-11-24.
 */
@Service
public class KmlAreasReader {

    private static final Logger logger = LoggerFactory.getLogger(KmlAreasReader.class);

    @Autowired
    private KmlPolygonImporter polygonImporter;

    public List<Area> read(InputStream kmlFile) {
        logger.debug("reading for stream {} ", kmlFile);

        List<Polygon> polygons = polygonImporter.importPolygons(kmlFile);
        List<Area> areas = polygons.stream().map( p -> new Area(p)).collect(Collectors.toList());
        IntStream.range(0,areas.size()).forEach( idx -> areas.get(idx).setNr(idx+1) );

        return areas;
    }

}
