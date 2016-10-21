package pl.gihon.fdd.poi.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.google.common.collect.Lists;

import pl.gihon.fdd.poi.filter.ExcludedForFilter;
import pl.gihon.fdd.poi.filter.Filter;
import pl.gihon.fdd.poi.filter.PredicateForFilter;
import pl.gihon.fdd.poi.importer.AreasImporter;
import pl.gihon.fdd.poi.importer.LocatedPlacesImporterCsv;
import pl.gihon.fdd.poi.importer.LocatedPlacesReaderJson;
import pl.gihon.fdd.poi.importer.PlacesImporter;
import pl.gihon.fdd.poi.io.StorageService;
import pl.gihon.fdd.poi.localisator.google.GoogleLocalisator;
import pl.gihon.fdd.poi.model.Area;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;
import pl.gihon.fdd.poi.printer.googlemymaps.CsvPlacePrinter;
import pl.gihon.fdd.poi.printer.oldpythonprinter.PyJsonPrinter;
import pl.gihon.fdd.poi.validator.ValidationException;
import pl.gihon.fdd.poi.validator.Validator;

@RequestMapping(MainController.HOME_MAPPING)
@Controller
@SessionAttributes({ "areas", "places", "unassignedPlaces" })
public class MainController {

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
	private PlacesImporter importer;
	@Autowired
	private Validator validator;
	@Autowired
	private Filter filter;
	@Autowired
	private GoogleLocalisator localisator;
	@Autowired
	private LocatedPlacesImporterCsv locatedPlacesImporter;
	@Autowired
	private AreasImporter areasImporter;
	@Autowired
	private List<PredicateForFilter> filters;
	@Autowired
	private LocatedPlacesReaderJson placesJsonReader;

	@ModelAttribute("areas")
	public List<Area> areas() {
		List<Area> areas = Lists.newArrayList();
		Area area1 = new Area(1, "area1");
		area1.addPlace(unassignedPlaces().get(0));
		unassignedPlaces().remove(0);
		areas.add(area1);
		return areas;
	}

	@ModelAttribute("unassignedPlaces")
	public List<LocatedPlace> unassignedPlaces() {
		List<LocatedPlace> placesPredefined = new ArrayList<>();
		placesPredefined.add(new LocatedPlace("lat", "lng", new Place(1L, "st1", "ci1", "", "", null, "")));
		placesPredefined.add(new LocatedPlace("lat", "lng", new Place(2L, "st2", "ci2", "", "", null, "")));
		placesPredefined.add(new LocatedPlace("lat", "lng", new Place(3L, "st3", "ci3", "", "", null, "")));
		placesPredefined.add(new LocatedPlace("lat", "lng", new Place(4L, "st4", "ci4", "", "", null, "")));
		return placesPredefined;
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
		for (int id : form.getPlaceIdNumbers()) {
			Optional<LocatedPlace> placeCandidate = unassignedPlaces.stream().filter(p -> p.getId() == id).findFirst();
			// TODO : what if no place or no area
			LocatedPlace place = placeCandidate.get();

			areaCandidate.get().addPlace(place);
			int indexOf = unassignedPlaces.indexOf(place);
			unassignedPlaces.remove(indexOf);
		}
		return HOME_REDIRECT;
	}

	@PostMapping("print/mymaps")
	public RedirectView printToMyMaps(@ModelAttribute("unassignedPlaces") List<LocatedPlace> unassignedPlaces,
			@ModelAttribute("areas") List<Area> areas, RedirectAttributes redirectAttributes) throws IOException {
		File file = Files.createTempFile("mymaps", ".csv").toFile();
		String filePath = file.getAbsolutePath();
		List<SavedFile> savedFiles = Lists.newArrayList(new SavedFile(file.getName(), link(file.getName())));
		CsvPlacePrinter myMapsPrinter = new CsvPlacePrinter(filePath);
		myMapsPrinter.printWithUnassigned(areas, unassignedPlaces);
		String msg = String.format("%d areas and %d unassigned places printed to file at %s", areas.size(),
				unassignedPlaces.size(), file);
		LOGGER.info(msg);
		redirectAttributes.addFlashAttribute("message", msg);
		redirectAttributes.addFlashAttribute("savedFiles", savedFiles);
		return HOME_REDIRECT;
	}

