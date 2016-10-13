package pl.gihon.fdd.poi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

@Configuration
public class CacheConfig {

	private static final String LOCATIONS_CACHE_NAME = "LOCATIONS_EH_CACHE";

	@Bean
	public CacheManager cacheManager() {
		CacheManager cacheManager = CacheManager.create();
		cacheManager.addCache(LOCATIONS_CACHE_NAME);
		return cacheManager;
	}

	@Bean
	public Cache locationCache() {
		return cacheManager().getCache(LOCATIONS_CACHE_NAME);
	}

}
