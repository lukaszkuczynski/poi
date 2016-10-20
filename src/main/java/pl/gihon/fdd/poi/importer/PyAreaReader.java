package pl.gihon.fdd.poi.importer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import pl.gihon.fdd.poi.printer.oldpythonprinter.PyArea;

@Component
public class PyAreaReader {
	public List<PyArea> readAll(File file) throws JsonProcessingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();

		// CsvSchema schema = mapper.schemaFor(Place.class)
		// Schema schema = ob

		ObjectReader reader = objectMapper.reader().forType(PyArea.class);
		MappingIterator<PyArea> values = reader.readValues(file);
		return values.readAll();
	}
}
