package pl.gihon.fdd.poi.localisator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;

import com.mongodb.DBCollection;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import pl.gihon.fdd.poi.exception.PoiException;
import pl.gihon.fdd.poi.localisator.google.ImportToCacheException;
import pl.gihon.fdd.poi.localisator.google.LocationCache;
import pl.gihon.fdd.poi.web.KeyVal;

/**
 * Created by luk on 2016-10-31.
 */
@Component
public class CacheMongo implements LocationCache {

	private static final char CSV_SEPARATOR = ';';
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
		Query matchAll = new Query();
//		getCollection().remove(matchAll);
		throw new RuntimeException("NYI");
	}

	@Override
	public ByteArrayOutputStream export() {
		throw new RuntimeException("NYI");
	}

	// FIXME violation of SRP
	@Override
	public void importToCache(InputStream stream) {
		try (CSVReader reader = new CSVReader(new InputStreamReader(stream, "UTF-8"), CSV_SEPARATOR)) {
			List<String[]> lines = reader.readAll();
			for (String[] columns : lines) {
				if (columns.length != 2) {
					throw new ImportToCacheException("column length should be 2, is " + columns.length);
				}
				String key = columns[0];
				String value = columns[1];
				this.put(key, value);
			}
		} catch (IOException e) {
			throw new PoiException(e);
		}
	}

	@Override
	public boolean containsKey(String key) {
		return getValue(key).isPresent();
	}

	@Override
	public int getSize() {
		return (int) getCollection().count();
	}

	private DBCollection getCollection() {
		return mongoTemplate.getCollection(mongoTemplate.getCollectionName(CacheElement.class));
	}

}
