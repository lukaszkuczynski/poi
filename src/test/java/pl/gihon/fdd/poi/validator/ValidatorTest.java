package pl.gihon.fdd.poi.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;

import pl.gihon.fdd.poi.model.Place;

public class ValidatorTest {

	@Test
	public void validator_givenValidPlaces_throwsNothing() {
		List<SinglePlaceValidator> validators = Lists.newArrayList(new KoninSingleValidator());
		Validator validator = new StandardValidator(validators);
		List<Place> places = Lists.newArrayList(new Place(1L, "street 1", "Konin"), new Place(2L, "street 2", "Konin"));
		// TODO: google catch excpetion gives error
		/// java.lang.NoClassDefFoundError:
		// org/mockito/internal/creation/MockitoMethodProxy
		// CatchException.catchException(validator).validate(places);
		// Exception caughtException = CatchException.caughtException();
		// assertThat(caughtException).isNull();
		try {
			validator.validate(places);
			assertTrue(true);
		} catch (Exception e) {
			assertTrue("should not throw error", false);
		}

	}

	@Test
	public void validator_givenInvalidPlace_throwsException() {
		List<SinglePlaceValidator> validators = Lists.newArrayList(new KoninSingleValidator());
		Validator validator = new StandardValidator(validators);
		Place validPlace = new Place(1L, "street 1", "Konin");
		Place INvalidPlace = new Place(1L, "street 1", "Konina");

		List<Place> places = Lists.newArrayList(validPlace, INvalidPlace);

		try {
			validator.validate(places);
			assertTrue("given invalid place should throw error", false);
		} catch (Exception e) {
			assertTrue(true);
			assertThat(e).isInstanceOf(ValidationException.class);
			assertThat(e).hasMessageContaining("Konin");
		}

	}

}
