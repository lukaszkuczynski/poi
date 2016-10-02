package pl.gihon.fdd.poi.validator;

import pl.gihon.fdd.poi.exception.PoiException;

public class ValidationException extends PoiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5924623376435502760L;

	public ValidationException(String string) {
		super(string);
	}

}
