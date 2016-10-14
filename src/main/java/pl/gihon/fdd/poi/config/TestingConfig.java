package pl.gihon.fdd.poi.config;

import java.util.List;
import java.util.function.Predicate;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.filter.FilterMustNotLangs;
import pl.gihon.fdd.poi.importer.CsvImporter;
import pl.gihon.fdd.poi.importer.Importer;
import pl.gihon.fdd.poi.model.Place;
import pl.gihon.fdd.poi.validator.PredicateValidator;
import pl.gihon.fdd.poi.validator.SinglePlaceValidator;

/**
 * externalize to props: validCities
 * 
 * @author luk
 *
 */
@Configuration
public class TestingConfig {

	private List<String> validCities = Lists.newArrayList("Konin");

	@Bean
	Importer importer() {
		return new CsvImporter();
	}

	@Bean
	SinglePlaceValidator validatorCityNameInArray() {
		Predicate<Place> predicate = p -> validCities.contains(p.getCity());
		PredicateValidator predicateValidator = new PredicateValidator(predicate, "validatorCityNameInArray");
		return predicateValidator;
	}

	@Bean
	SinglePlaceValidator validatorIdPositive() {
		Predicate<Place> predicate = p -> p.getId() > 0;
		PredicateValidator predicateValidator = new PredicateValidator(predicate, "validatorIdPositive");
		return predicateValidator;
	}

	@Bean
	FilterMustNotLangs filterNonsens() {
		return new FilterMustNotLangs("zczxcz", "zczczx");
	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("10MB");
		factory.setMaxRequestSize("10MB");
		return factory.createMultipartConfig();
	}

}
