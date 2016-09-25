package pl.gihon.fdd.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import pl.gihon.fdd.exception.StorageInternalException;

@Component
public class StorageService {

	private static Logger logger = LoggerFactory.getLogger(StorageService.class);

	public Object loadAll() {
		logger.info("loading all");
		return null;
	}

	public Resource loadAsResource(String filename) {
		logger.info("loading {}", filename);
		return null;
	}

	public int store(MultipartFile file) {
		try {
			InputStream is = file.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtils.copy(is, baos);

			logger.info("storing file");
			String content = baos.toString();
			logger.debug("file content as follows");
			logger.debug("{}", content);
			return content.length();
		} catch (IOException e) {
			throw new StorageInternalException(e);
		}

	}

}
