package pl.gihon.fdd.poi.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import pl.gihon.fdd.poi.filter.ExcludedForFilter;
import pl.gihon.fdd.poi.filter.Filter;
import pl.gihon.fdd.poi.filter.PredicateForFilter;
import pl.gihon.fdd.poi.importer.Importer;
import pl.gihon.fdd.poi.io.StorageService;
import pl.gihon.fdd.poi.localisator.google.GoogleLocalisator;
import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;
import pl.gihon.fdd.poi.printer.googlemymaps.CsvPlacePrinter;
import pl.gihon.fdd.poi.validator.ValidationException;
import pl.gihon.fdd.poi.validator.Validator;

@RequestMapping(MainController.HOME_MAPPING)
@Controller
@SessionAttributes({ "areas", "places", "unassignedPlaces" })
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
	@Autowired
	private Filter filter;
	@Autowired
	private GoogleLocalisator localisator;

	@Autowired
	private List<PredicateForFilter> filters;

	{
		Place place1 = new Place(1, "Wielka 1", "Poznan");
		LocatedPlace pl1 = new LocatedPlace("41", "11", place1);
		placesPredefined.add(pl1);
		Place place2 = new Place(2, "Wielka 23", "Poznan");
		LocatedPlace pl2 = new LocatedPlace("32", "5", place2);
		placesPredefined.add(pl2);
		Place place3 = new Place(3, "Srubowa 3", "Koni");
		LocatedPlace pl3 = new LocatedPlace("45", "14", place3);
		placesPredefined.add(pl3);
		Place place4 = new Place(4, "Szczególna 23", "Dziębieżewo");
		LocatedPlace pl4 = new LocatedPlace("56", "23", place4);
		placesPredefined.add(pl4);
	}

	@ModelAttribute("areas")
	public List<Area> areas() {
		return new ArrayList<>();
	}

	@ModelAttribute("unassignedPlaces")
	public List<LocatedPlace> unassignedPlaces() {
		// return placesPredefined;
		return new ArrayList<>();
	}

	@GetMapping("")
	public ModelAndView main(@ModelAttribute("unassignedPlaces") List<LocatedPlace> unassignedPlaces,
			@ModelAttribute("areas") List<Area> areas, @ModelAttribute("places") List<Place> places) {
		ModelAndView modelAndView = new ModelAndView("main");
		modelAndView.addObject("unassignedPlaces", unassignedPlaces);
		modelAndView.addObject("areas", areas);
		modelAndView.addObject("places", places);
		modelAndView.addObject("apiPart", localisator.getStartOfApiKey());
		modelAndView.addObject("cacheSize", localisator.cacheSize());
		modelAndView.addObject("filters", filters);
		return modelAndView;
	}

	@PostMapping("area")
	public RedirectView addArea(@ModelAttribute("areas") List<Area> areas, @ModelAttribute Area area) {
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
			redirectAttributes.addFlashAttribute("message", msg);
		} catch (ValidationException e) {
			String msg = e.getMessage();
			LOGGER.warn(msg);
			redirectAttributes.addFlashAttribute("message", msg);
			redirectAttributes.addFlashAttribute("errors", e.getErrorMessages());
		}
		return HOME_REDIRECT;
	}

	@PostMapping("filter")
	public RedirectView filter(@ModelAttribute("places") List<Place> places, RedirectAttributes redirectAttributes) {
		List<Place> matching = filter.filter(places);

		String msg = String.format("Places filtered, before %d, after %d", places.size(), matching.size());
		LOGGER.info(msg);
		redirectAttributes.addFlashAttribute("message", msg);

		List<ExcludedForFilter> exclusions = filter.getExclusions();
		redirectAttributes.addFlashAttribute("exclusions", exclusions);

		places.clear();
		places.addAll(matching);

		return HOME_REDIRECT;
	}

	@PostMapping("locate")
	public RedirectView locate(@ModelAttribute("places") List<Place> places,
			@ModelAttribute("unassignedPlaces") List<LocatedPlace> unassignedPlaces,
			RedirectAttributes redirectAttributes) {
		unassignedPlaces.clear();
		unassignedPlaces.addAll(localisator.locate(places));
		String msg = String.format("%d places located, became 'unassigned places'", unassignedPlaces.size());
		LOGGER.info(msg);
		redirectAttributes.addFlashAttribute("message", msg);

		return HOME_REDIRECT;
	}

	@PostMapping("area/assign-place")
	public RedirectView assignPlaceToArea(@ModelAttribute AssignPlaceToAreaForm form,
			@ModelAttribute("unassignedPlaces") List<LocatedPlace> unassignedPlaces,
			@ModelAttribute("areas") List<Area> areas) {
		Optional<Area> areaCandidate = areas.stream().filter(a -> a.getNr() == form.getAreaNr()).findFirst();
		Optional<LocatedPlace> placeCandidate = unassignedPlaces.stream().filter(p -> p.getId() == form.getPlaceId())
				.findFirst();
		// TODO : what if no place or no area
		LocatedPlace place = placeCandidate.get();

		areaCandidate.get().addPlace(place);
		int indexOf = unassignedPlaces.indexOf(place);
		unassignedPlaces.remove(indexOf);
		return HOME_REDIRECT;
	}

	@PostMapping("print/mymaps")
	public RedirectView printToMyMaps(@ModelAttribute("unassignedPlaces") List<LocatedPlace> unassignedPlaces,
			@ModelAttribute("areas") List<Area> areas, RedirectAttributes redirectAttributes) throws IOException {
		String file = Files.createTempFile("mymaps", ".csv").toFile().getAbsolutePath();
		CsvPlacePrinter myMapsPrinter = new CsvPlacePrinter(file);
		myMapsPrinter.printWithUnassigned(areas, unassignedPlaces);
		String msg = String.format("%d areas and %d unassigned places printed to file at %s", areas.size(),
				unassignedPlaces.size(), file);
		LOGGER.info(msg);
		redirectAttributes.addFlashAttribute("message", msg);
		return HOME_REDIRECT;
	}

}
