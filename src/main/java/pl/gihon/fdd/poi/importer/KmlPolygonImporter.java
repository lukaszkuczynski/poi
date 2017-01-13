package pl.gihon.fdd.poi.importer;

import de.micromata.opengis.kml.v_2_2_0.*;
import org.springframework.stereotype.Component;
import pl.gihon.fdd.poi.model.Polygon;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luk on 2016-11-18.
 */
@Component
public class KmlPolygonImporter {

    public Kml importAreas(InputStream is) {
        final Kml kml = Kml.unmarshal(is);
        return kml;
    }

    public List<Polygon> importPolygons(InputStream is) {

        Kml kml = this.importAreas(is);

        // then
        final Document document = (Document)kml.getFeature();
        List<Feature> t = document.getFeature();
        List<Polygon> polygons = new ArrayList<>();
        //for each loop for iterating through the folders
        for(Object o : t) {
            Folder f = (Folder) o;
            List<Feature> tg = f.getFeature();


            //Iterating through placemarks inside all folders
            for (Object ftg : tg) {
                List<String> coordinates = new ArrayList<>();
                Placemark g = (Placemark) ftg;
                String polygonName = g.getName();
                Geometry geometry = g.getGeometry();
                de.micromata.opengis.kml.v_2_2_0.Polygon polygon = (de.micromata.opengis.kml.v_2_2_0.Polygon) geometry;
                List coordList = polygon.getOuterBoundaryIs().getLinearRing().getCoordinates();
                for (Object point : coordList) {
                    Coordinate coordinate = (Coordinate) point;
                    coordinates.add(coordinate.toString());
                }
                polygons.add(new Polygon(polygonName, coordinates));
            }



        }
        return polygons;

    }

    public List<Polygon> importPolygonsOneLayer(InputStream is) {

        Kml kml = this.importAreas(is);

        // then
        final Document document = (Document)kml.getFeature();
        List<Feature> t = document.getFeature();
        List<Polygon> polygons = new ArrayList<>();
        //for each loop for iterating through the folders
        for(Object o : t) {
                List<String> coordinates = new ArrayList<>();
                Placemark g = (Placemark) o;
                String polygonName = g.getName();
                Geometry geometry = g.getGeometry();
                de.micromata.opengis.kml.v_2_2_0.Polygon polygon = (de.micromata.opengis.kml.v_2_2_0.Polygon) geometry;
                List coordList = polygon.getOuterBoundaryIs().getLinearRing().getCoordinates();
                for (Object point : coordList) {
                    Coordinate coordinate = (Coordinate) point;
                    coordinates.add(coordinate.toString());
                }
                polygons.add(new Polygon(polygonName, coordinates));



        }
        return polygons;

    }
}
