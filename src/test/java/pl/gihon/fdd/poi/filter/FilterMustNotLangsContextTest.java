package pl.gihon.fdd.poi.filter;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import pl.gihon.fdd.poi.ContextTest;

@Configuration
@Import(value = FilterConfig.class)
public class FilterMustNotLangsContextTest extends ContextTest {

	@Autowired
	PredicatesFilter filter;

	@Test
	public void ioc_hasFilterDefined() {

	}

}
