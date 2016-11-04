package pl.gihon.fdd.poi.localisator;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pl.gihon.fdd.poi.MongoBootTest;

/**
 * Created by luk on 2016-10-31.
 */

public class CacheMongoTestBoot extends MongoBootTest {

	@Autowired
	private CacheMongo cacheMongo;

	@Test
	public void testGetValue() throws Exception {
		Optional<String> val = cacheMongo.getValue("aa");
		assertThat(val.isPresent()).isTrue();
	}

    @Test
    public void testPut() throws Exception {
        String key = RandomStringUtils.randomAlphabetic(5);
        String value = RandomStringUtils.randomAlphabetic(5);

        cacheMongo.put(key, value);

        Optional<String> valSupposedToBePut = cacheMongo.getValue(key);
        assertThat(valSupposedToBePut.isPresent()).isTrue();
    }


    @Test
    public void testSize() throws Exception {
        int size = cacheMongo.getSize();

        assertThat(size).isNotZero();
    }

}