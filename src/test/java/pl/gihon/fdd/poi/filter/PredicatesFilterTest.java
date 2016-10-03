package pl.gihon.fdd.poi.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;

import pl.gihon.fdd.poi.model.Place;

public class PredicatesFilterTest {

	@Test
	public void filter_givenPredicates_doesHisJob() {

		PredicateForFilter idPositive = new PredicateForFilter(p -> p.getId() > 0, "idPositive");
		PredicateForFilter streetSt1 = new PredicateForFilter(p -> p.getStreetAndFlat().equals("st1"), "streetSt1");
		PredicateForFilter cityIsKonin = new PredicateForFilter(p -> p.getCity().equals("Konin"), "cityIsKonin");
		List<PredicateForFilter> predicates = Lists.newArrayList(idPositive, streetSt1, cityIsKonin);
		Filter filter = new PredicatesFilter(predicates);

		List<Place> places = Lists.newArrayList(new Place(-1, "st1", "Konin"), new Place(1, "st1", "Konin"),
				new Place(2, "st1", "Mierzejewo"));

		List<Place> filtered = filter.filter(places);

		assertThat(filtered).hasSize(1);
	}
}
