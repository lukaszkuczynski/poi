package pl.gihon.fdd.poi.filter;

import java.util.List;

import com.google.common.collect.Lists;

public class FilterMustNotStatus extends PredicateForFilter {

	private List<String> statuses;

	public FilterMustNotStatus(String statusesNot, String name) {
		super(name);
		this.statuses = Lists.newArrayList(statusesNot.split(","));
		this.predicate = p -> {
			boolean matches = true;
			for (String statusNot : statuses) {
				matches &= !p.getStatus().toLowerCase().contains(statusNot.toLowerCase());
			}
			return matches;
		};
	}

}
