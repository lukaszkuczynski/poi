package pl.gihon.fdd.poi.importer;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.gihon.fdd.poi.model.Area;

import java.io.InputStream;
import java.util.List;

/**
 * Created by luk on 2016-11-24.
 */
@Service
public class KmlAreasReader {

    private static final Logger logger = LoggerFactory.getLogger(KmlAreasReader.class);

    public List<Area> read(InputStream kmlFile) {
        logger.debug("reading for stream {} ", kmlFile);
        return Lists.newArrayList(new Area());
    }
}
