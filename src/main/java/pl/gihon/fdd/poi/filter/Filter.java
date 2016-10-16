package pl.gihon.fdd.poi.filter;

import java.util.List;

import pl.gihon.fdd.poi.model.Place;

public interface Filter {

	public List<Place> filter(List<Place> places);

	List<ExcludedForFilter> getExclusions();
}
