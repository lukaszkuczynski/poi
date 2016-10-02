package pl.gihon.fdd.poi.validator;

public class ValidationResult {

	private boolean valid;
	private String errorText;

	public boolean isValid() {
		return valid;
	}

	public String getErrorText() {
		return errorText;
	}

	private ValidationResult(boolean valid, String errorText) {
		super();
		this.valid = valid;
		this.errorText = errorText;
	}

	public static ValidationResult valid() {
		return new ValidationResult(true, "");
	}

	public static ValidationResult invalid(String errorText) {
		return new ValidationResult(false, errorText);
	}

}
