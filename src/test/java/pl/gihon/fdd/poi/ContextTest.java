package pl.gihon.fdd.poi;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class ContextTest {

	static {
		System.setProperty("GOOGLE_API_KEY", "FAKE_KEY");
	}

}
