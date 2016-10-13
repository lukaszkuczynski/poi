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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import pl.gihon.fdd.poi.exception.PoiException;
import pl.gihon.fdd.poi.localisator.Localisator;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

@Component
public class GoogleLocalisator implements Localisator, Cacheable {

	private static final Object GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?";
	@Value("${google.geocoding.sleep}")
	private int sleepTime;
	@Value("${google.api.key}")
	private String googleApiKey;

	private Cache cache;
	private static Logger logger = LoggerFactory.getLogger(GoogleLocalisator.class);

	@Autowired
	public GoogleLocalisator(Cache cache) {
		super();
		this.cache = cache;
	}

	@Override
	public List<LocatedPlace> locate(List<Place> places) {
		return places.stream().map(p -> locate(p)).collect(Collectors.toList());
	}

	public String getStartOfApiKey() {
		return googleApiKey.substring(0, 5);
	}

	private LocatedPlace locate(Place place) {

		try {
			String locationText = "";
			if (!cache.isKeyInCache(place.getFullAddress())) {
				logger.debug("Querying api for {}", place.getFullAddress());
				locationText = queryApiForText(place);
				cache.put(new Element(place.getFullAddress(), locationText));
				Thread.sleep(sleepTime);
			} else {
				logger.debug("Found {} in cache", place.getFullAddress());
				Element element = cache.get(place.getFullAddress());
				locationText = (String) element.getObjectValue();
			}
			GeocodingResponse response = mapResponse(locationText);
			if (!response.getStatus().equals("OK")) {
				logger.error("status not OK, {}", response);
				throw new GoogleLocalisatorException(place, response);
			}
			if (response.getResults().size() < 1) {
				logger.error("no results found for {}", place);
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
		logger.debug("going to query api with request {}", request);
		CloseableHttpResponse response = client.execute(request);
		logger.debug("api returns response {}", response);
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

	@Override
	public boolean isInCache(String key) {
		return cache.isKeyInCache(key);
	}

	@Override
	public int cacheSize() {
		return cache.getSize();
	}

	@Override
	public void clearCache() {
		cache.removeAll();
	}

}
