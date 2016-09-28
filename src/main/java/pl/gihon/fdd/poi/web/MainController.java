package pl.gihon.fdd.poi.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;

@RequestMapping("/")
@Controller
@SessionAttributes({ "areas", "locatedPlaces" })
public class MainController {
	// TODO remove later on
	static public List<LocatedPlace> placesPredefined = new ArrayList<>();

	static {
		Place place1 = new Place(1, "Wielka 1", "Poznan");
		LocatedPlace pl1 = new LocatedPlace("41", "11", place1);
		placesPredefined.add(pl1);
		Place place2 = new Place(1, "Wielka 23", "Poznan");
		LocatedPlace pl2 = new LocatedPlace("32", "5", place2);
		placesPredefined.add(pl2);
		Place place3 = new Place(1, "Srubowa 3", "Koni");
		LocatedPlace pl3 = new LocatedPlace("45", "14", place3);
		placesPredefined.add(pl3);
		Place place4 = new Place(1, "Szczególna 23", "Dziębieżewo");
		LocatedPlace pl4 = new LocatedPlace("56", "23", place4);
		placesPredefined.add(pl4);
	}

	@ModelAttribute("areas")
	public List<Area> areas() {
		return new ArrayList<>();
	}

	@ModelAttribute("locatedPlaces")
	public List<LocatedPlace> locatedPlaces() {
		// return new ArrayList<>();
		return placesPredefined;
	}

	@GetMapping("")
	public ModelAndView main(@ModelAttribute("locatedPlaces") List<LocatedPlace> locatedPlaces) {
		ModelAndView modelAndView = new ModelAndView("main");
		modelAndView.addObject("locatedPlaces", locatedPlaces);
		return modelAndView;
	}

}