	private String link(String name) {
		return "/files/" + name;
	}

	@PostMapping("print/areas")
	public RedirectView printAreas(@ModelAttribute("areas") List<Area> areas, RedirectAttributes redirectAttributes)
			throws IOException {
		File areasFile = Files.createTempFile("areas", ".json").toFile();
		File placesFile = Files.createTempFile("pyplaces", ".json").toFile();

		List<SavedFile> savedFiles = Lists.newArrayList(new SavedFile(areasFile.getName(), link(areasFile.getName())),
				new SavedFile(placesFile.getName(), link(placesFile.getName())));

		PyJsonPrinter printer = new PyJsonPrinter(areasFile, placesFile);
		printer.print(areas);

		String msg = String.format("%d areas printed, areas file %s, places file %s", areas.size(), areasFile,
				placesFile);
		LOGGER.info(msg);
		redirectAttributes.addFlashAttribute("message", msg);
		redirectAttributes.addFlashAttribute("savedFiles", savedFiles);

		return HOME_REDIRECT;
	}

	@PostMapping("upload/mymaps")
	public RedirectView uploadFromMyMaps(@ModelAttribute("unassignedPlaces") List<LocatedPlace> unassignedPlaces,
			RedirectAttributes redirectAttributes, MultipartFile file) throws IOException {

		File tempUploaded = Files.createTempFile("uploaded_mymaps", ".csv").toFile();
		file.transferTo(tempUploaded);
		List<LocatedPlace> readUnassignedPlaces = locatedPlacesImporter.importUnassignedPlaces(tempUploaded);
		unassignedPlaces.clear();
		unassignedPlaces.addAll(readUnassignedPlaces);
		String msg = String.format("From uploaded file I have read %d unassigned places, path %s",
				readUnassignedPlaces.size(), tempUploaded.getAbsolutePath());
		LOGGER.info(msg);
		redirectAttributes.addFlashAttribute("message", msg);
		return HOME_REDIRECT;
	}

	@PostMapping("upload/py")
	public RedirectView uploadAreas(@ModelAttribute("areas") List<Area> areas,
			@ModelAttribute("unassignedPlaces") List<LocatedPlace> unassignedPlaces,
			RedirectAttributes redirectAttributes, MultipartFile areasFile, MultipartFile placesFile)
					throws IOException {

		File fileTempAreas = Files.createTempFile("uploaded_pyareas", ".json").toFile();
		areasFile.transferTo(fileTempAreas);
		File fileTempPlaces = Files.createTempFile("uploaded_pyplaces", ".json").toFile();
		placesFile.transferTo(fileTempPlaces);

		areas.clear();
		List<LocatedPlace> places = placesJsonReader.readPlaces(fileTempPlaces);
		unassignedPlaces.clear();
		unassignedPlaces.addAll(places);
		List<Area> areasImported = areasImporter.importAreas(fileTempAreas, unassignedPlaces);

		List<Long> importedAssignedPlacesIds = new ArrayList<>();
		for (Area area : areasImported) {
			importedAssignedPlacesIds
					.addAll(area.getPlaces().stream().map(LocatedPlace::getId).collect(Collectors.toList()));
		}
		List<LocatedPlace> placesUnassignedAfterImport = unassignedPlaces.stream()
				.filter(lp -> !importedAssignedPlacesIds.contains(lp.getId())).collect(Collectors.toList());
		areas.addAll(areasImported);
		int unassignedBefore = unassignedPlaces.size();
		unassignedPlaces.clear();
		unassignedPlaces.addAll(placesUnassignedAfterImport);
		int unassignedAfter = unassignedPlaces.size();
		String msg = String.format(
				"From uploaded file I have read %d areas, unassigned places before %d after %d, temp file is at path %s",
				areasImported.size(), unassignedBefore, unassignedAfter, fileTempAreas.getAbsolutePath());
		LOGGER.info(msg);
		redirectAttributes.addFlashAttribute("message", msg);
		return HOME_REDIRECT;
	}

}
