package myscv.main;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
 * Main controller for this API.
 */
@RestController
public class CsvController {

	@RequestMapping(value = "/showColumns", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String showColumns(String[] columns) {
		return readCSV.getColumns(columns, "none");
	}
	
	@RequestMapping(value = "/showCSVListJSON", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String showCSVListJSON() {
		return readCSV.getWholeSheet();
	}
	
	@RequestMapping(value = "/listErrorsJSON", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String listErrorsJSON() {
		return readCSV.getErrorsSheet();
	}
	
	@RequestMapping(value = "/sortedCSV", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String sortedCSV(String column) {
		return readCSV.sortedCSV(column, "all");
	}
	
	@RequestMapping(value = "/sortedColumn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String sortedColumn(String column) {
		return readCSV.sortedCSV(column, column);
	}

}
