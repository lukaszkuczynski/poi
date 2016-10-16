package pl.gihon.fdd.poi.filter;

import java.util.List;

import com.google.common.collect.Lists;

public class FilterMustNotStatus extends PredicateForFilter {

	private List<String> statuses;

	private String statusesNot;

	public FilterMustNotStatus(String statusesNot, String name) {
		super(name);
		this.statusesNot = statusesNot;
		this.statuses = Lists.newArrayList(statusesNot.split(","));
		this.predicate = p -> {
			boolean matches = true;
			for (String statusNot : statuses) {
				matches &= !p.getStatus().toLowerCase().contains(statusNot.toLowerCase());
			}
			return matches;
		};
	}

	@Override
	public String toString() {
		return "FilterMustNotStatus [statusesNot=" + statusesNot + ", getName()=" + getName() + "]";
	}

}
