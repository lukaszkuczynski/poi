package pl.gihon.fdd.poi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PoiApplicationTests {

	static {
		System.setProperty("GOOGLE_API_KEY", "FAKE KEY");
	}

	@Test
	public void contextLoads() {
	}

}
