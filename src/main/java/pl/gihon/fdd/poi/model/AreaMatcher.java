package pl.gihon.fdd.poi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Matches places to areas basing on Polygon
 * Created by luk on 2016-11-25.
 */
public class AreaMatcher {

    public AreaMatchResult match(List<LocatedPlace> places, List<Area> areas) {
        List<LocatedPlace> unassigned = new ArrayList<>();
        boolean allMatch = areas.stream().allMatch(Area::hasPolygon);
        if (!allMatch) throw new AreaMatchException("all given areas has to have polygons");


        for (LocatedPlace place : places) {
            long matchingCount = areas.stream().filter(a -> a.contains(place)).count();
            if (matchingCount > 1) {
                throw new AreaMatchException("more than one area match place = "+place);
            } else if (matchingCount == 0) {
                unassigned.add(place);
            } else {
                Optional<Area> first = areas.stream().filter(a -> a.contains(place)).findFirst();
                first.get().addPlace(place);
            }
        }
        return new AreaMatchResult(areas, unassigned);

    }

}
