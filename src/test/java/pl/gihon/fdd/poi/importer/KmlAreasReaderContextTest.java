package pl.gihon.fdd.poi.importer;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.gihon.fdd.poi.model.Area;

import java.io.InputStream;
import java.util.List;

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

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("poi test.kml");

        List<Area> areas = reader.read(is);
        assertThat(areas).hasSize(2);
        Area area1st = areas.get(0);
        assertThat(area1st.getName()).isEqualTo("zwierzyniec");
        assertThat(area1st.hasPolygon()).isTrue();
    }
}