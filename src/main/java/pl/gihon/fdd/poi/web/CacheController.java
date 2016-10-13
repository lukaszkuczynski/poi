package pl.gihon.fdd.poi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cache")
public class CacheController {

	@GetMapping
	public String cache() {
		return "cache";
	}

}
