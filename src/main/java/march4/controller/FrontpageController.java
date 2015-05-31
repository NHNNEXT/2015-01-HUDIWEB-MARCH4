package march4.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/goggg")
public class FrontpageController {
	private static final Logger log = LoggerFactory
			.getLogger(FrontpageController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(HttpSession session) {
		String sessionEmail = (String) session.getAttribute("email");
		log.debug("user session is ", sessionEmail);
		return sessionEmail;
	}

}