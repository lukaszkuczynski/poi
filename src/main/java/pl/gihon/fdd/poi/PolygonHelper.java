package pl.gihon.fdd.poi;

import com.google.common.collect.Lists;
import pl.gihon.fdd.poi.model.Polygon;

import java.util.List;

/**
 * Created by luk on 2016-11-25.
 */
public class PolygonHelper {

    public static Polygon square(String name, double topLeftLong, double topLeftLat, double bottomRightLong, double doubleRightLat) {
        String leftTop = String.valueOf(topLeftLong) + "," + String.valueOf(topLeftLat);
        String rightTop = String.valueOf(bottomRightLong) + "," + String.valueOf(topLeftLat);
        String rightBottom = String.valueOf(bottomRightLong) + "," + String.valueOf(doubleRightLat);
        String leftBottom = String.valueOf(topLeftLong) + "," + String.valueOf(doubleRightLat);

        List<String> coordinates = Lists.newArrayList(leftTop, rightTop, rightBottom, leftBottom, leftTop);
        return new Polygon(name, coordinates);
    }
}
