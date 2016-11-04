package pl.gihon.fdd.poi.localisator;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by luk on 2016-10-31.
 */

@Document(collection = "cache")
public class CacheElement {

	private String key;
	private String value;

    public CacheElement(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public CacheElement() {
    }

    public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
