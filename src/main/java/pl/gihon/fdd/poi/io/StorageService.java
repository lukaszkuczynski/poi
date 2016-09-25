package pl.gihon.fdd.poi.io;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	Resource loadAsResource(String filename);

	int store(MultipartFile file);

}
