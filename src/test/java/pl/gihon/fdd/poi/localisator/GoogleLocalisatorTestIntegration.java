package pl.gihon.fdd.poi.localisator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.GoogleApiContextTest;
import pl.gihon.fdd.poi.PlaceHelper;
import pl.gihon.fdd.poi.localisator.google.GoogleLocalisator;
import pl.gihon.fdd.poi.localisator.google.LocationCache;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

/**
 * has to have valid GOOGLE_API_KEY as env var during test
 * 
 * @author luk
 *
 */
public class GoogleLocalisatorTestIntegration extends GoogleApiContextTest {

	@Autowired
	private GoogleLocalisator localisator;
	@Autowired
	private LocationCache locationCache;

	private static List<Place> placesWithCityAndStreet(String city, String street) {
		Place place = PlaceHelper.simple(1, street, city);
		List<Place> places = Lists.newArrayList(place);
		return places;
	}

	@Test
	public void test_localise() {
		List<Place> places = placesWithCityAndStreet("Warszawa", "aleja Solidarności 102");
		List<LocatedPlace> locatedPlaces = localisator.locate(places);
		LocatedPlace locatedPlace = locatedPlaces.get(0);
		assertThat(locatedPlace.getLatitude()).isNotEmpty();
		assertThat(locatedPlace.getLatitude()).startsWith("52.237");
		assertThat(locatedPlace.getLongitude()).startsWith("20.981");

	}

	@Test
	public void localisator_fillsCacheWhenLocating() {
		localisator.clearCache();
		assertThat(localisator.cacheSize()).isZero();

		List<Place> places = placesWithCityAndStreet("Warszawa", "aleja Solidarności 102");
		localisator.locate(places);

		assertThat(localisator.cacheSize()).isEqualTo(1);
	}

	@Test
	public void localisator_usesCachedLocation() {
		List<Place> places = placesWithCityAndStreet("Warszawa", "aleja Solidarności 102");
		String resp = "{   \"results\" : [      {         \"address_components\" : [            {               \"long_name\" : \"102\",               \"short_name\" : \"102\",               \"types\" : [ \"street_number\" ]            },            {               \"long_name\" : \"aleja Solidarności\",               \"short_name\" : \"aleja Solidarności\",               \"types\" : [ \"route\" ]            },            {               \"long_name\" : \"Wola\",               \"short_name\" : \"Wola\",               \"types\" : [ \"political\", \"sublocality\", \"sublocality_level_1\" ]            },            {               \"long_name\" : \"Warszawa\",               \"short_name\" : \"Warszawa\",               \"types\" : [ \"locality\", \"political\" ]            },            {               \"long_name\" : \"Warszawa\",               \"short_name\" : \"Warszawa\",               \"types\" : [ \"administrative_area_level_2\", \"political\" ]            },            {               \"long_name\" : \"mazowieckie\",               \"short_name\" : \"mazowieckie\",               \"types\" : [ \"administrative_area_level_1\", \"political\" ]            },            {               \"long_name\" : \"Poland\",               \"short_name\" : \"PL\",               \"types\" : [ \"country\", \"political\" ]            }         ],         \"formatted_address\" : \"aleja Solidarności 102, Warszawa, Poland\",         \"geometry\" : {            \"location\" : {               \"lat\" : 22,               \"lng\" : 11            },            \"location_type\" : \"ROOFTOP\",            \"viewport\" : {               \"northeast\" : {                  \"lat\" : 52.2392839802915,                  \"lng\" : 20.9825288802915               },               \"southwest\" : {                  \"lat\" : 22,                  \"lng\" : 11               }            }         },         \"place_id\" : \"ChIJK__4jIHMHkcRKd5eQaVMNyw\",         \"types\" : [ \"street_address\" ]      }   ],   \"status\" : \"OK\"}";
		locationCache.put("Warszawa,aleja Solidarności 102", resp);

		// when
		List<LocatedPlace> locatedPlaces = localisator.locate(places);

		// then
		assertThat(locatedPlaces.get(0).getLatitude()).isEqualTo("22");
		assertThat(locatedPlaces.get(0).getLongitude()).isEqualTo("11");
	}

}
