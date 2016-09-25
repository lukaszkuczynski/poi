package pl.gihon.fdd.poi.io;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import pl.gihon.fdd.poi.exception.StorageFileNotFoundException;
import pl.gihon.fdd.poi.exception.StorageInternalException;

@Component
public class StorageServiceTmpFolder implements StorageService {

	private static Logger logger = LoggerFactory.getLogger(StorageService.class);

	private static String TEMP_FOLDER = System.getProperty("java.io.tmpdir");

	public Object loadAll() {
		logger.info("loading all");
		return null;
	}

	@Override
	public Resource loadAsResource(String filename) {
		logger.info("loading {}", filename);
		String tmpFolderWithSeparator = tmpFolderWithSeparator();
		File file = new File(tmpFolderWithSeparator + filename);
		if (!file.exists()) {
			throw new StorageFileNotFoundException(filename);
		}
		return new FileSystemResource(file);
	}

	@Override
	public int store(MultipartFile file) {
		try {
			logger.debug("storing file {} in temp", file);
			String tmpFolderWithSeparator = tmpFolderWithSeparator();
			File destFile = new File(tmpFolderWithSeparator + file.getOriginalFilename());
			file.transferTo(destFile);
			return (int) file.getSize();
		} catch (IOException e) {
			throw new StorageInternalException(e);
		}

	}

	private static String tmpFolderWithSeparator() {
		String tmpFolderWithSeparator = TEMP_FOLDER;
		if (!StringUtils.endsWithIgnoreCase(TEMP_FOLDER, File.separator)) {
			tmpFolderWithSeparator = tmpFolderWithSeparator + File.separator;
		}
		return tmpFolderWithSeparator;
	}

}
