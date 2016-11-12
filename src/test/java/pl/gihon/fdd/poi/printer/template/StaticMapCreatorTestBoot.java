package pl.gihon.fdd.poi.printer.template;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.gihon.fdd.poi.AreasHelper;
import pl.gihon.fdd.poi.ContextTest;
import pl.gihon.fdd.poi.GoogleApiContextTest;

import static org.junit.Assert.*;

/**
 * Created by luk on 2016-11-12.
 */
public class StaticMapCreatorTestBoot extends GoogleApiContextTest {

    @Autowired
    private StaticMapCreator staticMapCreator;

    @Test
    public void testGetForArea() throws Exception {
        String outImage = "d:\\temp\\printerout\\01.png";
        staticMapCreator.getForArea(AreasHelper.areas().get(0), outImage);
    }
}