package pl.gihon.fdd.poi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.gihon.fdd.poi.importer.CsvImporter;
import pl.gihon.fdd.poi.importer.Importer;

@Configuration
public class TestingConfig {

	@Bean
	Importer importer() {
		return new CsvImporter();
	}

}
