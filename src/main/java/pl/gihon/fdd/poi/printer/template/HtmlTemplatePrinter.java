package pl.gihon.fdd.poi.printer.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.printer.Printer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by luk on 2016-11-07.
 */
@Component
public class HtmlTemplatePrinter implements Printer {

    @Autowired
    SpringTemplateEngine templateEngineForHtml;

    @Value("${printer.html.output.folder}")
    String outputPath;

    public static final String TEMPLATE_NAME = "card.html";


    private static Logger logger = LoggerFactory.getLogger(HtmlTemplatePrinter.class);


    @Override
    public void print(List<Area> areas) throws IOException {

        for(Area area : areas) {
            printOneArea(area);
        }

    }

    private void printOneArea(Area area) throws IOException {
        Context context = new Context();

        String filename = "out"+area.getNr()+".html";

        context.setVariable("area", area);
        String content = templateEngineForHtml.process("template", context);
        String outputFileName = outputPath + filename;
        FileOutputStream fos = new FileOutputStream(outputFileName);
        fos.write(content.getBytes());

    }

    @Override
    public void printWithUnassigned(List<Area> areas, List<LocatedPlace> unassignedPlaces) throws IOException {
        logger.warn("unassigned places will be skipped, printing only areas");
        print(areas);
    }
}
