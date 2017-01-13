package pl.gihon.fdd.poi.model;

import com.google.common.collect.Lists;
import com.googlecode.catchexception.CatchException;
import org.junit.Before;
import org.junit.Test;
import pl.gihon.fdd.poi.AreasHelper;
import pl.gihon.fdd.poi.PlaceHelper;
import pl.gihon.fdd.poi.PolygonHelper;
import pl.gihon.fdd.poi.validator.ValidationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;


/**
 * Created by luk on 2016-11-25.
 */
public class AreaMatcherTest {

    private AreaMatcher areaMatcher;

    @Before
    public void setUp() throws Exception {
        areaMatcher = new AreaMatcher();
    }

    private static List<LocatedPlace> placesInLat50_55Long20_22() {
        return Lists.newArrayList(PlaceHelper.simpleLocated(51,21), PlaceHelper.simpleLocated(52,22));
    }


    @Test
    public void matcher_givenAnyAreaWithoutPolygon_throwsError() throws Exception {
        List<Area> areasWithoutPolygons = AreasHelper.areas();
        List<LocatedPlace> places = placesInLat50_55Long20_22();
        try {
            areaMatcher.match(places, areasWithoutPolygons);
            assertTrue("given areas without polygon should throw error", false);
        } catch (Exception e) {
            assertTrue(true);
            assertThat(e).isInstanceOf(AreaMatchException.class);
        }
    }

    @Test
    public void matcher_givenAreasOverlapping_throwsError() {
        Polygon polygon1 = PolygonHelper.square("area1", 20.0, 55.0, 23.0, 50.0);
        Polygon polygon1prim = PolygonHelper.square("area1prim", 21.0, 55.0, 23.0, 50.0);
        Area area1 = new Area(polygon1);
        Area area1prim = new Area(polygon1prim);
        List<LocatedPlace> places = placesInLat50_55Long20_22();
        try {
            areaMatcher.match(places, Lists.newArrayList(area1, area1prim));
            assertTrue("given areas overlap should throw error", false);
        } catch (Exception e) {
            assertTrue(true);
            assertThat(e).isInstanceOf(AreaMatchException.class);
        }
    }

    @Test
    public void matcher_givenAreasOk_returnsNoUnassigned() {
        List<Area> areas = AreasHelper.areasWithPolygons_20552253_24552653();
        List<LocatedPlace> places = PlaceHelper.placesWithinPolygons_20552253_24552653();

        // when
        AreaMatchResult result = areaMatcher.match(places, areas);

        // then
        assertThat(result.getUnassignedPlaces()).hasSize(0);
    }

    @Test
    public void matcher_givenAreasWithPlacesAssignedWell() {
        List<Area> areas = AreasHelper.areasWithPolygons_20552253_24552653();
        List<LocatedPlace> places = PlaceHelper.placesWithinPolygons_20552253_24552653();

        // when
        AreaMatchResult result = areaMatcher.match(places, areas);

        // then
        assertThat(result.getAreas().get(0).contains(places.get(0)));
        assertThat(result.getAreas().get(1).contains(places.get(1)));
    }

}