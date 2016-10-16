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
	private List<ExcludedForFilter> exclusions = Lists.newArrayList();

	private static Logger LOGGER = LoggerFactory.getLogger(PredicatesFilter.class);

	@Autowired
	public PredicatesFilter(List<PredicateForFilter> predicatesForFilter) {
		super();
		this.predicatesForFilter = predicatesForFilter;

	}

	@Override
	public List<ExcludedForFilter> getExclusions() {
		return exclusions;
	}

	@Override
	public List<Place> filter(List<Place> places) {
		// TODO any wiser way of chaining predicates?
		List<Place> filtered = Lists.newArrayList(places);
		exclusions.clear();

		for (PredicateForFilter predicateForFilter : predicatesForFilter) {
			LOGGER.debug("using predicate {}", predicateForFilter.getName());
			// TODO check if it is safe to Lists.newA , if it copies
			final List<Place> beforeFilter = Lists.newArrayList(filtered);
			LOGGER.trace("before filtering {}", filtered);
			filtered = Lists.newArrayList(
					filtered.stream().filter(predicateForFilter.getPredicate()).collect(Collectors.toList()));
			LOGGER.debug("after applying filter {} elements in list", filtered.size());
			final List<Place> afterFilter = Lists.newArrayList(filtered);
			List<Place> excluded = beforeFilter.stream().filter(p -> !afterFilter.contains(p))
					.collect(Collectors.toList());

			// TODO should be in result
			if (excluded.size() > 0) {
				exclusions.add(new ExcludedForFilter(excluded, predicateForFilter));
			}
			LOGGER.trace("after filtering {}", filtered);
		}

		return filtered;
	}

}
