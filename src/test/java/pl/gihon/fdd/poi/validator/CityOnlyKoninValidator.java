package pl.gihon.fdd.poi.validator;

import pl.gihon.fdd.poi.model.Place;

public class CityOnlyKoninValidator implements SinglePlaceValidator {

	@Override
	public ValidationResult validate(Place place) {
		if (place.getCity() == "Konin") {
			return ValidationResult.valid();
		} else {
			return ValidationResult.invalid("has to be Konin city!");
		}
	}

}
