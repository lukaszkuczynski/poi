package pl.gihon.fdd.poi.validator;

import java.util.List;

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
		// for each place
		// for each validator
		// collect list of errors and return exception if found
	}

}
