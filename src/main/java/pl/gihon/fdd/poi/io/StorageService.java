package pl.gihon.fdd.poi.io;

import java.io.File;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	Resource loadAsResource(String filename);

	File store(MultipartFile file);

}
