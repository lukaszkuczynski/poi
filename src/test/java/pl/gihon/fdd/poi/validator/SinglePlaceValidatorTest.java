package pl.gihon.fdd.poi.validator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.gihon.fdd.poi.model.Place;

public class SinglePlaceValidatorTest {

	private SinglePlaceValidator validator = new CityOnlyKoninValidator();

	@Test
	public void singleValidator_givenValidPlace_returnInfoValid() {
		// given
		Place place = new Place(1L, "street", "Konin");

		// when
		ValidationResult validationResult = validator.validate(place);

		// then
		assertThat(validationResult.isValid()).isTrue();
		assertThat(validationResult.getErrorText()).isEmpty();
	}

	@Test
	public void singleValidator_givenInvalidPlace_returnInfoInvalidWithTextError() {
		// given
		Place place = new Place(1L, "street", "Konina");

		// when
		ValidationResult validationResult = validator.validate(place);

		// then
		assertThat(validationResult.isValid()).isFalse();
		assertThat(validationResult.getErrorText()).contains("Konin");
	}

}
