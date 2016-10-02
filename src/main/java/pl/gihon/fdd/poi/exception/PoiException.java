package pl.gihon.fdd.poi.exception;

public class PoiException extends RuntimeException {

	public PoiException(Exception e) {
		super(e);
	}

	public PoiException(String string) {
		super(string);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2753998296651848491L;

}
