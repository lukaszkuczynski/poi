package pl.gihon.fdd.poi.model;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Point;

import java.util.List;

/**
 * Created by luk on 2016-11-18.
 */
public class Polygon {

    private String name;
    private List<String> coordinates;

    public String getName() {
        return name;
    }

    public List<String> getCoordinates() {
        return coordinates;
    }

    public Polygon(String name, List<String> coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }

    public boolean contains(String lng, String lat) {
        GeometryFactory gf = new GeometryFactory();
        Coordinate[] points = new Coordinate[coordinates.size()];
        for (int n=0; n<points.length; n++) {
            points[n] = new Coordinate(Double.valueOf(coordinates.get(n).split(",")[0]), Double.valueOf(coordinates.get(n).split(",")[1]));
        }
        LinearRing jtsRing = gf.createLinearRing(points);
        com.vividsolutions.jts.geom.Polygon polygon = gf.createPolygon(jtsRing, null);
        Coordinate coordinate = new Coordinate(Double.valueOf(lng), Double.valueOf(lat));
        Point point = gf.createPoint(coordinate);
        return polygon.contains(point);

    }
}
