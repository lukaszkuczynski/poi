package pl.gihon.fdd.poi.importer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by luk on 2016-11-24.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class KmlAreasReaderContextTest {

    @Autowired
    private KmlAreasReader reader;

    @Test
    public void testRead() throws Exception {
        reader.read(null);
    }
}