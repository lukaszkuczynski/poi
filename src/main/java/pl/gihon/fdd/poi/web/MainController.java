package pl.gihon.fdd.poi.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import pl.gihon.fdd.poi.importer.Importer;
import pl.gihon.fdd.poi.io.StorageService;
import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;
import pl.gihon.fdd.poi.validator.ValidationException;
import pl.gihon.fdd.poi.validator.Validator;

@RequestMapping(MainController.HOME_MAPPING)
@Controller
@SessionAttributes({ "areas", "locatedPlaces", "places" })
public class MainController {
	// TODO remove later on
	static public List<LocatedPlace> placesPredefined = new ArrayList<>();

	@ModelAttribute("places")
	private List<Place> places() {
		return new ArrayList<>();
	}

	private static Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	static final String HOME_MAPPING = "/";
	private static RedirectView HOME_REDIRECT = new RedirectView(HOME_MAPPING);

	@Autowired
	private StorageService storage;
	@Autowired
	private Importer importer;
	@Autowired
	private Validator validator;

	{
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
		return placesPredefined;
	}

	@GetMapping("")
	public ModelAndView main(@ModelAttribute("locatedPlaces") List<LocatedPlace> locatedPlaces,
			@ModelAttribute("areas") List<Area> areas, @ModelAttribute("places") List<Place> places) {
		ModelAndView modelAndView = new ModelAndView("main");
		modelAndView.addObject("locatedPlaces", locatedPlaces);
		modelAndView.addObject("areas", areas);
		modelAndView.addObject("places", places);
		return modelAndView;
	}

	@PostMapping("area")
	public RedirectView addArea(@ModelAttribute("areas") List<Area> areas, @ModelAttribute Area area) {
		// TODO : form mapping doesnt work
		areas.add(area);
		return HOME_REDIRECT;
	}

	@PostMapping("csv_file")
	public RedirectView handleFileUpload(@RequestParam("file") MultipartFile file,
			@ModelAttribute("places") List<Place> places, RedirectAttributes redirectAttributes) {
		String msg = "You successfully uploaded " + file.getOriginalFilename() + ", uploaded size " + file.getSize();
		LOGGER.info(msg);
		redirectAttributes.addFlashAttribute("message", msg);
		File storedFile = storage.store(file);
		List<Place> placesImported = importer.importPlaces(storedFile);
		places.clear();
		places.addAll(placesImported);
		return HOME_REDIRECT;
	}

	@PostMapping("validate")
	public RedirectView validate(@ModelAttribute("places") List<Place> places, RedirectAttributes redirectAttributes) {
		try {
			validator.validate(places);
			String msg = "validation successful";
			LOGGER.info(msg);
		} catch (ValidationException e) {
			String msg = e.getMessage();
			LOGGER.warn(msg);
			redirectAttributes.addFlashAttribute("message", msg);
			redirectAttributes.addFlashAttribute("errors", e.getErrorMessages());
		}
		return HOME_REDIRECT;
	}

}
