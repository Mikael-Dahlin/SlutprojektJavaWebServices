package myscv.main;

/*
 * Class that prints the HTML response as a String.
 */
public class printHTML {

	/*
	 * Method that prints the welcome page.
	 */
	public static String welcomePage() {
		String page = "";
		page += "<html><head><title>Welcome to CSV_JSON API</title></head>";
		page += "<body>";
		page += "<h4>This is all the methods of this API</h4>";
		page += "<ul>";
		page += " <li>/</li> ";
		page += " <li>/showCSVListJSON - no parameters</li> ";
		page += " <li>/showColumns - takes a parameter called columns</li> ";
		page += " <li>/listErrorsJSON - no parameters</li> ";
		page += " <li>/sortedCSV - takes a parameter called column</li> ";
		page += " <li>/sortedColumn - takes a parameter called column</li> ";
		page += "</ul>";
		page += "</body></html>";
		return page;
	}
	
	/*
	 * Method that tells you that the requested URL did not work and calls the welcome page.
	 */
	public static String errorPrint() {
		return "No function with that url, please try the following:" + welcomePage();
	}
}
