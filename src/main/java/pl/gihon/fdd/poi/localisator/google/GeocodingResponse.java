package pl.gihon.fdd.poi.localisator.google;

import java.util.List;

public class GeocodingResponse {

	private List<Result> results;
	private String status;
	private String error_message;

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	@Override
	public String toString() {
		return "GeocodingResponse [status=" + status + ", error_message=" + error_message + "]";
	}

}
