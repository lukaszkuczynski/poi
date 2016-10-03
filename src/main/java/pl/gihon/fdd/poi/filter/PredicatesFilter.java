package pl.gihon.fdd.poi.filter;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.model.Place;

@Component
public class PredicatesFilter implements Filter {

	private List<PredicateForFilter> predicatesForFilter;

	private static Logger LOGGER = LoggerFactory.getLogger(PredicateFilter.class);

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
			LOGGER.debug("using predicate {}", predicateForFilter.getName());
			LOGGER.trace("before filtering {}", filtered);
			filtered = Lists.newArrayList(
					filtered.stream().filter(predicateForFilter.getPredicate()).collect(Collectors.toList()));
			LOGGER.debug("after applying filter {} elements in list", filtered.size());
			LOGGER.trace("after filtering {}", filtered);
		}
		return filtered;
	}

}
