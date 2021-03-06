package pl.gihon.fdd.poi;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by luk on 2016-11-20.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class GoogleApiBootTest {

    static {
        System.setProperty("LANGS_MUST_NOT", "FAKE_LANGS_NOT");
        System.setProperty("STATUSES_MUST_NOT", "FAKE_STATUSES_NOT");
        System.setProperty("MONGO_URI", "mongodb://localhost/poi");
    }

}
