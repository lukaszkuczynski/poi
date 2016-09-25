package pl.gihon.fdd.poi.exception;

public class StorageFileNotFoundException extends RuntimeException {

	public StorageFileNotFoundException(String filename) {
		throw new RuntimeException("file " + filename + " not found on storage");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
