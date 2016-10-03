package pl.gihon.fdd.poi.filter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.model.Place;

public class PredicateFilter implements Filter {

	@Override
	public List<Place> filter(List<Place> places) {
		return Lists.newArrayList(places.stream().filter(predicate).collect(Collectors.toList()));
	}

	private Predicate<Place> predicate;

	public PredicateFilter(Predicate<Place> predicate) {
		super();
		this.predicate = predicate;
	}

}
