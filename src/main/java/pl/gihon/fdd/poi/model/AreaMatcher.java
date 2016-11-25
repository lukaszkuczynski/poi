package pl.gihon.fdd.poi.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Matches places to areas basing on Polygon
 * Created by luk on 2016-11-25.
 */
public class AreaMatcher {

    public AreaMatchResult match(List<LocatedPlace> places, List<Area> areas) {
        List<LocatedPlace> unassigned = new ArrayList<>();

        for (Area area : areas) {

        }
        return new AreaMatchResult(areas, unassigned);

    }

}
