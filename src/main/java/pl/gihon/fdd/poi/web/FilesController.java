package pl.gihon.fdd.poi.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.gihon.fdd.poi.io.FolderService;
import pl.gihon.fdd.poi.model.EasyFile;

@Controller
public class FilesController {

	private static Logger logger = LoggerFactory.getLogger(FilesController.class);

	@Autowired
	private FolderService folderService;

	@GetMapping("/files")
	public String files() {
		logger.info("entering files view");
		return "files";
	}

	@ResponseBody
	@GetMapping("/files/api")
	public List<EasyFile> getFiles() {
		List<EasyFile> files = folderService.loadFromTempFiltered("areas", "pyplaces", "mymaps");
		files.sort((ef1, ef2) -> ef2.getModified().compareTo(ef1.getModified()));
		return files;
	}

}
