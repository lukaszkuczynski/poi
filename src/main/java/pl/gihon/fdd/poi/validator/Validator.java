package pl.gihon.fdd.poi.validator;

import java.util.List;

import pl.gihon.fdd.poi.model.Place;

public interface Validator {
	void validate(List<Place> places);
}
