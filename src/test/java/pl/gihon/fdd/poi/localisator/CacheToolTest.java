package pl.gihon.fdd.poi.localisator;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import pl.gihon.fdd.poi.localisator.google.CacheTool;

public class CacheToolTest {

	private static Cache testCache = getCache();

	private static Cache getCache() {
		CacheManager cacheManager = CacheManager.getInstance();
		String cacheName = "testcache";
		cacheManager.addCache(cacheName);
		Cache testCache = cacheManager.getCache(cacheName);
		return testCache;
	}

	@Before
	public void setup() {
		testCache.removeAll();
	}

	@Test
	public void tool_canExport() throws IOException {
		CacheTool tool = new CacheTool(testCache);
		testCache.put(new Element("adr1", "{json1=a1, json11=a11}"));
		testCache.put(new Element("adr2", "{json2=a2, json22=a22}"));

		// when
		ByteArrayOutputStream baos = tool.export();

		// then
		String text = baos.toString();
		assertThat(text).contains("\"adr1\";\"{json1=a1, json11=a11}\"");
		assertThat(text).contains("\"adr2\";\"{json2=a2, json22=a22}\"");
	}

	@Test
	public void tool_canImport() throws IOException {
		CacheTool tool = new CacheTool(testCache);
		String oneCsvLine = "\"adr1\";\"{json1=a1, json11=a11}\"";
		String secondCsvLine = "\"adr2\";\"{json2=a2, json22=a22}\"";
		String text = oneCsvLine + "\r\n" + secondCsvLine;
		InputStream stream = new ByteArrayInputStream(text.getBytes());

		// when
		assertThat(testCache.getSize()).isZero();
		tool.importToCache(stream);

		// then
		assertThat(testCache.getSize()).isEqualTo(2);
		assertThat(testCache.get("adr1").getObjectValue()).isEqualTo("{json1=a1, json11=a11}");
		assertThat(testCache.get("adr2").getObjectValue()).isEqualTo("{json2=a2, json22=a22}");
	}

}
