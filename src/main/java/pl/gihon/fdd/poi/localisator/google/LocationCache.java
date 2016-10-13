package pl.gihon.fdd.poi.localisator.google;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import pl.gihon.fdd.poi.web.KeyVal;

public interface LocationCache {

	void put(String key, String value);

	Optional<String> getValue(String key);

	void clear();

	public ByteArrayOutputStream export();

	public void importToCache(InputStream stream);

	public boolean containsKey(String key);

	public int getSize();

	public List<KeyVal> getAllRows();

}
