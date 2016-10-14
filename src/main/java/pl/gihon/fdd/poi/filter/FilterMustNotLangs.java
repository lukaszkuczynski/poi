package pl.gihon.fdd.poi.filter;

import java.util.List;

import com.google.common.collect.Lists;

public class FilterMustNotLangs extends PredicateForFilter {

	private List<String> langs;

	public FilterMustNotLangs(String langsNot, String name) {
		super(name);
		this.langs = Lists.newArrayList(langsNot.split(","));
		this.predicate = p -> {
			boolean matches = true;
			for (String langNot : langs) {
				matches &= !p.getLang1().toLowerCase().contains(langNot.toLowerCase())
						&& !p.getLang2().toLowerCase().contains(langNot.toLowerCase());
			}
			return matches;
		};
	}

}
