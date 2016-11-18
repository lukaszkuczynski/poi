package pl.gihon.fdd.poi.model;

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
}
