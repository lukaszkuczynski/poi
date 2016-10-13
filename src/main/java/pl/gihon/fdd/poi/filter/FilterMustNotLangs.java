package pl.gihon.fdd.poi.filter;

public class FilterMustNotLangs extends PredicateForFilter {

	public FilterMustNotLangs(String langsNot, String name) {
		super(p -> !p.getLang1().toLowerCase().contains(langsNot.toLowerCase())
				&& !p.getLang2().toLowerCase().contains(langsNot.toLowerCase()), name);
	}

}
