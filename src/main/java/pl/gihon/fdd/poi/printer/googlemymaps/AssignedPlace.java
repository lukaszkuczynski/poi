package pl.gihon.fdd.poi.printer.googlemymaps;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

@JsonIgnoreProperties({ "fullAddress" })
public class AssignedPlace extends LocatedPlace {
	Long areaNr;

	public Long getAreaNr() {
		return areaNr;
	}

	public void setAreaNr(Long areaNr) {
		this.areaNr = areaNr;
	}

	// Jackson, be happy!
	public AssignedPlace() {
		super();
	}

	AssignedPlace(LocatedPlace p, Long areaNr) {
		super(p.getLatitude(), p.getLongitude(), new Place(p.getId(), p.getStreetAndFlat(), p.getCity(), p.getLang1(),
				p.getLang2(), p.getLastMet(), p.getStatus()));
		this.areaNr = areaNr;
	}
}