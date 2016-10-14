package pl.gihon.fdd.poi.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.ContextTest;
import pl.gihon.fdd.poi.config.Config;
import pl.gihon.fdd.poi.model.Place;

@Configuration
@Import(value = Config.class)
public class FilterMustNotLangsContextTest extends ContextTest {

	@Autowired
	PredicatesFilter filter;

	@BeforeClass
	public static void setupclass() {
		System.setProperty("LANGS_MUST_NOT", "urdi,suahili");
	}

	@Test
	public void ioc_hasFilterDefined() {
		Place urdiPlace = new Place(1L, "U", "U");
		urdiPlace.setLang2("urdi");
		urdiPlace.setLang1("");
		Place americanPlace = new Place(2L, "A", "A");
		americanPlace.setLang1("english");
		americanPlace.setLang2("other");
		List<Place> places = Lists.newArrayList(urdiPlace, americanPlace);

		//
		List<Place> placesFiltered = filter.filter(places);

		// then
		assertThat(placesFiltered).contains(americanPlace).doesNotContain(urdiPlace);

	}

}
