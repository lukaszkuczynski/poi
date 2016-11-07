package pl.gihon.fdd.poi.printer.template;

import org.junit.Test;
import pl.gihon.fdd.poi.AreasHelper;
import pl.gihon.fdd.poi.printer.Printer;

import static org.junit.Assert.*;

/**
 * Created by luk on 2016-11-07.
 */
public class HtmlTemplatePrinterTestIntegration {

    @Test
    public void testPrint() throws Exception {
        HtmlTemplatePrinterOptions options = new HtmlTemplatePrinterOptions();
        String output = "";
        options.setOutputFolder(output);
        String templatePath = "";
        options.setTemplatePath(templatePath);

        Printer printer = new HtmlTemplatePrinter(options);

        printer.print(AreasHelper.areas());
    }
}