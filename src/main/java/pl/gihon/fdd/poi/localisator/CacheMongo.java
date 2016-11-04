package pl.gihon.fdd.poi.localisator;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import pl.gihon.fdd.poi.localisator.google.LocationCache;
import pl.gihon.fdd.poi.web.KeyVal;

/**
 * Created by luk on 2016-10-31.
 */
@Component
@Profile(value = "cachemongo")
public class CacheMongo implements LocationCache {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void put(String key, String value) {
        mongoTemplate.save(new CacheElement(key, value));
	}

	@Override
	public Optional<String> getValue(String key) {
		Query searchUserQuery = new Query(Criteria.where("key").is(key));
		CacheElement element = mongoTemplate.findOne(searchUserQuery, CacheElement.class);
        if (element != null) {
            return Optional.ofNullable(element.getValue());
        } else {
            return Optional.empty();
        }
	}

	@Override
	public void clear() {

	}

	@Override
	public ByteArrayOutputStream export() {
		return null;
	}

	@Override
	public void importToCache(InputStream stream) {

	}

	@Override
	public boolean containsKey(String key) {
		return false;
	}

	@Override
	public int getSize() {
		return (int) mongoTemplate.getCollection(mongoTemplate.getCollectionName(CacheElement.class)).count();
	}

	@Override
	public List<KeyVal> getAllRows() {
		return null;
	}
}
