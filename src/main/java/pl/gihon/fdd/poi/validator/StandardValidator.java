package pl.gihon.fdd.poi.validator;

import java.util.List;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import pl.gihon.fdd.poi.model.Place;

public class StandardValidator implements Validator {

	List<SinglePlaceValidator> validators;

	@Autowired
	public StandardValidator(List<SinglePlaceValidator> validators) {
		super();
		this.validators = validators;
	}

	@Override
	public void validate(List<Place> places) {
		List<String> errorMessages = Lists.newArrayList();
		for (Place place : places) {
			for (SinglePlaceValidator validator : validators) {
				ValidationResult validationResult = validator.validate(place);
				if (!validationResult.isValid()) {
					errorMessages.add(validationResult.getErrorText());
				}
			}
		}
		if (!errorMessages.isEmpty()) {
			throw new ValidationException("invalid, first is " + errorMessages.get(0));
		}

	}

}
