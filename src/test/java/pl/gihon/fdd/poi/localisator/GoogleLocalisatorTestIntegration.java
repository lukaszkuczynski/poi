package pl.gihon.fdd.poi.localisator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.localisator.google.GoogleLocalisator;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoogleLocalisatorTestIntegration {

	@Autowired
	private GoogleLocalisator localisator;

	@Test
	public void test_localise() {
		Place place = new Place(1, "Warszawa", "aleja Solidarności 102");
		List<Place> places = Lists.newArrayList(place);
		List<LocatedPlace> locatedPlaces = localisator.locate(places);
		LocatedPlace locatedPlace = locatedPlaces.get(0);
		assertThat(locatedPlace.getLatitude()).isNotEmpty();
		assertThat(locatedPlace.getLatitude()).startsWith("52.237");
		assertThat(locatedPlace.getLongitude()).startsWith("20.981");

	}

}
