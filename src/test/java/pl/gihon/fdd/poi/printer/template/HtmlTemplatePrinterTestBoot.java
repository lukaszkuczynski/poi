package pl.gihon.fdd.poi.printer.template;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.gihon.fdd.poi.AreasHelper;
import pl.gihon.fdd.poi.ContextTest;
import pl.gihon.fdd.poi.printer.Printer;

import static org.junit.Assert.*;

/**
 * Created by luk on 2016-11-07.
 */
public class HtmlTemplatePrinterTestBoot extends ContextTest {

    @Autowired
    HtmlTemplatePrinter printer;


    @Test
    public void testPrint() throws Exception {
        printer.print(AreasHelper.areas());
    }
}