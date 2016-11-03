package pl.gihon.fdd.poi;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("cachemongo")
public abstract class MongoBootTest extends ContextTest {

	static {
		System.setProperty("MONGO_URI", "mongodb://localhost/poi");
	}

}
