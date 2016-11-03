package pl.gihon.fdd.poi.localisator;

import org.springframework.context.annotation.Bean;

/**
 * Created by luk on 2016-10-31.
 */
public class MongoConfig {

    @Bean
    CacheMongo cacheMongo() {
        return new CacheMongo();
    }

}
