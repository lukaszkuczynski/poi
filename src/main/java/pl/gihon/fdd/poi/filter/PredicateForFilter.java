package pl.gihon.fdd.poi.filter;

import java.util.function.Predicate;

import pl.gihon.fdd.poi.model.Place;

public class PredicateForFilter {

	private Predicate<Place> predicate;
	private String name;

	public PredicateForFilter(Predicate<Place> predicate, String name) {
		super();
		this.predicate = predicate;
		this.name = name;
	}

	public Predicate<Place> getPredicate() {
		return predicate;
	}

	public String getName() {
		return name;
	}

}
