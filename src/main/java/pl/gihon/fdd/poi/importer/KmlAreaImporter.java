package pl.gihon.fdd.poi.importer;

import de.micromata.opengis.kml.v_2_2_0.Kml;

import java.io.InputStream;

/**
 * Created by luk on 2016-11-18.
 */
public class KmlAreaImporter {

    public Kml importAreas(InputStream is) {
        final Kml kml = Kml.unmarshal(is);
        return kml;
    }
}
