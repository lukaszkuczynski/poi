package pl.gihon.fdd.poi.web;

public class SavedFile {

	private String name;
	private String link;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public SavedFile(String name, String link) {
		super();
		this.name = name;
		this.link = link;
	}

}
