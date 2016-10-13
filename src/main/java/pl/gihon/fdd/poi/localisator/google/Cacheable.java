package pl.gihon.fdd.poi.localisator.google;

public interface Cacheable {

	public boolean isInCache(String key);

	public int cacheSize();

	public void clearCache();
}
