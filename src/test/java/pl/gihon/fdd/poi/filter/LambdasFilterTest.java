package pl.gihon.fdd.poi.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Predicate;

import org.assertj.core.util.Lists;
import org.junit.Test;

import pl.gihon.fdd.poi.model.Place;

public class LambdasFilterTest {

	@Test
	public void filter_givenPredicates_doesHisJob() {

		Predicate<Place> minusId = p -> p.getId() > 0;
		Predicate<Place> streetSt1 = p -> p.getStreetAndFlat().equals("st1");
		Predicate<Place> cityIsKonin = p -> p.getCity().equals("Konin");
		List<Predicate<Place>> predicates = Lists.newArrayList(minusId, streetSt1, cityIsKonin);
		Filter filter = new LambdasFilter(predicates);

		List<Place> places = Lists.newArrayList(
				new Place(-1, "st1", "Konin"), 
				new Place(1, "st1", "Konin"),
				new Place(2, "st1", "Mierzejewo")
		);

		List<Place> filtered = filter.filter(places);

		assertThat(filtered).hasSize(1);
	}
}
