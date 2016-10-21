package pl.gihon.fdd.poi.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.model.EasyFile;

@Component
public class FolderService {

	public List<EasyFile> loadFromTemp() {
		File tempFolder = new File(System.getProperty("java.io.tmpdir"));
		File[] filesArray = tempFolder.listFiles();
		ArrayList<File> filesList = Lists.newArrayList(filesArray);
		return filesList.stream().map(f -> new EasyFile(f)).collect(Collectors.toList());
	}

	public List<EasyFile> loadFromTempFiltered(String... acceptedNames) {
		File tempFolder = new File(System.getProperty("java.io.tmpdir"));
		File[] filesArray = tempFolder.listFiles(f -> StringUtils.indexOfAny(f.getName(), acceptedNames) != -1);
		ArrayList<File> filesList = Lists.newArrayList(filesArray);
		return filesList.stream().map(f -> new EasyFile(f)).collect(Collectors.toList());
	}

}
