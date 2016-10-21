package pl.gihon.fdd.poi.model;

import java.io.File;
import java.util.Date;

public class EasyFile {

	private String name;
	private String path;
	private Date modified;
	private long size;

	public EasyFile(File f) {
		this.name = f.getName();
		this.modified = new Date(f.lastModified());
		this.path = f.getAbsolutePath();
		this.size = f.length();
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public Date getModified() {
		return modified;
	}

	public long getSize() {
		return size;
	}

}
