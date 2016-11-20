package pl.gihon.fdd.poi.printer.template;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.gihon.fdd.poi.AreasHelper;
import pl.gihon.fdd.poi.BootTest;
import pl.gihon.fdd.poi.GoogleApiBootTest;

/**
 * Created by luk on 2016-11-07.
 */
public class HtmlTemplatePrinterTestBoot extends GoogleApiBootTest {

    @Autowired
    HtmlTemplatePrinter printer;


    @Test
    public void testPrint() throws Exception {
        printer.print(AreasHelper.areas());
    }
}