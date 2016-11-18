package pl.gihon.fdd.poi.model;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created by luk on 2016-11-18.
 */
public class PolygonTest {

    @Test
    public void testContains() throws Exception {
        ArrayList<String> coordinatesOfSkierniewiceNorth = Lists.newArrayList(
                "20.1257515,51.9771618",
                "20.1357079,51.9716631",
                "20.1374245,51.964154",
                "20.1767349,51.9748355",
                "20.186348,51.9909058",
                "20.1655769,51.9927028",
                "20.1257515,51.9771618");


        Polygon polygon = new Polygon("SkierniewiceNorth", coordinatesOfSkierniewiceNorth);


        boolean contains = polygon.contains("20.149", "51.97");
        assertThat(contains).isTrue();

        boolean outsideContains = polygon.contains("20.16248", "51.96954");
        assertThat(outsideContains).isFalse();

    }
}