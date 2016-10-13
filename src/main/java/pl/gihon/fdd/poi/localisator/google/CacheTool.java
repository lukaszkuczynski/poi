package pl.gihon.fdd.poi.localisator.google;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import pl.gihon.fdd.poi.exception.PoiException;
import pl.gihon.fdd.poi.web.KeyVal;

@Component
public class CacheTool {

	private static final char CSV_SEPARATOR = ';';
	private static Logger logger = LoggerFactory.getLogger(CacheTool.class);
	private Cache cache;

	@Autowired
	public CacheTool(Cache cache) {
		super();
		this.cache = cache;
	}

	public int getCacheSize() {
		return cache.getSize();
	}

	public ByteArrayOutputStream export() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(baos), CSV_SEPARATOR)) {
			for (Object key : cache.getKeys()) {
				String keyText = (String) key;
				Element element = cache.get(keyText);
				Object value = element.getObjectValue();
				String valueText = (String) value;
				String[] strings = new String[] { keyText, valueText };
				writer.writeNext(strings);
			}
		} catch (IOException e) {
			throw new PoiException(e);
		}
		return baos;
	}

	public void importToCache(InputStream stream) {
		try (CSVReader reader = new CSVReader(new InputStreamReader(stream), CSV_SEPARATOR)) {
			List<String[]> lines = reader.readAll();
			for (String[] columns : lines) {
				if (columns.length != 2) {
					throw new ImportToCacheException("column length should be 2, is " + columns.length);
				}
				String key = columns[0];
				String value = columns[1];
				cache.put(new Element(key, value));
			}
		} catch (IOException e) {
			throw new PoiException(e);
		}

	}

	public Optional<String> getByKey(String key) {
		if (!cache.isKeyInCache(key)) {
			return Optional.empty();
		}
		Element element = cache.get(key);
		String objectValueAsStr = (String) element.getObjectValue();
		return Optional.of(objectValueAsStr);
	}

	public List<KeyVal> getAllRows() {
		List<KeyVal> keyvals = new ArrayList<>();

		for (Object key : cache.getKeys()) {
			Element element = cache.get(key);
			if (element == null) {
				logger.warn("for key {} null in cache", key);
				continue;
			}
			String keyText = (String) key;
			Object value = element.getObjectValue();
			String valueText = (String) value;
			keyvals.add(new KeyVal(keyText, valueText));
		}
		return keyvals;
	}

}
