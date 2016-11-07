package pl.gihon.fdd.poi.printer.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.printer.Printer;

import java.io.IOException;
import java.util.List;

/**
 * Created by luk on 2016-11-07.
 */
public class HtmlTemplatePrinter implements Printer {

    private static Logger logger = LoggerFactory.getLogger(HtmlTemplatePrinter.class);

    public HtmlTemplatePrinter(HtmlTemplatePrinterOptions options) {

    }

    @Override
    public void print(List<Area> areas) throws IOException {

    }

    @Override
    public void printWithUnassigned(List<Area> areas, List<LocatedPlace> unassignedPlaces) throws IOException {
        logger.warn("unassigned places will be skipped, printing only areas");
        print(areas);
    }
}
