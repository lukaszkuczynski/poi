package pl.gihon.fdd.poi.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.model.Place;

/**
 * standard validator expects List of Single Validators
 * 
 * @author luk
 *
 */
@Component
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
			throw new ValidationException(String.format("invalid, first exceptions is '%s', total number %d",
					errorMessages.get(0), errorMessages.size()), errorMessages);
		}

	}

}
