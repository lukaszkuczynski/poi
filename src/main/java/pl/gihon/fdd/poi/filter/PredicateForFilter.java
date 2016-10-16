package pl.gihon.fdd.poi.filter;

import java.util.function.Predicate;

import pl.gihon.fdd.poi.model.Place;

public class PredicateForFilter {

	protected Predicate<Place> predicate;
	private String name;

	public PredicateForFilter(Predicate<Place> predicate, String name) {
		super();
		this.predicate = predicate;
		this.name = name;
	}

	public PredicateForFilter(String name) {
		super();
		this.name = name;
	}

	public Predicate<Place> getPredicate() {
		return predicate;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "PredicateForFilter [name=" + name + "]";
	}

}
