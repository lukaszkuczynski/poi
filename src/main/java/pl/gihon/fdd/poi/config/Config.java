package pl.gihon.fdd.poi.config;

import java.util.function.Predicate;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.gihon.fdd.poi.filter.FilterMustNotLangs;
import pl.gihon.fdd.poi.filter.FilterMustNotStatus;
import pl.gihon.fdd.poi.importer.CsvImporter;
import pl.gihon.fdd.poi.importer.PlacesImporter;
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
public class Config {

	@Value("${LANGS_MUST_NOT}")
	private String langsMustNot;
	@Value("${STATUSES_MUST_NOT}")
	private String statusesMustNot;

	@Bean
	PlacesImporter importer() {
		return new CsvImporter();
	}

	@Bean
	SinglePlaceValidator validatorIdPositive() {
		Predicate<Place> predicate = p -> p.getId() > 0;
		PredicateValidator predicateValidator = new PredicateValidator(predicate, "validatorIdPositive");
		return predicateValidator;
	}

	@Bean
	FilterMustNotLangs filterMustNotLangs() {
		return new FilterMustNotLangs(langsMustNot, "langs_not");
	}

	@Bean
	FilterMustNotStatus filterMustNotStatuses() {
		return new FilterMustNotStatus(statusesMustNot, "statuses_not");
	}

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize("10MB");
		factory.setMaxRequestSize("10MB");
		return factory.createMultipartConfig();
	}

}
