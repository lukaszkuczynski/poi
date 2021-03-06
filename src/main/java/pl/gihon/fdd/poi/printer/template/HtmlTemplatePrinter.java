package pl.gihon.fdd.poi.printer.template;

import org.apache.commons.io.FileUtils;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    /**
     * if set to true printer will always query for image even having it cached
     */
    @Value("${printer.html.nocache}")
    boolean noCache;


    public static final String TEMPLATE_NAME = "card.html";


    private static Logger logger = LoggerFactory.getLogger(HtmlTemplatePrinter.class);

    @Autowired
    private StaticMapCreator staticMapCreator;


    @Override
    public void print(List<Area> areas) throws IOException {
        logger.info("printing {} areas with noCache {}", areas.size(), noCache);
        for(Area area : areas) {
            printOneArea(area);
        }

    }

    private void printOneArea(Area area) throws IOException {

        String imageSrc = ensureImagePathForArea(area);

        Context context = new Context();
        context.setVariable("area", area);
        context.setVariable("imageSrc", imageSrc);

        String filename = "out"+area.getNr()+".html";
        String content = templateEngineForHtml.process("template", context);

        String outputFileName = outputPath + filename;
        FileOutputStream fos = new FileOutputStream(outputFileName);
        fos.write(content.getBytes());
    }

    private String getAreaImageFileName(Area area) {
        return outputPath + area.getNr()+".png";
    }

    private String ensureImagePathForArea(Area area) throws IOException {
        String imgFileName = getAreaImageFileName(area);
        if (noCache || !isCachedImageFor(area)) {
            logger.debug("will use map creator and save on {}", imgFileName);
            staticMapCreator.getForArea(area, imgFileName);
        }
        return imgFileName;
    }

    private boolean isCachedImageFor(Area area) {
        Path path = Paths.get(getAreaImageFileName(area));
        boolean cached = Files.exists(path);
        logger.debug("checking if cached for area {} : cache {}", area, cached);
        return cached;
    }

    @Override
    public void printWithUnassigned(List<Area> areas, List<LocatedPlace> unassignedPlaces) throws IOException {
        logger.warn("unassigned places will be skipped, printing only areas");
        print(areas);
    }
}
