package pl.gihon.fdd.poi.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;

import pl.gihon.fdd.poi.model.Place;

public class FilterMustNotStatusTest {

	@Test
	public void filter_havingForbiddenStatus_removes() {
		FilterMustNotStatus filter1 = new FilterMustNotStatus("bad,ugly", "bad&ugly statuses");
		PredicatesFilter filter = new PredicatesFilter(Lists.newArrayList(filter1));
		Place goodPlace = new Place(1L, "a", "a");
		goodPlace.setStatus("good");
		Place badPlace = new Place(2L, "b", "b");
		badPlace.setStatus("BADd");
		Place uglyPlace = new Place(3L, "c", "c");
		uglyPlace.setStatus("very ugly");
		ArrayList<Place> places = Lists.newArrayList(goodPlace, badPlace, uglyPlace);

		// when
		List<Place> filtered = filter.filter(places);

		// then
		assertThat(filtered).contains(goodPlace).doesNotContain(badPlace, uglyPlace);

	}

}
