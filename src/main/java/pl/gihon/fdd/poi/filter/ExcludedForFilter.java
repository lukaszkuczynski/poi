package pl.gihon.fdd.poi.filter;

import java.util.List;

import pl.gihon.fdd.poi.model.Place;

/**
 * TODO: introduce greater FilterResult als containing Filtered that passed
 * 
 * @author luk
 *
 */
public class ExcludedForFilter {
	private List<Place> places;
	private PredicateForFilter filter;

	public ExcludedForFilter(List<Place> places, PredicateForFilter filter) {
		super();
		this.places = places;
		this.filter = filter;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public PredicateForFilter getFilter() {
		return filter;
	}

	@Override
	public String toString() {
		return "ExcludedForFilter [filter=" + filter + ", places count = " + places.size() + "]";
	}

}
