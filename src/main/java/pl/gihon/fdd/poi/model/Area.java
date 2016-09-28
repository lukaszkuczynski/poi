package pl.gihon.fdd.poi.model;

import java.util.ArrayList;
import java.util.List;

public class Area {

	private long id;
	private String name;
	private List<LocatedPlace> places = new ArrayList<>();

	public Area(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Area() {
		super();
	}

	public int getPlaceCount() {
		return places.size();
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<LocatedPlace> getPlaces() {
		return places;
	}

}
