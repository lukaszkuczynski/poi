package pl.gihon.fdd.exception;

import java.io.IOException;

public class StorageInternalException extends RuntimeException {

	public StorageInternalException(IOException e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5884006591354227513L;

}
