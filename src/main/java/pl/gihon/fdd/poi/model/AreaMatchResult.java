package pl.gihon.fdd.poi.model;

import java.util.List;

/**
 * Created by luk on 2016-11-25.
 */
public class AreaMatchResult {

    private List<Area> areas;
    private List<LocatedPlace> unassignedPlaces;

    public AreaMatchResult(List<Area> areas, List<LocatedPlace> unassignedPlaces) {
        this.areas = areas;
        this.unassignedPlaces = unassignedPlaces;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public List<LocatedPlace> getUnassignedPlaces() {
        return unassignedPlaces;
    }
}
