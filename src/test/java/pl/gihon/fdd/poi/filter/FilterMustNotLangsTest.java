package pl.gihon.fdd.poi.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.gihon.fdd.poi.model.Place;

public class FilterMustNotLangsTest {

	@Test
	public void filter_rejectsForbiddenInLang1() {
		FilterMustNotLangs filter = new FilterMustNotLangs("braz", "not brazilian");
		Place brazilianPlace = new Place();
		brazilianPlace.setLang1("brazilian");
		brazilianPlace.setLang2("other");
		Place americanPlace = new Place();
		americanPlace.setLang1("english");
		americanPlace.setLang2("other");

		// when
		assertThat(filter.getPredicate().test(brazilianPlace)).isFalse();
		assertThat(filter.getPredicate().test(americanPlace)).isTrue();
	}

	@Test
	public void filter_rejectsForbiddenInLang2() {
		FilterMustNotLangs filter = new FilterMustNotLangs("braz", "not brazilian");
		Place brazilianPlace = new Place();
		brazilianPlace.setLang2("Brazilian");
		brazilianPlace.setLang1("");
		Place americanPlace = new Place();
		americanPlace.setLang1("english");
		americanPlace.setLang2("other");

		// when
		assertThat(filter.getPredicate().test(brazilianPlace)).isFalse();
		assertThat(filter.getPredicate().test(americanPlace)).isTrue();
	}

	@Test
	public void filter_givenParam_rejectsAllMentioned() {
		FilterMustNotLangs filter = new FilterMustNotLangs("braz,esper", "not braz and esperanto");
		Place brazilianPlace = new Place();
		brazilianPlace.setLang2("Brazilian");
		brazilianPlace.setLang1("");
		Place esperantoPlace = new Place();
		esperantoPlace.setLang2("Maro");
		esperantoPlace.setLang1("Esperanto");
		Place americanPlace = new Place();
		americanPlace.setLang1("english");
		americanPlace.setLang2("other");

		// when
		assertThat(filter.getPredicate().test(esperantoPlace)).isFalse();
		assertThat(filter.getPredicate().test(brazilianPlace)).isFalse();
		assertThat(filter.getPredicate().test(americanPlace)).isTrue();

	}

}
