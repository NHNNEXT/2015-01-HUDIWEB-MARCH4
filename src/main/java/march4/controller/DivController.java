package march4.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/", method = RequestMethod.GET)
public class DivController {
	private static final Logger log = LoggerFactory
			.getLogger(DivController.class);

	@RequestMapping(value = { "", "/", "/world/**", "/building/**", "/users/**",
			"/dummy/**", "/projects/**", "/signup/**", "/signin/**", "/signout/**", "/errorPage/**" })
	public String index() {
		log.debug("index requested.");
		return "index";
	}

	@RequestMapping(value = "/div/{divId}")
	public String div(@PathVariable("divId") String divId) {
		log.debug("div '" + divId + "' requested.");
		return "div/" + divId;

	}
}
