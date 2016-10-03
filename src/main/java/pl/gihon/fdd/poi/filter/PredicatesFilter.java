package pl.gihon.fdd.poi.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.model.Place;

//@Component
public class PredicatesFilter implements Filter {

	private List<PredicateForFilter> predicatesForFilter;

	@Autowired
	public PredicatesFilter(List<PredicateForFilter> predicatesForFilter) {
		super();
		this.predicatesForFilter = predicatesForFilter;
	}

	@Override
	public List<Place> filter(List<Place> places) {
		// TODO any wiser way of chaining predicates?
		List<Place> filtered = Lists.newArrayList(places);
		for (PredicateForFilter predicateForFilter : predicatesForFilter) {
			// System.out.println("----------");
			// System.out.println(predicate);
			// System.out.println("before");
			// System.out.println(filtered);
			filtered = Lists.newArrayList(
					filtered.stream().filter(predicateForFilter.getPredicate()).collect(Collectors.toList()));
			// System.out.println("after");
			// System.out.println(filtered);
		}
		return filtered;
	}

}
