package pl.gihon.fdd.poi.validator;

import java.util.function.Predicate;

import pl.gihon.fdd.poi.model.Place;

public class PredicateValidator implements SinglePlaceValidator {

	private Predicate<Place> predicate;
	private String name;

	public PredicateValidator(Predicate<Place> predicate, String name) {
		super();
		this.predicate = predicate;
		this.name = name;
	}

	@Override
	public ValidationResult validate(Place place) {
		if (predicate.test(place)) {
			return ValidationResult.valid();
		} else {
			return ValidationResult.invalid(String.format("predicate '%s' not satisfied", this.name));
		}
	}

}
