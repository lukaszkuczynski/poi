package pl.gihon.fdd.poi.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.function.Predicate;

import org.assertj.core.util.Lists;
import org.junit.Test;

import pl.gihon.fdd.poi.PlaceHelper;
import pl.gihon.fdd.poi.model.Place;

public class PredicateValidatorTest {

	@Test
	public void predicateValidator_givenPlaceOK_returnsValidationOK() {
		// given
		List<String> validCities = Lists.newArrayList("Konin", "Mrągowo");
		Predicate<Place> cityIsInArray = p -> validCities.contains(p.getCity());
		PredicateValidator predicateValidator = new PredicateValidator(cityIsInArray, "city name is in array");
		Place place = PlaceHelper.simple(1L, "St1", "Mrągowo");

		// when
		ValidationResult validationResult = predicateValidator.validate(place);

		// then
		assertTrue(validationResult.isValid());
		assertThat(validationResult.getErrorText()).isEmpty();
	}

	@Test
	public void predicateValidator_givenInvalidPlace_returnsValidationError() {
		// given
		List<String> validCities = Lists.newArrayList("Konin", "Mrągowo");
		Predicate<Place> cityIsInArray = p -> validCities.contains(p.getCity());
		PredicateValidator predicateValidator = new PredicateValidator(cityIsInArray, "city name is in array");
		Place place = PlaceHelper.simple(1L, "St1", "Brzeźno");

		// when
		ValidationResult validationResult = predicateValidator.validate(place);

		// then
		assertFalse(validationResult.isValid());
		assertThat(validationResult.getErrorText()).isNotEmpty();
		assertThat(validationResult.getErrorText()).containsIgnoringCase("city name is in array");
	}

}
