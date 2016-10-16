package pl.gihon.fdd.poi.filter;

import java.util.List;

import com.google.common.collect.Lists;

public class FilterMustNotLangs extends PredicateForFilter {

	private List<String> langs;

	private String langsNot;

	public FilterMustNotLangs(String langsNot, String name) {
		super(name);
		this.langsNot = langsNot;
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

	@Override
	public String toString() {
		return "FilterMustNotLangs [langsNot=" + langsNot + ", getName()=" + getName() + "]";
	}

}
