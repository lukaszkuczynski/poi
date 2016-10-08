package pl.gihon.fdd.poi.printer.oldpythonprinter;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import pl.gihon.fdd.poi.printer.AreasHelper;

public class PyJsonPrinterTestIntegration {

	@Test
	public void test_print() throws IOException {

		String areasFilePath = "d:\\temp\\areas.json";
		String placesFilePath = "d:\\temp\\places.json";
		PyJsonPrinter printer = new PyJsonPrinter(new File(areasFilePath), new File(placesFilePath));

		printer.print(AreasHelper.areas());

	}

}
