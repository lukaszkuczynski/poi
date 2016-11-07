package pl.gihon.fdd.poi.localisator.google;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import pl.gihon.fdd.poi.exception.PoiException;
import pl.gihon.fdd.poi.web.KeyVal;

@Component
@Profile(value = "cachemap")
public class LocationCacheMap implements LocationCache {

	private static final char CSV_SEPARATOR = ';';
	private Map<String, String> map = new HashMap<>();

	@Override
	public void put(String key, String value) {
		map.put(key, value);
	}

	@Override
	public Optional<String> getValue(String key) {
		if (!map.containsKey(key)) {
			return Optional.empty();
		}
		return Optional.of(map.get(key));
	}

	@Override
	public void clear() {
		this.map.clear();

	}

	@Override
	public ByteArrayOutputStream export() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(baos), CSV_SEPARATOR)) {
			for (String key : map.keySet()) {
				String valueText = this.getValue(key).get();
				String[] strings = new String[] { key, valueText };
				writer.writeNext(strings);
			}
		} catch (IOException e) {
			throw new PoiException(e);
		}
		return baos;
	}

	@Override
	public void importToCache(InputStream stream) {
		try (CSVReader reader = new CSVReader(new InputStreamReader(stream), CSV_SEPARATOR)) {
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
		return map.containsKey(key);
	}

	@Override
	public int getSize() {
		return map.size();
	}

}
