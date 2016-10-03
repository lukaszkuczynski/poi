package pl.gihon.fdd.poi.validator;

import java.util.List;

import pl.gihon.fdd.poi.exception.PoiException;

public class ValidationException extends PoiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5924623376435502760L;

	private List<String> errorMessages;

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public ValidationException(String msg, List<String> errorMessages) {
		super(msg);
		this.errorMessages = errorMessages;
	}

}
