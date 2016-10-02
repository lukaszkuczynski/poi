package pl.gihon.fdd.poi.filter;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.model.Place;

public class LambdasFilter implements Filter {

	@Override
	public List<Place> filter(List<Place> places) {
		// TODO any wiser way of chaining predicates?
		List<Place> filtered = Lists.newArrayList(places);
		for (Predicate<Place> predicate : predicates) {
			// System.out.println("----------");
			// System.out.println(predicate);
			// System.out.println("before");
			// System.out.println(filtered);
			filtered = Lists.newArrayList(filtered.stream().filter(predicate).collect(Collectors.toList()));
			// System.out.println("after");
			// System.out.println(filtered);
		}
		return filtered;
	}

	private List<Predicate<Place>> predicates;

	public LambdasFilter(List<Predicate<Place>> predicates) {
		super();
		this.predicates = predicates;
	}

}
