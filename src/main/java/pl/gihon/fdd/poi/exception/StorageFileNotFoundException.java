package pl.gihon.fdd.poi.exception;

public class StorageFileNotFoundException extends PoiException {

	public StorageFileNotFoundException(String filename) {
		super("file " + filename + " not found on storage");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
