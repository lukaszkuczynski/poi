package pl.gihon.fdd.poi.printer.template;

/**
 * Created by luk on 2016-11-07.
 */
public class HtmlTemplatePrinterOptions {

    private String outputFolder;

    private String templatePath;

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public String getTemplatePath() {
        return templatePath;
    }
}
