package pl.gihon.fdd.poi.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import pl.gihon.fdd.poi.exception.PoiException;
import pl.gihon.fdd.poi.localisator.google.CacheTool;

@Controller
@RequestMapping("/cache")
public class CacheController {

	private static final RedirectView CACHE_REDIRECT = new RedirectView("/cache");
	private static final Logger logger = LoggerFactory.getLogger(CacheController.class);

	@Autowired
	private CacheTool cacheTool;

	@GetMapping
	public String cache(Model model) {
		model.addAttribute("cacheSize", cacheTool.getCacheSize());
		return "cache";
	}

	@PostMapping("/upload")
	public RedirectView upload(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
		logger.debug("uploading file size {}", file.getSize());
		redirectAttributes.addFlashAttribute("message", "uploaded file size " + file.getSize());
		cacheTool.importToCache(file.getInputStream());
		return CACHE_REDIRECT;
	}

	@GetMapping("/key/{cacheKey}")
	@ResponseBody
	public String getCacheValue(@PathVariable("cacheKey") String key) {
		Optional<String> valueOpt = cacheTool.getByKey(key);
		return valueOpt.orElse("<no value>");
	}

	@PostMapping(value = "/export")
	public void getFile(HttpServletResponse response) {
		try {
			ByteArrayOutputStream baos = cacheTool.export();
			response.getOutputStream().write(baos.toByteArray());
			String filename = "export_cache_" + new Date().toString() + ".csv";
			response.setContentType("text/csv");
			String disposition = "attachment; fileName=" + filename;
			response.setHeader("Content-Disposition", disposition);
			response.flushBuffer();
		} catch (IOException ex) {
			throw new PoiException("IOError writing file to output stream");
		}

	}

}
