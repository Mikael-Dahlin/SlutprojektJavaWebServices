package myscv.main;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * Controller that handles the welcome page and errors.
 */
@RestController
public class WelcomeController implements ErrorController {

	@RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public String start() {
		return printHTML.welcomePage();
	}

	private static final String PATH = "/error";

	@RequestMapping(value = PATH, produces = MediaType.TEXT_HTML_VALUE)
	public String error() {
		return printHTML.errorPrint();
	}

	@Override
	public String getErrorPath() {
		
		return PATH;
	}
}
