package pl.gihon.fdd.poi.importer;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.gihon.fdd.poi.exception.PoiException;
import pl.gihon.fdd.poi.model.LocatedPlace;
import pl.gihon.fdd.poi.model.Place;
import pl.gihon.fdd.poi.printer.oldpythonprinter.PyPlace;

@Component
public class LocatedPlacesReaderJson {

	public List<LocatedPlace> readPlaces(File file) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		MappingIterator<PyPlace> values = mapper.readerFor(PyPlace.class).readValues(file);
		List<PyPlace> pyPlaces = values.readAll();
		List<LocatedPlace> places = pyPlaces.stream().map(pp -> toLocated(pp)).collect(Collectors.toList());
		return places;
	}

	private LocatedPlace toLocated(PyPlace pp) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String lastmet = pp.getLastmet();
			Date lastMetDate = null;
			if (!StringUtils.isEmpty(lastmet)) {
				lastMetDate = sdf.parse(pp.getLastmet());
			}
			return new LocatedPlace(pp.getLatitude(), pp.getLongitude(), new Place(pp.getId(), pp.getStreet(),
					pp.getCity(), pp.getLang1(), pp.getLang2(), lastMetDate, pp.getStatus()));
		} catch (ParseException e) {
			throw new PoiException(e);
		}
	}

}
