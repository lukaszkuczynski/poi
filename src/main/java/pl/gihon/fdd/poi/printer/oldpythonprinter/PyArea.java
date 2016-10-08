package pl.gihon.fdd.poi.printer.oldpythonprinter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import pl.gihon.fdd.poi.model.Area;

public class PyArea {

	private long nr;
	private String name;
	private String district;
	private List<Long> place_ids;

	public PyArea(Area area) {
		this.nr = area.getNr();
		this.name = area.getName();
		List<Long> placeIdsFromArea = area.getPlaces().stream().map(p -> p.getId()).collect(Collectors.toList());
		this.place_ids = new ArrayList<>();
		this.place_ids.addAll(placeIdsFromArea);
		this.district = "";
	}

	public long getNr() {
		return nr;
	}

	public String getName() {
		return name;
	}

	public List<Long> getPlace_ids() {
		return place_ids;
	}

	public String getDistrict() {
		return district;
	}

}
