package pl.gihon.fdd.poi.localisator.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import pl.gihon.fdd.poi.exception.PoiException;
import pl.gihon.fdd.poi.localisator.Localisator;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

@Component
public class GoogleLocalisator implements Localisator {

	private static final Object GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
	@Value("${google.geocoding.sleep}")
	private int sleepTime;
	@Value("${google.api.key}")
	private String googleApiKey;

	@Override
	public List<LocatedPlace> locate(List<Place> places) {
		return places.stream().map(p -> locate(p)).collect(Collectors.toList());
	}

	public String getStartOfApiKey() {
		return googleApiKey.substring(0, 5);
	}

	private LocatedPlace locate(Place place) {

		try {

			String responseText = queryApiForText(place);
			Thread.sleep(sleepTime);
			GeocodingResponse response = mapResponse(responseText);
			if (!response.getStatus().equals("OK")) {
				throw new GoogleLocalisatorException(place, response);
			}
			if (response.getResults().size() < 1) {
				throw new GoogleLocalisatorException(place, "no results found");
			}

			Location location = response.getResults().get(0).getGeometry().getLocation();
			return new LocatedPlace(location.getLat(), location.getLng(), place);

		} catch (IOException | InterruptedException e) {
			throw new PoiException(e);
		}
	}

	private GeocodingResponse mapResponse(String responseText) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectReader reader = mapper.reader().forType(GeocodingResponse.class);
		Object readValue = reader.readValue(responseText);
		return (GeocodingResponse) readValue;
	}

	private String queryApiForText(Place place)
			throws IOException, UnsupportedEncodingException, ClientProtocolException {
		String addressValues = URLEncoder.encode(String.format("%s, %s", place.getCity(), place.getStreetAndFlat()),
				"UTF-8");

		StringBuilder sb = new StringBuilder();
		sb.append(GEOCODING_URL).append("address=").append(addressValues).append("&key=").append(googleApiKey);

		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(sb.toString());
		CloseableHttpResponse response = client.execute(request);

		BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		String inputLine;
		StringBuffer responseTextBuffer = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			responseTextBuffer.append(inputLine);
		}
		in.close();
		String responseText = responseTextBuffer.toString();
		return responseText;
	}

}
